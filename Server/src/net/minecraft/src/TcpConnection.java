package net.minecraft.src;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.crypto.SecretKey;

public class TcpConnection implements INetworkManager
{
    public static AtomicInteger field_74471_a = new AtomicInteger();
    public static AtomicInteger field_74469_b = new AtomicInteger();

    /** The object used for synchronization on the send queue. */
    private Object sendQueueLock = new Object();

    /** The socket used by this network manager. */
    private Socket networkSocket;

    /** The InetSocketAddress of the remote endpoint */
    private final SocketAddress remoteSocketAddress;

    /** The input stream connected to the socket. */
    private volatile DataInputStream socketInputStream;

    /** The output stream connected to the socket. */
    private volatile DataOutputStream socketOutputStream;

    /** Whether the network is currently operational. */
    private volatile boolean isRunning = true;

    /**
     * Whether this network manager is currently terminating (and should ignore further errors).
     */
    private volatile boolean isTerminating = false;

    /**
     * Linked list of packets that have been read and are awaiting processing.
     */
    private List readPackets = Collections.synchronizedList(new ArrayList());

    /** Linked list of packets awaiting sending. */
    private List dataPackets = Collections.synchronizedList(new ArrayList());

    /** Linked list of packets with chunk data that are awaiting sending. */
    private List chunkDataPackets = Collections.synchronizedList(new ArrayList());

    /** A reference to the NetHandler object. */
    private NetHandler theNetHandler;

    /**
     * Whether this server is currently terminating. If this is a client, this is always false.
     */
    private boolean isServerTerminating = false;

    /** The thread used for writing. */
    private Thread writeThread;

    /** The thread used for reading. */
    private Thread readThread;

    /** A String indicating why the network has shutdown. */
    private String terminationReason = "";
    private Object[] field_74480_w;
    private int field_74490_x = 0;

    /**
     * The length in bytes of the packets in both send queues (data and chunkData).
     */
    private int sendQueueByteLength = 0;
    public static int[] field_74470_c = new int[256];
    public static int[] field_74467_d = new int[256];
    public int field_74468_e = 0;
    boolean isInputBeingDecrypted = false;
    boolean isOutputEncrypted = false;
    private SecretKey sharedKeyForEncryption = null;
    private PrivateKey field_74463_A = null;
    private int field_74464_B = 50;

    public TcpConnection(Socket par1Socket, String par2Str, NetHandler par3NetHandler, PrivateKey par4PrivateKey) throws IOException
    {
        this.field_74463_A = par4PrivateKey;
        this.networkSocket = par1Socket;
        this.remoteSocketAddress = par1Socket.getRemoteSocketAddress();
        this.theNetHandler = par3NetHandler;

        try
        {
            par1Socket.setSoTimeout(30000);
            par1Socket.setTrafficClass(24);
        }
        catch (SocketException var6)
        {
            System.err.println(var6.getMessage());
        }

        this.socketInputStream = new DataInputStream(par1Socket.getInputStream());
        this.socketOutputStream = new DataOutputStream(new BufferedOutputStream(par1Socket.getOutputStream(), 5120));
        this.readThread = new TcpReaderThread(this, par2Str + " read thread");
        this.writeThread = new TcpWriterThread(this, par2Str + " write thread");
        this.readThread.start();
        this.writeThread.start();
    }

    /**
     * Sets the NetHandler for this NetworkManager. Server-only.
     */
    public void setNetHandler(NetHandler par1NetHandler)
    {
        this.theNetHandler = par1NetHandler;
    }

    /**
     * Adds the packet to the correct send queue (chunk data packets go to a separate queue).
     */
    public void addToSendQueue(Packet par1Packet)
    {
        if (!this.isServerTerminating)
        {
            Object var2 = this.sendQueueLock;

            synchronized (this.sendQueueLock)
            {
                this.sendQueueByteLength += par1Packet.getPacketSize() + 1;

                if (par1Packet.isChunkDataPacket)
                {
                    this.chunkDataPackets.add(par1Packet);
                }
                else
                {
                    this.dataPackets.add(par1Packet);
                }
            }
        }
    }

