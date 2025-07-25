package net.minecraft.src;

class GuiBeaconButtonPower extends GuiBeaconButton
{
    private final int field_82261_l;
    private final int field_82262_m;

    final GuiBeacon field_82263_k;

    public GuiBeaconButtonPower(GuiBeacon par1, int par2, int par3, int par4, int par5, int par6)
    {
        super(par2, par3, par4, "/gui/inventory.png", 0 + Potion.potionTypes[par5].getStatusIconIndex() % 8 * 18, 198 + Potion.potionTypes[par5].getStatusIconIndex() / 8 * 18);
        this.field_82263_k = par1;
        this.field_82261_l = par5;
        this.field_82262_m = par6;
    }

    public void func_82251_b(int par1, int par2)
    {
        String var3 = StatCollector.translateToLocal(Potion.potionTypes[this.field_82261_l].getName());

        if (this.field_82262_m >= 3 && this.field_82261_l != Potion.regeneration.id)
        {
            var3 = var3 + " II";
        }

        this.field_82263_k.drawCreativeTabHoveringText(var3, par1, par2);
    }
}
