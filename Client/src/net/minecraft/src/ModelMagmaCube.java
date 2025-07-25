package net.minecraft.src;

public class ModelMagmaCube extends ModelBase
{
    ModelRenderer[] field_78109_a = new ModelRenderer[8];
    ModelRenderer field_78108_b;

    public ModelMagmaCube()
    {
        for (int var1 = 0; var1 < this.field_78109_a.length; ++var1)
        {
            byte var2 = 0;
            int var3 = var1;

            if (var1 == 2)
            {
                var2 = 24;
                var3 = 10;
            }
            else if (var1 == 3)
            {
                var2 = 24;
                var3 = 19;
            }

            this.field_78109_a[var1] = new ModelRenderer(this, var2, var3);
            this.field_78109_a[var1].addBox(-4.0F, (float)(16 + var1), -4.0F, 8, 1, 8);
        }

        this.field_78108_b = new ModelRenderer(this, 0, 16);
        this.field_78108_b.addBox(-2.0F, 18.0F, -2.0F, 4, 4, 4);
    }

    public int func_78107_a()
    {
        return 5;
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    public void setLivingAnimations(EntityLiving par1EntityLiving, float par2, float par3, float par4)
    {
        EntityMagmaCube var5 = (EntityMagmaCube)par1EntityLiving;
        float var6 = var5.field_70812_c + (var5.field_70811_b - var5.field_70812_c) * par4;

        if (var6 < 0.0F)
        {
            var6 = 0.0F;
        }

        for (int var7 = 0; var7 < this.field_78109_a.length; ++var7)
        {
            this.field_78109_a[var7].rotationPointY = (float)(-(4 - var7)) * var6 * 1.7F;
        }
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        this.field_78108_b.render(par7);
        ModelRenderer[] var8 = this.field_78109_a;
        int var9 = var8.length;

        for (int var10 = 0; var10 < var9; ++var10)
        {
            ModelRenderer var11 = var8[var10];
            var11.render(par7);
        }
    }
}
