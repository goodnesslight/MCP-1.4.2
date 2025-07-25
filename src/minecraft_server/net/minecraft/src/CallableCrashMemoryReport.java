package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableCrashMemoryReport implements Callable
{
    final CrashReport field_83004_a;

    CallableCrashMemoryReport(CrashReport par1CrashReport)
    {
        this.field_83004_a = par1CrashReport;
    }

    public String func_83003_a()
    {
        int var1 = AxisAlignedBB.getAABBPool().func_83013_c();
        int var2 = 56 * var1;
        int var3 = var2 / 1024 / 1024;
        int var4 = AxisAlignedBB.getAABBPool().func_83012_d();
        int var5 = 56 * var4;
        int var6 = var5 / 1024 / 1024;
        return var1 + " (" + var2 + " bytes; " + var3 + " MB) allocated, " + var4 + " (" + var5 + " bytes; " + var6 + " MB) used";
    }

    public Object call()
    {
        return this.func_83003_a();
    }
}
