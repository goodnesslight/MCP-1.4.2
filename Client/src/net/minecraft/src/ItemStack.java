package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public final class ItemStack
{
    /** Size of the stack. */
    public int stackSize;

    /**
     * Number of animation frames to go when receiving an item (by walking into it, for example).
     */
    public int animationsToGo;

    /** ID of the item. */
    public int itemID;

    /**
     * A NBTTagMap containing data about an ItemStack. Can only be used for non stackable items
     */
    public NBTTagCompound stackTagCompound;

    /** Damage dealt to the item or number of use. Raise when using items. */
    private int itemDamage;
    private EntityItemFrame field_82843_f;

    public ItemStack(Block par1Block)
    {
        this(par1Block, 1);
    }

    public ItemStack(Block par1Block, int par2)
    {
        this(par1Block.blockID, par2, 0);
    }

    public ItemStack(Block par1Block, int par2, int par3)
    {
        this(par1Block.blockID, par2, par3);
    }

    public ItemStack(Item par1Item)
    {
        this(par1Item.shiftedIndex, 1, 0);
    }

    public ItemStack(Item par1Item, int par2)
    {
        this(par1Item.shiftedIndex, par2, 0);
    }

    public ItemStack(Item par1Item, int par2, int par3)
    {
        this(par1Item.shiftedIndex, par2, par3);
    }

    public ItemStack(int par1, int par2, int par3)
    {
        this.stackSize = 0;
        this.field_82843_f = null;
        this.itemID = par1;
        this.stackSize = par2;
        this.itemDamage = par3;
    }

    public static ItemStack loadItemStackFromNBT(NBTTagCompound par0NBTTagCompound)
    {
        ItemStack var1 = new ItemStack();
        var1.readFromNBT(par0NBTTagCompound);
        return var1.getItem() != null ? var1 : null;
    }

    private ItemStack()
    {
        this.stackSize = 0;
        this.field_82843_f = null;
    }

    /**
     * Remove the argument from the stack size. Return a new stack object with argument size.
     */
    public ItemStack splitStack(int par1)
    {
        ItemStack var2 = new ItemStack(this.itemID, par1, this.itemDamage);

        if (this.stackTagCompound != null)
        {
            var2.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
        }

        this.stackSize -= par1;
        return var2;
    }

    /**
     * Returns the object corresponding to the stack.
     */
    public Item getItem()
    {
        return Item.itemsList[this.itemID];
    }

    /**
     * Returns the icon index of the current stack.
     */
    public int getIconIndex()
    {
        return this.getItem().getIconIndex(this);
    }

    public boolean tryPlaceItemIntoWorld(EntityPlayer par1EntityPlayer, World par2World, int par3, int par4, int par5, int par6, float par7, float par8, float par9)
    {
        boolean var10 = this.getItem().onItemUse(this, par1EntityPlayer, par2World, par3, par4, par5, par6, par7, par8, par9);

        if (var10)
        {
            par1EntityPlayer.addStat(StatList.objectUseStats[this.itemID], 1);
        }

        return var10;
    }

    /**
     * Returns the strength of the stack against a given block.
     */
    public float getStrVsBlock(Block par1Block)
    {
        return this.getItem().getStrVsBlock(this, par1Block);
    }

    /**
     * Called whenever this item stack is equipped and right clicked. Returns the new item stack to put in the position
     * where this item is. Args: world, player
     */
    public ItemStack useItemRightClick(World par1World, EntityPlayer par2EntityPlayer)
    {
        return this.getItem().onItemRightClick(this, par1World, par2EntityPlayer);
    }

    public ItemStack onFoodEaten(World par1World, EntityPlayer par2EntityPlayer)
    {
        return this.getItem().onFoodEaten(this, par1World, par2EntityPlayer);
    }

    /**
     * Write the stack fields to a NBT object. Return the new NBT object.
     */
    public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setShort("id", (short)this.itemID);
        par1NBTTagCompound.setByte("Count", (byte)this.stackSize);
        par1NBTTagCompound.setShort("Damage", (short)this.itemDamage);

        if (this.stackTagCompound != null)
        {
            par1NBTTagCompound.setTag("tag", this.stackTagCompound);
        }

        return par1NBTTagCompound;
    }

    /**
     * Read the stack fields from a NBT object.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        this.itemID = par1NBTTagCompound.getShort("id");
        this.stackSize = par1NBTTagCompound.getByte("Count");
        this.itemDamage = par1NBTTagCompound.getShort("Damage");

        if (par1NBTTagCompound.hasKey("tag"))
        {
            this.stackTagCompound = par1NBTTagCompound.getCompoundTag("tag");
        }
    }

    /**
     * Returns maximum size of the stack.
     */
    public int getMaxStackSize()
    {
        return this.getItem().getItemStackLimit();
    }

    /**
     * Returns true if the ItemStack can hold 2 or more units of the item.
     */
    public boolean isStackable()
    {
        return this.getMaxStackSize() > 1 && (!this.isItemStackDamageable() || !this.isItemDamaged());
    }

    /**
     * true if this itemStack is damageable
     */
    public boolean isItemStackDamageable()
    {
        return Item.itemsList[this.itemID].getMaxDamage() > 0;
    }

    public boolean getHasSubtypes()
    {
        return Item.itemsList[this.itemID].getHasSubtypes();
    }

    /**
     * returns true when a damageable item is damaged
     */
    public boolean isItemDamaged()
    {
        return this.isItemStackDamageable() && this.itemDamage > 0;
    }

    /**
     * gets the damage of an itemstack, for displaying purposes
     */
    public int getItemDamageForDisplay()
    {
        return this.itemDamage;
    }

    /**
     * gets the damage of an itemstack
     */
    public int getItemDamage()
    {
        return this.itemDamage;
    }

    /**
     * Sets the item damage of the ItemStack.
     */
    public void setItemDamage(int par1)
    {
        this.itemDamage = par1;
    }

    /**
     * Returns the max damage an item in the stack can take.
     */
    public int getMaxDamage()
    {
        return Item.itemsList[this.itemID].getMaxDamage();
    }

    /**
     * Damages the item in the ItemStack
     */
    public void damageItem(int par1, EntityLiving par2EntityLiving)
    {
        if (this.isItemStackDamageable())
        {
            if (par1 > 0 && par2EntityLiving instanceof EntityPlayer)
            {
                int var3 = EnchantmentHelper.getUnbreakingModifier(par2EntityLiving);

                if (var3 > 0 && par2EntityLiving.worldObj.rand.nextInt(var3 + 1) > 0)
                {
                    return;
                }
            }

            if (!(par2EntityLiving instanceof EntityPlayer) || !((EntityPlayer)par2EntityLiving).capabilities.isCreativeMode)
            {
                this.itemDamage += par1;
            }

            if (this.itemDamage > this.getMaxDamage())
            {
                par2EntityLiving.renderBrokenItemStack(this);

                if (par2EntityLiving instanceof EntityPlayer)
                {
                    ((EntityPlayer)par2EntityLiving).addStat(StatList.objectBreakStats[this.itemID], 1);
                }

                --this.stackSize;

                if (this.stackSize < 0)
                {
                    this.stackSize = 0;
                }

                this.itemDamage = 0;
            }
        }
    }

    /**
     * Calls the corresponding fct in di
     */
    public void hitEntity(EntityLiving par1EntityLiving, EntityPlayer par2EntityPlayer)
    {
        boolean var3 = Item.itemsList[this.itemID].hitEntity(this, par1EntityLiving, par2EntityPlayer);

        if (var3)
        {
            par2EntityPlayer.addStat(StatList.objectUseStats[this.itemID], 1);
        }
    }

    public void onBlockDestroyed(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer)
    {
        boolean var7 = Item.itemsList[this.itemID].onBlockDestroyed(this, par1World, par2, par3, par4, par5, par6EntityPlayer);

        if (var7)
        {
            par6EntityPlayer.addStat(StatList.objectUseStats[this.itemID], 1);
        }
    }

    /**
     * Returns the damage against a given entity.
     */
    public int getDamageVsEntity(Entity par1Entity)
    {
        return Item.itemsList[this.itemID].getDamageVsEntity(par1Entity);
    }

    /**
     * Checks if the itemStack object can harvest a specified block
     */
    public boolean canHarvestBlock(Block par1Block)
    {
        return Item.itemsList[this.itemID].canHarvestBlock(par1Block);
    }

    public boolean interactWith(EntityLiving par1EntityLiving)
    {
        return Item.itemsList[this.itemID].itemInteractionForEntity(this, par1EntityLiving);
    }

    /**
     * Returns a new stack with the same properties.
     */
    public ItemStack copy()
    {
        ItemStack var1 = new ItemStack(this.itemID, this.stackSize, this.itemDamage);

        if (this.stackTagCompound != null)
        {
            var1.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
        }

        return var1;
    }

    public static boolean func_77970_a(ItemStack par0ItemStack, ItemStack par1ItemStack)
    {
        return par0ItemStack == null && par1ItemStack == null ? true : (par0ItemStack != null && par1ItemStack != null ? (par0ItemStack.stackTagCompound == null && par1ItemStack.stackTagCompound != null ? false : par0ItemStack.stackTagCompound == null || par0ItemStack.stackTagCompound.equals(par1ItemStack.stackTagCompound)) : false);
    }

    /**
     * compares ItemStack argument1 with ItemStack argument2; returns true if both ItemStacks are equal
     */
    public static boolean areItemStacksEqual(ItemStack par0ItemStack, ItemStack par1ItemStack)
    {
        return par0ItemStack == null && par1ItemStack == null ? true : (par0ItemStack != null && par1ItemStack != null ? par0ItemStack.isItemStackEqual(par1ItemStack) : false);
    }

    /**
     * compares ItemStack argument to the instance ItemStack; returns true if both ItemStacks are equal
     */
    private boolean isItemStackEqual(ItemStack par1ItemStack)
    {
        return this.stackSize != par1ItemStack.stackSize ? false : (this.itemID != par1ItemStack.itemID ? false : (this.itemDamage != par1ItemStack.itemDamage ? false : (this.stackTagCompound == null && par1ItemStack.stackTagCompound != null ? false : this.stackTagCompound == null || this.stackTagCompound.equals(par1ItemStack.stackTagCompound))));
    }

    /**
     * compares ItemStack argument to the instance ItemStack; returns true if the Items contained in both ItemStacks are
     * equal
     */
    public boolean isItemEqual(ItemStack par1ItemStack)
    {
        return this.itemID == par1ItemStack.itemID && this.itemDamage == par1ItemStack.itemDamage;
    }

    public String getItemName()
    {
        return Item.itemsList[this.itemID].getItemNameIS(this);
    }

    /**
     * Creates a copy of a ItemStack, a null parameters will return a null.
     */
    public static ItemStack copyItemStack(ItemStack par0ItemStack)
    {
        return par0ItemStack == null ? null : par0ItemStack.copy();
    }

    public String toString()
    {
        return this.stackSize + "x" + Item.itemsList[this.itemID].getItemName() + "@" + this.itemDamage;
    }

    /**
     * Called each tick as long the ItemStack in on player inventory. Used to progress the pickup animation and update
     * maps.
     */
    public void updateAnimation(World par1World, Entity par2Entity, int par3, boolean par4)
    {
        if (this.animationsToGo > 0)
        {
            --this.animationsToGo;
        }

        Item.itemsList[this.itemID].onUpdate(this, par1World, par2Entity, par3, par4);
    }

    public void onCrafting(World par1World, EntityPlayer par2EntityPlayer, int par3)
    {
        par2EntityPlayer.addStat(StatList.objectCraftStats[this.itemID], par3);
        Item.itemsList[this.itemID].onCreated(this, par1World, par2EntityPlayer);
    }

    public int getMaxItemUseDuration()
    {
        return this.getItem().getMaxItemUseDuration(this);
    }

    public EnumAction getItemUseAction()
    {
        return this.getItem().getItemUseAction(this);
    }

    /**
     * Called when the player releases the use item button. Args: world, entityplayer, itemInUseCount
     */
    public void onPlayerStoppedUsing(World par1World, EntityPlayer par2EntityPlayer, int par3)
    {
        this.getItem().onPlayerStoppedUsing(this, par1World, par2EntityPlayer, par3);
    }

    /**
     * Returns true if the ItemStack has an NBTTagCompound. Currently used to store enchantments.
     */
    public boolean hasTagCompound()
    {
        return this.stackTagCompound != null;
    }

    /**
     * Returns the NBTTagCompound of the ItemStack.
     */
    public NBTTagCompound getTagCompound()
    {
        return this.stackTagCompound;
    }

    public NBTTagList getEnchantmentTagList()
    {
        return this.stackTagCompound == null ? null : (NBTTagList)this.stackTagCompound.getTag("ench");
    }

    /**
     * Assigns a NBTTagCompound to the ItemStack, minecraft validates that only non-stackable items can have it.
     */
    public void setTagCompound(NBTTagCompound par1NBTTagCompound)
    {
        this.stackTagCompound = par1NBTTagCompound;
    }

    public String func_82833_r()
    {
        String var1 = this.getItem().getItemDisplayName(this);

        if (this.stackTagCompound != null && this.stackTagCompound.hasKey("display"))
        {
            NBTTagCompound var2 = this.stackTagCompound.getCompoundTag("display");

            if (var2.hasKey("Name"))
            {
                var1 = var2.getString("Name");
            }
        }

        return var1;
    }

    public void func_82834_c(String par1Str)
    {
        if (this.stackTagCompound == null)
        {
            this.stackTagCompound = new NBTTagCompound();
        }

        if (!this.stackTagCompound.hasKey("display"))
        {
            this.stackTagCompound.setCompoundTag("display", new NBTTagCompound());
        }

        this.stackTagCompound.getCompoundTag("display").setString("Name", par1Str);
    }

    public boolean func_82837_s()
    {
        return this.stackTagCompound == null ? false : (!this.stackTagCompound.hasKey("display") ? false : this.stackTagCompound.getCompoundTag("display").hasKey("Name"));
    }

    public List func_82840_a(EntityPlayer par1EntityPlayer, boolean par2)
    {
        ArrayList var3 = new ArrayList();
        Item var4 = Item.itemsList[this.itemID];
        String var5 = this.func_82833_r();

        if (par2)
        {
            String var6 = "";

            if (var5.length() > 0)
            {
                var5 = var5 + " (";
                var6 = ")";
            }

            if (this.getHasSubtypes())
            {
                var5 = var5 + String.format("#%04d/%d%s", new Object[] {Integer.valueOf(this.itemID), Integer.valueOf(this.itemDamage), var6});
            }
            else
            {
                var5 = var5 + String.format("#%04d%s", new Object[] {Integer.valueOf(this.itemID), var6});
            }
        }
        else if (!this.func_82837_s())
        {
            if (this.itemID == Item.map.shiftedIndex)
            {
                var5 = var5 + " #" + this.itemDamage;
            }
        }
        else
        {
            var5 = "\u00a7o" + var5;
        }

        var3.add(var5);
        var4.addInformation(this, par1EntityPlayer, var3, par2);

        if (this.hasTagCompound())
        {
            NBTTagList var10 = this.getEnchantmentTagList();

            if (var10 != null)
            {
                for (int var7 = 0; var7 < var10.tagCount(); ++var7)
                {
                    short var8 = ((NBTTagCompound)var10.tagAt(var7)).getShort("id");
                    short var9 = ((NBTTagCompound)var10.tagAt(var7)).getShort("lvl");

                    if (Enchantment.enchantmentsList[var8] != null)
                    {
                        var3.add(Enchantment.enchantmentsList[var8].getTranslatedName(var9));
                    }
                }
            }

            if (this.stackTagCompound.hasKey("display"))
            {
                NBTTagCompound var11 = this.stackTagCompound.getCompoundTag("display");

                if (var11.hasKey("color"))
                {
                    if (par2)
                    {
                        var3.add("Color: #" + Integer.toHexString(var11.getInteger("color")).toUpperCase());
                    }
                    else
                    {
                        var3.add("\u00a7o" + StatCollector.translateToLocal("item.dyed"));
                    }
                }

                if (var11.hasKey("Lore"))
                {
                    NBTTagList var12 = var11.getTagList("Lore");

                    if (var12.tagCount() > 0)
                    {
                        for (int var13 = 0; var13 < var12.tagCount(); ++var13)
                        {
                            var3.add("\u00a75\u00a7o" + ((NBTTagString)var12.tagAt(var13)).data);
                        }
                    }
                }
            }
        }

        if (par2 && this.isItemDamaged())
        {
            var3.add("Durability: " + (this.getMaxDamage() - this.getItemDamageForDisplay()) + " / " + this.getMaxDamage());
        }

        return var3;
    }

    public boolean hasEffect()
    {
        return this.getItem().hasEffect(this);
    }

    public EnumRarity getRarity()
    {
        return this.getItem().getRarity(this);
    }

    /**
     * True if it is a tool and has no enchantments to begin with
     */
    public boolean isItemEnchantable()
    {
        return !this.getItem().isItemTool(this) ? false : !this.isItemEnchanted();
    }

    /**
     * Adds an enchantment with a desired level on the ItemStack.
     */
    public void addEnchantment(Enchantment par1Enchantment, int par2)
    {
        if (this.stackTagCompound == null)
        {
            this.setTagCompound(new NBTTagCompound());
        }

        if (!this.stackTagCompound.hasKey("ench"))
        {
            this.stackTagCompound.setTag("ench", new NBTTagList("ench"));
        }

        NBTTagList var3 = (NBTTagList)this.stackTagCompound.getTag("ench");
        NBTTagCompound var4 = new NBTTagCompound();
        var4.setShort("id", (short)par1Enchantment.effectId);
        var4.setShort("lvl", (short)((byte)par2));
        var3.appendTag(var4);
    }

    /**
     * True if the item has enchantment data
     */
    public boolean isItemEnchanted()
    {
        return this.stackTagCompound != null && this.stackTagCompound.hasKey("ench");
    }

    public void func_77983_a(String par1Str, NBTBase par2NBTBase)
    {
        if (this.stackTagCompound == null)
        {
            this.setTagCompound(new NBTTagCompound());
        }

        this.stackTagCompound.setTag(par1Str, par2NBTBase);
    }

    public boolean func_82835_x()
    {
        return this.getItem().func_82788_x();
    }

    public boolean func_82839_y()
    {
        return this.field_82843_f != null;
    }

    public void func_82842_a(EntityItemFrame par1EntityItemFrame)
    {
        this.field_82843_f = par1EntityItemFrame;
    }

    public EntityItemFrame func_82836_z()
    {
        return this.field_82843_f;
    }

    public int func_82838_A()
    {
        return this.hasTagCompound() && this.stackTagCompound.hasKey("RepairCost") ? this.stackTagCompound.getInteger("RepairCost") : 0;
    }

    public void func_82841_c(int par1)
    {
        if (!this.hasTagCompound())
        {
            this.stackTagCompound = new NBTTagCompound();
        }

        this.stackTagCompound.setInteger("RepairCost", par1);
    }
}
