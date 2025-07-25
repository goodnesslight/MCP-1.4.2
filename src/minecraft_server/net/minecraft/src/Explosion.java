package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Explosion
{
    /** whether or not the explosion sets fire to blocks around it */
    public boolean isFlaming = false;
    public boolean field_82755_b = true;
    private int field_77289_h = 16;
    private Random explosionRNG = new Random();
    private World worldObj;
    public double explosionX;
    public double explosionY;
    public double explosionZ;
    public Entity exploder;
    public float explosionSize;
    public List field_77281_g = new ArrayList();
    private Map field_77288_k = new HashMap();

    public Explosion(World par1World, Entity par2Entity, double par3, double par5, double par7, float par9)
    {
        this.worldObj = par1World;
        this.exploder = par2Entity;
        this.explosionSize = par9;
        this.explosionX = par3;
        this.explosionY = par5;
        this.explosionZ = par7;
    }

    /**
     * Does the first part of the explosion (destroy blocks)
     */
    public void doExplosionA()
    {
        float var1 = this.explosionSize;
        HashSet var2 = new HashSet();
        int var3;
        int var4;
        int var5;
        double var15;
        double var17;
        double var19;

        for (var3 = 0; var3 < this.field_77289_h; ++var3)
        {
            for (var4 = 0; var4 < this.field_77289_h; ++var4)
            {
                for (var5 = 0; var5 < this.field_77289_h; ++var5)
                {
                    if (var3 == 0 || var3 == this.field_77289_h - 1 || var4 == 0 || var4 == this.field_77289_h - 1 || var5 == 0 || var5 == this.field_77289_h - 1)
                    {
                        double var6 = (double)((float)var3 / ((float)this.field_77289_h - 1.0F) * 2.0F - 1.0F);
                        double var8 = (double)((float)var4 / ((float)this.field_77289_h - 1.0F) * 2.0F - 1.0F);
                        double var10 = (double)((float)var5 / ((float)this.field_77289_h - 1.0F) * 2.0F - 1.0F);
                        double var12 = Math.sqrt(var6 * var6 + var8 * var8 + var10 * var10);
                        var6 /= var12;
                        var8 /= var12;
                        var10 /= var12;
                        float var14 = this.explosionSize * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
                        var15 = this.explosionX;
                        var17 = this.explosionY;
                        var19 = this.explosionZ;

                        for (float var21 = 0.3F; var14 > 0.0F; var14 -= var21 * 0.75F)
                        {
                            int var22 = MathHelper.floor_double(var15);
                            int var23 = MathHelper.floor_double(var17);
                            int var24 = MathHelper.floor_double(var19);
                            int var25 = this.worldObj.getBlockId(var22, var23, var24);

                            if (var25 > 0)
                            {
                                Block var26 = Block.blocksList[var25];
                                float var27 = this.exploder != null ? this.exploder.func_82146_a(this, var26, var22, var23, var24) : var26.getExplosionResistance(this.exploder);
                                var14 -= (var27 + 0.3F) * var21;
                            }

                            if (var14 > 0.0F)
                            {
                                var2.add(new ChunkPosition(var22, var23, var24));
                            }

                            var15 += var6 * (double)var21;
                            var17 += var8 * (double)var21;
                            var19 += var10 * (double)var21;
                        }
                    }
                }
            }
        }

        this.field_77281_g.addAll(var2);
        this.explosionSize *= 2.0F;
        var3 = MathHelper.floor_double(this.explosionX - (double)this.explosionSize - 1.0D);
        var4 = MathHelper.floor_double(this.explosionX + (double)this.explosionSize + 1.0D);
        var5 = MathHelper.floor_double(this.explosionY - (double)this.explosionSize - 1.0D);
        int var28 = MathHelper.floor_double(this.explosionY + (double)this.explosionSize + 1.0D);
        int var7 = MathHelper.floor_double(this.explosionZ - (double)this.explosionSize - 1.0D);
        int var29 = MathHelper.floor_double(this.explosionZ + (double)this.explosionSize + 1.0D);
        List var9 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)var3, (double)var5, (double)var7, (double)var4, (double)var28, (double)var29));
        Vec3 var30 = this.worldObj.func_82732_R().getVecFromPool(this.explosionX, this.explosionY, this.explosionZ);

        for (int var11 = 0; var11 < var9.size(); ++var11)
        {
            Entity var31 = (Entity)var9.get(var11);
            double var13 = var31.getDistance(this.explosionX, this.explosionY, this.explosionZ) / (double)this.explosionSize;

            if (var13 <= 1.0D)
            {
                var15 = var31.posX - this.explosionX;
                var17 = var31.posY + (double)var31.getEyeHeight() - this.explosionY;
                var19 = var31.posZ - this.explosionZ;
                double var33 = (double)MathHelper.sqrt_double(var15 * var15 + var17 * var17 + var19 * var19);

                if (var33 != 0.0D)
                {
                    var15 /= var33;
                    var17 /= var33;
                    var19 /= var33;
                    double var32 = (double)this.worldObj.getBlockDensity(var30, var31.boundingBox);
                    double var34 = (1.0D - var13) * var32;
                    var31.attackEntityFrom(DamageSource.explosion, (int)((var34 * var34 + var34) / 2.0D * 8.0D * (double)this.explosionSize + 1.0D));
                    var31.motionX += var15 * var34;
                    var31.motionY += var17 * var34;
                    var31.motionZ += var19 * var34;

                    if (var31 instanceof EntityPlayer)
                    {
                        this.field_77288_k.put((EntityPlayer)var31, this.worldObj.func_82732_R().getVecFromPool(var15 * var34, var17 * var34, var19 * var34));
                    }
                }
            }
        }

        this.explosionSize = var1;
    }

    /**
     * Does the second part of the explosion (sound, particles, drop spawn)
     */
    public void doExplosionB(boolean par1)
    {
        this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);

        if (this.explosionSize >= 2.0F && this.field_82755_b)
        {
            this.worldObj.spawnParticle("hugeexplosion", this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
        }
        else
        {
            this.worldObj.spawnParticle("largeexplode", this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
        }

        Iterator var2;
        ChunkPosition var3;
        int var4;
        int var5;
        int var6;
        int var7;

        if (this.field_82755_b)
        {
            var2 = this.field_77281_g.iterator();

            while (var2.hasNext())
            {
                var3 = (ChunkPosition)var2.next();
                var4 = var3.x;
                var5 = var3.y;
                var6 = var3.z;
                var7 = this.worldObj.getBlockId(var4, var5, var6);

                if (par1)
                {
                    double var8 = (double)((float)var4 + this.worldObj.rand.nextFloat());
                    double var10 = (double)((float)var5 + this.worldObj.rand.nextFloat());
                    double var12 = (double)((float)var6 + this.worldObj.rand.nextFloat());
                    double var14 = var8 - this.explosionX;
                    double var16 = var10 - this.explosionY;
                    double var18 = var12 - this.explosionZ;
                    double var20 = (double)MathHelper.sqrt_double(var14 * var14 + var16 * var16 + var18 * var18);
                    var14 /= var20;
                    var16 /= var20;
                    var18 /= var20;
                    double var22 = 0.5D / (var20 / (double)this.explosionSize + 0.1D);
                    var22 *= (double)(this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F);
                    var14 *= var22;
                    var16 *= var22;
                    var18 *= var22;
                    this.worldObj.spawnParticle("explode", (var8 + this.explosionX * 1.0D) / 2.0D, (var10 + this.explosionY * 1.0D) / 2.0D, (var12 + this.explosionZ * 1.0D) / 2.0D, var14, var16, var18);
                    this.worldObj.spawnParticle("smoke", var8, var10, var12, var14, var16, var18);
                }

                if (var7 > 0)
                {
                    Block.blocksList[var7].dropBlockAsItemWithChance(this.worldObj, var4, var5, var6, this.worldObj.getBlockMetadata(var4, var5, var6), 0.3F, 0);

                    if (this.worldObj.setBlockAndMetadataWithUpdate(var4, var5, var6, 0, 0, this.worldObj.isRemote))
                    {
                        this.worldObj.notifyBlocksOfNeighborChange(var4, var5, var6, 0);
                    }

                    Block.blocksList[var7].onBlockDestroyedByExplosion(this.worldObj, var4, var5, var6);
                }
            }
        }

        if (this.isFlaming)
        {
            var2 = this.field_77281_g.iterator();

            while (var2.hasNext())
            {
                var3 = (ChunkPosition)var2.next();
                var4 = var3.x;
                var5 = var3.y;
                var6 = var3.z;
                var7 = this.worldObj.getBlockId(var4, var5, var6);
                int var24 = this.worldObj.getBlockId(var4, var5 - 1, var6);

                if (var7 == 0 && Block.opaqueCubeLookup[var24] && this.explosionRNG.nextInt(3) == 0)
                {
                    this.worldObj.setBlockWithNotify(var4, var5, var6, Block.fire.blockID);
                }
            }
        }
    }

    public Map func_77277_b()
    {
        return this.field_77288_k;
    }
}
