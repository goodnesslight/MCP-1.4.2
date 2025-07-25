package net.minecraft.client;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import javax.swing.JPanel;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.MinecraftApplet;
import net.minecraft.src.AchievementList;
import net.minecraft.src.AnvilSaveConverter;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.CallableClientMemoryStats;
import net.minecraft.src.CallableClientProfiler;
import net.minecraft.src.CallableGLInfo;
import net.minecraft.src.CallableLWJGLVersion;
import net.minecraft.src.CallableModded;
import net.minecraft.src.CallableTexturePack;
import net.minecraft.src.CallableType2;
import net.minecraft.src.ColorizerFoliage;
import net.minecraft.src.ColorizerGrass;
import net.minecraft.src.ColorizerWater;
import net.minecraft.src.CrashReport;
import net.minecraft.src.EffectRenderer;
import net.minecraft.src.EntityBoat;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityItemFrame;
import net.minecraft.src.EntityList;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityMinecart;
import net.minecraft.src.EntityPainting;
import net.minecraft.src.EntityRenderer;
import net.minecraft.src.EnumMovingObjectType;
import net.minecraft.src.EnumOS;
import net.minecraft.src.EnumOSHelper;
import net.minecraft.src.EnumOptions;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GLAllocation;
import net.minecraft.src.GameSettings;
import net.minecraft.src.GameWindowListener;
import net.minecraft.src.GuiAchievement;
import net.minecraft.src.GuiChat;
import net.minecraft.src.GuiConnecting;
import net.minecraft.src.GuiErrorScreen;
import net.minecraft.src.GuiGameOver;
import net.minecraft.src.GuiIngame;
import net.minecraft.src.GuiIngameMenu;
import net.minecraft.src.GuiInventory;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiMemoryErrorScreen;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSleepMP;
import net.minecraft.src.HttpUtil;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.IPlayerUsage;
import net.minecraft.src.ISaveFormat;
import net.minecraft.src.ISaveHandler;
import net.minecraft.src.IntegratedServer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemRenderer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.LoadingScreenRenderer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MemoryConnection;
import net.minecraft.src.MinecraftError;
import net.minecraft.src.MinecraftFakeLauncher;
import net.minecraft.src.MouseHelper;
import net.minecraft.src.MovementInputFromOptions;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.OpenGlHelper;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.PlayerControllerMP;
import net.minecraft.src.PlayerUsageSnooper;
import net.minecraft.src.Profiler;
import net.minecraft.src.ProfilerResult;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.RenderGlobal;
import net.minecraft.src.RenderManager;
import net.minecraft.src.ReportedException;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.ScreenShotHelper;
import net.minecraft.src.ServerData;
import net.minecraft.src.Session;
import net.minecraft.src.SoundManager;
import net.minecraft.src.StatCollector;
import net.minecraft.src.StatFileWriter;
import net.minecraft.src.StatList;
import net.minecraft.src.StatStringFormatKeyInv;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TextureCompassFX;
import net.minecraft.src.TextureFlamesFX;
import net.minecraft.src.TextureLavaFX;
import net.minecraft.src.TextureLavaFlowFX;
import net.minecraft.src.TexturePackList;
import net.minecraft.src.TexturePortalFX;
import net.minecraft.src.TextureWatchFX;
import net.minecraft.src.TextureWaterFX;
import net.minecraft.src.TextureWaterFlowFX;
import net.minecraft.src.ThreadClientSleep;
import net.minecraft.src.ThreadDownloadResources;
import net.minecraft.src.ThreadShutdown;
import net.minecraft.src.Timer;
import net.minecraft.src.WorldClient;
import net.minecraft.src.WorldInfo;
import net.minecraft.src.WorldRenderer;
import net.minecraft.src.WorldSettings;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

public abstract class Minecraft implements Runnable, IPlayerUsage {

   public static byte[] field_71444_a = new byte[10485760];
   private ServerData field_71422_O;
   private static Minecraft field_71432_P;
   public PlayerControllerMP field_71442_b;
   private boolean field_71431_Q = false;
   private boolean field_71434_R = false;
   private CrashReport field_71433_S;
   public int field_71443_c;
   public int field_71440_d;
   private Timer field_71428_T = new Timer(20.0F);
   private PlayerUsageSnooper field_71427_U = new PlayerUsageSnooper("client", this);
   public WorldClient field_71441_e;
   public RenderGlobal field_71438_f;
   public EntityClientPlayerMP field_71439_g;
   public EntityLiving field_71451_h;
   public EffectRenderer field_71452_i;
   public Session field_71449_j = null;
   public String field_71450_k;
   public Canvas field_71447_l;
   public boolean field_71448_m = false;
   public volatile boolean field_71445_n = false;
   public RenderEngine field_71446_o;
   public FontRenderer field_71466_p;
   public FontRenderer field_71464_q;
   public GuiScreen field_71462_r = null;
   public LoadingScreenRenderer field_71461_s;
   public EntityRenderer field_71460_t;
   private ThreadDownloadResources field_71430_V;
   private int field_71429_W = 0;
   private int field_71436_X;
   private int field_71435_Y;
   private IntegratedServer field_71437_Z;
   public GuiAchievement field_71458_u = new GuiAchievement(this);
   public GuiIngame field_71456_v;
   public boolean field_71454_w = false;
   public MovingObjectPosition field_71476_x = null;
   public GameSettings field_71474_y;
   protected MinecraftApplet field_71473_z;
   public SoundManager field_71416_A = new SoundManager();
   public MouseHelper field_71417_B;
   public TexturePackList field_71418_C;
   public File field_71412_D;
   private ISaveFormat field_71469_aa;
   private static int field_71470_ab;
   private int field_71467_ac = 0;
   private boolean field_71468_ad;
   public StatFileWriter field_71413_E;
   private String field_71475_ae;
   private int field_71477_af;
   private TextureWaterFX field_71471_ag = new TextureWaterFX();
   private TextureLavaFX field_71472_ah = new TextureLavaFX();
   boolean field_71414_F = false;
   public boolean field_71415_G = false;
   long field_71423_H = func_71386_F();
   private int field_71457_ai = 0;
   private boolean field_71459_aj;
   private INetworkManager field_71453_ak;
   private boolean field_71455_al;
   public final Profiler field_71424_I = new Profiler();
   private long field_83002_am = -1L;
   private static File field_71463_am = null;
   public volatile boolean field_71425_J = true;
   public String field_71426_K = "";
   long field_71419_L = func_71386_F();
   int field_71420_M = 0;
   long field_71421_N = -1L;
   private String field_71465_an = "root";


   public Minecraft(Canvas p_i3022_1_, MinecraftApplet p_i3022_2_, int p_i3022_3_, int p_i3022_4_, boolean p_i3022_5_) {
      StatList.func_75919_a();
      this.field_71435_Y = p_i3022_4_;
      this.field_71431_Q = p_i3022_5_;
      this.field_71473_z = p_i3022_2_;
      Packet3Chat.field_73478_a = 32767;
      this.func_71389_H();
      this.field_71447_l = p_i3022_1_;
      this.field_71443_c = p_i3022_3_;
      this.field_71440_d = p_i3022_4_;
      this.field_71431_Q = p_i3022_5_;
      field_71432_P = this;
   }

   private void func_71389_H() {
      ThreadClientSleep var1 = new ThreadClientSleep(this, "Timer hack thread");
      var1.setDaemon(true);
      var1.start();
   }

   public void func_71404_a(CrashReport p_71404_1_) {
      this.field_71434_R = true;
      this.field_71433_S = p_71404_1_;
   }

   public void func_71377_b(CrashReport p_71377_1_) {
      this.field_71434_R = true;
      this.func_71406_c(p_71377_1_);
   }

   public abstract void func_71406_c(CrashReport var1);

   public void func_71367_a(String p_71367_1_, int p_71367_2_) {
      this.field_71475_ae = p_71367_1_;
      this.field_71477_af = p_71367_2_;
   }

