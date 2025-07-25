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
import net.minecraft.src.IPlayerUsage;
import net.minecraft.src.PlayerUsageSnooperThread;

public class PlayerUsageSnooper {

   private Map field_76482_a = new HashMap();
   private final String field_76480_b = UUID.randomUUID().toString();
   private final URL field_76481_c;
   private final IPlayerUsage field_76478_d;
   private final Timer field_76479_e = new Timer("Snooper Timer", true);
   private final Object field_76476_f = new Object();
   private boolean field_76477_g = false;
   private int field_76483_h = 0;


   public PlayerUsageSnooper(String p_i3428_1_, IPlayerUsage p_i3428_2_) {
      try {
         this.field_76481_c = new URL("http://snoop.minecraft.net/" + p_i3428_1_ + "?version=" + 1);
      } catch (MalformedURLException var4) {
         throw new IllegalArgumentException();
      }

      this.field_76478_d = p_i3428_2_;
   }

   public void func_76463_a() {
      if(!this.field_76477_g) {
         this.field_76477_g = true;
         this.func_76464_f();
         this.field_76479_e.schedule(new PlayerUsageSnooperThread(this), 0L, 900000L);
      }
   }

   private void func_76464_f() {
      this.func_76467_g();
      this.func_76472_a("snooper_token", this.field_76480_b);
      this.func_76472_a("os_name", System.getProperty("os.name"));
      this.func_76472_a("os_version", System.getProperty("os.version"));
      this.func_76472_a("os_architecture", System.getProperty("os.arch"));
      this.func_76472_a("java_version", System.getProperty("java.version"));
      this.func_76472_a("version", "1.4.2");
      this.field_76478_d.func_70001_b(this);
   }

   private void func_76467_g() {
      RuntimeMXBean var1 = ManagementFactory.getRuntimeMXBean();
      List var2 = var1.getInputArguments();
      int var3 = 0;
      Iterator var4 = var2.iterator();

      while(var4.hasNext()) {
         String var5 = (String)var4.next();
         if(var5.startsWith("-X")) {
            this.func_76472_a("jvm_arg[" + var3++ + "]", var5);
         }
      }

      this.func_76472_a("jvm_args", Integer.valueOf(var3));
   }

   public void func_76471_b() {
      this.func_76472_a("memory_total", Long.valueOf(Runtime.getRuntime().totalMemory()));
      this.func_76472_a("memory_max", Long.valueOf(Runtime.getRuntime().maxMemory()));
      this.func_76472_a("memory_free", Long.valueOf(Runtime.getRuntime().freeMemory()));
      this.func_76472_a("cpu_cores", Integer.valueOf(Runtime.getRuntime().availableProcessors()));
      this.field_76478_d.func_70000_a(this);
   }

   public void func_76472_a(String p_76472_1_, Object p_76472_2_) {
      Object var3 = this.field_76476_f;
      synchronized(this.field_76476_f) {
         this.field_76482_a.put(p_76472_1_, p_76472_2_);
      }
   }

   public boolean func_76468_d() {
      return this.field_76477_g;
   }

   public void func_76470_e() {
      this.field_76479_e.cancel();
   }

   // $FF: synthetic method
   static IPlayerUsage func_76473_a(PlayerUsageSnooper p_76473_0_) {
      return p_76473_0_.field_76478_d;
   }

   // $FF: synthetic method
   static Object func_76474_b(PlayerUsageSnooper p_76474_0_) {
      return p_76474_0_.field_76476_f;
   }

   // $FF: synthetic method
   static Map func_76469_c(PlayerUsageSnooper p_76469_0_) {
      return p_76469_0_.field_76482_a;
   }

   // $FF: synthetic method
   static int func_76466_d(PlayerUsageSnooper p_76466_0_) {
      return p_76466_0_.field_76483_h++;
   }

   // $FF: synthetic method
   static URL func_76475_e(PlayerUsageSnooper p_76475_0_) {
      return p_76475_0_.field_76481_c;
   }
}
