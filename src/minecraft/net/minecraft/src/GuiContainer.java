package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public abstract class GuiContainer extends GuiScreen
{
    /** Stacks renderer. Icons, stack size, health, etc... */
    protected static RenderItem itemRenderer = new RenderItem();

    /** The X size of the inventory window in pixels. */
    protected int xSize = 176;

    /** The Y size of the inventory window in pixels. */
    protected int ySize = 166;

    /** A list of the players inventory slots. */
    public Container inventorySlots;

    /**
     * Starting X position for the Gui. Inconsistent use for Gui backgrounds.
     */
    protected int guiLeft;

    /**
     * Starting Y position for the Gui. Inconsistent use for Gui backgrounds.
     */
    protected int guiTop;
    private Slot field_82320_o;

    public GuiContainer(Container par1Container)
    {
        this.inventorySlots = par1Container;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.mc.thePlayer.craftingInventory = this.inventorySlots;
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        int var4 = this.guiLeft;
        int var5 = this.guiTop;
        this.drawGuiContainerBackgroundLayer(par3, par1, par2);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        super.drawScreen(par1, par2, par3);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)var4, (float)var5, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        this.field_82320_o = null;
        short var6 = 240;
        short var7 = 240;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var6 / 1.0F, (float)var7 / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        for (int var10 = 0; var10 < this.inventorySlots.inventorySlots.size(); ++var10)
        {
            Slot var11 = (Slot)this.inventorySlots.inventorySlots.get(var10);
            this.drawSlotInventory(var11);

            if (this.isMouseOverSlot(var11, par1, par2))
            {
                this.field_82320_o = var11;
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                int var8 = var11.xDisplayPosition;
                int var9 = var11.yDisplayPosition;
                this.drawGradientRect(var8, var9, var8 + 16, var9 + 16, -2130706433, -2130706433);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
            }
        }

        this.drawGuiContainerForegroundLayer(par1, par2);
        InventoryPlayer var12 = this.mc.thePlayer.inventory;

        if (var12.getItemStack() != null)
        {
            GL11.glTranslatef(0.0F, 0.0F, 32.0F);
            this.zLevel = 200.0F;
            itemRenderer.zLevel = 200.0F;
            itemRenderer.func_82406_b(this.fontRenderer, this.mc.renderEngine, var12.getItemStack(), par1 - var4 - 8, par2 - var5 - 8);
            itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, var12.getItemStack(), par1 - var4 - 8, par2 - var5 - 8);
            this.zLevel = 0.0F;
            itemRenderer.zLevel = 0.0F;
        }

        if (var12.getItemStack() == null && this.field_82320_o != null && this.field_82320_o.getHasStack())
        {
            ItemStack var13 = this.field_82320_o.getStack();
            this.func_74184_a(var13, par1 - var4, par2 - var5);
        }

        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableStandardItemLighting();
    }

    protected void func_74184_a(ItemStack par1ItemStack, int par2, int par3)
    {
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        List var4 = par1ItemStack.func_82840_a(this.mc.thePlayer, this.mc.gameSettings.field_82882_x);

        if (!var4.isEmpty())
        {
            int var5 = 0;
            Iterator var6 = var4.iterator();

            while (var6.hasNext())
            {
                String var7 = (String)var6.next();
                int var8 = this.fontRenderer.getStringWidth(var7);

                if (var8 > var5)
                {
                    var5 = var8;
                }
            }

            int var15 = par2 + 12;
            int var16 = par3 - 12;
            int var9 = 8;

            if (var4.size() > 1)
            {
                var9 += 2 + (var4.size() - 1) * 10;
            }

            this.zLevel = 300.0F;
            itemRenderer.zLevel = 300.0F;
            int var10 = -267386864;
            this.drawGradientRect(var15 - 3, var16 - 4, var15 + var5 + 3, var16 - 3, var10, var10);
            this.drawGradientRect(var15 - 3, var16 + var9 + 3, var15 + var5 + 3, var16 + var9 + 4, var10, var10);
            this.drawGradientRect(var15 - 3, var16 - 3, var15 + var5 + 3, var16 + var9 + 3, var10, var10);
            this.drawGradientRect(var15 - 4, var16 - 3, var15 - 3, var16 + var9 + 3, var10, var10);
            this.drawGradientRect(var15 + var5 + 3, var16 - 3, var15 + var5 + 4, var16 + var9 + 3, var10, var10);
            int var11 = 1347420415;
            int var12 = (var11 & 16711422) >> 1 | var11 & -16777216;
            this.drawGradientRect(var15 - 3, var16 - 3 + 1, var15 - 3 + 1, var16 + var9 + 3 - 1, var11, var12);
            this.drawGradientRect(var15 + var5 + 2, var16 - 3 + 1, var15 + var5 + 3, var16 + var9 + 3 - 1, var11, var12);
            this.drawGradientRect(var15 - 3, var16 - 3, var15 + var5 + 3, var16 - 3 + 1, var11, var11);
            this.drawGradientRect(var15 - 3, var16 + var9 + 2, var15 + var5 + 3, var16 + var9 + 3, var12, var12);

            for (int var13 = 0; var13 < var4.size(); ++var13)
            {
                String var14 = (String)var4.get(var13);

                if (var13 == 0)
                {
                    var14 = "\u00a7" + Integer.toHexString(par1ItemStack.getRarity().rarityColor) + var14;
                }
                else
                {
                    var14 = "\u00a77" + var14;
                }

                this.fontRenderer.drawStringWithShadow(var14, var15, var16, -1);

                if (var13 == 0)
                {
                    var16 += 2;
                }

                var16 += 10;
            }

            this.zLevel = 0.0F;
            itemRenderer.zLevel = 0.0F;
        }
    }

    /**
     * Draws the text when mouse is over creative inventory tab. Params: current creative tab to be checked, current
     * mouse x position, current mouse y position.
     */
    protected void drawCreativeTabHoveringText(String par1Str, int par2, int par3)
    {
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        int var4 = this.fontRenderer.getStringWidth(par1Str);
        int var5 = par2 + 12;
        int var6 = par3 - 12;
        byte var8 = 8;
        this.zLevel = 300.0F;
        itemRenderer.zLevel = 300.0F;
        int var9 = -267386864;
        this.drawGradientRect(var5 - 3, var6 - 4, var5 + var4 + 3, var6 - 3, var9, var9);
        this.drawGradientRect(var5 - 3, var6 + var8 + 3, var5 + var4 + 3, var6 + var8 + 4, var9, var9);
        this.drawGradientRect(var5 - 3, var6 - 3, var5 + var4 + 3, var6 + var8 + 3, var9, var9);
        this.drawGradientRect(var5 - 4, var6 - 3, var5 - 3, var6 + var8 + 3, var9, var9);
        this.drawGradientRect(var5 + var4 + 3, var6 - 3, var5 + var4 + 4, var6 + var8 + 3, var9, var9);
        int var10 = 1347420415;
        int var11 = (var10 & 16711422) >> 1 | var10 & -16777216;
        this.drawGradientRect(var5 - 3, var6 - 3 + 1, var5 - 3 + 1, var6 + var8 + 3 - 1, var10, var11);
        this.drawGradientRect(var5 + var4 + 2, var6 - 3 + 1, var5 + var4 + 3, var6 + var8 + 3 - 1, var10, var11);
        this.drawGradientRect(var5 - 3, var6 - 3, var5 + var4 + 3, var6 - 3 + 1, var10, var10);
        this.drawGradientRect(var5 - 3, var6 + var8 + 2, var5 + var4 + 3, var6 + var8 + 3, var11, var11);
        this.fontRenderer.drawStringWithShadow(par1Str, var5, var6, -1);
        this.zLevel = 0.0F;
        itemRenderer.zLevel = 0.0F;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {}

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected abstract void drawGuiContainerBackgroundLayer(float var1, int var2, int var3);

    /**
     * Draws an inventory slot
     */
    private void drawSlotInventory(Slot par1Slot)
    {
        int var2 = par1Slot.xDisplayPosition;
        int var3 = par1Slot.yDisplayPosition;
        ItemStack var4 = par1Slot.getStack();
        boolean var5 = false;
        this.zLevel = 100.0F;
        itemRenderer.zLevel = 100.0F;

        if (var4 == null)
        {
            int var6 = par1Slot.getBackgroundIconIndex();

            if (var6 >= 0)
            {
                GL11.glDisable(GL11.GL_LIGHTING);
                this.mc.renderEngine.bindTexture(this.mc.renderEngine.getTexture("/gui/items.png"));
                this.drawTexturedModalRect(var2, var3, var6 % 16 * 16, var6 / 16 * 16, 16, 16);
                GL11.glEnable(GL11.GL_LIGHTING);
                var5 = true;
            }
        }

        if (!var5)
        {
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            itemRenderer.func_82406_b(this.fontRenderer, this.mc.renderEngine, var4, var2, var3);
            itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, var4, var2, var3);
        }

        itemRenderer.zLevel = 0.0F;
        this.zLevel = 0.0F;
    }

    /**
     * Returns the slot at the given coordinates or null if there is none.
     */
    private Slot getSlotAtPosition(int par1, int par2)
    {
        for (int var3 = 0; var3 < this.inventorySlots.inventorySlots.size(); ++var3)
        {
            Slot var4 = (Slot)this.inventorySlots.inventorySlots.get(var3);

            if (this.isMouseOverSlot(var4, par1, par2))
            {
                return var4;
            }
        }

        return null;
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
        boolean var4 = par3 == this.mc.gameSettings.keyBindPickBlock.keyCode + 100;

        if (par3 == 0 || par3 == 1 || var4)
        {
            Slot var5 = this.getSlotAtPosition(par1, par2);
            int var6 = this.guiLeft;
            int var7 = this.guiTop;
            boolean var8 = par1 < var6 || par2 < var7 || par1 >= var6 + this.xSize || par2 >= var7 + this.ySize;
            int var9 = -1;

            if (var5 != null)
            {
                var9 = var5.slotNumber;
            }

            if (var8)
            {
                var9 = -999;
            }

            if (var9 != -1)
            {
                if (var4)
                {
                    this.handleMouseClick(var5, var9, par3, 3);
                }
                else
                {
                    boolean var10 = var9 != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
                    this.handleMouseClick(var5, var9, par3, var10 ? 1 : 0);
                }
            }
        }
    }

    /**
     * Returns if the passed mouse position is over the specified slot.
     */
    private boolean isMouseOverSlot(Slot par1Slot, int par2, int par3)
    {
        return this.func_74188_c(par1Slot.xDisplayPosition, par1Slot.yDisplayPosition, 16, 16, par2, par3);
    }

    protected boolean func_74188_c(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        int var7 = this.guiLeft;
        int var8 = this.guiTop;
        par5 -= var7;
        par6 -= var8;
        return par5 >= par1 - 1 && par5 < par1 + par3 + 1 && par6 >= par2 - 1 && par6 < par2 + par4 + 1;
    }

    protected void handleMouseClick(Slot par1Slot, int par2, int par3, int par4)
    {
        if (par1Slot != null)
        {
            par2 = par1Slot.slotNumber;
        }

        this.mc.playerController.windowClick(this.inventorySlots.windowId, par2, par3, par4, this.mc.thePlayer);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        if (par2 == 1 || par2 == this.mc.gameSettings.keyBindInventory.keyCode)
        {
            this.mc.thePlayer.closeScreen();
        }

        this.func_82319_a(par2);

        if (par2 == this.mc.gameSettings.keyBindPickBlock.keyCode && this.field_82320_o != null && this.field_82320_o.getHasStack())
        {
            this.handleMouseClick(this.field_82320_o, this.field_82320_o.slotNumber, this.ySize, 3);
        }
    }

    protected boolean func_82319_a(int par1)
    {
        if (this.mc.thePlayer.inventory.getItemStack() == null && this.field_82320_o != null)
        {
            for (int var2 = 0; var2 < 9; ++var2)
            {
                if (par1 == 2 + var2)
                {
                    this.handleMouseClick(this.field_82320_o, this.field_82320_o.slotNumber, var2, 2);
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        if (this.mc.thePlayer != null)
        {
            this.inventorySlots.onCraftGuiClosed(this.mc.thePlayer);
        }
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();

        if (!this.mc.thePlayer.isEntityAlive() || this.mc.thePlayer.isDead)
        {
            this.mc.thePlayer.closeScreen();
        }
    }
}