   public void func_71384_a() throws LWJGLException {
      if(this.field_71447_l != null) {
         Graphics var1 = this.field_71447_l.getGraphics();
         if(var1 != null) {
            var1.setColor(Color.BLACK);
            var1.fillRect(0, 0, this.field_71443_c, this.field_71440_d);
            var1.dispose();
         }

         Display.setParent(this.field_71447_l);
      } else if(this.field_71431_Q) {
         Display.setFullscreen(true);
         this.field_71443_c = Display.getDisplayMode().getWidth();
         this.field_71440_d = Display.getDisplayMode().getHeight();
         if(this.field_71443_c <= 0) {
            this.field_71443_c = 1;
         }

         if(this.field_71440_d <= 0) {
            this.field_71440_d = 1;
         }
      } else {
         Display.setDisplayMode(new DisplayMode(this.field_71443_c, this.field_71440_d));
      }

      Display.setTitle("Minecraft Minecraft 1.4.2");
      System.out.println("LWJGL Version: " + Sys.getVersion());

      try {
         Display.create((new PixelFormat()).withDepthBits(24));
      } catch (LWJGLException var5) {
         var5.printStackTrace();

         try {
            Thread.sleep(1000L);
         } catch (InterruptedException var4) {
            ;
         }

         Display.create();
      }

      OpenGlHelper.func_77474_a();
      this.field_71412_D = func_71380_b();
      this.field_71469_aa = new AnvilSaveConverter(new File(this.field_71412_D, "saves"));
      this.field_71474_y = new GameSettings(this, this.field_71412_D);
      this.field_71418_C = new TexturePackList(this.field_71412_D, this);
      this.field_71446_o = new RenderEngine(this.field_71418_C, this.field_71474_y);
      this.func_71357_I();
      this.field_71466_p = new FontRenderer(this.field_71474_y, "/font/default.png", this.field_71446_o, false);
      this.field_71464_q = new FontRenderer(this.field_71474_y, "/font/alternate.png", this.field_71446_o, false);
      if(this.field_71474_y.field_74363_ab != null) {
         StringTranslate.func_74808_a().func_74810_a(this.field_71474_y.field_74363_ab);
         this.field_71466_p.func_78264_a(StringTranslate.func_74808_a().func_74804_d());
         this.field_71466_p.func_78275_b(StringTranslate.func_74802_e(this.field_71474_y.field_74363_ab));
      }

      ColorizerWater.func_76914_a(this.field_71446_o.func_78346_a("/misc/watercolor.png"));
      ColorizerGrass.func_77479_a(this.field_71446_o.func_78346_a("/misc/grasscolor.png"));
      ColorizerFoliage.func_77467_a(this.field_71446_o.func_78346_a("/misc/foliagecolor.png"));
      this.field_71460_t = new EntityRenderer(this);
      RenderManager.field_78727_a.field_78721_f = new ItemRenderer(this);
      this.field_71413_E = new StatFileWriter(this.field_71449_j, this.field_71412_D);
      AchievementList.field_76004_f.func_75988_a(new StatStringFormatKeyInv(this));
      this.func_71357_I();
      Mouse.create();
      this.field_71417_B = new MouseHelper(this.field_71447_l);
      this.func_71361_d("Pre startup");
      GL11.glEnable(3553);
      GL11.glShadeModel(7425);
      GL11.glClearDepth(1.0D);
      GL11.glEnable(2929);
      GL11.glDepthFunc(515);
      GL11.glEnable(3008);
      GL11.glAlphaFunc(516, 0.1F);
      GL11.glCullFace(1029);
      GL11.glMatrixMode(5889);
      GL11.glLoadIdentity();
      GL11.glMatrixMode(5888);
      this.func_71361_d("Startup");
      this.field_71416_A.func_77373_a(this.field_71474_y);
      this.field_71446_o.func_78355_a(this.field_71472_ah);
      this.field_71446_o.func_78355_a(this.field_71471_ag);
      this.field_71446_o.func_78355_a(new TexturePortalFX());
      this.field_71446_o.func_78355_a(new TextureCompassFX(this));
      this.field_71446_o.func_78355_a(new TextureWatchFX(this));
      this.field_71446_o.func_78355_a(new TextureWaterFlowFX());
      this.field_71446_o.func_78355_a(new TextureLavaFlowFX());
      this.field_71446_o.func_78355_a(new TextureFlamesFX(0));
      this.field_71446_o.func_78355_a(new TextureFlamesFX(1));
      this.field_71438_f = new RenderGlobal(this, this.field_71446_o);
      GL11.glViewport(0, 0, this.field_71443_c, this.field_71440_d);
      this.field_71452_i = new EffectRenderer(this.field_71441_e, this.field_71446_o);

      try {
         this.field_71430_V = new ThreadDownloadResources(this.field_71412_D, this);
         this.field_71430_V.start();
      } catch (Exception var3) {
         ;
      }

      this.func_71361_d("Post startup");
      this.field_71456_v = new GuiIngame(this);
      if(this.field_71475_ae != null) {
         this.func_71373_a(new GuiConnecting(this, this.field_71475_ae, this.field_71477_af));
      } else {
         this.func_71373_a(new GuiMainMenu());
      }

      this.field_71461_s = new LoadingScreenRenderer(this);
      if(this.field_71474_y.field_74353_u && !this.field_71431_Q) {
         this.func_71352_k();
      }

   }

