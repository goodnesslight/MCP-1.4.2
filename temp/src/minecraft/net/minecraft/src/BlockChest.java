package net.minecraft.src;

import java.util.Iterator;
import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityOcelot;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryLargeChest;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.World;

public class BlockChest extends BlockContainer {

   private Random field_72293_a = new Random();


   protected BlockChest(int p_i3928_1_) {
      super(p_i3928_1_, Material.field_76245_d);
      this.field_72059_bZ = 26;
      this.func_71849_a(CreativeTabs.field_78031_c);
   }

   public boolean func_71926_d() {
      return false;
   }

   public boolean func_71886_c() {
      return false;
   }

   public int func_71857_b() {
      return 22;
   }

   public void func_71861_g(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_) {
      super.func_71861_g(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
      this.func_72290_b_(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
      int var5 = p_71861_1_.func_72798_a(p_71861_2_, p_71861_3_, p_71861_4_ - 1);
      int var6 = p_71861_1_.func_72798_a(p_71861_2_, p_71861_3_, p_71861_4_ + 1);
      int var7 = p_71861_1_.func_72798_a(p_71861_2_ - 1, p_71861_3_, p_71861_4_);
      int var8 = p_71861_1_.func_72798_a(p_71861_2_ + 1, p_71861_3_, p_71861_4_);
      if(var5 == this.field_71990_ca) {
         this.func_72290_b_(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_ - 1);
      }

      if(var6 == this.field_71990_ca) {
         this.func_72290_b_(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_ + 1);
      }

      if(var7 == this.field_71990_ca) {
         this.func_72290_b_(p_71861_1_, p_71861_2_ - 1, p_71861_3_, p_71861_4_);
      }

      if(var8 == this.field_71990_ca) {
         this.func_72290_b_(p_71861_1_, p_71861_2_ + 1, p_71861_3_, p_71861_4_);
      }

   }

   public void func_71860_a(World p_71860_1_, int p_71860_2_, int p_71860_3_, int p_71860_4_, EntityLiving p_71860_5_) {
      int var6 = p_71860_1_.func_72798_a(p_71860_2_, p_71860_3_, p_71860_4_ - 1);
      int var7 = p_71860_1_.func_72798_a(p_71860_2_, p_71860_3_, p_71860_4_ + 1);
      int var8 = p_71860_1_.func_72798_a(p_71860_2_ - 1, p_71860_3_, p_71860_4_);
      int var9 = p_71860_1_.func_72798_a(p_71860_2_ + 1, p_71860_3_, p_71860_4_);
      byte var10 = 0;
      int var11 = MathHelper.func_76128_c((double)(p_71860_5_.field_70177_z * 4.0F / 360.0F) + 0.5D) & 3;
      if(var11 == 0) {
         var10 = 2;
      }

      if(var11 == 1) {
         var10 = 5;
      }

      if(var11 == 2) {
         var10 = 3;
      }

      if(var11 == 3) {
         var10 = 4;
      }

      if(var6 != this.field_71990_ca && var7 != this.field_71990_ca && var8 != this.field_71990_ca && var9 != this.field_71990_ca) {
         p_71860_1_.func_72921_c(p_71860_2_, p_71860_3_, p_71860_4_, var10);
      } else {
         if((var6 == this.field_71990_ca || var7 == this.field_71990_ca) && (var10 == 4 || var10 == 5)) {
            if(var6 == this.field_71990_ca) {
               p_71860_1_.func_72921_c(p_71860_2_, p_71860_3_, p_71860_4_ - 1, var10);
            } else {
               p_71860_1_.func_72921_c(p_71860_2_, p_71860_3_, p_71860_4_ + 1, var10);
            }

            p_71860_1_.func_72921_c(p_71860_2_, p_71860_3_, p_71860_4_, var10);
         }

         if((var8 == this.field_71990_ca || var9 == this.field_71990_ca) && (var10 == 2 || var10 == 3)) {
            if(var8 == this.field_71990_ca) {
               p_71860_1_.func_72921_c(p_71860_2_ - 1, p_71860_3_, p_71860_4_, var10);
            } else {
               p_71860_1_.func_72921_c(p_71860_2_ + 1, p_71860_3_, p_71860_4_, var10);
            }

            p_71860_1_.func_72921_c(p_71860_2_, p_71860_3_, p_71860_4_, var10);
         }
      }

   }

   public void func_72290_b_(World p_72290_1_, int p_72290_2_, int p_72290_3_, int p_72290_4_) {
      if(!p_72290_1_.field_72995_K) {
         int var5 = p_72290_1_.func_72798_a(p_72290_2_, p_72290_3_, p_72290_4_ - 1);
         int var6 = p_72290_1_.func_72798_a(p_72290_2_, p_72290_3_, p_72290_4_ + 1);
         int var7 = p_72290_1_.func_72798_a(p_72290_2_ - 1, p_72290_3_, p_72290_4_);
         int var8 = p_72290_1_.func_72798_a(p_72290_2_ + 1, p_72290_3_, p_72290_4_);
         boolean var9 = true;
         int var10;
         int var11;
         boolean var12;
         byte var13;
         int var14;
         if(var5 != this.field_71990_ca && var6 != this.field_71990_ca) {
            if(var7 != this.field_71990_ca && var8 != this.field_71990_ca) {
               var13 = 3;
               if(Block.field_71970_n[var5] && !Block.field_71970_n[var6]) {
                  var13 = 3;
               }

               if(Block.field_71970_n[var6] && !Block.field_71970_n[var5]) {
                  var13 = 2;
               }

               if(Block.field_71970_n[var7] && !Block.field_71970_n[var8]) {
                  var13 = 5;
               }

               if(Block.field_71970_n[var8] && !Block.field_71970_n[var7]) {
                  var13 = 4;
               }
            } else {
               var10 = p_72290_1_.func_72798_a(var7 == this.field_71990_ca?p_72290_2_ - 1:p_72290_2_ + 1, p_72290_3_, p_72290_4_ - 1);
               var11 = p_72290_1_.func_72798_a(var7 == this.field_71990_ca?p_72290_2_ - 1:p_72290_2_ + 1, p_72290_3_, p_72290_4_ + 1);
               var13 = 3;
               var12 = true;
               if(var7 == this.field_71990_ca) {
                  var14 = p_72290_1_.func_72805_g(p_72290_2_ - 1, p_72290_3_, p_72290_4_);
               } else {
                  var14 = p_72290_1_.func_72805_g(p_72290_2_ + 1, p_72290_3_, p_72290_4_);
               }

               if(var14 == 2) {
                  var13 = 2;
               }

               if((Block.field_71970_n[var5] || Block.field_71970_n[var10]) && !Block.field_71970_n[var6] && !Block.field_71970_n[var11]) {
                  var13 = 3;
               }

               if((Block.field_71970_n[var6] || Block.field_71970_n[var11]) && !Block.field_71970_n[var5] && !Block.field_71970_n[var10]) {
                  var13 = 2;
               }
            }
         } else {
            var10 = p_72290_1_.func_72798_a(p_72290_2_ - 1, p_72290_3_, var5 == this.field_71990_ca?p_72290_4_ - 1:p_72290_4_ + 1);
            var11 = p_72290_1_.func_72798_a(p_72290_2_ + 1, p_72290_3_, var5 == this.field_71990_ca?p_72290_4_ - 1:p_72290_4_ + 1);
            var13 = 5;
            var12 = true;
            if(var5 == this.field_71990_ca) {
               var14 = p_72290_1_.func_72805_g(p_72290_2_, p_72290_3_, p_72290_4_ - 1);
            } else {
               var14 = p_72290_1_.func_72805_g(p_72290_2_, p_72290_3_, p_72290_4_ + 1);
            }

            if(var14 == 4) {
               var13 = 4;
            }

            if((Block.field_71970_n[var7] || Block.field_71970_n[var10]) && !Block.field_71970_n[var8] && !Block.field_71970_n[var11]) {
               var13 = 5;
            }

            if((Block.field_71970_n[var8] || Block.field_71970_n[var11]) && !Block.field_71970_n[var7] && !Block.field_71970_n[var10]) {
               var13 = 4;
            }
         }

         p_72290_1_.func_72921_c(p_72290_2_, p_72290_3_, p_72290_4_, var13);
      }
   }

   public int func_71895_b(IBlockAccess p_71895_1_, int p_71895_2_, int p_71895_3_, int p_71895_4_, int p_71895_5_) {
      return 4;
   }

   public int func_71851_a(int p_71851_1_) {
      return 4;
   }

   public boolean func_71930_b(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_) {
      int var5 = 0;
      if(p_71930_1_.func_72798_a(p_71930_2_ - 1, p_71930_3_, p_71930_4_) == this.field_71990_ca) {
         ++var5;
      }

      if(p_71930_1_.func_72798_a(p_71930_2_ + 1, p_71930_3_, p_71930_4_) == this.field_71990_ca) {
         ++var5;
      }

      if(p_71930_1_.func_72798_a(p_71930_2_, p_71930_3_, p_71930_4_ - 1) == this.field_71990_ca) {
         ++var5;
      }

      if(p_71930_1_.func_72798_a(p_71930_2_, p_71930_3_, p_71930_4_ + 1) == this.field_71990_ca) {
         ++var5;
      }

      return var5 > 1?false:(this.func_72291_l(p_71930_1_, p_71930_2_ - 1, p_71930_3_, p_71930_4_)?false:(this.func_72291_l(p_71930_1_, p_71930_2_ + 1, p_71930_3_, p_71930_4_)?false:(this.func_72291_l(p_71930_1_, p_71930_2_, p_71930_3_, p_71930_4_ - 1)?false:!this.func_72291_l(p_71930_1_, p_71930_2_, p_71930_3_, p_71930_4_ + 1))));
   }

   private boolean func_72291_l(World p_72291_1_, int p_72291_2_, int p_72291_3_, int p_72291_4_) {
      return p_72291_1_.func_72798_a(p_72291_2_, p_72291_3_, p_72291_4_) != this.field_71990_ca?false:(p_72291_1_.func_72798_a(p_72291_2_ - 1, p_72291_3_, p_72291_4_) == this.field_71990_ca?true:(p_72291_1_.func_72798_a(p_72291_2_ + 1, p_72291_3_, p_72291_4_) == this.field_71990_ca?true:(p_72291_1_.func_72798_a(p_72291_2_, p_72291_3_, p_72291_4_ - 1) == this.field_71990_ca?true:p_72291_1_.func_72798_a(p_72291_2_, p_72291_3_, p_72291_4_ + 1) == this.field_71990_ca)));
   }

   public void func_71863_a(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_) {
      super.func_71863_a(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_5_);
      TileEntityChest var6 = (TileEntityChest)p_71863_1_.func_72796_p(p_71863_2_, p_71863_3_, p_71863_4_);
      if(var6 != null) {
         var6.func_70321_h();
      }

   }

   public void func_71852_a(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_) {
      TileEntityChest var7 = (TileEntityChest)p_71852_1_.func_72796_p(p_71852_2_, p_71852_3_, p_71852_4_);
      if(var7 != null) {
         for(int var8 = 0; var8 < var7.func_70302_i_(); ++var8) {
            ItemStack var9 = var7.func_70301_a(var8);
            if(var9 != null) {
               float var10 = this.field_72293_a.nextFloat() * 0.8F + 0.1F;
               float var11 = this.field_72293_a.nextFloat() * 0.8F + 0.1F;

               EntityItem var14;
               for(float var12 = this.field_72293_a.nextFloat() * 0.8F + 0.1F; var9.field_77994_a > 0; p_71852_1_.func_72838_d(var14)) {
                  int var13 = this.field_72293_a.nextInt(21) + 10;
                  if(var13 > var9.field_77994_a) {
                     var13 = var9.field_77994_a;
                  }

                  var9.field_77994_a -= var13;
                  var14 = new EntityItem(p_71852_1_, (double)((float)p_71852_2_ + var10), (double)((float)p_71852_3_ + var11), (double)((float)p_71852_4_ + var12), new ItemStack(var9.field_77993_c, var13, var9.func_77960_j()));
                  float var15 = 0.05F;
                  var14.field_70159_w = (double)((float)this.field_72293_a.nextGaussian() * var15);
                  var14.field_70181_x = (double)((float)this.field_72293_a.nextGaussian() * var15 + 0.2F);
                  var14.field_70179_y = (double)((float)this.field_72293_a.nextGaussian() * var15);
                  if(var9.func_77942_o()) {
                     var14.field_70294_a.func_77982_d((NBTTagCompound)var9.func_77978_p().func_74737_b());
                  }
               }
            }
         }
      }

      super.func_71852_a(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, p_71852_5_, p_71852_6_);
   }

   public boolean func_71903_a(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_) {
      Object var10 = (TileEntityChest)p_71903_1_.func_72796_p(p_71903_2_, p_71903_3_, p_71903_4_);
      if(var10 == null) {
         return true;
      } else if(p_71903_1_.func_72809_s(p_71903_2_, p_71903_3_ + 1, p_71903_4_)) {
         return true;
      } else if(func_72292_n(p_71903_1_, p_71903_2_, p_71903_3_, p_71903_4_)) {
         return true;
      } else if(p_71903_1_.func_72798_a(p_71903_2_ - 1, p_71903_3_, p_71903_4_) == this.field_71990_ca && (p_71903_1_.func_72809_s(p_71903_2_ - 1, p_71903_3_ + 1, p_71903_4_) || func_72292_n(p_71903_1_, p_71903_2_ - 1, p_71903_3_, p_71903_4_))) {
         return true;
      } else if(p_71903_1_.func_72798_a(p_71903_2_ + 1, p_71903_3_, p_71903_4_) == this.field_71990_ca && (p_71903_1_.func_72809_s(p_71903_2_ + 1, p_71903_3_ + 1, p_71903_4_) || func_72292_n(p_71903_1_, p_71903_2_ + 1, p_71903_3_, p_71903_4_))) {
         return true;
      } else if(p_71903_1_.func_72798_a(p_71903_2_, p_71903_3_, p_71903_4_ - 1) == this.field_71990_ca && (p_71903_1_.func_72809_s(p_71903_2_, p_71903_3_ + 1, p_71903_4_ - 1) || func_72292_n(p_71903_1_, p_71903_2_, p_71903_3_, p_71903_4_ - 1))) {
         return true;
      } else if(p_71903_1_.func_72798_a(p_71903_2_, p_71903_3_, p_71903_4_ + 1) == this.field_71990_ca && (p_71903_1_.func_72809_s(p_71903_2_, p_71903_3_ + 1, p_71903_4_ + 1) || func_72292_n(p_71903_1_, p_71903_2_, p_71903_3_, p_71903_4_ + 1))) {
         return true;
      } else {
         if(p_71903_1_.func_72798_a(p_71903_2_ - 1, p_71903_3_, p_71903_4_) == this.field_71990_ca) {
            var10 = new InventoryLargeChest("container.chestDouble", (TileEntityChest)p_71903_1_.func_72796_p(p_71903_2_ - 1, p_71903_3_, p_71903_4_), (IInventory)var10);
         }

         if(p_71903_1_.func_72798_a(p_71903_2_ + 1, p_71903_3_, p_71903_4_) == this.field_71990_ca) {
            var10 = new InventoryLargeChest("container.chestDouble", (IInventory)var10, (TileEntityChest)p_71903_1_.func_72796_p(p_71903_2_ + 1, p_71903_3_, p_71903_4_));
         }

         if(p_71903_1_.func_72798_a(p_71903_2_, p_71903_3_, p_71903_4_ - 1) == this.field_71990_ca) {
            var10 = new InventoryLargeChest("container.chestDouble", (TileEntityChest)p_71903_1_.func_72796_p(p_71903_2_, p_71903_3_, p_71903_4_ - 1), (IInventory)var10);
         }

         if(p_71903_1_.func_72798_a(p_71903_2_, p_71903_3_, p_71903_4_ + 1) == this.field_71990_ca) {
            var10 = new InventoryLargeChest("container.chestDouble", (IInventory)var10, (TileEntityChest)p_71903_1_.func_72796_p(p_71903_2_, p_71903_3_, p_71903_4_ + 1));
         }

         if(p_71903_1_.field_72995_K) {
            return true;
         } else {
            p_71903_5_.func_71007_a((IInventory)var10);
            return true;
         }
      }
   }

   public TileEntity func_72274_a(World p_72274_1_) {
      return new TileEntityChest();
   }

   private static boolean func_72292_n(World p_72292_0_, int p_72292_1_, int p_72292_2_, int p_72292_3_) {
      Iterator var4 = p_72292_0_.func_72872_a(EntityOcelot.class, AxisAlignedBB.func_72332_a().func_72299_a((double)p_72292_1_, (double)(p_72292_2_ + 1), (double)p_72292_3_, (double)(p_72292_1_ + 1), (double)(p_72292_2_ + 2), (double)(p_72292_3_ + 1))).iterator();

      EntityOcelot var6;
      do {
         if(!var4.hasNext()) {
            return false;
         }

         EntityOcelot var5 = (EntityOcelot)var4.next();
         var6 = (EntityOcelot)var5;
      } while(!var6.func_70906_o());

      return true;
   }
}
