package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandServerSaveOn extends CommandBase
{
    public String getCommandName()
    {
        return "save-on";
    }

    public int func_82362_a()
    {
        return 4;
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        MinecraftServer var3 = MinecraftServer.getServer();

        for (int var4 = 0; var4 < var3.worldServers.length; ++var4)
        {
            if (var3.worldServers[var4] != null)
            {
                WorldServer var5 = var3.worldServers[var4];
                var5.canNotSave = false;
            }
        }

        notifyAdmins(par1ICommandSender, "commands.save.enabled", new Object[0]);
    }
}