   private void func_71357_I() throws LWJGLException {
      ScaledResolution var1 = new ScaledResolution(this.field_71474_y, this.field_71443_c, this.field_71440_d);
      GL11.glClear(16640);
      GL11.glMatrixMode(5889);
      GL11.glLoadIdentity();
      GL11.glOrtho(0.0D, var1.func_78327_c(), var1.func_78324_d(), 0.0D, 1000.0D, 3000.0D);
      GL11.glMatrixMode(5888);
      GL11.glLoadIdentity();
      GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
      GL11.glViewport(0, 0, this.field_71443_c, this.field_71440_d);
      GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
      GL11.glDisable(2896);
      GL11.glEnable(3553);
      GL11.glDisable(2912);
      Tessellator var2 = Tessellator.field_78398_a;
      GL11.glBindTexture(3553, this.field_71446_o.func_78341_b("/title/mojang.png"));
      var2.func_78382_b();
      var2.func_78378_d(16777215);
      var2.func_78374_a(0.0D, (double)this.field_71440_d, 0.0D, 0.0D, 0.0D);
      var2.func_78374_a((double)this.field_71443_c, (double)this.field_71440_d, 0.0D, 0.0D, 0.0D);
      var2.func_78374_a((double)this.field_71443_c, 0.0D, 0.0D, 0.0D, 0.0D);
      var2.func_78374_a(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
      var2.func_78381_a();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      var2.func_78378_d(16777215);
      short var3 = 256;
      short var4 = 256;
      this.func_71392_a((var1.func_78326_a() - var3) / 2, (var1.func_78328_b() - var4) / 2, 0, 0, var3, var4);
      GL11.glDisable(2896);
      GL11.glDisable(2912);
      GL11.glEnable(3008);
      GL11.glAlphaFunc(516, 0.1F);
      Display.swapBuffers();
   }

   public void func_71392_a(int p_71392_1_, int p_71392_2_, int p_71392_3_, int p_71392_4_, int p_71392_5_, int p_71392_6_) {
      float var7 = 0.00390625F;
      float var8 = 0.00390625F;
      Tessellator var9 = Tessellator.field_78398_a;
      var9.func_78382_b();
      var9.func_78374_a((double)(p_71392_1_ + 0), (double)(p_71392_2_ + p_71392_6_), 0.0D, (double)((float)(p_71392_3_ + 0) * var7), (double)((float)(p_71392_4_ + p_71392_6_) * var8));
      var9.func_78374_a((double)(p_71392_1_ + p_71392_5_), (double)(p_71392_2_ + p_71392_6_), 0.0D, (double)((float)(p_71392_3_ + p_71392_5_) * var7), (double)((float)(p_71392_4_ + p_71392_6_) * var8));
      var9.func_78374_a((double)(p_71392_1_ + p_71392_5_), (double)(p_71392_2_ + 0), 0.0D, (double)((float)(p_71392_3_ + p_71392_5_) * var7), (double)((float)(p_71392_4_ + 0) * var8));
      var9.func_78374_a((double)(p_71392_1_ + 0), (double)(p_71392_2_ + 0), 0.0D, (double)((float)(p_71392_3_ + 0) * var7), (double)((float)(p_71392_4_ + 0) * var8));
      var9.func_78381_a();
   }

   public static File func_71380_b() {
      if(field_71463_am == null) {
         field_71463_am = func_71394_a("minecraft");
      }

      return field_71463_am;
   }

   public static File func_71394_a(String p_71394_0_) {
      String var1 = System.getProperty("user.home", ".");
      File var2;
      switch(EnumOSHelper.field_74533_a[func_71376_c().ordinal()]) {
      case 1:
      case 2:
         var2 = new File(var1, '.' + p_71394_0_ + '/');
         break;
      case 3:
         String var3 = System.getenv("APPDATA");
         if(var3 != null) {
            var2 = new File(var3, "." + p_71394_0_ + '/');
         } else {
            var2 = new File(var1, '.' + p_71394_0_ + '/');
         }
         break;
      case 4:
         var2 = new File(var1, "Library/Application Support/" + p_71394_0_);
         break;
      default:
         var2 = new File(var1, p_71394_0_ + '/');
      }

      if(!var2.exists() && !var2.mkdirs()) {
         throw new RuntimeException("The working directory could not be created: " + var2);
      } else {
         return var2;
      }
   }

   public static EnumOS func_71376_c() {
      String var0 = System.getProperty("os.name").toLowerCase();
      return var0.contains("win")?EnumOS.WINDOWS:(var0.contains("mac")?EnumOS.MACOS:(var0.contains("solaris")?EnumOS.SOLARIS:(var0.contains("sunos")?EnumOS.SOLARIS:(var0.contains("linux")?EnumOS.LINUX:(var0.contains("unix")?EnumOS.LINUX:EnumOS.UNKNOWN)))));
   }

   public ISaveFormat func_71359_d() {
      return this.field_71469_aa;
   }

   public void func_71373_a(GuiScreen p_71373_1_) {
      if(!(this.field_71462_r instanceof GuiErrorScreen)) {
         if(this.field_71462_r != null) {
            this.field_71462_r.func_73874_b();
         }

         this.field_71413_E.func_77446_d();
         if(p_71373_1_ == null && this.field_71441_e == null) {
            p_71373_1_ = new GuiMainMenu();
         } else if(p_71373_1_ == null && this.field_71439_g.func_70630_aN() <= 0) {
            p_71373_1_ = new GuiGameOver();
         }

         if(p_71373_1_ instanceof GuiMainMenu) {
            this.field_71474_y.field_74330_P = false;
            this.field_71456_v.func_73827_b().func_73761_a();
         }

         this.field_71462_r = (GuiScreen)p_71373_1_;
         if(p_71373_1_ != null) {
            this.func_71364_i();
            ScaledResolution var2 = new ScaledResolution(this.field_71474_y, this.field_71443_c, this.field_71440_d);
            int var3 = var2.func_78326_a();
            int var4 = var2.func_78328_b();
            ((GuiScreen)p_71373_1_).func_73872_a(this, var3, var4);
            this.field_71454_w = false;
         } else {
            this.func_71381_h();
         }

      }
   }

   private void func_71361_d(String p_71361_1_) {
      int var2 = GL11.glGetError();
      if(var2 != 0) {
         String var3 = GLU.gluErrorString(var2);
         System.out.println("########## GL ERROR ##########");
         System.out.println("@ " + p_71361_1_);
         System.out.println(var2 + ": " + var3);
      }

   }

   public void func_71405_e() {
      try {
         this.field_71413_E.func_77446_d();

         try {
            if(this.field_71430_V != null) {
               this.field_71430_V.func_74574_b();
            }
         } catch (Exception var9) {
            ;
         }

         System.out.println("Stopping!");

         try {
            this.func_71403_a((WorldClient)null);
         } catch (Throwable var8) {
            ;
         }

         try {
            GLAllocation.func_74525_a();
         } catch (Throwable var7) {
            ;
         }

         this.field_71416_A.func_77370_b();
         Mouse.destroy();
         Keyboard.destroy();
      } finally {
         Display.destroy();
         if(!this.field_71434_R) {
            System.exit(0);
         }

      }

      System.gc();
   }

   public void run() {
      this.field_71425_J = true;

      try {
         this.func_71384_a();
      } catch (Exception var11) {
         var11.printStackTrace();
         this.func_71377_b(this.func_71396_d(new CrashReport("Failed to start game", var11)));
         return;
      }

      while(true) {
         try {
            if(this.field_71425_J) {
               if(this.field_71434_R && this.field_71433_S != null) {
                  this.func_71377_b(this.field_71433_S);
                  return;
               }

               if(this.field_71468_ad) {
                  this.field_71468_ad = false;
                  this.field_71446_o.func_78352_b();
               }

               try {
                  this.func_71411_J();
               } catch (OutOfMemoryError var10) {
                  this.func_71398_f();
                  this.func_71373_a(new GuiMemoryErrorScreen());
                  System.gc();
               }
               continue;
            }
         } catch (MinecraftError var12) {
            ;
         } catch (ReportedException var13) {
            this.func_71396_d(var13.func_71575_a());
            this.func_71398_f();
            var13.printStackTrace();
            this.func_71377_b(var13.func_71575_a());
         } catch (Throwable var14) {
            CrashReport var2 = this.func_71396_d(new CrashReport("Unexpected error", var14));
            this.func_71398_f();
            var14.printStackTrace();
            this.func_71377_b(var2);
         } finally {
            this.func_71405_e();
         }

         return;
      }
   }

   private void func_71411_J() {
      if(this.field_71473_z != null && !this.field_71473_z.isActive()) {
         this.field_71425_J = false;
      } else {
         AxisAlignedBB.func_72332_a().func_72298_a();
         if(this.field_71441_e != null) {
            this.field_71441_e.func_82732_R().func_72343_a();
         }

         this.field_71424_I.func_76320_a("root");
         if(this.field_71447_l == null && Display.isCloseRequested()) {
            this.func_71400_g();
         }

         if(this.field_71445_n && this.field_71441_e != null) {
            float var1 = this.field_71428_T.field_74281_c;
            this.field_71428_T.func_74275_a();
            this.field_71428_T.field_74281_c = var1;
         } else {
            this.field_71428_T.func_74275_a();
         }

         long var6 = System.nanoTime();
         this.field_71424_I.func_76320_a("tick");

         for(int var3 = 0; var3 < this.field_71428_T.field_74280_b; ++var3) {
            this.func_71407_l();
         }

         this.field_71424_I.func_76318_c("preRenderErrors");
         long var7 = System.nanoTime() - var6;
         this.func_71361_d("Pre render");
         RenderBlocks.field_78667_b = this.field_71474_y.field_74347_j;
         this.field_71424_I.func_76318_c("sound");
         this.field_71416_A.func_77369_a(this.field_71439_g, this.field_71428_T.field_74281_c);
         this.field_71424_I.func_76319_b();
         this.field_71424_I.func_76320_a("render");
         this.field_71424_I.func_76320_a("display");
         GL11.glEnable(3553);
         if(!Keyboard.isKeyDown(65)) {
            Display.update();
         }

         if(this.field_71439_g != null && this.field_71439_g.func_70094_T()) {
            this.field_71474_y.field_74320_O = 0;
         }

         this.field_71424_I.func_76319_b();
         if(!this.field_71454_w) {
            this.field_71424_I.func_76318_c("gameRenderer");
            this.field_71460_t.func_78480_b(this.field_71428_T.field_74281_c);
            this.field_71424_I.func_76319_b();
         }

         GL11.glFlush();
         this.field_71424_I.func_76319_b();
         if(!Display.isActive() && this.field_71431_Q) {
            this.func_71352_k();
         }

         if(this.field_71474_y.field_74330_P && this.field_71474_y.field_74329_Q) {
            if(!this.field_71424_I.field_76327_a) {
               this.field_71424_I.func_76317_a();
            }

            this.field_71424_I.field_76327_a = true;
            this.func_71366_a(var7);
         } else {
            this.field_71424_I.field_76327_a = false;
            this.field_71421_N = System.nanoTime();
         }

         this.field_71458_u.func_73847_a();
         this.field_71424_I.func_76320_a("root");
         Thread.yield();
         if(Keyboard.isKeyDown(65)) {
            Display.update();
         }

         this.func_71365_K();
         if(this.field_71447_l != null && !this.field_71431_Q && (this.field_71447_l.getWidth() != this.field_71443_c || this.field_71447_l.getHeight() != this.field_71440_d)) {
            this.field_71443_c = this.field_71447_l.getWidth();
            this.field_71440_d = this.field_71447_l.getHeight();
            if(this.field_71443_c <= 0) {
               this.field_71443_c = 1;
            }

            if(this.field_71440_d <= 0) {
               this.field_71440_d = 1;
            }

            this.func_71370_a(this.field_71443_c, this.field_71440_d);
         }

         this.func_71361_d("Post render");
         ++this.field_71420_M;
         boolean var5 = this.field_71445_n;
         this.field_71445_n = this.func_71356_B() && this.field_71462_r != null && this.field_71462_r.func_73868_f() && !this.field_71437_Z.func_71344_c();
         if(this.func_71387_A() && this.field_71439_g != null && this.field_71439_g.field_71174_a != null && this.field_71445_n != var5) {
            ((MemoryConnection)this.field_71439_g.field_71174_a.func_72548_f()).func_74437_a(this.field_71445_n);
         }

         while(func_71386_F() >= this.field_71419_L + 1000L) {
            field_71470_ab = this.field_71420_M;
            this.field_71426_K = field_71470_ab + " fps, " + WorldRenderer.field_78922_b + " chunk updates";
            WorldRenderer.field_78922_b = 0;
            this.field_71419_L += 1000L;
            this.field_71420_M = 0;
            this.field_71427_U.func_76471_b();
            if(!this.field_71427_U.func_76468_d()) {
               this.field_71427_U.func_76463_a();
            }
         }

         this.field_71424_I.func_76319_b();
         if(this.field_71474_y.field_74350_i > 0) {
            EntityRenderer var10000 = this.field_71460_t;
            Display.sync(EntityRenderer.func_78465_a(this.field_71474_y.field_74350_i));
         }

      }
   }

   public void func_71398_f() {
      try {
         field_71444_a = new byte[0];
         this.field_71438_f.func_72728_f();
      } catch (Throwable var4) {
         ;
      }

      try {
         System.gc();
         AxisAlignedBB.func_72332_a().func_72300_b();
         this.field_71441_e.func_82732_R().func_72344_b();
      } catch (Throwable var3) {
         ;
      }

      try {
         System.gc();
         this.func_71403_a((WorldClient)null);
      } catch (Throwable var2) {
         ;
      }

      System.gc();
   }

   private void func_71365_K() {
      if(Keyboard.isKeyDown(60)) {
         if(!this.field_71414_F) {
            this.field_71414_F = true;
            this.field_71456_v.func_73827_b().func_73765_a(ScreenShotHelper.func_74291_a(field_71463_am, this.field_71443_c, this.field_71440_d));
         }
      } else {
         this.field_71414_F = false;
      }

   }

   private void func_71383_b(int p_71383_1_) {
      List var2 = this.field_71424_I.func_76321_b(this.field_71465_an);
      if(var2 != null && !var2.isEmpty()) {
         ProfilerResult var3 = (ProfilerResult)var2.remove(0);
         if(p_71383_1_ == 0) {
            if(var3.field_76331_c.length() > 0) {
               int var4 = this.field_71465_an.lastIndexOf(".");
               if(var4 >= 0) {
                  this.field_71465_an = this.field_71465_an.substring(0, var4);
               }
            }
         } else {
            --p_71383_1_;
            if(p_71383_1_ < var2.size() && !((ProfilerResult)var2.get(p_71383_1_)).field_76331_c.equals("unspecified")) {
               if(this.field_71465_an.length() > 0) {
                  this.field_71465_an = this.field_71465_an + ".";
               }

               this.field_71465_an = this.field_71465_an + ((ProfilerResult)var2.get(p_71383_1_)).field_76331_c;
            }
         }

      }
   }

   private void func_71366_a(long p_71366_1_) {
      if(this.field_71424_I.field_76327_a) {
         List var3 = this.field_71424_I.func_76321_b(this.field_71465_an);
         ProfilerResult var4 = (ProfilerResult)var3.remove(0);
         GL11.glClear(256);
         GL11.glMatrixMode(5889);
         GL11.glEnable(2903);
         GL11.glLoadIdentity();
         GL11.glOrtho(0.0D, (double)this.field_71443_c, (double)this.field_71440_d, 0.0D, 1000.0D, 3000.0D);
         GL11.glMatrixMode(5888);
         GL11.glLoadIdentity();
         GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
         GL11.glLineWidth(1.0F);
         GL11.glDisable(3553);
         Tessellator var5 = Tessellator.field_78398_a;
         short var6 = 160;
         int var7 = this.field_71443_c - var6 - 10;
         int var8 = this.field_71440_d - var6 * 2;
         GL11.glEnable(3042);
         var5.func_78382_b();
         var5.func_78384_a(0, 200);
         var5.func_78377_a((double)((float)var7 - (float)var6 * 1.1F), (double)((float)var8 - (float)var6 * 0.6F - 16.0F), 0.0D);
         var5.func_78377_a((double)((float)var7 - (float)var6 * 1.1F), (double)(var8 + var6 * 2), 0.0D);
         var5.func_78377_a((double)((float)var7 + (float)var6 * 1.1F), (double)(var8 + var6 * 2), 0.0D);
         var5.func_78377_a((double)((float)var7 + (float)var6 * 1.1F), (double)((float)var8 - (float)var6 * 0.6F - 16.0F), 0.0D);
         var5.func_78381_a();
         GL11.glDisable(3042);
         double var9 = 0.0D;

         int var13;
         for(int var11 = 0; var11 < var3.size(); ++var11) {
            ProfilerResult var12 = (ProfilerResult)var3.get(var11);
            var13 = MathHelper.func_76128_c(var12.field_76332_a / 4.0D) + 1;
            var5.func_78371_b(6);
            var5.func_78378_d(var12.func_76329_a());
            var5.func_78377_a((double)var7, (double)var8, 0.0D);

            int var14;
            float var15;
            float var17;
            float var16;
            for(var14 = var13; var14 >= 0; --var14) {
               var15 = (float)((var9 + var12.field_76332_a * (double)var14 / (double)var13) * 3.1415927410125732D * 2.0D / 100.0D);
               var16 = MathHelper.func_76126_a(var15) * (float)var6;
               var17 = MathHelper.func_76134_b(var15) * (float)var6 * 0.5F;
               var5.func_78377_a((double)((float)var7 + var16), (double)((float)var8 - var17), 0.0D);
            }

            var5.func_78381_a();
            var5.func_78371_b(5);
            var5.func_78378_d((var12.func_76329_a() & 16711422) >> 1);

            for(var14 = var13; var14 >= 0; --var14) {
               var15 = (float)((var9 + var12.field_76332_a * (double)var14 / (double)var13) * 3.1415927410125732D * 2.0D / 100.0D);
               var16 = MathHelper.func_76126_a(var15) * (float)var6;
               var17 = MathHelper.func_76134_b(var15) * (float)var6 * 0.5F;
               var5.func_78377_a((double)((float)var7 + var16), (double)((float)var8 - var17), 0.0D);
               var5.func_78377_a((double)((float)var7 + var16), (double)((float)var8 - var17 + 10.0F), 0.0D);
            }

            var5.func_78381_a();
            var9 += var12.field_76332_a;
         }

         DecimalFormat var19 = new DecimalFormat("##0.00");
         GL11.glEnable(3553);
         String var18 = "";
         if(!var4.field_76331_c.equals("unspecified")) {
            var18 = var18 + "[0] ";
         }

         if(var4.field_76331_c.length() == 0) {
            var18 = var18 + "ROOT ";
         } else {
            var18 = var18 + var4.field_76331_c + " ";
         }

         var13 = 16777215;
         this.field_71466_p.func_78261_a(var18, var7 - var6, var8 - var6 / 2 - 16, var13);
         this.field_71466_p.func_78261_a(var18 = var19.format(var4.field_76330_b) + "%", var7 + var6 - this.field_71466_p.func_78256_a(var18), var8 - var6 / 2 - 16, var13);

         for(int var21 = 0; var21 < var3.size(); ++var21) {
            ProfilerResult var20 = (ProfilerResult)var3.get(var21);
            String var22 = "";
            if(var20.field_76331_c.equals("unspecified")) {
               var22 = var22 + "[?] ";
            } else {
               var22 = var22 + "[" + (var21 + 1) + "] ";
            }

            var22 = var22 + var20.field_76331_c;
            this.field_71466_p.func_78261_a(var22, var7 - var6, var8 + var6 / 2 + var21 * 8 + 20, var20.func_76329_a());
            this.field_71466_p.func_78261_a(var22 = var19.format(var20.field_76332_a) + "%", var7 + var6 - 50 - this.field_71466_p.func_78256_a(var22), var8 + var6 / 2 + var21 * 8 + 20, var20.func_76329_a());
            this.field_71466_p.func_78261_a(var22 = var19.format(var20.field_76330_b) + "%", var7 + var6 - this.field_71466_p.func_78256_a(var22), var8 + var6 / 2 + var21 * 8 + 20, var20.func_76329_a());
         }

      }
   }

   public void func_71400_g() {
      this.field_71425_J = false;
   }

   public void func_71381_h() {
      if(Display.isActive()) {
         if(!this.field_71415_G) {
            this.field_71415_G = true;
            this.field_71417_B.func_74372_a();
            this.func_71373_a((GuiScreen)null);
            this.field_71429_W = 10000;
         }
      }
   }

   public void func_71364_i() {
      if(this.field_71415_G) {
         KeyBinding.func_74506_a();
         this.field_71415_G = false;
         this.field_71417_B.func_74373_b();
      }
   }

   public void func_71385_j() {
      if(this.field_71462_r == null) {
         this.func_71373_a(new GuiIngameMenu());
         if(this.func_71356_B() && !this.field_71437_Z.func_71344_c()) {
            this.field_71416_A.func_82466_e();
         }

      }
   }

   private void func_71399_a(int p_71399_1_, boolean p_71399_2_) {
      if(!p_71399_2_) {
         this.field_71429_W = 0;
      }

      if(p_71399_1_ != 0 || this.field_71429_W <= 0) {
         if(p_71399_2_ && this.field_71476_x != null && this.field_71476_x.field_72313_a == EnumMovingObjectType.TILE && p_71399_1_ == 0) {
            int var3 = this.field_71476_x.field_72311_b;
            int var4 = this.field_71476_x.field_72312_c;
            int var5 = this.field_71476_x.field_72309_d;
            this.field_71442_b.func_78759_c(var3, var4, var5, this.field_71476_x.field_72310_e);
            if(this.field_71439_g.func_82246_f(var3, var4, var5)) {
               this.field_71452_i.func_78867_a(var3, var4, var5, this.field_71476_x.field_72310_e);
               this.field_71439_g.func_71038_i();
            }
         } else {
            this.field_71442_b.func_78767_c();
         }

      }
   }

   private void func_71402_c(int p_71402_1_) {
      if(p_71402_1_ != 0 || this.field_71429_W <= 0) {
         if(p_71402_1_ == 0) {
            this.field_71439_g.func_71038_i();
         }

         if(p_71402_1_ == 1) {
            this.field_71467_ac = 4;
         }

         boolean var2 = true;
         ItemStack var3 = this.field_71439_g.field_71071_by.func_70448_g();
         if(this.field_71476_x == null) {
            if(p_71402_1_ == 0 && this.field_71442_b.func_78762_g()) {
               this.field_71429_W = 10;
            }
         } else if(this.field_71476_x.field_72313_a == EnumMovingObjectType.ENTITY) {
            if(p_71402_1_ == 0) {
               this.field_71442_b.func_78764_a(this.field_71439_g, this.field_71476_x.field_72308_g);
            }

            if(p_71402_1_ == 1 && this.field_71442_b.func_78768_b(this.field_71439_g, this.field_71476_x.field_72308_g)) {
               var2 = false;
            }
         } else if(this.field_71476_x.field_72313_a == EnumMovingObjectType.TILE) {
            int var4 = this.field_71476_x.field_72311_b;
            int var5 = this.field_71476_x.field_72312_c;
            int var6 = this.field_71476_x.field_72309_d;
            int var7 = this.field_71476_x.field_72310_e;
            if(p_71402_1_ == 0) {
               this.field_71442_b.func_78743_b(var4, var5, var6, this.field_71476_x.field_72310_e);
            } else {
               int var8 = var3 != null?var3.field_77994_a:0;
               if(this.field_71442_b.func_78760_a(this.field_71439_g, this.field_71441_e, var3, var4, var5, var6, var7, this.field_71476_x.field_72307_f)) {
                  var2 = false;
                  this.field_71439_g.func_71038_i();
               }

               if(var3 == null) {
                  return;
               }

               if(var3.field_77994_a == 0) {
                  this.field_71439_g.field_71071_by.field_70462_a[this.field_71439_g.field_71071_by.field_70461_c] = null;
               } else if(var3.field_77994_a != var8 || this.field_71442_b.func_78758_h()) {
                  this.field_71460_t.field_78516_c.func_78444_b();
               }
            }
         }

         if(var2 && p_71402_1_ == 1) {
            ItemStack var9 = this.field_71439_g.field_71071_by.func_70448_g();
            if(var9 != null && this.field_71442_b.func_78769_a(this.field_71439_g, this.field_71441_e, var9)) {
               this.field_71460_t.field_78516_c.func_78445_c();
            }
         }

      }
   }

   public void func_71352_k() {
      try {
         this.field_71431_Q = !this.field_71431_Q;
         if(this.field_71431_Q) {
            Display.setDisplayMode(Display.getDesktopDisplayMode());
            this.field_71443_c = Display.getDisplayMode().getWidth();
            this.field_71440_d = Display.getDisplayMode().getHeight();
            if(this.field_71443_c <= 0) {
               this.field_71443_c = 1;
            }

            if(this.field_71440_d <= 0) {
               this.field_71440_d = 1;
            }
         } else {
            if(this.field_71447_l != null) {
               this.field_71443_c = this.field_71447_l.getWidth();
               this.field_71440_d = this.field_71447_l.getHeight();
            } else {
               this.field_71443_c = this.field_71436_X;
               this.field_71440_d = this.field_71435_Y;
            }

            if(this.field_71443_c <= 0) {
               this.field_71443_c = 1;
            }

            if(this.field_71440_d <= 0) {
               this.field_71440_d = 1;
            }
         }

         if(this.field_71462_r != null) {
            this.func_71370_a(this.field_71443_c, this.field_71440_d);
         }

         Display.setFullscreen(this.field_71431_Q);
         Display.setVSyncEnabled(this.field_71474_y.field_74352_v);
         Display.update();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void func_71370_a(int p_71370_1_, int p_71370_2_) {
      this.field_71443_c = p_71370_1_ <= 0?1:p_71370_1_;
      this.field_71440_d = p_71370_2_ <= 0?1:p_71370_2_;
      if(this.field_71462_r != null) {
         ScaledResolution var3 = new ScaledResolution(this.field_71474_y, p_71370_1_, p_71370_2_);
         int var4 = var3.func_78326_a();
         int var5 = var3.func_78328_b();
         this.field_71462_r.func_73872_a(this, var4, var5);
      }

   }

   public void func_71407_l() {
      if(this.field_71467_ac > 0) {
         --this.field_71467_ac;
      }

      this.field_71424_I.func_76320_a("stats");
      this.field_71413_E.func_77449_e();
      this.field_71424_I.func_76318_c("gui");
      if(!this.field_71445_n) {
         this.field_71456_v.func_73831_a();
      }

      this.field_71424_I.func_76318_c("pick");
      this.field_71460_t.func_78473_a(1.0F);
      this.field_71424_I.func_76318_c("gameMode");
      if(!this.field_71445_n && this.field_71441_e != null) {
         this.field_71442_b.func_78765_e();
      }

      GL11.glBindTexture(3553, this.field_71446_o.func_78341_b("/terrain.png"));
      this.field_71424_I.func_76318_c("textures");
      if(!this.field_71445_n) {
         this.field_71446_o.func_78343_a();
      }

      if(this.field_71462_r == null && this.field_71439_g != null) {
         if(this.field_71439_g.func_70630_aN() <= 0) {
            this.func_71373_a((GuiScreen)null);
         } else if(this.field_71439_g.func_70608_bn() && this.field_71441_e != null) {
            this.func_71373_a(new GuiSleepMP());
         }
      } else if(this.field_71462_r != null && this.field_71462_r instanceof GuiSleepMP && !this.field_71439_g.func_70608_bn()) {
         this.func_71373_a((GuiScreen)null);
      }

      if(this.field_71462_r != null) {
         this.field_71429_W = 10000;
      }

      if(this.field_71462_r != null) {
         this.field_71462_r.func_73862_m();
         if(this.field_71462_r != null) {
            this.field_71462_r.field_73884_l.func_73774_a();
            this.field_71462_r.func_73876_c();
         }
      }

      if(this.field_71462_r == null || this.field_71462_r.field_73885_j) {
         this.field_71424_I.func_76318_c("mouse");

         while(Mouse.next()) {
            KeyBinding.func_74510_a(Mouse.getEventButton() - 100, Mouse.getEventButtonState());
            if(Mouse.getEventButtonState()) {
               KeyBinding.func_74507_a(Mouse.getEventButton() - 100);
            }

            long var1 = func_71386_F() - this.field_71423_H;
            if(var1 <= 200L) {
               int var3 = Mouse.getEventDWheel();
               if(var3 != 0) {
                  this.field_71439_g.field_71071_by.func_70453_c(var3);
                  if(this.field_71474_y.field_74331_S) {
                     if(var3 > 0) {
                        var3 = 1;
                     }

                     if(var3 < 0) {
                        var3 = -1;
                     }

                     this.field_71474_y.field_74328_V += (float)var3 * 0.25F;
                  }
               }

               if(this.field_71462_r == null) {
                  if(!this.field_71415_G && Mouse.getEventButtonState()) {
                     this.func_71381_h();
                  }
               } else if(this.field_71462_r != null) {
                  this.field_71462_r.func_73867_d();
               }
            }
         }

         if(this.field_71429_W > 0) {
            --this.field_71429_W;
         }

         this.field_71424_I.func_76318_c("keyboard");

         boolean var4;
         while(Keyboard.next()) {
            KeyBinding.func_74510_a(Keyboard.getEventKey(), Keyboard.getEventKeyState());
            if(Keyboard.getEventKeyState()) {
               KeyBinding.func_74507_a(Keyboard.getEventKey());
            }

            if(this.field_83002_am > 0L) {
               if(func_71386_F() - this.field_83002_am >= 6000L) {
                  throw new ReportedException(new CrashReport("Manually triggered debug crash", new Throwable()));
               }

               if(!Keyboard.isKeyDown(46) || !Keyboard.isKeyDown(61)) {
                  this.field_83002_am = -1L;
               }
            } else if(Keyboard.isKeyDown(46) && Keyboard.isKeyDown(61)) {
               this.field_83002_am = func_71386_F();
            }

            if(Keyboard.getEventKeyState()) {
               if(Keyboard.getEventKey() == 87) {
                  this.func_71352_k();
               } else {
                  if(this.field_71462_r != null) {
                     this.field_71462_r.func_73860_n();
                  } else {
                     if(Keyboard.getEventKey() == 1) {
                        this.func_71385_j();
                     }

                     if(Keyboard.getEventKey() == 31 && Keyboard.isKeyDown(61)) {
                        this.func_71358_L();
                     }

                     if(Keyboard.getEventKey() == 20 && Keyboard.isKeyDown(61)) {
                        this.field_71446_o.func_78352_b();
                     }

                     if(Keyboard.getEventKey() == 33 && Keyboard.isKeyDown(61)) {
                        var4 = Keyboard.isKeyDown(42) | Keyboard.isKeyDown(54);
                        this.field_71474_y.func_74306_a(EnumOptions.RENDER_DISTANCE, var4?-1:1);
                     }

                     if(Keyboard.getEventKey() == 30 && Keyboard.isKeyDown(61)) {
                        this.field_71438_f.func_72712_a();
                     }

                     if(Keyboard.getEventKey() == 35 && Keyboard.isKeyDown(61)) {
                        this.field_71474_y.field_82882_x = !this.field_71474_y.field_82882_x;
                        this.field_71474_y.func_74303_b();
                     }

                     if(Keyboard.getEventKey() == 25 && Keyboard.isKeyDown(61)) {
                        this.field_71474_y.field_82881_y = !this.field_71474_y.field_82881_y;
                        this.field_71474_y.func_74303_b();
                     }

                     if(Keyboard.getEventKey() == 59) {
                        this.field_71474_y.field_74319_N = !this.field_71474_y.field_74319_N;
                     }

                     if(Keyboard.getEventKey() == 61) {
                        this.field_71474_y.field_74330_P = !this.field_71474_y.field_74330_P;
                        this.field_71474_y.field_74329_Q = GuiScreen.func_73877_p();
                     }

                     if(Keyboard.getEventKey() == 63) {
                        ++this.field_71474_y.field_74320_O;
                        if(this.field_71474_y.field_74320_O > 2) {
                           this.field_71474_y.field_74320_O = 0;
                        }
                     }

                     if(Keyboard.getEventKey() == 66) {
                        this.field_71474_y.field_74326_T = !this.field_71474_y.field_74326_T;
                     }
                  }

                  int var5;
                  for(var5 = 0; var5 < 9; ++var5) {
                     if(Keyboard.getEventKey() == 2 + var5) {
                        this.field_71439_g.field_71071_by.field_70461_c = var5;
                     }
                  }

                  if(this.field_71474_y.field_74330_P && this.field_71474_y.field_74329_Q) {
                     if(Keyboard.getEventKey() == 11) {
                        this.func_71383_b(0);
                     }

                     for(var5 = 0; var5 < 9; ++var5) {
                        if(Keyboard.getEventKey() == 2 + var5) {
                           this.func_71383_b(var5 + 1);
                        }
                     }
                  }
               }
            }
         }

         var4 = this.field_71474_y.field_74343_n != 2;

         while(this.field_71474_y.field_74315_B.func_74509_c()) {
            this.func_71373_a(new GuiInventory(this.field_71439_g));
         }

         while(this.field_71474_y.field_74316_C.func_74509_c()) {
            this.field_71439_g.func_71040_bB();
         }

         while(this.field_71474_y.field_74310_D.func_74509_c() && var4) {
            this.func_71373_a(new GuiChat());
         }

         if(this.field_71462_r == null && this.field_71474_y.field_74323_J.func_74509_c() && var4) {
            this.func_71373_a(new GuiChat("/"));
         }

         if(this.field_71439_g.func_71039_bw()) {
            if(!this.field_71474_y.field_74313_G.field_74513_e) {
               this.field_71442_b.func_78766_c(this.field_71439_g);
            }

            label338:
            while(true) {
               if(!this.field_71474_y.field_74312_F.func_74509_c()) {
                  while(this.field_71474_y.field_74313_G.func_74509_c()) {
                     ;
                  }

                  while(true) {
                     if(this.field_71474_y.field_74322_I.func_74509_c()) {
                        continue;
                     }
                     break label338;
                  }
               }
            }
         } else {
            while(this.field_71474_y.field_74312_F.func_74509_c()) {
               this.func_71402_c(0);
            }

            while(this.field_71474_y.field_74313_G.func_74509_c()) {
               this.func_71402_c(1);
            }

            while(this.field_71474_y.field_74322_I.func_74509_c()) {
               this.func_71397_M();
            }
         }

         if(this.field_71474_y.field_74313_G.field_74513_e && this.field_71467_ac == 0 && !this.field_71439_g.func_71039_bw()) {
            this.func_71402_c(1);
         }

         this.func_71399_a(0, this.field_71462_r == null && this.field_71474_y.field_74312_F.field_74513_e && this.field_71415_G);
      }

      if(this.field_71441_e != null) {
         if(this.field_71439_g != null) {
            ++this.field_71457_ai;
            if(this.field_71457_ai == 30) {
               this.field_71457_ai = 0;
               this.field_71441_e.func_72897_h(this.field_71439_g);
            }
         }

         this.field_71424_I.func_76318_c("gameRenderer");
         if(!this.field_71445_n) {
            this.field_71460_t.func_78464_a();
         }

         this.field_71424_I.func_76318_c("levelRenderer");
         if(!this.field_71445_n) {
            this.field_71438_f.func_72734_e();
         }

         this.field_71424_I.func_76318_c("level");
         if(!this.field_71445_n) {
            if(this.field_71441_e.field_73015_s > 0) {
               --this.field_71441_e.field_73015_s;
            }

            this.field_71441_e.func_72939_s();
         }

         if(!this.field_71445_n) {
            this.field_71441_e.func_72891_a(this.field_71441_e.field_73013_u > 0, true);
            this.field_71441_e.func_72835_b();
         }

         this.field_71424_I.func_76318_c("animateTick");
         if(!this.field_71445_n && this.field_71441_e != null) {
            this.field_71441_e.func_73029_E(MathHelper.func_76128_c(this.field_71439_g.field_70165_t), MathHelper.func_76128_c(this.field_71439_g.field_70163_u), MathHelper.func_76128_c(this.field_71439_g.field_70161_v));
         }

         this.field_71424_I.func_76318_c("particles");
         if(!this.field_71445_n) {
            this.field_71452_i.func_78868_a();
         }
      } else if(this.field_71453_ak != null) {
         this.field_71424_I.func_76318_c("pendingConnection");
         this.field_71453_ak.func_74428_b();
      }

      this.field_71424_I.func_76319_b();
      this.field_71423_H = func_71386_F();
   }

   private void func_71358_L() {
      System.out.println("FORCING RELOAD!");
      if(this.field_71416_A != null) {
         this.field_71416_A.func_82464_d();
      }

      this.field_71416_A = new SoundManager();
      this.field_71416_A.func_77373_a(this.field_71474_y);
      this.field_71430_V.func_74573_a();
   }

   public void func_71371_a(String p_71371_1_, String p_71371_2_, WorldSettings p_71371_3_) {
      this.func_71403_a((WorldClient)null);
      System.gc();
      ISaveHandler var4 = this.field_71469_aa.func_75804_a(p_71371_1_, false);
      WorldInfo var5 = var4.func_75757_d();
      if(var5 == null && p_71371_3_ != null) {
         this.field_71413_E.func_77450_a(StatList.field_75937_g, 1);
         var5 = new WorldInfo(p_71371_3_, p_71371_1_);
         var4.func_75761_a(var5);
      }

      if(p_71371_3_ == null) {
         p_71371_3_ = new WorldSettings(var5);
      }

      this.field_71413_E.func_77450_a(StatList.field_75936_f, 1);
      this.field_71437_Z = new IntegratedServer(this, p_71371_1_, p_71371_2_, p_71371_3_);
      this.field_71437_Z.func_71256_s();
      this.field_71455_al = true;
      this.field_71461_s.func_73720_a(StatCollector.func_74838_a("menu.loadingLevel"));

      while(!this.field_71437_Z.func_71200_ad()) {
         String var6 = this.field_71437_Z.func_71195_b_();
         if(var6 != null) {
            this.field_71461_s.func_73719_c(StatCollector.func_74838_a(var6));
         } else {
            this.field_71461_s.func_73719_c("");
         }

         try {
            Thread.sleep(200L);
         } catch (InterruptedException var9) {
            ;
         }
      }

      this.func_71373_a((GuiScreen)null);

      try {
         NetClientHandler var10 = new NetClientHandler(this, this.field_71437_Z);
         this.field_71453_ak = var10.func_72548_f();
      } catch (IOException var8) {
         this.func_71377_b(this.func_71396_d(new CrashReport("Connecting to integrated server", var8)));
      }

   }

   public void func_71403_a(WorldClient p_71403_1_) {
      this.func_71353_a(p_71403_1_, "");
   }

   public void func_71353_a(WorldClient p_71353_1_, String p_71353_2_) {
      this.field_71413_E.func_77446_d();
      if(p_71353_1_ == null) {
         NetClientHandler var3 = this.func_71391_r();
         if(var3 != null) {
            var3.func_72547_c();
         }

         if(this.field_71453_ak != null) {
            this.field_71453_ak.func_74431_f();
         }

         if(this.field_71437_Z != null) {
            this.field_71437_Z.func_71263_m();
         }

         this.field_71437_Z = null;
      }

      this.field_71451_h = null;
      this.field_71453_ak = null;
      if(this.field_71461_s != null) {
         this.field_71461_s.func_73721_b(p_71353_2_);
         this.field_71461_s.func_73719_c("");
      }

      if(p_71353_1_ == null && this.field_71441_e != null) {
         if(this.field_71418_C.func_77295_a()) {
            this.field_71418_C.func_77304_b();
         }

         this.func_71351_a((ServerData)null);
         this.field_71455_al = false;
      }

      this.field_71416_A.func_77368_a((String)null, 0.0F, 0.0F, 0.0F);
      this.field_71416_A.func_82464_d();
      this.field_71441_e = p_71353_1_;
      if(p_71353_1_ != null) {
         if(this.field_71438_f != null) {
            this.field_71438_f.func_72732_a(p_71353_1_);
         }

         if(this.field_71452_i != null) {
            this.field_71452_i.func_78870_a(p_71353_1_);
         }

         if(this.field_71439_g == null) {
            this.field_71439_g = this.field_71442_b.func_78754_a(p_71353_1_);
            this.field_71442_b.func_78745_b(this.field_71439_g);
         }

         this.field_71439_g.func_70065_x();
         p_71353_1_.func_72838_d(this.field_71439_g);
         this.field_71439_g.field_71158_b = new MovementInputFromOptions(this.field_71474_y);
         this.field_71442_b.func_78748_a(this.field_71439_g);
         this.field_71451_h = this.field_71439_g;
      } else {
         this.field_71469_aa.func_75800_d();
         this.field_71439_g = null;
      }

      System.gc();
      this.field_71423_H = 0L;
   }

   public void func_71360_a(String p_71360_1_, File p_71360_2_) {
      int var3 = p_71360_1_.indexOf("/");
      String var4 = p_71360_1_.substring(0, var3);
      p_71360_1_ = p_71360_1_.substring(var3 + 1);
      if(var4.equalsIgnoreCase("sound3")) {
         this.field_71416_A.func_77372_a(p_71360_1_, p_71360_2_);
      } else if(var4.equalsIgnoreCase("streaming")) {
         this.field_71416_A.func_77374_b(p_71360_1_, p_71360_2_);
      } else if(var4.equalsIgnoreCase("music") || var4.equalsIgnoreCase("newmusic")) {
         this.field_71416_A.func_77365_c(p_71360_1_, p_71360_2_);
      }

   }

   public String func_71393_m() {
      return this.field_71438_f.func_72735_c();
   }

   public String func_71408_n() {
      return this.field_71438_f.func_72723_d();
   }

   public String func_71388_o() {
      return this.field_71441_e.func_72827_u();
   }

   public String func_71374_p() {
      return "P: " + this.field_71452_i.func_78869_b() + ". T: " + this.field_71441_e.func_72981_t();
   }

   public void func_71354_a(int p_71354_1_) {
      this.field_71441_e.func_72974_f();
      this.field_71441_e.func_73022_a();
      int var2 = 0;
      if(this.field_71439_g != null) {
         var2 = this.field_71439_g.field_70157_k;
         this.field_71441_e.func_72900_e(this.field_71439_g);
      }

      this.field_71451_h = null;
      this.field_71439_g = this.field_71442_b.func_78754_a(this.field_71441_e);
      this.field_71439_g.field_71093_bK = p_71354_1_;
      this.field_71451_h = this.field_71439_g;
      this.field_71439_g.func_70065_x();
      this.field_71441_e.func_72838_d(this.field_71439_g);
      this.field_71442_b.func_78745_b(this.field_71439_g);
      this.field_71439_g.field_71158_b = new MovementInputFromOptions(this.field_71474_y);
      this.field_71439_g.field_70157_k = var2;
      this.field_71442_b.func_78748_a(this.field_71439_g);
      if(this.field_71462_r instanceof GuiGameOver) {
         this.func_71373_a((GuiScreen)null);
      }

   }

   void func_71390_a(boolean p_71390_1_) {
      this.field_71459_aj = p_71390_1_;
   }

   public final boolean func_71355_q() {
      return this.field_71459_aj;
   }

   public NetClientHandler func_71391_r() {
      return this.field_71439_g != null?this.field_71439_g.field_71174_a:null;
   }

   public static void main(String[] p_main_0_) {
      HashMap var1 = new HashMap();
      boolean var2 = false;
      boolean var3 = true;
      boolean var4 = false;
      String var5 = "Player" + func_71386_F() % 1000L;
      if(p_main_0_.length > 0) {
         var5 = p_main_0_[0];
      }

      String var6 = "-";
      if(p_main_0_.length > 1) {
         var6 = p_main_0_[1];
      }

      for(int var7 = 2; var7 < p_main_0_.length; ++var7) {
         String var8 = p_main_0_[var7];
         String var9 = var7 == p_main_0_.length - 1?null:p_main_0_[var7 + 1];
         boolean var10 = false;
         if(!var8.equals("-demo") && !var8.equals("--demo")) {
            if(var8.equals("--applet")) {
               var3 = false;
            } else if(var8.equals("--password") && var9 != null) {
               String[] var11 = HttpUtil.func_82718_a(var5, var9);
               if(var11 != null) {
                  var5 = var11[0];
                  var6 = var11[1];
                  System.out.println("Logged in insecurely as " + var5 + " - sessionId is " + var6);
               } else {
                  System.out.println("Could not log in as " + var5 + " with given password");
               }

               var10 = true;
            }
         } else {
            var2 = true;
         }

         if(var10) {
            ++var7;
         }
      }

      var1.put("demo", "" + var2);
      var1.put("stand-alone", "" + var3);
      var1.put("username", var5);
      var1.put("fullscreen", "" + var4);
      var1.put("sessionid", var6);
      Frame var13 = new Frame();
      var13.setTitle("Minecraft");
      var13.setBackground(Color.BLACK);
      JPanel var12 = new JPanel();
      var13.setLayout(new BorderLayout());
      var12.setPreferredSize(new Dimension(854, 480));
      var13.add(var12, "Center");
      var13.pack();
      var13.setLocationRelativeTo((Component)null);
      var13.setVisible(true);
      var13.addWindowListener(new GameWindowListener());
      MinecraftFakeLauncher var14 = new MinecraftFakeLauncher(var1);
      MinecraftApplet var15 = new MinecraftApplet();
      var15.setStub(var14);
      var14.setLayout(new BorderLayout());
      var14.add(var15, "Center");
      var14.validate();
      var13.removeAll();
      var13.setLayout(new BorderLayout());
      var13.add(var14, "Center");
      var13.validate();
      var15.init();
      var15.start();
      Runtime.getRuntime().addShutdownHook(new ThreadShutdown());
   }

   public static boolean func_71382_s() {
      return field_71432_P == null || !field_71432_P.field_71474_y.field_74319_N;
   }

   public static boolean func_71375_t() {
      return field_71432_P != null && field_71432_P.field_71474_y.field_74347_j;
   }

   public static boolean func_71379_u() {
      return field_71432_P != null && field_71432_P.field_71474_y.field_74348_k;
   }

   public static boolean func_71368_v() {
      return field_71432_P != null && field_71432_P.field_71474_y.field_74330_P;
   }

   public boolean func_71409_c(String p_71409_1_) {
      return !p_71409_1_.startsWith("/")?false:false;
   }

   private void func_71397_M() {
      if(this.field_71476_x != null) {
         boolean var1 = this.field_71439_g.field_71075_bZ.field_75098_d;
         int var3 = 0;
         boolean var4 = false;
         int var2;
         int var5;
         if(this.field_71476_x.field_72313_a == EnumMovingObjectType.TILE) {
            var5 = this.field_71476_x.field_72311_b;
            int var6 = this.field_71476_x.field_72312_c;
            int var7 = this.field_71476_x.field_72309_d;
            Block var8 = Block.field_71973_m[this.field_71441_e.func_72798_a(var5, var6, var7)];
            if(var8 == null) {
               return;
            }

            var2 = var8.func_71922_a(this.field_71441_e, var5, var6, var7);
            if(var2 == 0) {
               return;
            }

            var4 = Item.field_77698_e[var2].func_77614_k();
            int var9 = var2 < 256 && !Block.field_71973_m[var8.field_71990_ca].func_82505_u_()?var2:var8.field_71990_ca;
            var3 = Block.field_71973_m[var9].func_71873_h(this.field_71441_e, var5, var6, var7);
         } else {
            if(this.field_71476_x.field_72313_a != EnumMovingObjectType.ENTITY || this.field_71476_x.field_72308_g == null || !var1) {
               return;
            }

            if(this.field_71476_x.field_72308_g instanceof EntityPainting) {
               var2 = Item.field_77780_as.field_77779_bT;
            } else if(this.field_71476_x.field_72308_g instanceof EntityItemFrame) {
               EntityItemFrame var10 = (EntityItemFrame)this.field_71476_x.field_72308_g;
               if(var10.func_82335_i() == null) {
                  var2 = Item.field_82802_bI.field_77779_bT;
               } else {
                  var2 = var10.func_82335_i().field_77993_c;
                  var3 = var10.func_82335_i().func_77960_j();
                  var4 = true;
               }
            } else if(this.field_71476_x.field_72308_g instanceof EntityMinecart) {
               EntityMinecart var11 = (EntityMinecart)this.field_71476_x.field_72308_g;
               if(var11.field_70505_a == 2) {
                  var2 = Item.field_77763_aO.field_77779_bT;
               } else if(var11.field_70505_a == 1) {
                  var2 = Item.field_77762_aN.field_77779_bT;
               } else {
                  var2 = Item.field_77773_az.field_77779_bT;
               }
            } else if(this.field_71476_x.field_72308_g instanceof EntityBoat) {
               var2 = Item.field_77769_aE.field_77779_bT;
            } else {
               var2 = Item.field_77815_bC.field_77779_bT;
               var3 = EntityList.func_75619_a(this.field_71476_x.field_72308_g);
               var4 = true;
               if(var3 <= 0 || !EntityList.field_75627_a.containsKey(Integer.valueOf(var3))) {
                  return;
               }
            }
         }

         this.field_71439_g.field_71071_by.func_70433_a(var2, var3, var4, var1);
         if(var1) {
            var5 = this.field_71439_g.field_71069_bz.field_75151_b.size() - 9 + this.field_71439_g.field_71071_by.field_70461_c;
            this.field_71442_b.func_78761_a(this.field_71439_g.field_71071_by.func_70301_a(this.field_71439_g.field_71071_by.field_70461_c), var5);
         }

      }
   }

   public CrashReport func_71396_d(CrashReport p_71396_1_) {
      p_71396_1_.func_71500_a("LWJGL", new CallableLWJGLVersion(this));
      p_71396_1_.func_71500_a("OpenGL", new CallableGLInfo(this));
      p_71396_1_.func_71500_a("Is Modded", new CallableModded(this));
      p_71396_1_.func_71500_a("Type", new CallableType2(this));
      p_71396_1_.func_71500_a("Texture Pack", new CallableTexturePack(this));
      p_71396_1_.func_71500_a("Profiler Position", new CallableClientProfiler(this));
      p_71396_1_.func_71500_a("Vec3 Pool Size", new CallableClientMemoryStats(this));
      if(this.field_71441_e != null) {
         this.field_71441_e.func_72914_a(p_71396_1_);
      }

      return p_71396_1_;
   }

   public static Minecraft func_71410_x() {
      return field_71432_P;
   }

   public void func_71395_y() {
      this.field_71468_ad = true;
   }

   public void func_70000_a(PlayerUsageSnooper p_70000_1_) {
      p_70000_1_.func_76472_a("fps", Integer.valueOf(field_71470_ab));
      p_70000_1_.func_76472_a("texpack_name", this.field_71418_C.func_77292_e().func_77538_c());
      p_70000_1_.func_76472_a("texpack_resolution", Integer.valueOf(this.field_71418_C.func_77292_e().func_77534_f()));
      p_70000_1_.func_76472_a("vsync_enabled", Boolean.valueOf(this.field_71474_y.field_74352_v));
      p_70000_1_.func_76472_a("display_frequency", Integer.valueOf(Display.getDisplayMode().getFrequency()));
      p_70000_1_.func_76472_a("display_type", this.field_71431_Q?"fullscreen":"windowed");
      if(this.field_71437_Z != null && this.field_71437_Z.func_80003_ah() != null) {
         p_70000_1_.func_76472_a("snooper_partner", this.field_71437_Z.func_80003_ah().func_80006_f());
      }

   }

   public void func_70001_b(PlayerUsageSnooper p_70001_1_) {
      p_70001_1_.func_76472_a("opengl_version", GL11.glGetString(7938));
      p_70001_1_.func_76472_a("opengl_vendor", GL11.glGetString(7936));
      p_70001_1_.func_76472_a("client_brand", ClientBrandRetriever.getClientModName());
      p_70001_1_.func_76472_a("applet", Boolean.valueOf(this.field_71448_m));
      ContextCapabilities var2 = GLContext.getCapabilities();
      p_70001_1_.func_76472_a("gl_caps[ARB_multitexture]", Boolean.valueOf(var2.GL_ARB_multitexture));
      p_70001_1_.func_76472_a("gl_caps[ARB_multisample]", Boolean.valueOf(var2.GL_ARB_multisample));
      p_70001_1_.func_76472_a("gl_caps[ARB_texture_cube_map]", Boolean.valueOf(var2.GL_ARB_texture_cube_map));
      p_70001_1_.func_76472_a("gl_caps[ARB_vertex_blend]", Boolean.valueOf(var2.GL_ARB_vertex_blend));
      p_70001_1_.func_76472_a("gl_caps[ARB_matrix_palette]", Boolean.valueOf(var2.GL_ARB_matrix_palette));
      p_70001_1_.func_76472_a("gl_caps[ARB_vertex_program]", Boolean.valueOf(var2.GL_ARB_vertex_program));
      p_70001_1_.func_76472_a("gl_caps[ARB_vertex_shader]", Boolean.valueOf(var2.GL_ARB_vertex_shader));
      p_70001_1_.func_76472_a("gl_caps[ARB_fragment_program]", Boolean.valueOf(var2.GL_ARB_fragment_program));
      p_70001_1_.func_76472_a("gl_caps[ARB_fragment_shader]", Boolean.valueOf(var2.GL_ARB_fragment_shader));
      p_70001_1_.func_76472_a("gl_caps[ARB_shader_objects]", Boolean.valueOf(var2.GL_ARB_shader_objects));
      p_70001_1_.func_76472_a("gl_caps[ARB_vertex_buffer_object]", Boolean.valueOf(var2.GL_ARB_vertex_buffer_object));
      p_70001_1_.func_76472_a("gl_caps[ARB_framebuffer_object]", Boolean.valueOf(var2.GL_ARB_framebuffer_object));
      p_70001_1_.func_76472_a("gl_caps[ARB_pixel_buffer_object]", Boolean.valueOf(var2.GL_ARB_pixel_buffer_object));
      p_70001_1_.func_76472_a("gl_caps[ARB_uniform_buffer_object]", Boolean.valueOf(var2.GL_ARB_uniform_buffer_object));
      p_70001_1_.func_76472_a("gl_caps[ARB_texture_non_power_of_two]", Boolean.valueOf(var2.GL_ARB_texture_non_power_of_two));
      p_70001_1_.func_76472_a("gl_caps[gl_max_vertex_uniforms]", Integer.valueOf(GL11.glGetInteger('\u8b4a')));
      p_70001_1_.func_76472_a("gl_caps[gl_max_fragment_uniforms]", Integer.valueOf(GL11.glGetInteger('\u8b49')));
      p_70001_1_.func_76472_a("gl_max_texture_size", Integer.valueOf(func_71369_N()));
   }

   private static int func_71369_N() {
      for(int var0 = 16384; var0 > 0; var0 >>= 1) {
         GL11.glTexImage2D('\u8064', 0, 6408, var0, var0, 0, 6408, 5121, (ByteBuffer)null);
         int var1 = GL11.glGetTexLevelParameteri('\u8064', 0, 4096);
         if(var1 != 0) {
            return var0;
         }
      }

      return -1;
   }

   public boolean func_70002_Q() {
      return this.field_71474_y.field_74355_t;
   }

   public void func_71351_a(ServerData p_71351_1_) {
      this.field_71422_O = p_71351_1_;
   }

   public ServerData func_71362_z() {
      return this.field_71422_O;
   }

   public boolean func_71387_A() {
      return this.field_71455_al;
   }

   public boolean func_71356_B() {
      return this.field_71455_al && this.field_71437_Z != null;
   }

   public IntegratedServer func_71401_C() {
      return this.field_71437_Z;
   }

   public static void func_71363_D() {
      if(field_71432_P != null) {
         IntegratedServer var0 = field_71432_P.func_71401_C();
         if(var0 != null) {
            var0.func_71260_j();
         }

      }
   }

   public PlayerUsageSnooper func_71378_E() {
      return this.field_71427_U;
   }

   public static long func_71386_F() {
      return Sys.getTime() * 1000L / Sys.getTimerResolution();
   }

   public boolean func_71372_G() {
      return this.field_71431_Q;
   }

}
