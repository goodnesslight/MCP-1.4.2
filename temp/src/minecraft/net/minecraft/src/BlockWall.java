package net.minecraft.src;

import java.util.List;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockWall extends Block {

   public static final String[] field_82539_a = new String[]{"normal", "mossy"};


   public BlockWall(int p_i5108_1_, Block p_i5108_2_) {
      super(p_i5108_1_, p_i5108_2_.field_72059_bZ, p_i5108_2_.field_72018_cp);
      this.func_71848_c(p_i5108_2_.field_71989_cb);
      this.func_71894_b(p_i5108_2_.field_72029_cc / 3.0F);
      this.func_71884_a(p_i5108_2_.field_72020_cn);
      this.func_71849_a(CreativeTabs.field_78030_b);
   }

   public int func_71858_a(int p_71858_1_, int p_71858_2_) {
      return p_71858_2_ == 1?Block.field_72087_ao.field_72059_bZ:super.func_71851_a(p_71858_1_);
   }

   public int func_71857_b() {
      return 32;
   }

   public boolean func_71886_c() {
      return false;
   }

   public boolean func_71918_c(IBlockAccess p_71918_1_, int p_71918_2_, int p_71918_3_, int p_71918_4_) {
      return false;
   }

   public boolean func_71926_d() {
      return false;
   }

   public void func_71902_a(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_) {
      boolean var5 = this.func_82538_d(p_71902_1_, p_71902_2_, p_71902_3_, p_71902_4_ - 1);
      boolean var6 = this.func_82538_d(p_71902_1_, p_71902_2_, p_71902_3_, p_71902_4_ + 1);
      boolean var7 = this.func_82538_d(p_71902_1_, p_71902_2_ - 1, p_71902_3_, p_71902_4_);
      boolean var8 = this.func_82538_d(p_71902_1_, p_71902_2_ + 1, p_71902_3_, p_71902_4_);
      float var9 = 0.25F;
      float var10 = 0.75F;
      float var11 = 0.25F;
      float var12 = 0.75F;
      float var13 = 1.0F;
      if(var5) {
         var11 = 0.0F;
      }

      if(var6) {
         var12 = 1.0F;
      }

      if(var7) {
         var9 = 0.0F;
      }

      if(var8) {
         var10 = 1.0F;
      }

      if(var5 && var6 && !var7 && !var8) {
         var13 = 0.8125F;
         var9 = 0.3125F;
         var10 = 0.6875F;
      } else if(!var5 && !var6 && var7 && var8) {
         var13 = 0.8125F;
         var11 = 0.3125F;
         var12 = 0.6875F;
      }

      this.func_71905_a(var9, 0.0F, var11, var10, var13, var12);
   }

   public AxisAlignedBB func_71872_e(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_) {
      boolean var5 = this.func_82538_d(p_71872_1_, p_71872_2_, p_71872_3_, p_71872_4_ - 1);
      boolean var6 = this.func_82538_d(p_71872_1_, p_71872_2_, p_71872_3_, p_71872_4_ + 1);
      boolean var7 = this.func_82538_d(p_71872_1_, p_71872_2_ - 1, p_71872_3_, p_71872_4_);
      boolean var8 = this.func_82538_d(p_71872_1_, p_71872_2_ + 1, p_71872_3_, p_71872_4_);
      float var9 = 0.375F;
      float var10 = 0.625F;
      float var11 = 0.375F;
      float var12 = 0.625F;
      if(var5) {
         var11 = 0.0F;
      }

      if(var6) {
         var12 = 1.0F;
      }

      if(var7) {
         var9 = 0.0F;
      }

      if(var8) {
         var10 = 1.0F;
      }

      return AxisAlignedBB.func_72332_a().func_72299_a((double)((float)p_71872_2_ + var9), (double)p_71872_3_, (double)((float)p_71872_4_ + var11), (double)((float)p_71872_2_ + var10), (double)((float)p_71872_3_ + 1.5F), (double)((float)p_71872_4_ + var12));
   }

   public boolean func_82538_d(IBlockAccess p_82538_1_, int p_82538_2_, int p_82538_3_, int p_82538_4_) {
      int var5 = p_82538_1_.func_72798_a(p_82538_2_, p_82538_3_, p_82538_4_);
      if(var5 != this.field_71990_ca && var5 != Block.field_71993_bv.field_71990_ca) {
         Block var6 = Block.field_71973_m[var5];
         return var6 != null && var6.field_72018_cp.func_76218_k() && var6.func_71886_c()?var6.field_72018_cp != Material.field_76266_z:false;
      } else {
         return true;
      }
   }

   public void func_71879_a(int p_71879_1_, CreativeTabs p_71879_2_, List p_71879_3_) {
      p_71879_3_.add(new ItemStack(p_71879_1_, 1, 0));
      p_71879_3_.add(new ItemStack(p_71879_1_, 1, 1));
   }

   public int func_71899_b(int p_71899_1_) {
      return p_71899_1_;
   }

   public boolean func_71877_c(IBlockAccess p_71877_1_, int p_71877_2_, int p_71877_3_, int p_71877_4_, int p_71877_5_) {
      return p_71877_5_ == 0?super.func_71877_c(p_71877_1_, p_71877_2_, p_71877_3_, p_71877_4_, p_71877_5_):true;
   }

}
