package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class ConvertingProgressUpdate implements IProgressUpdate
{
    private long field_82309_b;

    final MinecraftServer field_82310_a;

    public ConvertingProgressUpdate(MinecraftServer par1MinecraftServer)
    {
        this.field_82310_a = par1MinecraftServer;
        this.field_82309_b = System.currentTimeMillis();
    }

    /**
     * Shows the 'Saving level' string.
     */
    public void displaySavingString(String par1Str) {}

    /**
     * Updates the progress bar on the loading screen to the specified amount. Args: loadProgress
     */
    public void setLoadingProgress(int par1)
    {
        if (System.currentTimeMillis() - this.field_82309_b >= 1000L)
        {
            this.field_82309_b = System.currentTimeMillis();
            MinecraftServer.logger.info("Converting... " + par1 + "%");
        }
    }

    /**
     * Displays a string on the loading screen supposed to indicate what is being done currently.
     */
    public void displayLoadingString(String par1Str) {}
}
