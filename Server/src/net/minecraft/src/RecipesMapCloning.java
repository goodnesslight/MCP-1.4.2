package net.minecraft.src;

public class RecipesMapCloning implements IRecipe
{
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World)
    {
        int var3 = 0;
        ItemStack var4 = null;

        for (int var5 = 0; var5 < par1InventoryCrafting.getSizeInventory(); ++var5)
        {
            ItemStack var6 = par1InventoryCrafting.getStackInSlot(var5);

            if (var6 != null)
            {
                if (var6.itemID == Item.map.shiftedIndex)
                {
                    if (var4 != null)
                    {
                        return false;
                    }

                    var4 = var6;
                }
                else
                {
                    if (var6.itemID != Item.field_82801_bO.shiftedIndex)
                    {
                        return false;
                    }

                    ++var3;
                }
            }
        }

        return var4 != null && var3 > 0;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting)
    {
        int var2 = 0;
        ItemStack var3 = null;

        for (int var4 = 0; var4 < par1InventoryCrafting.getSizeInventory(); ++var4)
        {
            ItemStack var5 = par1InventoryCrafting.getStackInSlot(var4);

            if (var5 != null)
            {
                if (var5.itemID == Item.map.shiftedIndex)
                {
                    if (var3 != null)
                    {
                        return null;
                    }

                    var3 = var5;
                }
                else
                {
                    if (var5.itemID != Item.field_82801_bO.shiftedIndex)
                    {
                        return null;
                    }

                    ++var2;
                }
            }
        }

        if (var3 != null && var2 >= 1)
        {
            return new ItemStack(Item.map, var2 + 1, var3.getItemDamage());
        }
        else
        {
            return null;
        }
    }

    /**
     * Returns the size of the recipe area
     */
    public int getRecipeSize()
    {
        return 9;
    }

    public ItemStack getRecipeOutput()
    {
        return null;
    }
}
