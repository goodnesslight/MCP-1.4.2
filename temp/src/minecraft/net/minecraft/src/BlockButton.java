package net.minecraft.src;

import java.util.List;
import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityArrow;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockButton extends Block {

   private final boolean field_82537_a;


   protected BlockButton(int p_i5100_1_, int p_i5100_2_, boolean p_i5100_3_) {
      super(p_i5100_1_, p_i5100_2_, Material.field_76265_p);
      this.func_71907_b(true);
      this.func_71849_a(CreativeTabs.field_78028_d);
      this.field_82537_a = p_i5100_3_;
   }

   public AxisAlignedBB func_71872_e(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_) {
      return null;
   }

   public int func_71859_p_() {
      return this.field_82537_a?30:20;
   }

   public boolean func_71926_d() {
      return false;
   }

   public boolean func_71886_c() {
      return false;
   }

   public boolean func_71850_a_(World p_71850_1_, int p_71850_2_, int p_71850_3_, int p_71850_4_, int p_71850_5_) {
      return p_71850_5_ == 2 && p_71850_1_.func_72809_s(p_71850_2_, p_71850_3_, p_71850_4_ + 1)?true:(p_71850_5_ == 3 && p_71850_1_.func_72809_s(p_71850_2_, p_71850_3_, p_71850_4_ - 1)?true:(p_71850_5_ == 4 && p_71850_1_.func_72809_s(p_71850_2_ + 1, p_71850_3_, p_71850_4_)?true:p_71850_5_ == 5 && p_71850_1_.func_72809_s(p_71850_2_ - 1, p_71850_3_, p_71850_4_)));
   }

   public boolean func_71930_b(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_) {
      return p_71930_1_.func_72809_s(p_71930_2_ - 1, p_71930_3_, p_71930_4_)?true:(p_71930_1_.func_72809_s(p_71930_2_ + 1, p_71930_3_, p_71930_4_)?true:(p_71930_1_.func_72809_s(p_71930_2_, p_71930_3_, p_71930_4_ - 1)?true:p_71930_1_.func_72809_s(p_71930_2_, p_71930_3_, p_71930_4_ + 1)));
   }

   public void func_71909_a(World p_71909_1_, int p_71909_2_, int p_71909_3_, int p_71909_4_, int p_71909_5_, float p_71909_6_, float p_71909_7_, float p_71909_8_) {
      int var9 = p_71909_1_.func_72805_g(p_71909_2_, p_71909_3_, p_71909_4_);
      int var10 = var9 & 8;
      var9 &= 7;
      if(p_71909_5_ == 2 && p_71909_1_.func_72809_s(p_71909_2_, p_71909_3_, p_71909_4_ + 1)) {
         var9 = 4;
      } else if(p_71909_5_ == 3 && p_71909_1_.func_72809_s(p_71909_2_, p_71909_3_, p_71909_4_ - 1)) {
         var9 = 3;
      } else if(p_71909_5_ == 4 && p_71909_1_.func_72809_s(p_71909_2_ + 1, p_71909_3_, p_71909_4_)) {
         var9 = 2;
      } else if(p_71909_5_ == 5 && p_71909_1_.func_72809_s(p_71909_2_ - 1, p_71909_3_, p_71909_4_)) {
         var9 = 1;
      } else {
         var9 = this.func_72260_l(p_71909_1_, p_71909_2_, p_71909_3_, p_71909_4_);
      }

      p_71909_1_.func_72921_c(p_71909_2_, p_71909_3_, p_71909_4_, var9 + var10);
   }

   private int func_72260_l(World p_72260_1_, int p_72260_2_, int p_72260_3_, int p_72260_4_) {
      return p_72260_1_.func_72809_s(p_72260_2_ - 1, p_72260_3_, p_72260_4_)?1:(p_72260_1_.func_72809_s(p_72260_2_ + 1, p_72260_3_, p_72260_4_)?2:(p_72260_1_.func_72809_s(p_72260_2_, p_72260_3_, p_72260_4_ - 1)?3:(p_72260_1_.func_72809_s(p_72260_2_, p_72260_3_, p_72260_4_ + 1)?4:1)));
   }

   public void func_71863_a(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_) {
      if(this.func_72261_n(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_)) {
         int var6 = p_71863_1_.func_72805_g(p_71863_2_, p_71863_3_, p_71863_4_) & 7;
         boolean var7 = false;
         if(!p_71863_1_.func_72809_s(p_71863_2_ - 1, p_71863_3_, p_71863_4_) && var6 == 1) {
            var7 = true;
         }

         if(!p_71863_1_.func_72809_s(p_71863_2_ + 1, p_71863_3_, p_71863_4_) && var6 == 2) {
            var7 = true;
         }

         if(!p_71863_1_.func_72809_s(p_71863_2_, p_71863_3_, p_71863_4_ - 1) && var6 == 3) {
            var7 = true;
         }

         if(!p_71863_1_.func_72809_s(p_71863_2_, p_71863_3_, p_71863_4_ + 1) && var6 == 4) {
            var7 = true;
         }

         if(var7) {
            this.func_71897_c(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_1_.func_72805_g(p_71863_2_, p_71863_3_, p_71863_4_), 0);
            p_71863_1_.func_72859_e(p_71863_2_, p_71863_3_, p_71863_4_, 0);
         }
      }

   }

   private boolean func_72261_n(World p_72261_1_, int p_72261_2_, int p_72261_3_, int p_72261_4_) {
      if(!this.func_71930_b(p_72261_1_, p_72261_2_, p_72261_3_, p_72261_4_)) {
         this.func_71897_c(p_72261_1_, p_72261_2_, p_72261_3_, p_72261_4_, p_72261_1_.func_72805_g(p_72261_2_, p_72261_3_, p_72261_4_), 0);
         p_72261_1_.func_72859_e(p_72261_2_, p_72261_3_, p_72261_4_, 0);
         return false;
      } else {
         return true;
      }
   }

   public void func_71902_a(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_) {
      int var5 = p_71902_1_.func_72805_g(p_71902_2_, p_71902_3_, p_71902_4_);
      this.func_82534_e(var5);
   }

   private void func_82534_e(int p_82534_1_) {
      int var2 = p_82534_1_ & 7;
      boolean var3 = (p_82534_1_ & 8) > 0;
      float var4 = 0.375F;
      float var5 = 0.625F;
      float var6 = 0.1875F;
      float var7 = 0.125F;
      if(var3) {
         var7 = 0.0625F;
      }

      if(var2 == 1) {
         this.func_71905_a(0.0F, var4, 0.5F - var6, var7, var5, 0.5F + var6);
      } else if(var2 == 2) {
         this.func_71905_a(1.0F - var7, var4, 0.5F - var6, 1.0F, var5, 0.5F + var6);
      } else if(var2 == 3) {
         this.func_71905_a(0.5F - var6, var4, 0.0F, 0.5F + var6, var5, var7);
      } else if(var2 == 4) {
         this.func_71905_a(0.5F - var6, var4, 1.0F - var7, 0.5F + var6, var5, 1.0F);
      }

   }

   public void func_71921_a(World p_71921_1_, int p_71921_2_, int p_71921_3_, int p_71921_4_, EntityPlayer p_71921_5_) {}

   public boolean func_71903_a(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_) {
      int var10 = p_71903_1_.func_72805_g(p_71903_2_, p_71903_3_, p_71903_4_);
      int var11 = var10 & 7;
      int var12 = 8 - (var10 & 8);
      if(var12 == 0) {
         return true;
      } else {
         p_71903_1_.func_72921_c(p_71903_2_, p_71903_3_, p_71903_4_, var11 + var12);
         p_71903_1_.func_72909_d(p_71903_2_, p_71903_3_, p_71903_4_, p_71903_2_, p_71903_3_, p_71903_4_);
         p_71903_1_.func_72908_a((double)p_71903_2_ + 0.5D, (double)p_71903_3_ + 0.5D, (double)p_71903_4_ + 0.5D, "random.click", 0.3F, 0.6F);
         this.func_82536_d(p_71903_1_, p_71903_2_, p_71903_3_, p_71903_4_, var11);
         p_71903_1_.func_72836_a(p_71903_2_, p_71903_3_, p_71903_4_, this.field_71990_ca, this.func_71859_p_());
         return true;
      }
   }

   public void func_71852_a(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_) {
      if((p_71852_6_ & 8) > 0) {
         int var7 = p_71852_6_ & 7;
         this.func_82536_d(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, var7);
      }

      super.func_71852_a(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, p_71852_5_, p_71852_6_);
   }

   public boolean func_71865_a(IBlockAccess p_71865_1_, int p_71865_2_, int p_71865_3_, int p_71865_4_, int p_71865_5_) {
      return (p_71865_1_.func_72805_g(p_71865_2_, p_71865_3_, p_71865_4_) & 8) > 0;
   }

   public boolean func_71855_c(IBlockAccess p_71855_1_, int p_71855_2_, int p_71855_3_, int p_71855_4_, int p_71855_5_) {
      int var6 = p_71855_1_.func_72805_g(p_71855_2_, p_71855_3_, p_71855_4_);
      if((var6 & 8) == 0) {
         return false;
      } else {
         int var7 = var6 & 7;
         return var7 == 5 && p_71855_5_ == 1?true:(var7 == 4 && p_71855_5_ == 2?true:(var7 == 3 && p_71855_5_ == 3?true:(var7 == 2 && p_71855_5_ == 4?true:var7 == 1 && p_71855_5_ == 5)));
      }
   }

   public boolean func_71853_i() {
      return true;
   }

   public void func_71847_b(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_) {
      if(!p_71847_1_.field_72995_K) {
         int var6 = p_71847_1_.func_72805_g(p_71847_2_, p_71847_3_, p_71847_4_);
         if((var6 & 8) != 0) {
            if(this.field_82537_a) {
               this.func_82535_o(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_);
            } else {
               p_71847_1_.func_72921_c(p_71847_2_, p_71847_3_, p_71847_4_, var6 & 7);
               int var7 = var6 & 7;
               this.func_82536_d(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, var7);
               p_71847_1_.func_72908_a((double)p_71847_2_ + 0.5D, (double)p_71847_3_ + 0.5D, (double)p_71847_4_ + 0.5D, "random.click", 0.3F, 0.5F);
               p_71847_1_.func_72909_d(p_71847_2_, p_71847_3_, p_71847_4_, p_71847_2_, p_71847_3_, p_71847_4_);
            }

         }
      }
   }

   public void func_71919_f() {
      float var1 = 0.1875F;
      float var2 = 0.125F;
      float var3 = 0.125F;
      this.func_71905_a(0.5F - var1, 0.5F - var2, 0.5F - var3, 0.5F + var1, 0.5F + var2, 0.5F + var3);
   }

   public void func_71869_a(World p_71869_1_, int p_71869_2_, int p_71869_3_, int p_71869_4_, Entity p_71869_5_) {
      if(!p_71869_1_.field_72995_K) {
         if(this.field_82537_a) {
            if((p_71869_1_.func_72805_g(p_71869_2_, p_71869_3_, p_71869_4_) & 8) == 0) {
               this.func_82535_o(p_71869_1_, p_71869_2_, p_71869_3_, p_71869_4_);
            }
         }
      }
   }

   private void func_82535_o(World p_82535_1_, int p_82535_2_, int p_82535_3_, int p_82535_4_) {
      int var5 = p_82535_1_.func_72805_g(p_82535_2_, p_82535_3_, p_82535_4_);
      int var6 = var5 & 7;
      boolean var7 = (var5 & 8) != 0;
      this.func_82534_e(var5);
      List var9 = p_82535_1_.func_72872_a(EntityArrow.class, AxisAlignedBB.func_72332_a().func_72299_a((double)p_82535_2_ + this.field_72026_ch, (double)p_82535_3_ + this.field_72023_ci, (double)p_82535_4_ + this.field_72024_cj, (double)p_82535_2_ + this.field_72021_ck, (double)p_82535_3_ + this.field_72022_cl, (double)p_82535_4_ + this.field_72019_cm));
      boolean var8 = !var9.isEmpty();
      if(var8 && !var7) {
         p_82535_1_.func_72921_c(p_82535_2_, p_82535_3_, p_82535_4_, var6 | 8);
         this.func_82536_d(p_82535_1_, p_82535_2_, p_82535_3_, p_82535_4_, var6);
         p_82535_1_.func_72909_d(p_82535_2_, p_82535_3_, p_82535_4_, p_82535_2_, p_82535_3_, p_82535_4_);
         p_82535_1_.func_72908_a((double)p_82535_2_ + 0.5D, (double)p_82535_3_ + 0.5D, (double)p_82535_4_ + 0.5D, "random.click", 0.3F, 0.6F);
      }

      if(!var8 && var7) {
         p_82535_1_.func_72921_c(p_82535_2_, p_82535_3_, p_82535_4_, var6);
         this.func_82536_d(p_82535_1_, p_82535_2_, p_82535_3_, p_82535_4_, var6);
         p_82535_1_.func_72909_d(p_82535_2_, p_82535_3_, p_82535_4_, p_82535_2_, p_82535_3_, p_82535_4_);
         p_82535_1_.func_72908_a((double)p_82535_2_ + 0.5D, (double)p_82535_3_ + 0.5D, (double)p_82535_4_ + 0.5D, "random.click", 0.3F, 0.5F);
      }

      if(var8) {
         p_82535_1_.func_72836_a(p_82535_2_, p_82535_3_, p_82535_4_, this.field_71990_ca, this.func_71859_p_());
      }

   }

   private void func_82536_d(World p_82536_1_, int p_82536_2_, int p_82536_3_, int p_82536_4_, int p_82536_5_) {
      p_82536_1_.func_72898_h(p_82536_2_, p_82536_3_, p_82536_4_, this.field_71990_ca);
      if(p_82536_5_ == 1) {
         p_82536_1_.func_72898_h(p_82536_2_ - 1, p_82536_3_, p_82536_4_, this.field_71990_ca);
      } else if(p_82536_5_ == 2) {
         p_82536_1_.func_72898_h(p_82536_2_ + 1, p_82536_3_, p_82536_4_, this.field_71990_ca);
      } else if(p_82536_5_ == 3) {
         p_82536_1_.func_72898_h(p_82536_2_, p_82536_3_, p_82536_4_ - 1, this.field_71990_ca);
      } else if(p_82536_5_ == 4) {
         p_82536_1_.func_72898_h(p_82536_2_, p_82536_3_, p_82536_4_ + 1, this.field_71990_ca);
      } else {
         p_82536_1_.func_72898_h(p_82536_2_, p_82536_3_ - 1, p_82536_4_, this.field_71990_ca);
      }

   }
}
