package net.minecraft.src;

import java.util.List;
import java.util.Random;
import net.minecraft.server.MinecraftServer;

public class CommandWeather extends CommandBase
{
    public String getCommandName()
    {
        return "weather";
    }

    public int func_82362_a()
    {
        return 2;
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length < 1)
        {
            throw new WrongUsageException("commands.weather.usage", new Object[0]);
        }
        else
        {
            int var3 = (300 + (new Random()).nextInt(600)) * 20;

            if (par2ArrayOfStr.length >= 2)
            {
                var3 = parseIntBounded(par1ICommandSender, par2ArrayOfStr[1], 1, 1000000) * 20;
            }

            WorldServer var4 = MinecraftServer.getServer().worldServers[0];
            WorldInfo var5 = var4.getWorldInfo();
            var5.setRainTime(var3);
            var5.setThunderTime(var3);

            if ("clear".equalsIgnoreCase(par2ArrayOfStr[0]))
            {
                var5.setRaining(false);
                var5.setThundering(false);
                notifyAdmins(par1ICommandSender, "commands.weather.clear", new Object[0]);
            }
            else if ("rain".equalsIgnoreCase(par2ArrayOfStr[0]))
            {
                var5.setRaining(true);
                var5.setThundering(false);
                notifyAdmins(par1ICommandSender, "commands.weather.rain", new Object[0]);
            }
            else if ("thunder".equalsIgnoreCase(par2ArrayOfStr[0]))
            {
                var5.setRaining(true);
                var5.setThundering(true);
                notifyAdmins(par1ICommandSender, "commands.weather.thunder", new Object[0]);
            }
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] {"clear", "rain", "thunder"}): null;
    }
}
