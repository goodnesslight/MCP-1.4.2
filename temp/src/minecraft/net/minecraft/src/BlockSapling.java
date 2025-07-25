package net.minecraft.src;

import java.util.List;
import java.util.Random;
import net.minecraft.src.BlockFlower;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenBigTree;
import net.minecraft.src.WorldGenForest;
import net.minecraft.src.WorldGenHugeTrees;
import net.minecraft.src.WorldGenTaiga2;
import net.minecraft.src.WorldGenTrees;
import net.minecraft.src.WorldGenerator;

public class BlockSapling extends BlockFlower {

   public static final String[] field_72270_a = new String[]{"oak", "spruce", "birch", "jungle"};


   protected BlockSapling(int p_i3992_1_, int p_i3992_2_) {
      super(p_i3992_1_, p_i3992_2_);
      float var3 = 0.4F;
      this.func_71905_a(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var3 * 2.0F, 0.5F + var3);
      this.func_71849_a(CreativeTabs.field_78031_c);
   }

   public void func_71847_b(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_) {
      if(!p_71847_1_.field_72995_K) {
         super.func_71847_b(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, p_71847_5_);
         if(p_71847_1_.func_72957_l(p_71847_2_, p_71847_3_ + 1, p_71847_4_) >= 9 && p_71847_5_.nextInt(7) == 0) {
            int var6 = p_71847_1_.func_72805_g(p_71847_2_, p_71847_3_, p_71847_4_);
            if((var6 & 8) == 0) {
               p_71847_1_.func_72921_c(p_71847_2_, p_71847_3_, p_71847_4_, var6 | 8);
            } else {
               this.func_72269_c(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, p_71847_5_);
            }
         }

      }
   }

   public int func_71858_a(int p_71858_1_, int p_71858_2_) {
      p_71858_2_ &= 3;
      return p_71858_2_ == 1?63:(p_71858_2_ == 2?79:(p_71858_2_ == 3?30:super.func_71858_a(p_71858_1_, p_71858_2_)));
   }

   public void func_72269_c(World p_72269_1_, int p_72269_2_, int p_72269_3_, int p_72269_4_, Random p_72269_5_) {
      int var6 = p_72269_1_.func_72805_g(p_72269_2_, p_72269_3_, p_72269_4_) & 3;
      Object var7 = null;
      int var8 = 0;
      int var9 = 0;
      boolean var10 = false;
      if(var6 == 1) {
         var7 = new WorldGenTaiga2(true);
      } else if(var6 == 2) {
         var7 = new WorldGenForest(true);
      } else if(var6 == 3) {
         for(var8 = 0; var8 >= -1; --var8) {
            for(var9 = 0; var9 >= -1; --var9) {
               if(this.func_72268_e(p_72269_1_, p_72269_2_ + var8, p_72269_3_, p_72269_4_ + var9, 3) && this.func_72268_e(p_72269_1_, p_72269_2_ + var8 + 1, p_72269_3_, p_72269_4_ + var9, 3) && this.func_72268_e(p_72269_1_, p_72269_2_ + var8, p_72269_3_, p_72269_4_ + var9 + 1, 3) && this.func_72268_e(p_72269_1_, p_72269_2_ + var8 + 1, p_72269_3_, p_72269_4_ + var9 + 1, 3)) {
                  var7 = new WorldGenHugeTrees(true, 10 + p_72269_5_.nextInt(20), 3, 3);
                  var10 = true;
                  break;
               }
            }

            if(var7 != null) {
               break;
            }
         }

         if(var7 == null) {
            var9 = 0;
            var8 = 0;
            var7 = new WorldGenTrees(true, 4 + p_72269_5_.nextInt(7), 3, 3, false);
         }
      } else {
         var7 = new WorldGenTrees(true);
         if(p_72269_5_.nextInt(10) == 0) {
            var7 = new WorldGenBigTree(true);
         }
      }

      if(var10) {
         p_72269_1_.func_72822_b(p_72269_2_ + var8, p_72269_3_, p_72269_4_ + var9, 0);
         p_72269_1_.func_72822_b(p_72269_2_ + var8 + 1, p_72269_3_, p_72269_4_ + var9, 0);
         p_72269_1_.func_72822_b(p_72269_2_ + var8, p_72269_3_, p_72269_4_ + var9 + 1, 0);
         p_72269_1_.func_72822_b(p_72269_2_ + var8 + 1, p_72269_3_, p_72269_4_ + var9 + 1, 0);
      } else {
         p_72269_1_.func_72822_b(p_72269_2_, p_72269_3_, p_72269_4_, 0);
      }

      if(!((WorldGenerator)var7).func_76484_a(p_72269_1_, p_72269_5_, p_72269_2_ + var8, p_72269_3_, p_72269_4_ + var9)) {
         if(var10) {
            p_72269_1_.func_72961_c(p_72269_2_ + var8, p_72269_3_, p_72269_4_ + var9, this.field_71990_ca, var6);
            p_72269_1_.func_72961_c(p_72269_2_ + var8 + 1, p_72269_3_, p_72269_4_ + var9, this.field_71990_ca, var6);
            p_72269_1_.func_72961_c(p_72269_2_ + var8, p_72269_3_, p_72269_4_ + var9 + 1, this.field_71990_ca, var6);
            p_72269_1_.func_72961_c(p_72269_2_ + var8 + 1, p_72269_3_, p_72269_4_ + var9 + 1, this.field_71990_ca, var6);
         } else {
            p_72269_1_.func_72961_c(p_72269_2_, p_72269_3_, p_72269_4_, this.field_71990_ca, var6);
         }
      }

   }

   public boolean func_72268_e(World p_72268_1_, int p_72268_2_, int p_72268_3_, int p_72268_4_, int p_72268_5_) {
      return p_72268_1_.func_72798_a(p_72268_2_, p_72268_3_, p_72268_4_) == this.field_71990_ca && (p_72268_1_.func_72805_g(p_72268_2_, p_72268_3_, p_72268_4_) & 3) == p_72268_5_;
   }

   public int func_71899_b(int p_71899_1_) {
      return p_71899_1_ & 3;
   }

   public void func_71879_a(int p_71879_1_, CreativeTabs p_71879_2_, List p_71879_3_) {
      p_71879_3_.add(new ItemStack(p_71879_1_, 1, 0));
      p_71879_3_.add(new ItemStack(p_71879_1_, 1, 1));
      p_71879_3_.add(new ItemStack(p_71879_1_, 1, 2));
      p_71879_3_.add(new ItemStack(p_71879_1_, 1, 3));
   }

}
