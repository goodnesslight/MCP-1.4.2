package net.minecraft.src;

public class ItemCarrotOnAStick extends Item
{
    public ItemCarrotOnAStick(int par1)
    {
        super(par1);
        this.setCreativeTab(CreativeTabs.tabTransport);
        this.setMaxStackSize(1);
        this.setMaxDamage(25);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (par3EntityPlayer.func_70115_ae() && par3EntityPlayer.ridingEntity instanceof EntityPig)
        {
            EntityPig var4 = (EntityPig)par3EntityPlayer.ridingEntity;

            if (var4.func_82183_n().func_82633_h() && par1ItemStack.getMaxDamage() - par1ItemStack.getItemDamage() >= 7)
            {
                var4.func_82183_n().func_82632_g();
                par1ItemStack.damageItem(7, par3EntityPlayer);

                if (par1ItemStack.stackSize == 0)
                {
                    return new ItemStack(Item.fishingRod);
                }
            }
        }

        return par1ItemStack;
    }
}
