package net.minecraft.src;

import java.util.List;
import net.minecraft.server.MinecraftServer;

public class CommandDifficulty extends CommandBase
{
    private static final String[] field_82365_a = new String[] {"options.difficulty.peaceful", "options.difficulty.easy", "options.difficulty.normal", "options.difficulty.hard"};

    public String getCommandName()
    {
        return "difficulty";
    }

    public int func_82362_a()
    {
        return 2;
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.translateString("commands.difficulty.usage", new Object[0]);
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length > 0)
        {
            int var3 = this.func_82364_d(par1ICommandSender, par2ArrayOfStr[0]);
            MinecraftServer.getServer().setDifficultyForAllWorlds(var3);
            String var4 = StatCollector.translateToLocal(field_82365_a[var3]);
            notifyAdmins(par1ICommandSender, 1, "commands.difficulty.success", new Object[] {var4});
        }
        else
        {
            throw new WrongUsageException("commands.difficulty.usage", new Object[0]);
        }
    }

    protected int func_82364_d(ICommandSender par1ICommandSender, String par2Str)
    {
        return !par2Str.equalsIgnoreCase("peaceful") && !par2Str.equalsIgnoreCase("p") ? (!par2Str.equalsIgnoreCase("easy") && !par2Str.equalsIgnoreCase("e") ? (!par2Str.equalsIgnoreCase("normal") && !par2Str.equalsIgnoreCase("n") ? (!par2Str.equalsIgnoreCase("hard") && !par2Str.equalsIgnoreCase("h") ? parseIntBounded(par1ICommandSender, par2Str, 0, 3) : 3) : 2) : 1) : 0;
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] {"peaceful", "easy", "normal", "hard"}): null;
    }
}
