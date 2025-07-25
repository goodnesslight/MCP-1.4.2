package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;

public class EntityFallingSand extends Entity
{
    public int blockID;
    public int field_70285_b;

    /** How long the block has been falling for. */
    public int fallTime;
    public boolean field_70284_d;
    private boolean field_82157_e;
    private boolean field_82155_f;
    private int field_82156_g;
    private float field_82158_h;

    public EntityFallingSand(World par1World)
    {
        super(par1World);
        this.fallTime = 0;
        this.field_70284_d = true;
        this.field_82157_e = false;
        this.field_82155_f = false;
        this.field_82156_g = 20;
        this.field_82158_h = 2.0F;
    }

    public EntityFallingSand(World par1World, double par2, double par4, double par6, int par8)
    {
        this(par1World, par2, par4, par6, par8, 0);
    }

    public EntityFallingSand(World par1World, double par2, double par4, double par6, int par8, int par9)
    {
        super(par1World);
        this.fallTime = 0;
        this.field_70284_d = true;
        this.field_82157_e = false;
        this.field_82155_f = false;
        this.field_82156_g = 20;
        this.field_82158_h = 2.0F;
        this.blockID = par8;
        this.field_70285_b = par9;
        this.preventEntitySpawning = true;
        this.setSize(0.98F, 0.98F);
        this.yOffset = this.height / 2.0F;
        this.setPosition(par2, par4, par6);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.prevPosX = par2;
        this.prevPosY = par4;
        this.prevPosZ = par6;
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    protected void entityInit() {}

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return !this.isDead;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        if (this.blockID == 0)
        {
            this.setDead();
        }
        else
        {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            ++this.fallTime;
            this.motionY -= 0.03999999910593033D;
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9800000190734863D;
            this.motionY *= 0.9800000190734863D;
            this.motionZ *= 0.9800000190734863D;

            if (!this.worldObj.isRemote)
            {
                int var1 = MathHelper.floor_double(this.posX);
                int var2 = MathHelper.floor_double(this.posY);
                int var3 = MathHelper.floor_double(this.posZ);

                if (this.fallTime == 1)
                {
                    if (this.fallTime == 1 && this.worldObj.getBlockId(var1, var2, var3) == this.blockID)
                    {
                        this.worldObj.setBlockWithNotify(var1, var2, var3, 0);
                    }
                    else
                    {
                        this.setDead();
                    }
                }

                if (this.onGround)
                {
                    this.motionX *= 0.699999988079071D;
                    this.motionZ *= 0.699999988079071D;
                    this.motionY *= -0.5D;

                    if (this.worldObj.getBlockId(var1, var2, var3) != Block.pistonMoving.blockID)
                    {
                        this.setDead();

                        if (!this.field_82157_e && this.worldObj.canPlaceEntityOnSide(this.blockID, var1, var2, var3, true, 1, (Entity)null) && !BlockSand.canFallBelow(this.worldObj, var1, var2 - 1, var3) && this.worldObj.setBlockAndMetadataWithNotify(var1, var2, var3, this.blockID, this.field_70285_b))
                        {
                            if (Block.blocksList[this.blockID] instanceof BlockSand)
                            {
                                ((BlockSand)Block.blocksList[this.blockID]).func_82519_a_(this.worldObj, var1, var2, var3, this.field_70285_b);
                            }
                        }
                        else if (this.field_70284_d && !this.field_82157_e)
                        {
                            this.entityDropItem(new ItemStack(this.blockID, 1, Block.blocksList[this.blockID].damageDropped(this.field_70285_b)), 0.0F);
                        }
                    }
                }
                else if (this.fallTime > 100 && !this.worldObj.isRemote && (var2 < 1 || var2 > 256) || this.fallTime > 600)
                {
                    if (this.field_70284_d)
                    {
                        this.entityDropItem(new ItemStack(this.blockID, 1, Block.blocksList[this.blockID].damageDropped(this.field_70285_b)), 0.0F);
                    }

                    this.setDead();
                }
            }
        }
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void fall(float par1)
    {
        if (this.field_82155_f)
        {
            int var2 = MathHelper.ceiling_float_int(par1 - 1.0F);

            if (var2 > 0)
            {
                ArrayList var3 = new ArrayList(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox));
                DamageSource var4 = this.blockID == Block.field_82510_ck.blockID ? DamageSource.field_82728_o : DamageSource.field_82729_p;
                Iterator var5 = var3.iterator();

                while (var5.hasNext())
                {
                    Entity var6 = (Entity)var5.next();
                    var6.attackEntityFrom(var4, Math.min(MathHelper.floor_float((float)var2 * this.field_82158_h), this.field_82156_g));
                }

                if (this.blockID == Block.field_82510_ck.blockID && (double)this.rand.nextFloat() < 0.05000000074505806D + (double)var2 * 0.05D)
                {
                    int var7 = this.field_70285_b >> 2;
                    int var8 = this.field_70285_b & 3;
                    ++var7;

                    if (var7 > 2)
                    {
                        this.field_82157_e = true;
                    }
                    else
                    {
                        this.field_70285_b = var8 | var7 << 2;
                    }
                }
            }
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setByte("Tile", (byte)this.blockID);
        par1NBTTagCompound.setByte("Data", (byte)this.field_70285_b);
        par1NBTTagCompound.setByte("Time", (byte)this.fallTime);
        par1NBTTagCompound.setBoolean("DropItem", this.field_70284_d);
        par1NBTTagCompound.setBoolean("HurtEntities", this.field_82155_f);
        par1NBTTagCompound.setFloat("FallHurtAmount", this.field_82158_h);
        par1NBTTagCompound.setInteger("FallHurtMax", this.field_82156_g);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        this.blockID = par1NBTTagCompound.getByte("Tile") & 255;
        this.field_70285_b = par1NBTTagCompound.getByte("Data") & 255;
        this.fallTime = par1NBTTagCompound.getByte("Time") & 255;

        if (par1NBTTagCompound.hasKey("HurtEntities"))
        {
            this.field_82155_f = par1NBTTagCompound.getBoolean("HurtEntities");
            this.field_82158_h = par1NBTTagCompound.getFloat("FallHurtAmount");
            this.field_82156_g = par1NBTTagCompound.getInteger("FallHurtMax");
        }
        else if (this.blockID == Block.field_82510_ck.blockID)
        {
            this.field_82155_f = true;
        }

        if (par1NBTTagCompound.hasKey("DropItem"))
        {
            this.field_70284_d = par1NBTTagCompound.getBoolean("DropItem");
        }

        if (this.blockID == 0)
        {
            this.blockID = Block.sand.blockID;
        }
    }

    public float getShadowSize()
    {
        return 0.0F;
    }

    public World getWorld()
    {
        return this.worldObj;
    }

    public void func_82154_e(boolean par1)
    {
        this.field_82155_f = par1;
    }
}
