package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.BlockSand;
import net.minecraft.src.EntityFallingSand;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockDragonEgg extends Block {

   public BlockDragonEgg(int p_i3940_1_, int p_i3940_2_) {
      super(p_i3940_1_, p_i3940_2_, Material.field_76236_A);
   }

   public void func_71861_g(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_) {
      p_71861_1_.func_72836_a(p_71861_2_, p_71861_3_, p_71861_4_, this.field_71990_ca, this.func_71859_p_());
   }

   public void func_71863_a(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_) {
      p_71863_1_.func_72836_a(p_71863_2_, p_71863_3_, p_71863_4_, this.field_71990_ca, this.func_71859_p_());
   }

   public void func_71847_b(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_) {
      this.func_72236_l(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_);
   }

   private void func_72236_l(World p_72236_1_, int p_72236_2_, int p_72236_3_, int p_72236_4_) {
      if(BlockSand.func_72191_e_(p_72236_1_, p_72236_2_, p_72236_3_ - 1, p_72236_4_) && p_72236_3_ >= 0) {
         byte var5 = 32;
         if(!BlockSand.field_72192_a && p_72236_1_.func_72904_c(p_72236_2_ - var5, p_72236_3_ - var5, p_72236_4_ - var5, p_72236_2_ + var5, p_72236_3_ + var5, p_72236_4_ + var5)) {
            EntityFallingSand var6 = new EntityFallingSand(p_72236_1_, (double)((float)p_72236_2_ + 0.5F), (double)((float)p_72236_3_ + 0.5F), (double)((float)p_72236_4_ + 0.5F), this.field_71990_ca);
            p_72236_1_.func_72838_d(var6);
         } else {
            p_72236_1_.func_72859_e(p_72236_2_, p_72236_3_, p_72236_4_, 0);

            while(BlockSand.func_72191_e_(p_72236_1_, p_72236_2_, p_72236_3_ - 1, p_72236_4_) && p_72236_3_ > 0) {
               --p_72236_3_;
            }

            if(p_72236_3_ > 0) {
               p_72236_1_.func_72859_e(p_72236_2_, p_72236_3_, p_72236_4_, this.field_71990_ca);
            }
         }
      }

   }

   public boolean func_71903_a(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_) {
      this.func_72237_n(p_71903_1_, p_71903_2_, p_71903_3_, p_71903_4_);
      return true;
   }

   public void func_71921_a(World p_71921_1_, int p_71921_2_, int p_71921_3_, int p_71921_4_, EntityPlayer p_71921_5_) {
      this.func_72237_n(p_71921_1_, p_71921_2_, p_71921_3_, p_71921_4_);
   }

   private void func_72237_n(World p_72237_1_, int p_72237_2_, int p_72237_3_, int p_72237_4_) {
      if(p_72237_1_.func_72798_a(p_72237_2_, p_72237_3_, p_72237_4_) == this.field_71990_ca) {
         if(!p_72237_1_.field_72995_K) {
            for(int var5 = 0; var5 < 1000; ++var5) {
               int var6 = p_72237_2_ + p_72237_1_.field_73012_v.nextInt(16) - p_72237_1_.field_73012_v.nextInt(16);
               int var7 = p_72237_3_ + p_72237_1_.field_73012_v.nextInt(8) - p_72237_1_.field_73012_v.nextInt(8);
               int var8 = p_72237_4_ + p_72237_1_.field_73012_v.nextInt(16) - p_72237_1_.field_73012_v.nextInt(16);
               if(p_72237_1_.func_72798_a(var6, var7, var8) == 0) {
                  p_72237_1_.func_72832_d(var6, var7, var8, this.field_71990_ca, p_72237_1_.func_72805_g(p_72237_2_, p_72237_3_, p_72237_4_));
                  p_72237_1_.func_72859_e(p_72237_2_, p_72237_3_, p_72237_4_, 0);
                  short var9 = 128;

                  for(int var10 = 0; var10 < var9; ++var10) {
                     double var11 = p_72237_1_.field_73012_v.nextDouble();
                     float var13 = (p_72237_1_.field_73012_v.nextFloat() - 0.5F) * 0.2F;
                     float var14 = (p_72237_1_.field_73012_v.nextFloat() - 0.5F) * 0.2F;
                     float var15 = (p_72237_1_.field_73012_v.nextFloat() - 0.5F) * 0.2F;
                     double var16 = (double)var6 + (double)(p_72237_2_ - var6) * var11 + (p_72237_1_.field_73012_v.nextDouble() - 0.5D) * 1.0D + 0.5D;
                     double var18 = (double)var7 + (double)(p_72237_3_ - var7) * var11 + p_72237_1_.field_73012_v.nextDouble() * 1.0D - 0.5D;
                     double var20 = (double)var8 + (double)(p_72237_4_ - var8) * var11 + (p_72237_1_.field_73012_v.nextDouble() - 0.5D) * 1.0D + 0.5D;
                     p_72237_1_.func_72869_a("portal", var16, var18, var20, (double)var13, (double)var14, (double)var15);
                  }

                  return;
               }
            }

         }
      }
   }

   public int func_71859_p_() {
      return 3;
   }

   public boolean func_71926_d() {
      return false;
   }

   public boolean func_71886_c() {
      return false;
   }

   public int func_71857_b() {
      return 27;
   }
}
