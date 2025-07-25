package net.minecraft.src;

import java.util.List;
import net.minecraft.server.MinecraftServer;

public class CommandClearInventory extends CommandBase
{
    public String getCommandName()
    {
        return "clear";
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.translateString("commands.clear.usage", new Object[0]);
    }

    public int func_82362_a()
    {
        return 2;
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        EntityPlayerMP var3 = par2ArrayOfStr.length == 0 ? getCommandSenderAsPlayer(par1ICommandSender) : func_82359_c(par1ICommandSender, par2ArrayOfStr[0]);
        int var4 = par2ArrayOfStr.length >= 2 ? parseIntWithMin(par1ICommandSender, par2ArrayOfStr[1], 1) : -1;
        int var5 = par2ArrayOfStr.length >= 3 ? parseIntWithMin(par1ICommandSender, par2ArrayOfStr[2], 0) : -1;
        int var6 = var3.inventory.func_82347_b(var4, var5);
        var3.inventorySlots.updateCraftingResults();
        notifyAdmins(par1ICommandSender, "commands.clear.success", new Object[] {var3.getEntityName(), Integer.valueOf(var6)});
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, this.func_82369_d()) : null;
    }

    protected String[] func_82369_d()
    {
        return MinecraftServer.getServer().getAllUsernames();
    }

    public boolean func_82358_a(int par1)
    {
        return par1 == 0;
    }
}
