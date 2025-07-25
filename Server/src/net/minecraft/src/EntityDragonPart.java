package net.minecraft.src;

public class EntityDragonPart extends Entity
{
    /** The dragon entity this dragon part belongs to */
    public final IEntityMultiPart entityDragonObj;

    /** The name of the Dragon Part */
    public final String name;

    public EntityDragonPart(IEntityMultiPart par1, String par2, float par3, float par4)
    {
        super(par1.func_82194_d());
        this.setSize(par3, par4);
        this.entityDragonObj = par1;
        this.name = par2;
    }

    protected void entityInit() {}

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {}

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {}

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return true;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
        return this.entityDragonObj.attackEntityFromPart(this, par1DamageSource, par2);
    }

    /**
     * Returns true if Entity argument is equal to this Entity
     */
    public boolean isEntityEqual(Entity par1Entity)
    {
        return this == par1Entity || this.entityDragonObj == par1Entity;
    }
}