    /**
     * Sends a data packet if there is one to send, or sends a chunk data packet if there is one and the counter is up,
     * or does nothing.
     */
    private boolean sendPacket()
    {
        boolean var1 = false;

        try
        {
            Packet var2;
            int var10001;
            int[] var10000;

            if (this.field_74468_e == 0 || System.currentTimeMillis() - ((Packet)this.dataPackets.get(0)).creationTimeMillis >= (long)this.field_74468_e)
            {
                var2 = this.func_74460_a(false);

                if (var2 != null)
                {
                    Packet.writePacket(var2, this.socketOutputStream);

                    if (var2 instanceof Packet252SharedKey && !this.isOutputEncrypted)
                    {
                        if (!this.theNetHandler.isServerHandler())
                        {
                            this.sharedKeyForEncryption = ((Packet252SharedKey)var2).func_73304_d();
                        }

                        this.encryptOuputStream();
                    }

                    var10000 = field_74467_d;
                    var10001 = var2.getPacketId();
                    var10000[var10001] += var2.getPacketSize() + 1;
                    var1 = true;
                }
            }

            if (this.field_74464_B-- <= 0 && (this.field_74468_e == 0 || System.currentTimeMillis() - ((Packet)this.chunkDataPackets.get(0)).creationTimeMillis >= (long)this.field_74468_e))
            {
                var2 = this.func_74460_a(true);

                if (var2 != null)
                {
                    Packet.writePacket(var2, this.socketOutputStream);
                    var10000 = field_74467_d;
                    var10001 = var2.getPacketId();
                    var10000[var10001] += var2.getPacketSize() + 1;
                    this.field_74464_B = 0;
                    var1 = true;
                }
            }

            return var1;
        }
        catch (Exception var3)
        {
            if (!this.isTerminating)
            {
                this.onNetworkError(var3);
            }

            return false;
        }
    }

    private Packet func_74460_a(boolean par1)
    {
        Packet var2 = null;
        List var3 = par1 ? this.chunkDataPackets : this.dataPackets;
        Object var4 = this.sendQueueLock;

        synchronized (this.sendQueueLock)
        {
            while (!var3.isEmpty() && var2 == null)
            {
                var2 = (Packet)var3.remove(0);
                this.sendQueueByteLength -= var2.getPacketSize() + 1;

                if (this.func_74454_a(var2, par1))
                {
                    var2 = null;
                }
            }

            return var2;
        }
    }

    private boolean func_74454_a(Packet par1Packet, boolean par2)
    {
        if (!par1Packet.isRealPacket())
        {
            return false;
        }
        else
        {
            List var3 = par2 ? this.chunkDataPackets : this.dataPackets;
            Iterator var4 = var3.iterator();
            Packet var5;

            do
            {
                if (!var4.hasNext())
                {
                    return false;
                }

                var5 = (Packet)var4.next();
            }
            while (var5.getPacketId() != par1Packet.getPacketId());

            return par1Packet.containsSameEntityIDAs(var5);
        }
    }

    /**
     * Wakes reader and writer threads
     */
    public void wakeThreads()
    {
        if (this.readThread != null)
        {
            this.readThread.interrupt();
        }

        if (this.writeThread != null)
        {
            this.writeThread.interrupt();
        }
    }

    /**
     * Reads a single packet from the input stream and adds it to the read queue. If no packet is read, it shuts down
     * the network.
     */
    private boolean readPacket()
    {
        boolean var1 = false;

        try
        {
            Packet var2 = Packet.readPacket(this.socketInputStream, this.theNetHandler.isServerHandler(), this.networkSocket);

            if (var2 != null)
            {
                if (var2 instanceof Packet252SharedKey && !this.isInputBeingDecrypted)
                {
                    if (this.theNetHandler.isServerHandler())
                    {
                        this.sharedKeyForEncryption = ((Packet252SharedKey)var2).func_73303_a(this.field_74463_A);
                    }

                    this.decryptInputStream();
                }

                int[] var10000 = field_74470_c;
                int var10001 = var2.getPacketId();
                var10000[var10001] += var2.getPacketSize() + 1;

                if (!this.isServerTerminating)
                {
                    if (var2.isWritePacket() && this.theNetHandler.canProcessPackets())
                    {
                        this.field_74490_x = 0;
                        var2.processPacket(this.theNetHandler);
                    }
                    else
                    {
                        this.readPackets.add(var2);
                    }
                }

                var1 = true;
            }
            else
            {
                this.networkShutdown("disconnect.endOfStream", new Object[0]);
            }

            return var1;
        }
        catch (Exception var3)
        {
            if (!this.isTerminating)
            {
                this.onNetworkError(var3);
            }

            return false;
        }
    }

