package net.minecraft.src;

public class EntityCreeper extends EntityMob
{
    /**
     * Time when this creeper was last in an active state (Messed up code here, probably causes creeper animation to go
     * weird)
     */
    private int lastActiveTime;

    /**
     * The amount of time since the creeper was close enough to the player to ignite
     */
    private int timeSinceIgnited;
    private int field_82225_f = 30;
    private int field_82226_g = 3;

    public EntityCreeper(World par1World)
    {
        super(par1World);
        this.texture = "/mob/creeper.png";
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAICreeperSwell(this));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 0.25F, 0.3F));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 0.25F, false));
        this.tasks.addTask(5, new EntityAIWander(this, 0.2F));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return true;
    }

    public int func_82143_as()
    {
        return this.getAttackTarget() == null ? 3 : 3 + (this.health - 1);
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void fall(float par1)
    {
        super.fall(par1);
        this.timeSinceIgnited = (int)((float)this.timeSinceIgnited + par1 * 1.5F);

        if (this.timeSinceIgnited > this.field_82225_f - 5)
        {
            this.timeSinceIgnited = this.field_82225_f - 5;
        }
    }

    public int getMaxHealth()
    {
        return 20;
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, Byte.valueOf((byte) - 1));
        this.dataWatcher.addObject(17, Byte.valueOf((byte)0));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);

        if (this.dataWatcher.getWatchableObjectByte(17) == 1)
        {
            par1NBTTagCompound.setBoolean("powered", true);
        }

        par1NBTTagCompound.setShort("Fuse", (short)this.field_82225_f);
        par1NBTTagCompound.setByte("ExplosionRadius", (byte)this.field_82226_g);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.dataWatcher.updateObject(17, Byte.valueOf((byte)(par1NBTTagCompound.getBoolean("powered") ? 1 : 0)));

        if (par1NBTTagCompound.hasKey("Fuse"))
        {
            this.field_82225_f = par1NBTTagCompound.getShort("Fuse");
        }

        if (par1NBTTagCompound.hasKey("ExplosionRadius"))
        {
            this.field_82226_g = par1NBTTagCompound.getByte("ExplosionRadius");
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        if (this.isEntityAlive())
        {
            this.lastActiveTime = this.timeSinceIgnited;
            int var1 = this.getCreeperState();

            if (var1 > 0 && this.timeSinceIgnited == 0)
            {
                this.worldObj.playSoundAtEntity(this, "random.fuse", 1.0F, 0.5F);
            }

            this.timeSinceIgnited += var1;

            if (this.timeSinceIgnited < 0)
            {
                this.timeSinceIgnited = 0;
            }

            if (this.timeSinceIgnited >= this.field_82225_f)
            {
                this.timeSinceIgnited = this.field_82225_f;

                if (!this.worldObj.isRemote)
                {
                    boolean var2 = this.worldObj.func_82736_K().func_82766_b("mobGriefing");

                    if (this.getPowered())
                    {
                        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)(this.field_82226_g * 2), var2);
                    }
                    else
                    {
                        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)this.field_82226_g, var2);
                    }

                    this.setDead();
                }
            }
        }

        super.onUpdate();
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.creeper.say";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.creeper.death";
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource par1DamageSource)
    {
        super.onDeath(par1DamageSource);

        if (par1DamageSource.getEntity() instanceof EntitySkeleton)
        {
            this.dropItem(Item.record13.shiftedIndex + this.rand.nextInt(10), 1);
        }
    }

    public boolean attackEntityAsMob(Entity par1Entity)
    {
        return true;
    }

    /**
     * Returns true if the creeper is powered by a lightning bolt.
     */
    public boolean getPowered()
    {
        return this.dataWatcher.getWatchableObjectByte(17) == 1;
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        return Item.gunpowder.shiftedIndex;
    }

    /**
     * Returns the current state of creeper, -1 is idle, 1 is 'in fuse'
     */
    public int getCreeperState()
    {
        return this.dataWatcher.getWatchableObjectByte(16);
    }

    /**
     * Sets the state of creeper, -1 to idle and 1 to be 'in fuse'
     */
    public void setCreeperState(int par1)
    {
        this.dataWatcher.updateObject(16, Byte.valueOf((byte)par1));
    }

    /**
     * Called when a lightning bolt hits the entity.
     */
    public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt)
    {
        super.onStruckByLightning(par1EntityLightningBolt);
        this.dataWatcher.updateObject(17, Byte.valueOf((byte)1));
    }
}
