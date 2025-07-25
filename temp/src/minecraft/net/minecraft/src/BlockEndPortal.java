package net.minecraft.src;

import java.util.List;
import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.Entity;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityEndPortal;
import net.minecraft.src.World;

public class BlockEndPortal extends BlockContainer {

   public static boolean field_72275_a = false;


   protected BlockEndPortal(int p_i4003_1_, Material p_i4003_2_) {
      super(p_i4003_1_, 0, p_i4003_2_);
      this.func_71900_a(1.0F);
   }

   public TileEntity func_72274_a(World p_72274_1_) {
      return new TileEntityEndPortal();
   }

   public void func_71902_a(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_) {
      float var5 = 0.0625F;
      this.func_71905_a(0.0F, 0.0F, 0.0F, 1.0F, var5, 1.0F);
   }

   public boolean func_71877_c(IBlockAccess p_71877_1_, int p_71877_2_, int p_71877_3_, int p_71877_4_, int p_71877_5_) {
      return p_71877_5_ != 0?false:super.func_71877_c(p_71877_1_, p_71877_2_, p_71877_3_, p_71877_4_, p_71877_5_);
   }

   public void func_71871_a(World p_71871_1_, int p_71871_2_, int p_71871_3_, int p_71871_4_, AxisAlignedBB p_71871_5_, List p_71871_6_, Entity p_71871_7_) {}

   public boolean func_71926_d() {
      return false;
   }

   public boolean func_71886_c() {
      return false;
   }

   public int func_71925_a(Random p_71925_1_) {
      return 0;
   }

   public void func_71869_a(World p_71869_1_, int p_71869_2_, int p_71869_3_, int p_71869_4_, Entity p_71869_5_) {
      if(p_71869_5_.field_70154_o == null && p_71869_5_.field_70153_n == null && !p_71869_1_.field_72995_K) {
         p_71869_5_.func_71027_c(1);
      }

   }

   public void func_71862_a(World p_71862_1_, int p_71862_2_, int p_71862_3_, int p_71862_4_, Random p_71862_5_) {
      double var6 = (double)((float)p_71862_2_ + p_71862_5_.nextFloat());
      double var8 = (double)((float)p_71862_3_ + 0.8F);
      double var10 = (double)((float)p_71862_4_ + p_71862_5_.nextFloat());
      double var12 = 0.0D;
      double var14 = 0.0D;
      double var16 = 0.0D;
      p_71862_1_.func_72869_a("smoke", var6, var8, var10, var12, var14, var16);
   }

   public int func_71857_b() {
      return -1;
   }

   public void func_71861_g(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_) {
      if(!field_72275_a) {
         if(p_71861_1_.field_73011_w.field_76574_g != 0) {
            p_71861_1_.func_72859_e(p_71861_2_, p_71861_3_, p_71861_4_, 0);
         }

      }
   }

   public int func_71922_a(World p_71922_1_, int p_71922_2_, int p_71922_3_, int p_71922_4_) {
      return 0;
   }

}
