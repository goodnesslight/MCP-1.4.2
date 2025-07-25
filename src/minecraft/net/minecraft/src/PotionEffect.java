package net.minecraft.src;

public class PotionEffect
{
    /** ID value of the potion this effect matches. */
    private int potionID;

    /** The duration of the potion effect */
    private int duration;

    /** The amplifier of the potion effect */
    private int amplifier;
    private boolean field_82723_d;
    private boolean field_82724_e;

    public PotionEffect(int par1, int par2)
    {
        this(par1, par2, 0);
    }

    public PotionEffect(int par1, int par2, int par3)
    {
        this(par1, par2, par3, false);
    }

    public PotionEffect(int par1, int par2, int par3, boolean par4)
    {
        this.potionID = par1;
        this.duration = par2;
        this.amplifier = par3;
        this.field_82724_e = par4;
    }

    public PotionEffect(PotionEffect par1PotionEffect)
    {
        this.potionID = par1PotionEffect.potionID;
        this.duration = par1PotionEffect.duration;
        this.amplifier = par1PotionEffect.amplifier;
    }

    /**
     * merges the input PotionEffect into this one if this.amplifier <= tomerge.amplifier. The duration in the supplied
     * potion effect is assumed to be greater.
     */
    public void combine(PotionEffect par1PotionEffect)
    {
        if (this.potionID != par1PotionEffect.potionID)
        {
            System.err.println("This method should only be called for matching effects!");
        }

        if (par1PotionEffect.amplifier > this.amplifier)
        {
            this.amplifier = par1PotionEffect.amplifier;
            this.duration = par1PotionEffect.duration;
        }
        else if (par1PotionEffect.amplifier == this.amplifier && this.duration < par1PotionEffect.duration)
        {
            this.duration = par1PotionEffect.duration;
        }
        else if (!par1PotionEffect.field_82724_e && this.field_82724_e)
        {
            this.field_82724_e = par1PotionEffect.field_82724_e;
        }
    }

    /**
     * Retrieve the ID of the potion this effect matches.
     */
    public int getPotionID()
    {
        return this.potionID;
    }

    public int getDuration()
    {
        return this.duration;
    }

    public int getAmplifier()
    {
        return this.amplifier;
    }

    public void func_82721_a(boolean par1)
    {
        this.field_82723_d = par1;
    }

    public boolean func_82720_e()
    {
        return this.field_82724_e;
    }

    public boolean onUpdate(EntityLiving par1EntityLiving)
    {
        if (this.duration > 0)
        {
            if (Potion.potionTypes[this.potionID].isReady(this.duration, this.amplifier))
            {
                this.performEffect(par1EntityLiving);
            }

            this.deincrementDuration();
        }

        return this.duration > 0;
    }

    private int deincrementDuration()
    {
        return --this.duration;
    }

    public void performEffect(EntityLiving par1EntityLiving)
    {
        if (this.duration > 0)
        {
            Potion.potionTypes[this.potionID].performEffect(par1EntityLiving, this.amplifier);
        }
    }

    public String getEffectName()
    {
        return Potion.potionTypes[this.potionID].getName();
    }

    public int hashCode()
    {
        return this.potionID;
    }

    public String toString()
    {
        String var1 = "";

        if (this.getAmplifier() > 0)
        {
            var1 = this.getEffectName() + " x " + (this.getAmplifier() + 1) + ", Duration: " + this.getDuration();
        }
        else
        {
            var1 = this.getEffectName() + ", Duration: " + this.getDuration();
        }

        if (this.field_82723_d)
        {
            var1 = var1 + ", Splash: true";
        }

        return Potion.potionTypes[this.potionID].isUsable() ? "(" + var1 + ")" : var1;
    }

    public boolean equals(Object par1Obj)
    {
        if (!(par1Obj instanceof PotionEffect))
        {
            return false;
        }
        else
        {
            PotionEffect var2 = (PotionEffect)par1Obj;
            return this.potionID == var2.potionID && this.amplifier == var2.amplifier && this.duration == var2.duration && this.field_82723_d == var2.field_82723_d && this.field_82724_e == var2.field_82724_e;
        }
    }

    public NBTTagCompound func_82719_a(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setByte("Id", (byte)this.getPotionID());
        par1NBTTagCompound.setByte("Amplifier", (byte)this.getAmplifier());
        par1NBTTagCompound.setInteger("Duration", this.getDuration());
        par1NBTTagCompound.setBoolean("Ambient", this.func_82720_e());
        return par1NBTTagCompound;
    }

    public static PotionEffect func_82722_b(NBTTagCompound par0NBTTagCompound)
    {
        byte var1 = par0NBTTagCompound.getByte("Id");
        byte var2 = par0NBTTagCompound.getByte("Amplifier");
        int var3 = par0NBTTagCompound.getInteger("Duration");
        boolean var4 = par0NBTTagCompound.getBoolean("Ambient");
        return new PotionEffect(var1, var3, var2, var4);
    }
}
