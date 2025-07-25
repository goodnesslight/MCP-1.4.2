package net.minecraft.src;

public class EntityWitherSkull extends EntityFireball
{
    public EntityWitherSkull(World par1World)
    {
        super(par1World);
        this.setSize(0.3125F, 0.3125F);
    }

    public EntityWitherSkull(World par1World, EntityLiving par2EntityLiving, double par3, double par5, double par7)
    {
        super(par1World, par2EntityLiving, par3, par5, par7);
        this.setSize(0.3125F, 0.3125F);
    }

    protected float func_82341_c()
    {
        return this.func_82342_d() ? 0.73F : super.func_82341_c();
    }

    public EntityWitherSkull(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        super(par1World, par2, par4, par6, par8, par10, par12);
        this.setSize(0.3125F, 0.3125F);
    }

    /**
     * Returns true if the entity is on fire. Used by render to add the fire effect on rendering.
     */
    public boolean isBurning()
    {
        return false;
    }

    public float func_82146_a(Explosion par1Explosion, Block par2Block, int par3, int par4, int par5)
    {
        float var6 = super.func_82146_a(par1Explosion, par2Block, par3, par4, par5);

        if (this.func_82342_d() && par2Block != Block.bedrock)
        {
            var6 = Math.min(0.8F, var6);
        }

        return var6;
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
    {
        if (!this.worldObj.isRemote)
        {
            if (par1MovingObjectPosition.entityHit != null)
            {
                if (this.shootingEntity != null)
                {
                    if (par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), 8) && !par1MovingObjectPosition.entityHit.isEntityAlive())
                    {
                        this.shootingEntity.heal(5);
                    }
                }
                else
                {
                    par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.magic, 5);
                }

                if (par1MovingObjectPosition.entityHit instanceof EntityLiving)
                {
                    byte var2 = 0;

                    if (this.worldObj.difficultySetting > 1)
                    {
                        if (this.worldObj.difficultySetting == 2)
                        {
                            var2 = 10;
                        }
                        else if (this.worldObj.difficultySetting == 3)
                        {
                            var2 = 40;
                        }
                    }

                    if (var2 > 0)
                    {
                        ((EntityLiving)par1MovingObjectPosition.entityHit).addPotionEffect(new PotionEffect(Potion.field_82731_v.id, 20 * var2, 1));
                    }
                }
            }

            this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 1.0F, false, this.worldObj.func_82736_K().func_82766_b("mobGriefing"));
            this.setDead();
        }
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return false;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
        return false;
    }

    protected void entityInit()
    {
        this.dataWatcher.addObject(10, Byte.valueOf((byte)0));
    }

    public boolean func_82342_d()
    {
        return this.dataWatcher.getWatchableObjectByte(10) == 1;
    }

    public void func_82343_e(boolean par1)
    {
        this.dataWatcher.updateObject(10, Byte.valueOf((byte)(par1 ? 1 : 0)));
    }
}
