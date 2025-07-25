package net.minecraft.src;

import org.lwjgl.input.Keyboard;

public class GuiScreenAddServer extends GuiScreen
{
    /** This GUI's parent GUI. */
    private GuiScreen parentGui;
    private GuiTextField serverAddress;
    private GuiTextField serverName;

    /** ServerData to be modified by this GUI */
    private ServerData newServerData;

    public GuiScreenAddServer(GuiScreen par1GuiScreen, ServerData par2ServerData)
    {
        this.parentGui = par1GuiScreen;
        this.newServerData = par2ServerData;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.serverName.updateCursorCounter();
        this.serverAddress.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        StringTranslate var1 = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents(true);
        this.controlList.clear();
        this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, var1.translateKey("addServer.add")));
        this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, var1.translateKey("gui.cancel")));
        this.controlList.add(new GuiButton(2, this.width / 2 - 100, 142, var1.translateKey("addServer.hideAddress") + ": " + (this.newServerData.func_82820_d() ? var1.translateKey("gui.yes") : var1.translateKey("gui.no"))));
        this.serverName = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 66, 200, 20);
        this.serverName.setFocused(true);
        this.serverName.setText(this.newServerData.serverName);
        this.serverAddress = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 106, 200, 20);
        this.serverAddress.setMaxStringLength(128);
        this.serverAddress.setText(this.newServerData.serverIP);
        ((GuiButton)this.controlList.get(0)).enabled = this.serverAddress.getText().length() > 0 && this.serverAddress.getText().split(":").length > 0 && this.serverName.getText().length() > 0;
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.enabled)
        {
            if (par1GuiButton.id == 1)
            {
                this.parentGui.confirmClicked(false, 0);
            }
            else if (par1GuiButton.id == 0)
            {
                this.newServerData.serverName = this.serverName.getText();
                this.newServerData.serverIP = this.serverAddress.getText();
                this.parentGui.confirmClicked(true, 0);
            }
            else if (par1GuiButton.id == 2)
            {
                StringTranslate var2 = StringTranslate.getInstance();
                this.newServerData.func_82819_b(!this.newServerData.func_82820_d());
                ((GuiButton)this.controlList.get(2)).displayString = var2.translateKey("addServer.hideAddress") + ": " + (this.newServerData.func_82820_d() ? var2.translateKey("gui.yes") : var2.translateKey("gui.no"));
            }
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        this.serverName.textboxKeyTyped(par1, par2);
        this.serverAddress.textboxKeyTyped(par1, par2);

        if (par1 == 9)
        {
            if (this.serverName.isFocused())
            {
                this.serverName.setFocused(false);
                this.serverAddress.setFocused(true);
            }
            else
            {
                this.serverName.setFocused(true);
                this.serverAddress.setFocused(false);
            }
        }

        if (par1 == 13)
        {
            this.actionPerformed((GuiButton)this.controlList.get(0));
        }

        ((GuiButton)this.controlList.get(0)).enabled = this.serverAddress.getText().length() > 0 && this.serverAddress.getText().split(":").length > 0 && this.serverName.getText().length() > 0;

        if (((GuiButton)this.controlList.get(0)).enabled)
        {
            String var3 = this.serverAddress.getText().trim();
            String[] var4 = var3.split(":");

            if (var4.length > 2)
            {
                ((GuiButton)this.controlList.get(0)).enabled = false;
            }
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
        this.serverAddress.mouseClicked(par1, par2, par3);
        this.serverName.mouseClicked(par1, par2, par3);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        StringTranslate var4 = StringTranslate.getInstance();
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, var4.translateKey("addServer.title"), this.width / 2, 17, 16777215);
        this.drawString(this.fontRenderer, var4.translateKey("addServer.enterName"), this.width / 2 - 100, 53, 10526880);
        this.drawString(this.fontRenderer, var4.translateKey("addServer.enterIp"), this.width / 2 - 100, 94, 10526880);
        this.serverName.drawTextBox();
        this.serverAddress.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }
}
