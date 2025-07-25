package net.minecraft.src;

import java.util.Random;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.glu.GLU;

public class GuiEnchantment extends GuiContainer
{
    /** The book model used on the GUI. */
    private static ModelBook bookModel = new ModelBook();
    private Random rand = new Random();

    /** ContainerEnchantment object associated with this gui */
    private ContainerEnchantment containerEnchantment;
    public int field_74214_o;
    public float field_74213_p;
    public float field_74212_q;
    public float field_74211_r;
    public float field_74210_s;
    public float field_74209_t;
    public float field_74208_u;
    ItemStack field_74207_v;

    public GuiEnchantment(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5)
    {
        super(new ContainerEnchantment(par1InventoryPlayer, par2World, par3, par4, par5));
        this.containerEnchantment = (ContainerEnchantment)this.inventorySlots;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.enchant"), 12, 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        this.func_74205_h();
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
        int var4 = (this.width - this.xSize) / 2;
        int var5 = (this.height - this.ySize) / 2;

        for (int var6 = 0; var6 < 3; ++var6)
        {
            int var7 = par1 - (var4 + 60);
            int var8 = par2 - (var5 + 14 + 19 * var6);

            if (var7 >= 0 && var8 >= 0 && var7 < 108 && var8 < 19 && this.containerEnchantment.enchantItem(this.mc.thePlayer, var6))
            {
                this.mc.playerController.sendEnchantPacket(this.containerEnchantment.windowId, var6);
            }
        }
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        int var4 = this.mc.renderEngine.getTexture("/gui/enchant.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(var4);
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
        GL11.glPushMatrix();
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        ScaledResolution var7 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        GL11.glViewport((var7.getScaledWidth() - 320) / 2 * var7.getScaleFactor(), (var7.getScaledHeight() - 240) / 2 * var7.getScaleFactor(), 320 * var7.getScaleFactor(), 240 * var7.getScaleFactor());
        GL11.glTranslatef(-0.34F, 0.23F, 0.0F);
        GLU.gluPerspective(90.0F, 1.3333334F, 9.0F, 80.0F);
        float var8 = 1.0F;
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        RenderHelper.enableStandardItemLighting();
        GL11.glTranslatef(0.0F, 3.3F, -16.0F);
        GL11.glScalef(var8, var8, var8);
        float var9 = 5.0F;
        GL11.glScalef(var9, var9, var9);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        this.mc.renderEngine.bindTexture(this.mc.renderEngine.getTexture("/item/book.png"));
        GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
        float var10 = this.field_74208_u + (this.field_74209_t - this.field_74208_u) * par1;
        GL11.glTranslatef((1.0F - var10) * 0.2F, (1.0F - var10) * 0.1F, (1.0F - var10) * 0.25F);
        GL11.glRotatef(-(1.0F - var10) * 90.0F - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        float var11 = this.field_74212_q + (this.field_74213_p - this.field_74212_q) * par1 + 0.25F;
        float var12 = this.field_74212_q + (this.field_74213_p - this.field_74212_q) * par1 + 0.75F;
        var11 = (var11 - (float)MathHelper.truncateDoubleToInt((double)var11)) * 1.6F - 0.3F;
        var12 = (var12 - (float)MathHelper.truncateDoubleToInt((double)var12)) * 1.6F - 0.3F;

        if (var11 < 0.0F)
        {
            var11 = 0.0F;
        }

        if (var12 < 0.0F)
        {
            var12 = 0.0F;
        }

        if (var11 > 1.0F)
        {
            var11 = 1.0F;
        }

        if (var12 > 1.0F)
        {
            var12 = 1.0F;
        }

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        bookModel.render((Entity)null, 0.0F, var11, var12, var10, 0.0F, 0.0625F);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(var4);
        EnchantmentNameParts.instance.setRandSeed(this.containerEnchantment.nameSeed);

        for (int var13 = 0; var13 < 3; ++var13)
        {
            String var14 = EnchantmentNameParts.instance.generateRandomEnchantName();
            this.zLevel = 0.0F;
            this.mc.renderEngine.bindTexture(var4);
            int var15 = this.containerEnchantment.enchantLevels[var13];
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            if (var15 == 0)
            {
                this.drawTexturedModalRect(var5 + 60, var6 + 14 + 19 * var13, 0, 185, 108, 19);
            }
            else
            {
                String var16 = "" + var15;
                FontRenderer var17 = this.mc.standardGalacticFontRenderer;
                int var18 = 6839882;

                if (this.mc.thePlayer.experienceLevel < var15 && !this.mc.thePlayer.capabilities.isCreativeMode)
                {
                    this.drawTexturedModalRect(var5 + 60, var6 + 14 + 19 * var13, 0, 185, 108, 19);
                    var17.drawSplitString(var14, var5 + 62, var6 + 16 + 19 * var13, 104, (var18 & 16711422) >> 1);
                    var17 = this.mc.fontRenderer;
                    var18 = 4226832;
                    var17.drawStringWithShadow(var16, var5 + 62 + 104 - var17.getStringWidth(var16), var6 + 16 + 19 * var13 + 7, var18);
                }
                else
                {
                    int var19 = par2 - (var5 + 60);
                    int var20 = par3 - (var6 + 14 + 19 * var13);

                    if (var19 >= 0 && var20 >= 0 && var19 < 108 && var20 < 19)
                    {
                        this.drawTexturedModalRect(var5 + 60, var6 + 14 + 19 * var13, 0, 204, 108, 19);
                        var18 = 16777088;
                    }
                    else
                    {
                        this.drawTexturedModalRect(var5 + 60, var6 + 14 + 19 * var13, 0, 166, 108, 19);
                    }

                    var17.drawSplitString(var14, var5 + 62, var6 + 16 + 19 * var13, 104, var18);
                    var17 = this.mc.fontRenderer;
                    var18 = 8453920;
                    var17.drawStringWithShadow(var16, var5 + 62 + 104 - var17.getStringWidth(var16), var6 + 16 + 19 * var13 + 7, var18);
                }
            }
        }
    }

    public void func_74205_h()
    {
        ItemStack var1 = this.inventorySlots.getSlot(0).getStack();

        if (!ItemStack.areItemStacksEqual(var1, this.field_74207_v))
        {
            this.field_74207_v = var1;

            do
            {
                this.field_74211_r += (float)(this.rand.nextInt(4) - this.rand.nextInt(4));
            }
            while (this.field_74213_p <= this.field_74211_r + 1.0F && this.field_74213_p >= this.field_74211_r - 1.0F);
        }

        ++this.field_74214_o;
        this.field_74212_q = this.field_74213_p;
        this.field_74208_u = this.field_74209_t;
        boolean var2 = false;

        for (int var3 = 0; var3 < 3; ++var3)
        {
            if (this.containerEnchantment.enchantLevels[var3] != 0)
            {
                var2 = true;
            }
        }

        if (var2)
        {
            this.field_74209_t += 0.2F;
        }
        else
        {
            this.field_74209_t -= 0.2F;
        }

        if (this.field_74209_t < 0.0F)
        {
            this.field_74209_t = 0.0F;
        }

        if (this.field_74209_t > 1.0F)
        {
            this.field_74209_t = 1.0F;
        }

        float var5 = (this.field_74211_r - this.field_74213_p) * 0.4F;
        float var4 = 0.2F;

        if (var5 < -var4)
        {
            var5 = -var4;
        }

        if (var5 > var4)
        {
            var5 = var4;
        }

        this.field_74210_s += (var5 - this.field_74210_s) * 0.9F;
        this.field_74213_p += this.field_74210_s;
    }
}
