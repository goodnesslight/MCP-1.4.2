package net.minecraft.src;

public class StepSound
{
    public final String stepSoundName;
    public final float stepSoundVolume;
    public final float stepSoundPitch;

    public StepSound(String par1Str, float par2, float par3)
    {
        this.stepSoundName = par1Str;
        this.stepSoundVolume = par2;
        this.stepSoundPitch = par3;
    }

    public float getVolume()
    {
        return this.stepSoundVolume;
    }

    public float getPitch()
    {
        return this.stepSoundPitch;
    }

    public String func_72676_a()
    {
        return "dig." + this.stepSoundName;
    }

    /**
     * Used when a entity walks over, or otherwise interacts with the block.
     */
    public String getStepSound()
    {
        return "step." + this.stepSoundName;
    }

    public String func_82593_b()
    {
        return this.func_72676_a();
    }
}
