package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityAIBase;
import net.minecraft.src.EntityVillager;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Village;
import net.minecraft.src.World;

public class EntityAIVillagerMate extends EntityAIBase {

   private EntityVillager field_75450_b;
   private EntityVillager field_75451_c;
   private World field_75448_d;
   private int field_75449_e = 0;
   Village field_75452_a;


   public EntityAIVillagerMate(EntityVillager p_i3475_1_) {
      this.field_75450_b = p_i3475_1_;
      this.field_75448_d = p_i3475_1_.field_70170_p;
      this.func_75248_a(3);
   }

   public boolean func_75250_a() {
      if(this.field_75450_b.func_70874_b() != 0) {
         return false;
      } else if(this.field_75450_b.func_70681_au().nextInt(500) != 0) {
         return false;
      } else {
         this.field_75452_a = this.field_75448_d.field_72982_D.func_75550_a(MathHelper.func_76128_c(this.field_75450_b.field_70165_t), MathHelper.func_76128_c(this.field_75450_b.field_70163_u), MathHelper.func_76128_c(this.field_75450_b.field_70161_v), 0);
         if(this.field_75452_a == null) {
            return false;
         } else if(!this.func_75446_f()) {
            return false;
         } else {
            Entity var1 = this.field_75448_d.func_72857_a(EntityVillager.class, this.field_75450_b.field_70121_D.func_72314_b(8.0D, 3.0D, 8.0D), this.field_75450_b);
            if(var1 == null) {
               return false;
            } else {
               this.field_75451_c = (EntityVillager)var1;
               return this.field_75451_c.func_70874_b() == 0;
            }
         }
      }
   }

   public void func_75249_e() {
      this.field_75449_e = 300;
      this.field_75450_b.func_70947_e(true);
   }

   public void func_75251_c() {
      this.field_75452_a = null;
      this.field_75451_c = null;
      this.field_75450_b.func_70947_e(false);
   }

   public boolean func_75253_b() {
      return this.field_75449_e >= 0 && this.func_75446_f() && this.field_75450_b.func_70874_b() == 0;
   }

   public void func_75246_d() {
      --this.field_75449_e;
      this.field_75450_b.func_70671_ap().func_75651_a(this.field_75451_c, 10.0F, 30.0F);
      if(this.field_75450_b.func_70068_e(this.field_75451_c) > 2.25D) {
         this.field_75450_b.func_70661_as().func_75497_a(this.field_75451_c, 0.25F);
      } else if(this.field_75449_e == 0 && this.field_75451_c.func_70941_o()) {
         this.func_75447_i();
      }

      if(this.field_75450_b.func_70681_au().nextInt(35) == 0) {
         this.field_75448_d.func_72960_a(this.field_75450_b, (byte)12);
      }

   }

   private boolean func_75446_f() {
      if(!this.field_75452_a.func_82686_i()) {
         return false;
      } else {
         int var1 = (int)((double)((float)this.field_75452_a.func_75567_c()) * 0.35D);
         return this.field_75452_a.func_75562_e() < var1;
      }
   }

   private void func_75447_i() {
      EntityVillager var1 = new EntityVillager(this.field_75448_d);
      this.field_75451_c.func_70873_a(6000);
      this.field_75450_b.func_70873_a(6000);
      var1.func_70873_a(-24000);
      var1.func_70938_b(this.field_75450_b.func_70681_au().nextInt(5));
      var1.func_70012_b(this.field_75450_b.field_70165_t, this.field_75450_b.field_70163_u, this.field_75450_b.field_70161_v, 0.0F, 0.0F);
      this.field_75448_d.func_72838_d(var1);
      this.field_75448_d.func_72960_a(var1, (byte)12);
   }
}
