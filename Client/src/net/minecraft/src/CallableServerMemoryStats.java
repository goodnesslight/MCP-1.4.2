package net.minecraft.src;

import java.util.concurrent.Callable;
import net.minecraft.server.MinecraftServer;

public class CallableServerMemoryStats implements Callable
{
    final MinecraftServer field_82552_a;

    public CallableServerMemoryStats(MinecraftServer par1MinecraftServer)
    {
        this.field_82552_a = par1MinecraftServer;
    }

    public String func_82551_a()
    {
        int var1 = this.field_82552_a.worldServers[0].func_82732_R().func_82591_c();
        int var2 = 56 * var1;
        int var3 = var2 / 1024 / 1024;
        int var4 = this.field_82552_a.worldServers[0].func_82732_R().func_82590_d();
        int var5 = 56 * var4;
        int var6 = var5 / 1024 / 1024;
        return var1 + " (" + var2 + " bytes; " + var3 + " MB) allocated, " + var4 + " (" + var5 + " bytes; " + var6 + " MB) used";
    }

    public Object call()
    {
        return this.func_82551_a();
    }
}
