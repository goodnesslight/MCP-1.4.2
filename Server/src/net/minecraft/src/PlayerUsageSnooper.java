package net.minecraft.src;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.UUID;

public class PlayerUsageSnooper
{
    /** String map for report data */
    private Map dataMap = new HashMap();
    private final String uniqueID = UUID.randomUUID().toString();

    /** URL of the server to send the report to */
    private final URL serverUrl;
    private final IPlayerUsage playerStatsCollector;

    /** set to fire the snooperThread every 15 mins */
    private final Timer threadTrigger = new Timer("Snooper Timer", true);
    private final Object syncLock = new Object();
    private boolean isRunning = false;

    /** incremented on every getSelfCounterFor */
    private int selfCounter = 0;

    public PlayerUsageSnooper(String par1Str, IPlayerUsage par2IPlayerUsage)
    {
        try
        {
            this.serverUrl = new URL("http://snoop.minecraft.net/" + par1Str + "?version=" + 1);
        }
        catch (MalformedURLException var4)
        {
            throw new IllegalArgumentException();
        }

        this.playerStatsCollector = par2IPlayerUsage;
    }

    /**
     * Note issuing start multiple times is not an error.
     */
    public void startSnooper()
    {
        if (!this.isRunning)
        {
            this.isRunning = true;
            this.addBaseDataToSnooper();
            this.threadTrigger.schedule(new PlayerUsageSnooperThread(this), 0L, 900000L);
        }
    }

    private void addBaseDataToSnooper()
    {
        this.addJvmArgsToSnooper();
        this.addData("snooper_token", this.uniqueID);
        this.addData("os_name", System.getProperty("os.name"));
        this.addData("os_version", System.getProperty("os.version"));
        this.addData("os_architecture", System.getProperty("os.arch"));
        this.addData("java_version", System.getProperty("java.version"));
        this.addData("version", "1.4.2");
        this.playerStatsCollector.addServerTypeToSnooper(this);
    }

    private void addJvmArgsToSnooper()
    {
        RuntimeMXBean var1 = ManagementFactory.getRuntimeMXBean();
        List var2 = var1.getInputArguments();
        int var3 = 0;
        Iterator var4 = var2.iterator();

        while (var4.hasNext())
        {
            String var5 = (String)var4.next();

            if (var5.startsWith("-X"))
            {
                this.addData("jvm_arg[" + var3++ + "]", var5);
            }
        }

        this.addData("jvm_args", Integer.valueOf(var3));
    }

    public void addMemoryStatsToSnooper()
    {
        this.addData("memory_total", Long.valueOf(Runtime.getRuntime().totalMemory()));
        this.addData("memory_max", Long.valueOf(Runtime.getRuntime().maxMemory()));
        this.addData("memory_free", Long.valueOf(Runtime.getRuntime().freeMemory()));
        this.addData("cpu_cores", Integer.valueOf(Runtime.getRuntime().availableProcessors()));
        this.playerStatsCollector.addServerStatsToSnooper(this);
    }

    /**
     * Adds information to the report
     */
    public void addData(String par1Str, Object par2Obj)
    {
        Object var3 = this.syncLock;

        synchronized (this.syncLock)
        {
            this.dataMap.put(par1Str, par2Obj);
        }
    }

    public boolean isSnooperRunning()
    {
        return this.isRunning;
    }

    public void stopSnooper()
    {
        this.threadTrigger.cancel();
    }

    static IPlayerUsage getStatsCollectorFor(PlayerUsageSnooper par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.playerStatsCollector;
    }

    static Object getSyncLockFor(PlayerUsageSnooper par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.syncLock;
    }

    static Map getDataMapFor(PlayerUsageSnooper par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.dataMap;
    }

    /**
     * returns a value indicating how many times this function has been run on the snooper
     */
    static int getSelfCounterFor(PlayerUsageSnooper par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.selfCounter++;
    }

    static URL getServerUrlFor(PlayerUsageSnooper par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.serverUrl;
    }
}
