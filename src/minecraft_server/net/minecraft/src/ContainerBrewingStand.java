package net.minecraft.src;

import java.util.Iterator;

public class ContainerBrewingStand extends Container
{
    private TileEntityBrewingStand tileBrewingStand;

    /** Instance of Slot. */
    private final Slot theSlot;
    private int brewTime = 0;

    public ContainerBrewingStand(InventoryPlayer par1InventoryPlayer, TileEntityBrewingStand par2TileEntityBrewingStand)
    {
        this.tileBrewingStand = par2TileEntityBrewingStand;
        this.addSlotToContainer(new SlotBrewingStandPotion(par1InventoryPlayer.player, par2TileEntityBrewingStand, 0, 56, 46));
        this.addSlotToContainer(new SlotBrewingStandPotion(par1InventoryPlayer.player, par2TileEntityBrewingStand, 1, 79, 53));
        this.addSlotToContainer(new SlotBrewingStandPotion(par1InventoryPlayer.player, par2TileEntityBrewingStand, 2, 102, 46));
        this.theSlot = this.addSlotToContainer(new SlotBrewingStandIngredient(this, par2TileEntityBrewingStand, 3, 79, 17));
        int var3;

        for (var3 = 0; var3 < 3; ++var3)
        {
            for (int var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 142));
        }
    }

    public void onCraftGuiOpened(ICrafting par1ICrafting)
    {
        super.onCraftGuiOpened(par1ICrafting);
        par1ICrafting.updateCraftingInventoryInfo(this, 0, this.tileBrewingStand.getBrewTime());
    }

    /**
     * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
     */
    public void updateCraftingResults()
    {
        super.updateCraftingResults();
        Iterator var1 = this.crafters.iterator();

        while (var1.hasNext())
        {
            ICrafting var2 = (ICrafting)var1.next();

            if (this.brewTime != this.tileBrewingStand.getBrewTime())
            {
                var2.updateCraftingInventoryInfo(this, 0, this.tileBrewingStand.getBrewTime());
            }
        }

        this.brewTime = this.tileBrewingStand.getBrewTime();
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.tileBrewingStand.isUseableByPlayer(par1EntityPlayer);
    }

    public ItemStack func_82846_b(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(par2);

        if (var4 != null && var4.getHasStack())
        {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();

            if ((par2 < 0 || par2 > 2) && par2 != 3)
            {
                if (!this.theSlot.getHasStack() && this.theSlot.isItemValid(var5))
                {
                    if (!this.mergeItemStack(var5, 3, 4, false))
                    {
                        return null;
                    }
                }
                else if (SlotBrewingStandPotion.func_75243_a_(var3))
                {
                    if (!this.mergeItemStack(var5, 0, 3, false))
                    {
                        return null;
                    }
                }
                else if (par2 >= 4 && par2 < 31)
                {
                    if (!this.mergeItemStack(var5, 31, 40, false))
                    {
                        return null;
                    }
                }
                else if (par2 >= 31 && par2 < 40)
                {
                    if (!this.mergeItemStack(var5, 4, 31, false))
                    {
                        return null;
                    }
                }
                else if (!this.mergeItemStack(var5, 4, 40, false))
                {
                    return null;
                }
            }
            else
            {
                if (!this.mergeItemStack(var5, 4, 40, true))
                {
                    return null;
                }

                var4.onSlotChange(var5, var3);
            }

            if (var5.stackSize == 0)
            {
                var4.putStack((ItemStack)null);
            }
            else
            {
                var4.onSlotChanged();
            }

            if (var5.stackSize == var3.stackSize)
            {
                return null;
            }

            var4.func_82870_a(par1EntityPlayer, var5);
        }

        return var3;
    }
}
