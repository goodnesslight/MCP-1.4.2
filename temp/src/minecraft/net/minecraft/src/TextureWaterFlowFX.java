package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.TextureFX;

public class TextureWaterFlowFX extends TextureFX {

   protected float[] field_76880_g = new float[256];
   protected float[] field_76883_h = new float[256];
   protected float[] field_76884_i = new float[256];
   protected float[] field_76881_j = new float[256];
   private int field_76882_k = 0;


   public TextureWaterFlowFX() {
      super(Block.field_71942_A.field_72059_bZ + 1);
      this.field_76849_e = 2;
   }

   public void func_76846_a() {
      ++this.field_76882_k;

      int var1;
      int var2;
      float var3;
      int var5;
      int var6;
      for(var1 = 0; var1 < 16; ++var1) {
         for(var2 = 0; var2 < 16; ++var2) {
            var3 = 0.0F;

            for(int var4 = var2 - 2; var4 <= var2; ++var4) {
               var5 = var1 & 15;
               var6 = var4 & 15;
               var3 += this.field_76880_g[var5 + var6 * 16];
            }

            this.field_76883_h[var1 + var2 * 16] = var3 / 3.2F + this.field_76884_i[var1 + var2 * 16] * 0.8F;
         }
      }

      for(var1 = 0; var1 < 16; ++var1) {
         for(var2 = 0; var2 < 16; ++var2) {
            this.field_76884_i[var1 + var2 * 16] += this.field_76881_j[var1 + var2 * 16] * 0.05F;
            if(this.field_76884_i[var1 + var2 * 16] < 0.0F) {
               this.field_76884_i[var1 + var2 * 16] = 0.0F;
            }

            this.field_76881_j[var1 + var2 * 16] -= 0.3F;
            if(Math.random() < 0.2D) {
               this.field_76881_j[var1 + var2 * 16] = 0.5F;
            }
         }
      }

      float[] var12 = this.field_76883_h;
      this.field_76883_h = this.field_76880_g;
      this.field_76880_g = var12;

      for(var2 = 0; var2 < 256; ++var2) {
         var3 = this.field_76880_g[var2 - this.field_76882_k * 16 & 255];
         if(var3 > 1.0F) {
            var3 = 1.0F;
         }

         if(var3 < 0.0F) {
            var3 = 0.0F;
         }

         float var13 = var3 * var3;
         var5 = (int)(32.0F + var13 * 32.0F);
         var6 = (int)(50.0F + var13 * 64.0F);
         int var7 = 255;
         int var8 = (int)(146.0F + var13 * 50.0F);
         if(this.field_76851_c) {
            int var9 = (var5 * 30 + var6 * 59 + var7 * 11) / 100;
            int var10 = (var5 * 30 + var6 * 70) / 100;
            int var11 = (var5 * 30 + var7 * 70) / 100;
            var5 = var9;
            var6 = var10;
            var7 = var11;
         }

         this.field_76852_a[var2 * 4 + 0] = (byte)var5;
         this.field_76852_a[var2 * 4 + 1] = (byte)var6;
         this.field_76852_a[var2 * 4 + 2] = (byte)var7;
         this.field_76852_a[var2 * 4 + 3] = (byte)var8;
      }

   }
}
