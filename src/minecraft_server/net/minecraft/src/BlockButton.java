package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockButton extends Block
{
    private final boolean field_82537_a;

    protected BlockButton(int par1, int par2, boolean par3)
    {
        super(par1, par2, Material.circuits);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.field_82537_a = par3;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return this.field_82537_a ? 30 : 20;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * checks to see if you can place this block can be placed on that side of a block: BlockLever overrides
     */
    public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5)
    {
        return par5 == 2 && par1World.isBlockNormalCube(par2, par3, par4 + 1) ? true : (par5 == 3 && par1World.isBlockNormalCube(par2, par3, par4 - 1) ? true : (par5 == 4 && par1World.isBlockNormalCube(par2 + 1, par3, par4) ? true : par5 == 5 && par1World.isBlockNormalCube(par2 - 1, par3, par4)));
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return par1World.isBlockNormalCube(par2 - 1, par3, par4) ? true : (par1World.isBlockNormalCube(par2 + 1, par3, par4) ? true : (par1World.isBlockNormalCube(par2, par3, par4 - 1) ? true : par1World.isBlockNormalCube(par2, par3, par4 + 1)));
    }

    /**
     * called before onBlockPlacedBy by ItemBlock and ItemReed
     */
    public void updateBlockMetadata(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8)
    {
        int var9 = par1World.getBlockMetadata(par2, par3, par4);
        int var10 = var9 & 8;
        var9 &= 7;

        if (par5 == 2 && par1World.isBlockNormalCube(par2, par3, par4 + 1))
        {
            var9 = 4;
        }
        else if (par5 == 3 && par1World.isBlockNormalCube(par2, par3, par4 - 1))
        {
            var9 = 3;
        }
        else if (par5 == 4 && par1World.isBlockNormalCube(par2 + 1, par3, par4))
        {
            var9 = 2;
        }
        else if (par5 == 5 && par1World.isBlockNormalCube(par2 - 1, par3, par4))
        {
            var9 = 1;
        }
        else
        {
            var9 = this.getOrientation(par1World, par2, par3, par4);
        }

        par1World.setBlockMetadataWithNotify(par2, par3, par4, var9 + var10);
    }

    /**
     * Get side which this button is facing.
     */
    private int getOrientation(World par1World, int par2, int par3, int par4)
    {
        return par1World.isBlockNormalCube(par2 - 1, par3, par4) ? 1 : (par1World.isBlockNormalCube(par2 + 1, par3, par4) ? 2 : (par1World.isBlockNormalCube(par2, par3, par4 - 1) ? 3 : (par1World.isBlockNormalCube(par2, par3, par4 + 1) ? 4 : 1)));
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        if (this.redundantCanPlaceBlockAt(par1World, par2, par3, par4))
        {
            int var6 = par1World.getBlockMetadata(par2, par3, par4) & 7;
            boolean var7 = false;

            if (!par1World.isBlockNormalCube(par2 - 1, par3, par4) && var6 == 1)
            {
                var7 = true;
            }

            if (!par1World.isBlockNormalCube(par2 + 1, par3, par4) && var6 == 2)
            {
                var7 = true;
            }

            if (!par1World.isBlockNormalCube(par2, par3, par4 - 1) && var6 == 3)
            {
                var7 = true;
            }

            if (!par1World.isBlockNormalCube(par2, par3, par4 + 1) && var6 == 4)
            {
                var7 = true;
            }

            if (var7)
            {
                this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
                par1World.setBlockWithNotify(par2, par3, par4, 0);
            }
        }
    }

    /**
     * This method is redundant, check it out...
     */
    private boolean redundantCanPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        if (!this.canPlaceBlockAt(par1World, par2, par3, par4))
        {
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockWithNotify(par2, par3, par4, 0);
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        this.func_82534_e(var5);
    }

    private void func_82534_e(int par1)
    {
        int var2 = par1 & 7;
        boolean var3 = (par1 & 8) > 0;
        float var4 = 0.375F;
        float var5 = 0.625F;
        float var6 = 0.1875F;
        float var7 = 0.125F;

        if (var3)
        {
            var7 = 0.0625F;
        }

        if (var2 == 1)
        {
            this.setBlockBounds(0.0F, var4, 0.5F - var6, var7, var5, 0.5F + var6);
        }
        else if (var2 == 2)
        {
            this.setBlockBounds(1.0F - var7, var4, 0.5F - var6, 1.0F, var5, 0.5F + var6);
        }
        else if (var2 == 3)
        {
            this.setBlockBounds(0.5F - var6, var4, 0.0F, 0.5F + var6, var5, var7);
        }
        else if (var2 == 4)
        {
            this.setBlockBounds(0.5F - var6, var4, 1.0F - var7, 0.5F + var6, var5, 1.0F);
        }
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {}

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        int var10 = par1World.getBlockMetadata(par2, par3, par4);
        int var11 = var10 & 7;
        int var12 = 8 - (var10 & 8);

        if (var12 == 0)
        {
            return true;
        }
        else
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var11 + var12);
            par1World.markBlocksDirty(par2, par3, par4, par2, par3, par4);
            par1World.playSoundEffect((double)par2 + 0.5D, (double)par3 + 0.5D, (double)par4 + 0.5D, "random.click", 0.3F, 0.6F);
            this.func_82536_d(par1World, par2, par3, par4, var11);
            par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate());
            return true;
        }
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        if ((par6 & 8) > 0)
        {
            int var7 = par6 & 7;
            this.func_82536_d(par1World, par2, par3, par4, var7);
        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

    /**
     * Is this block powering the block on the specified side
     */
    public boolean isPoweringTo(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 8) > 0;
    }

    /**
     * Is this block indirectly powering the block on the specified side
     */
    public boolean isIndirectlyPoweringTo(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        int var6 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);

        if ((var6 & 8) == 0)
        {
            return false;
        }
        else
        {
            int var7 = var6 & 7;
            return var7 == 5 && par5 == 1 ? true : (var7 == 4 && par5 == 2 ? true : (var7 == 3 && par5 == 3 ? true : (var7 == 2 && par5 == 4 ? true : var7 == 1 && par5 == 5)));
        }
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    public boolean canProvidePower()
    {
        return true;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!par1World.isRemote)
        {
            int var6 = par1World.getBlockMetadata(par2, par3, par4);

            if ((var6 & 8) != 0)
            {
                if (this.field_82537_a)
                {
                    this.func_82535_o(par1World, par2, par3, par4);
                }
                else
                {
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 & 7);
                    int var7 = var6 & 7;
                    this.func_82536_d(par1World, par2, par3, par4, var7);
                    par1World.playSoundEffect((double)par2 + 0.5D, (double)par3 + 0.5D, (double)par4 + 0.5D, "random.click", 0.3F, 0.5F);
                    par1World.markBlocksDirty(par2, par3, par4, par2, par3, par4);
                }
            }
        }
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
        float var1 = 0.1875F;
        float var2 = 0.125F;
        float var3 = 0.125F;
        this.setBlockBounds(0.5F - var1, 0.5F - var2, 0.5F - var3, 0.5F + var1, 0.5F + var2, 0.5F + var3);
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        if (!par1World.isRemote)
        {
            if (this.field_82537_a)
            {
                if ((par1World.getBlockMetadata(par2, par3, par4) & 8) == 0)
                {
                    this.func_82535_o(par1World, par2, par3, par4);
                }
            }
        }
    }

    private void func_82535_o(World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getBlockMetadata(par2, par3, par4);
        int var6 = var5 & 7;
        boolean var7 = (var5 & 8) != 0;
        this.func_82534_e(var5);
        List var9 = par1World.getEntitiesWithinAABB(EntityArrow.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)par2 + this.minX, (double)par3 + this.minY, (double)par4 + this.minZ, (double)par2 + this.maxX, (double)par3 + this.maxY, (double)par4 + this.maxZ));
        boolean var8 = !var9.isEmpty();

        if (var8 && !var7)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 | 8);
            this.func_82536_d(par1World, par2, par3, par4, var6);
            par1World.markBlocksDirty(par2, par3, par4, par2, par3, par4);
            par1World.playSoundEffect((double)par2 + 0.5D, (double)par3 + 0.5D, (double)par4 + 0.5D, "random.click", 0.3F, 0.6F);
        }

        if (!var8 && var7)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var6);
            this.func_82536_d(par1World, par2, par3, par4, var6);
            par1World.markBlocksDirty(par2, par3, par4, par2, par3, par4);
            par1World.playSoundEffect((double)par2 + 0.5D, (double)par3 + 0.5D, (double)par4 + 0.5D, "random.click", 0.3F, 0.5F);
        }

        if (var8)
        {
            par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate());
        }
    }

    private void func_82536_d(World par1World, int par2, int par3, int par4, int par5)
    {
        par1World.notifyBlocksOfNeighborChange(par2, par3, par4, this.blockID);

        if (par5 == 1)
        {
            par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, this.blockID);
        }
        else if (par5 == 2)
        {
            par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, this.blockID);
        }
        else if (par5 == 3)
        {
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, this.blockID);
        }
        else if (par5 == 4)
        {
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, this.blockID);
        }
        else
        {
            par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
        }
    }
}
