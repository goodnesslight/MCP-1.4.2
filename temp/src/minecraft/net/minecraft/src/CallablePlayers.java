package net.minecraft.src;

import java.util.concurrent.Callable;
import net.minecraft.server.MinecraftServer;

public class CallablePlayers implements Callable {

   // $FF: synthetic field
   final MinecraftServer field_82550_a;


   public CallablePlayers(MinecraftServer p_i3374_1_) {
      this.field_82550_a = p_i3374_1_;
   }

   public String func_82549_a() {
      return MinecraftServer.func_71196_a(this.field_82550_a).func_72394_k() + " / " + MinecraftServer.func_71196_a(this.field_82550_a).func_72352_l() + "; " + MinecraftServer.func_71196_a(this.field_82550_a).field_72404_b;
   }

   // $FF: synthetic method
   public Object call() {
      return this.func_82549_a();
   }
}