    /**
     * Used to report network errors and causes a network shutdown.
     */
    private void onNetworkError(Exception par1Exception)
    {
        par1Exception.printStackTrace();
        this.networkShutdown("disconnect.genericReason", new Object[] {"Internal exception: " + par1Exception.toString()});
    }

    /**
     * Shuts down the network with the specified reason. Closes all streams and sockets, spawns NetworkMasterThread to
     * stop reading and writing threads.
     */
    public void networkShutdown(String par1Str, Object ... par2ArrayOfObj)
    {
        if (this.isRunning)
        {
            this.isTerminating = true;
            this.terminationReason = par1Str;
            this.field_74480_w = par2ArrayOfObj;
            this.isRunning = false;
            (new TcpMasterThread(this)).start();

            try
            {
                this.socketInputStream.close();
                this.socketInputStream = null;
                this.socketOutputStream.close();
                this.socketOutputStream = null;
                this.networkSocket.close();
                this.networkSocket = null;
            }
            catch (Throwable var4)
            {
                ;
            }
        }
    }

    /**
     * Checks timeouts and processes all pending read packets.
     */
    public void processReadPackets()
    {
        if (this.sendQueueByteLength > 2097152)
        {
            this.networkShutdown("disconnect.overflow", new Object[0]);
        }

        if (this.readPackets.isEmpty())
        {
            if (this.field_74490_x++ == 1200)
            {
                this.networkShutdown("disconnect.timeout", new Object[0]);
            }
        }
        else
        {
            this.field_74490_x = 0;
        }

        int var1 = 1000;

        while (!this.readPackets.isEmpty() && var1-- >= 0)
        {
            Packet var2 = (Packet)this.readPackets.remove(0);
            var2.processPacket(this.theNetHandler);
        }

        this.wakeThreads();

        if (this.isTerminating && this.readPackets.isEmpty())
        {
            this.theNetHandler.handleErrorMessage(this.terminationReason, this.field_74480_w);
        }
    }

    /**
     * Returns the socket address of the remote side. Server-only.
     */
    public SocketAddress getRemoteAddress()
    {
        return this.remoteSocketAddress;
    }

    /**
     * Shuts down the server. (Only actually used on the server)
     */
    public void serverShutdown()
    {
        if (!this.isServerTerminating)
        {
            this.wakeThreads();
            this.isServerTerminating = true;
            this.readThread.interrupt();
            (new TcpMonitorThread(this)).start();
        }
    }

    private void decryptInputStream() throws IOException
    {
        this.isInputBeingDecrypted = true;
        this.socketInputStream = new DataInputStream(CryptManager.decryptInputStream(this.sharedKeyForEncryption, this.networkSocket.getInputStream()));
    }

    /**
     * flushes the stream and replaces it with an encryptedOutputStream
     */
    private void encryptOuputStream() throws IOException
    {
        this.socketOutputStream.flush();
        this.isOutputEncrypted = true;
        this.socketOutputStream = new DataOutputStream(new BufferedOutputStream(CryptManager.encryptOuputStream(this.sharedKeyForEncryption, this.networkSocket.getOutputStream()), 5120));
    }

    /**
     * Returns the number of chunk data packets waiting to be sent.
     */
    public int getNumChunkDataPackets()
    {
        return this.chunkDataPackets.size();
    }

    public Socket getSocket()
    {
        return this.networkSocket;
    }

    /**
     * Whether the network is operational.
     */
    static boolean isRunning(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.isRunning;
    }

    /**
     * Is the server terminating? Client side aways returns false.
     */
    static boolean isServerTerminating(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.isServerTerminating;
    }

    /**
     * Static accessor to readPacket.
     */
    static boolean readNetworkPacket(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.readPacket();
    }

    /**
     * Static accessor to sendPacket.
     */
    static boolean sendNetworkPacket(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.sendPacket();
    }

    static DataOutputStream getOutputStream(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.socketOutputStream;
    }

    /**
     * Gets whether the Network manager is terminating.
     */
    static boolean isTerminating(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.isTerminating;
    }

    /**
     * Sends the network manager an error
     */
    static void sendError(TcpConnection par0TcpConnection, Exception par1Exception)
    {
        par0TcpConnection.onNetworkError(par1Exception);
    }

    /**
     * Returns the read thread.
     */
    static Thread getReadThread(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.readThread;
    }

    /**
     * Returns the write thread.
     */
    static Thread getWriteThread(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.writeThread;
    }
}
