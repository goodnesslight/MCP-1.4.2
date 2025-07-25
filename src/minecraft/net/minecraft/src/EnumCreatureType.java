package net.minecraft.src;

public enum EnumCreatureType
{
    monster(IMob.class, 70, Material.air, false, false),
    creature(EntityAnimal.class, 15, Material.air, true, true),
    ambient(EntityAmbientCreature.class, 15, Material.air, true, false),
    waterCreature(EntityWaterMob.class, 5, Material.water, true, false);

    /**
     * The root class of creatures associated with this EnumCreatureType (IMobs for aggressive creatures, EntityAnimals
     * for friendly ones)
     */
    private final Class creatureClass;
    private final int maxNumberOfCreature;
    private final Material creatureMaterial;

    /** A flag indicating whether this creature type is peaceful. */
    private final boolean isPeacefulCreature;
    private final boolean field_82707_i;

    private EnumCreatureType(Class par3Class, int par4, Material par5Material, boolean par6, boolean par7)
    {
        this.creatureClass = par3Class;
        this.maxNumberOfCreature = par4;
        this.creatureMaterial = par5Material;
        this.isPeacefulCreature = par6;
        this.field_82707_i = par7;
    }

    public Class getCreatureClass()
    {
        return this.creatureClass;
    }

    public int getMaxNumberOfCreature()
    {
        return this.maxNumberOfCreature;
    }

    public Material getCreatureMaterial()
    {
        return this.creatureMaterial;
    }

    /**
     * Gets whether or not this creature type is peaceful.
     */
    public boolean getPeacefulCreature()
    {
        return this.isPeacefulCreature;
    }

    public boolean func_82705_e()
    {
        return this.field_82707_i;
    }
}
