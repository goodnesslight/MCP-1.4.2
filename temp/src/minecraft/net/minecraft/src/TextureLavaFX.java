package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.MathHelper;
import net.minecraft.src.TextureFX;

public class TextureLavaFX extends TextureFX {

   protected float[] field_76876_g = new float[256];
   protected float[] field_76878_h = new float[256];
   protected float[] field_76879_i = new float[256];
   protected float[] field_76877_j = new float[256];


   public TextureLavaFX() {
      super(Block.field_71944_C.field_72059_bZ);
   }

   public void func_76846_a() {
      int var2;
      float var3;
      int var5;
      int var6;
      int var7;
      int var8;
      int var9;
      for(int var1 = 0; var1 < 16; ++var1) {
         for(var2 = 0; var2 < 16; ++var2) {
            var3 = 0.0F;
            int var4 = (int)(MathHelper.func_76126_a((float)var2 * 3.1415927F * 2.0F / 16.0F) * 1.2F);
            var5 = (int)(MathHelper.func_76126_a((float)var1 * 3.1415927F * 2.0F / 16.0F) * 1.2F);

            for(var6 = var1 - 1; var6 <= var1 + 1; ++var6) {
               for(var7 = var2 - 1; var7 <= var2 + 1; ++var7) {
                  var8 = var6 + var4 & 15;
                  var9 = var7 + var5 & 15;
                  var3 += this.field_76876_g[var8 + var9 * 16];
               }
            }

            this.field_76878_h[var1 + var2 * 16] = var3 / 10.0F + (this.field_76879_i[(var1 + 0 & 15) + (var2 + 0 & 15) * 16] + this.field_76879_i[(var1 + 1 & 15) + (var2 + 0 & 15) * 16] + this.field_76879_i[(var1 + 1 & 15) + (var2 + 1 & 15) * 16] + this.field_76879_i[(var1 + 0 & 15) + (var2 + 1 & 15) * 16]) / 4.0F * 0.8F;
            this.field_76879_i[var1 + var2 * 16] += this.field_76877_j[var1 + var2 * 16] * 0.01F;
            if(this.field_76879_i[var1 + var2 * 16] < 0.0F) {
               this.field_76879_i[var1 + var2 * 16] = 0.0F;
            }

            this.field_76877_j[var1 + var2 * 16] -= 0.06F;
            if(Math.random() < 0.0050D) {
               this.field_76877_j[var1 + var2 * 16] = 1.5F;
            }
         }
      }

      float[] var11 = this.field_76878_h;
      this.field_76878_h = this.field_76876_g;
      this.field_76876_g = var11;

      for(var2 = 0; var2 < 256; ++var2) {
         var3 = this.field_76876_g[var2] * 2.0F;
         if(var3 > 1.0F) {
            var3 = 1.0F;
         }

         if(var3 < 0.0F) {
            var3 = 0.0F;
         }

         var5 = (int)(var3 * 100.0F + 155.0F);
         var6 = (int)(var3 * var3 * 255.0F);
         var7 = (int)(var3 * var3 * var3 * var3 * 128.0F);
         if(this.field_76851_c) {
            var8 = (var5 * 30 + var6 * 59 + var7 * 11) / 100;
            var9 = (var5 * 30 + var6 * 70) / 100;
            int var10 = (var5 * 30 + var7 * 70) / 100;
            var5 = var8;
            var6 = var9;
            var7 = var10;
         }

         this.field_76852_a[var2 * 4 + 0] = (byte)var5;
         this.field_76852_a[var2 * 4 + 1] = (byte)var6;
         this.field_76852_a[var2 * 4 + 2] = (byte)var7;
         this.field_76852_a[var2 * 4 + 3] = -1;
      }

   }
}
