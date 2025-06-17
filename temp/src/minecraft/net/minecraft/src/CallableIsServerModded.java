package net.minecraft.src;

import java.util.concurrent.Callable;
import net.minecraft.server.MinecraftServer;

public class CallableIsServerModded implements Callable {

   // $FF: synthetic field
   final MinecraftServer field_82557_a;


   public CallableIsServerModded(MinecraftServer p_i3372_1_) {
      this.field_82557_a = p_i3372_1_;
   }

   public String func_82556_a() {
      String var1 = this.field_82557_a.getServerModName();
      return !var1.equals("vanilla")?"Definitely; \'" + var1 + "\'":"Unknown (can\'t tell)";
   }

   // $FF: synthetic method
   public Object call() {
      return this.func_82556_a();
   }
}
