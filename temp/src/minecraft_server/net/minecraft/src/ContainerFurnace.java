package net.minecraft.src;

import java.util.Iterator;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.ICrafting;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraft.src.SlotFurnace;
import net.minecraft.src.TileEntityFurnace;

public class ContainerFurnace extends Container {

   private TileEntityFurnace field_75158_e;
   private int field_75156_f = 0;
   private int field_75157_g = 0;
   private int field_75159_h = 0;


   public ContainerFurnace(InventoryPlayer p_i3607_1_, TileEntityFurnace p_i3607_2_) {
      this.field_75158_e = p_i3607_2_;
      this.func_75146_a(new Slot(p_i3607_2_, 0, 56, 17));
      this.func_75146_a(new Slot(p_i3607_2_, 1, 56, 53));
      this.func_75146_a(new SlotFurnace(p_i3607_1_.field_70458_d, p_i3607_2_, 2, 116, 35));

      int var3;
      for(var3 = 0; var3 < 3; ++var3) {
         for(int var4 = 0; var4 < 9; ++var4) {
            this.func_75146_a(new Slot(p_i3607_1_, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
         }
      }

      for(var3 = 0; var3 < 9; ++var3) {
         this.func_75146_a(new Slot(p_i3607_1_, var3, 8 + var3 * 18, 142));
      }

   }

   public void func_75132_a(ICrafting p_75132_1_) {
      super.func_75132_a(p_75132_1_);
      p_75132_1_.func_71112_a(this, 0, this.field_75158_e.field_70406_c);
      p_75132_1_.func_71112_a(this, 1, this.field_75158_e.field_70407_a);
      p_75132_1_.func_71112_a(this, 2, this.field_75158_e.field_70405_b);
   }

   public void func_75142_b() {
      super.func_75142_b();
      Iterator var1 = this.field_75149_d.iterator();

      while(var1.hasNext()) {
         ICrafting var2 = (ICrafting)var1.next();
         if(this.field_75156_f != this.field_75158_e.field_70406_c) {
            var2.func_71112_a(this, 0, this.field_75158_e.field_70406_c);
         }

         if(this.field_75157_g != this.field_75158_e.field_70407_a) {
            var2.func_71112_a(this, 1, this.field_75158_e.field_70407_a);
         }

         if(this.field_75159_h != this.field_75158_e.field_70405_b) {
            var2.func_71112_a(this, 2, this.field_75158_e.field_70405_b);
         }
      }

      this.field_75156_f = this.field_75158_e.field_70406_c;
      this.field_75157_g = this.field_75158_e.field_70407_a;
      this.field_75159_h = this.field_75158_e.field_70405_b;
   }

   public boolean func_75145_c(EntityPlayer p_75145_1_) {
      return this.field_75158_e.func_70300_a(p_75145_1_);
   }

   public ItemStack func_82846_b(EntityPlayer p_82846_1_, int p_82846_2_) {
      ItemStack var3 = null;
      Slot var4 = (Slot)this.field_75151_b.get(p_82846_2_);
      if(var4 != null && var4.func_75216_d()) {
         ItemStack var5 = var4.func_75211_c();
         var3 = var5.func_77946_l();
         if(p_82846_2_ == 2) {
            if(!this.func_75135_a(var5, 3, 39, true)) {
               return null;
            }

            var4.func_75220_a(var5, var3);
         } else if(p_82846_2_ != 1 && p_82846_2_ != 0) {
            if(FurnaceRecipes.func_77602_a().func_77603_b(var5.func_77973_b().field_77779_bT) != null) {
               if(!this.func_75135_a(var5, 0, 1, false)) {
                  return null;
               }
            } else if(TileEntityFurnace.func_70401_b(var5)) {
               if(!this.func_75135_a(var5, 1, 2, false)) {
                  return null;
               }
            } else if(p_82846_2_ >= 3 && p_82846_2_ < 30) {
               if(!this.func_75135_a(var5, 30, 39, false)) {
                  return null;
               }
            } else if(p_82846_2_ >= 30 && p_82846_2_ < 39 && !this.func_75135_a(var5, 3, 30, false)) {
               return null;
            }
         } else if(!this.func_75135_a(var5, 3, 39, false)) {
            return null;
         }

         if(var5.field_77994_a == 0) {
            var4.func_75215_d((ItemStack)null);
         } else {
            var4.func_75218_e();
         }

         if(var5.field_77994_a == var3.field_77994_a) {
            return null;
         }

         var4.func_82870_a(p_82846_1_, var5);
      }

      return var3;
   }
}
