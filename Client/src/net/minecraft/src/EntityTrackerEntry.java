package net.minecraft.src;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class EntityTrackerEntry
{
    public Entity myEntity;
    public int BlocksDistanceThreshold;

    /** check for sync when ticks % updateFrequency==0 */
    public int updateFrequency;
    public int lastScaledXPosition;
    public int lastScaledYPosition;
    public int lastScaledZPosition;
    public int lastYaw;
    public int lastPitch;
    public int lastHeadMotion;
    public double motionX;
    public double motionY;
    public double motionZ;
    public int ticks = 0;
    private double posX;
    private double posY;
    private double posZ;

    /** set to true on first sendLocationToClients */
    private boolean isDataInitialized = false;
    private boolean sendVelocityUpdates;

    /**
     * every 400 ticks a  full teleport packet is sent, rather than just a "move me +x" command, so that position
     * remains fully synced.
     */
    private int ticksSinceLastForcedTeleport = 0;
    private Entity ridingEntity;
    public boolean playerEntitiesUpdated = false;
    public Set trackedPlayers = new HashSet();

    public EntityTrackerEntry(Entity par1Entity, int par2, int par3, boolean par4)
    {
        this.myEntity = par1Entity;
        this.BlocksDistanceThreshold = par2;
        this.updateFrequency = par3;
        this.sendVelocityUpdates = par4;
        this.lastScaledXPosition = MathHelper.floor_double(par1Entity.posX * 32.0D);
        this.lastScaledYPosition = MathHelper.floor_double(par1Entity.posY * 32.0D);
        this.lastScaledZPosition = MathHelper.floor_double(par1Entity.posZ * 32.0D);
        this.lastYaw = MathHelper.floor_float(par1Entity.rotationYaw * 256.0F / 360.0F);
        this.lastPitch = MathHelper.floor_float(par1Entity.rotationPitch * 256.0F / 360.0F);
        this.lastHeadMotion = MathHelper.floor_float(par1Entity.func_70079_am() * 256.0F / 360.0F);
    }

    public boolean equals(Object par1Obj)
    {
        return par1Obj instanceof EntityTrackerEntry ? ((EntityTrackerEntry)par1Obj).myEntity.entityId == this.myEntity.entityId : false;
    }

    public int hashCode()
    {
        return this.myEntity.entityId;
    }

    /**
     * also sends velocity, rotation, and riding info.
     */
    public void sendLocationToAllClients(List par1List)
    {
        this.playerEntitiesUpdated = false;

        if (!this.isDataInitialized || this.myEntity.getDistanceSq(this.posX, this.posY, this.posZ) > 16.0D)
        {
            this.posX = this.myEntity.posX;
            this.posY = this.myEntity.posY;
            this.posZ = this.myEntity.posZ;
            this.isDataInitialized = true;
            this.playerEntitiesUpdated = true;
            this.sendEventsToPlayers(par1List);
        }

        if (this.ridingEntity != this.myEntity.ridingEntity)
        {
            this.ridingEntity = this.myEntity.ridingEntity;
            this.sendPacketToAllTrackingPlayers(new Packet39AttachEntity(this.myEntity, this.myEntity.ridingEntity));
        }

        if (this.myEntity instanceof EntityItemFrame && this.ticks % 10 == 0)
        {
            EntityItemFrame var23 = (EntityItemFrame)this.myEntity;
            ItemStack var24 = var23.func_82335_i();

            if (var24 != null && var24.getItem() instanceof ItemMap)
            {
                MapData var26 = Item.map.getMapData(var24, this.myEntity.worldObj);
                Iterator var27 = par1List.iterator();

                while (var27.hasNext())
                {
                    EntityPlayer var29 = (EntityPlayer)var27.next();
                    EntityPlayerMP var30 = (EntityPlayerMP)var29;
                    var26.updateVisiblePlayers(var30, var24);

                    if (var30.playerNetServerHandler.packetSize() <= 5)
                    {
                        Packet var31 = Item.map.createMapDataPacket(var24, this.myEntity.worldObj, var30);

                        if (var31 != null)
                        {
                            var30.playerNetServerHandler.sendPacketToPlayer(var31);
                        }
                    }
                }
            }

            DataWatcher var28 = this.myEntity.getDataWatcher();

            if (var28.hasChanges())
            {
                this.sendPacketToAllAssociatedPlayers(new Packet40EntityMetadata(this.myEntity.entityId, var28, false));
            }
        }
        else if (this.ticks++ % this.updateFrequency == 0 || this.myEntity.isAirBorne)
        {
            int var2;
            int var3;

            if (this.myEntity.ridingEntity == null)
            {
                ++this.ticksSinceLastForcedTeleport;
                var2 = this.myEntity.myEntitySize.multiplyBy32AndRound(this.myEntity.posX);
                var3 = MathHelper.floor_double(this.myEntity.posY * 32.0D);
                int var4 = this.myEntity.myEntitySize.multiplyBy32AndRound(this.myEntity.posZ);
                int var5 = MathHelper.floor_float(this.myEntity.rotationYaw * 256.0F / 360.0F);
                int var6 = MathHelper.floor_float(this.myEntity.rotationPitch * 256.0F / 360.0F);
                int var7 = var2 - this.lastScaledXPosition;
                int var8 = var3 - this.lastScaledYPosition;
                int var9 = var4 - this.lastScaledZPosition;
                Object var10 = null;
                boolean var11 = Math.abs(var7) >= 4 || Math.abs(var8) >= 4 || Math.abs(var9) >= 4 || this.ticks % 60 == 0;
                boolean var12 = Math.abs(var5 - this.lastYaw) >= 4 || Math.abs(var6 - this.lastPitch) >= 4;

                if (var7 >= -128 && var7 < 128 && var8 >= -128 && var8 < 128 && var9 >= -128 && var9 < 128 && this.ticksSinceLastForcedTeleport <= 400)
                {
                    if (var11 && var12)
                    {
                        var10 = new Packet33RelEntityMoveLook(this.myEntity.entityId, (byte)var7, (byte)var8, (byte)var9, (byte)var5, (byte)var6);
                    }
                    else if (var11)
                    {
                        var10 = new Packet31RelEntityMove(this.myEntity.entityId, (byte)var7, (byte)var8, (byte)var9);
                    }
                    else if (var12)
                    {
                        var10 = new Packet32EntityLook(this.myEntity.entityId, (byte)var5, (byte)var6);
                    }
                }
                else
                {
                    this.ticksSinceLastForcedTeleport = 0;
                    var10 = new Packet34EntityTeleport(this.myEntity.entityId, var2, var3, var4, (byte)var5, (byte)var6);
                }

                if (this.sendVelocityUpdates)
                {
                    double var13 = this.myEntity.motionX - this.motionX;
                    double var15 = this.myEntity.motionY - this.motionY;
                    double var17 = this.myEntity.motionZ - this.motionZ;
                    double var19 = 0.02D;
                    double var21 = var13 * var13 + var15 * var15 + var17 * var17;

                    if (var21 > var19 * var19 || var21 > 0.0D && this.myEntity.motionX == 0.0D && this.myEntity.motionY == 0.0D && this.myEntity.motionZ == 0.0D)
                    {
                        this.motionX = this.myEntity.motionX;
                        this.motionY = this.myEntity.motionY;
                        this.motionZ = this.myEntity.motionZ;
                        this.sendPacketToAllTrackingPlayers(new Packet28EntityVelocity(this.myEntity.entityId, this.motionX, this.motionY, this.motionZ));
                    }
                }

                if (var10 != null)
                {
                    this.sendPacketToAllTrackingPlayers((Packet)var10);
                }

                DataWatcher var32 = this.myEntity.getDataWatcher();

                if (var32.hasChanges())
                {
                    this.sendPacketToAllAssociatedPlayers(new Packet40EntityMetadata(this.myEntity.entityId, var32, false));
                }

                if (var11)
                {
                    this.lastScaledXPosition = var2;
                    this.lastScaledYPosition = var3;
                    this.lastScaledZPosition = var4;
                }

                if (var12)
                {
                    this.lastYaw = var5;
                    this.lastPitch = var6;
                }
            }
            else
            {
                var2 = MathHelper.floor_float(this.myEntity.rotationYaw * 256.0F / 360.0F);
                var3 = MathHelper.floor_float(this.myEntity.rotationPitch * 256.0F / 360.0F);
                boolean var25 = Math.abs(var2 - this.lastYaw) >= 4 || Math.abs(var3 - this.lastPitch) >= 4;

                if (var25)
                {
                    this.sendPacketToAllTrackingPlayers(new Packet32EntityLook(this.myEntity.entityId, (byte)var2, (byte)var3));
                    this.lastYaw = var2;
                    this.lastPitch = var3;
                }

                this.lastScaledXPosition = this.myEntity.myEntitySize.multiplyBy32AndRound(this.myEntity.posX);
                this.lastScaledYPosition = MathHelper.floor_double(this.myEntity.posY * 32.0D);
                this.lastScaledZPosition = this.myEntity.myEntitySize.multiplyBy32AndRound(this.myEntity.posZ);
            }

            var2 = MathHelper.floor_float(this.myEntity.func_70079_am() * 256.0F / 360.0F);

            if (Math.abs(var2 - this.lastHeadMotion) >= 4)
            {
                this.sendPacketToAllTrackingPlayers(new Packet35EntityHeadRotation(this.myEntity.entityId, (byte)var2));
                this.lastHeadMotion = var2;
            }

            this.myEntity.isAirBorne = false;
        }

        if (this.myEntity.velocityChanged)
        {
            this.sendPacketToAllAssociatedPlayers(new Packet28EntityVelocity(this.myEntity));
            this.myEntity.velocityChanged = false;
        }
    }

    /**
     * if this is a player, then it is not informed
     */
    public void sendPacketToAllTrackingPlayers(Packet par1Packet)
    {
        Iterator var2 = this.trackedPlayers.iterator();

        while (var2.hasNext())
        {
            EntityPlayerMP var3 = (EntityPlayerMP)var2.next();
            var3.playerNetServerHandler.sendPacketToPlayer(par1Packet);
        }
    }

    /**
     * if this is a player, then it recieves the message also
     */
    public void sendPacketToAllAssociatedPlayers(Packet par1Packet)
    {
        this.sendPacketToAllTrackingPlayers(par1Packet);

        if (this.myEntity instanceof EntityPlayerMP)
        {
            ((EntityPlayerMP)this.myEntity).playerNetServerHandler.sendPacketToPlayer(par1Packet);
        }
    }

    public void informAllAssociatedPlayersOfItemDestruction()
    {
        Iterator var1 = this.trackedPlayers.iterator();

        while (var1.hasNext())
        {
            EntityPlayerMP var2 = (EntityPlayerMP)var1.next();
            var2.destroyedItemsNetCache.add(Integer.valueOf(this.myEntity.entityId));
        }
    }

    public void removeFromWatchingList(EntityPlayerMP par1EntityPlayerMP)
    {
        if (this.trackedPlayers.contains(par1EntityPlayerMP))
        {
            par1EntityPlayerMP.destroyedItemsNetCache.add(Integer.valueOf(this.myEntity.entityId));
            this.trackedPlayers.remove(par1EntityPlayerMP);
        }
    }

    /**
     * if the player is more than the distance threshold (typically 64) then the player is removed instead
     */
    public void tryStartWachingThis(EntityPlayerMP par1EntityPlayerMP)
    {
        if (par1EntityPlayerMP != this.myEntity)
        {
            double var2 = par1EntityPlayerMP.posX - (double)(this.lastScaledXPosition / 32);
            double var4 = par1EntityPlayerMP.posZ - (double)(this.lastScaledZPosition / 32);

            if (var2 >= (double)(-this.BlocksDistanceThreshold) && var2 <= (double)this.BlocksDistanceThreshold && var4 >= (double)(-this.BlocksDistanceThreshold) && var4 <= (double)this.BlocksDistanceThreshold)
            {
                if (!this.trackedPlayers.contains(par1EntityPlayerMP) && this.isPlayerWatchingThisChunk(par1EntityPlayerMP))
                {
                    this.trackedPlayers.add(par1EntityPlayerMP);
                    Packet var6 = this.getPacketForThisEntity();
                    par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(var6);

                    if (this.myEntity instanceof EntityItemFrame)
                    {
                        par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet40EntityMetadata(this.myEntity.entityId, this.myEntity.getDataWatcher(), true));
                    }

                    this.motionX = this.myEntity.motionX;
                    this.motionY = this.myEntity.motionY;
                    this.motionZ = this.myEntity.motionZ;

                    if (this.sendVelocityUpdates && !(var6 instanceof Packet24MobSpawn))
                    {
                        par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet28EntityVelocity(this.myEntity.entityId, this.myEntity.motionX, this.myEntity.motionY, this.myEntity.motionZ));
                    }

                    if (this.myEntity.ridingEntity != null)
                    {
                        par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet39AttachEntity(this.myEntity, this.myEntity.ridingEntity));
                    }

                    if (this.myEntity instanceof EntityLiving)
                    {
                        for (int var7 = 0; var7 < 5; ++var7)
                        {
                            ItemStack var8 = ((EntityLiving)this.myEntity).getCurrentItemOrArmor(var7);

                            if (var8 != null)
                            {
                                par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet5PlayerInventory(this.myEntity.entityId, var7, var8));
                            }
                        }
                    }

                    if (this.myEntity instanceof EntityPlayer)
                    {
                        EntityPlayer var11 = (EntityPlayer)this.myEntity;

                        if (var11.isPlayerSleeping())
                        {
                            par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet17Sleep(this.myEntity, 0, MathHelper.floor_double(this.myEntity.posX), MathHelper.floor_double(this.myEntity.posY), MathHelper.floor_double(this.myEntity.posZ)));
                        }
                    }

                    if (this.myEntity instanceof EntityLiving)
                    {
                        EntityLiving var10 = (EntityLiving)this.myEntity;
                        Iterator var12 = var10.getActivePotionEffects().iterator();

                        while (var12.hasNext())
                        {
                            PotionEffect var9 = (PotionEffect)var12.next();
                            par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet41EntityEffect(this.myEntity.entityId, var9));
                        }
                    }
                }
            }
            else if (this.trackedPlayers.contains(par1EntityPlayerMP))
            {
                this.trackedPlayers.remove(par1EntityPlayerMP);
                par1EntityPlayerMP.destroyedItemsNetCache.add(Integer.valueOf(this.myEntity.entityId));
            }
        }
    }

    private boolean isPlayerWatchingThisChunk(EntityPlayerMP par1EntityPlayerMP)
    {
        return par1EntityPlayerMP.getServerForPlayer().getPlayerManager().isPlayerWatchingChunk(par1EntityPlayerMP, this.myEntity.chunkCoordX, this.myEntity.chunkCoordZ);
    }

    public void sendEventsToPlayers(List par1List)
    {
        Iterator var2 = par1List.iterator();

        while (var2.hasNext())
        {
            EntityPlayer var3 = (EntityPlayer)var2.next();
            this.tryStartWachingThis((EntityPlayerMP)var3);
        }
    }

    private Packet getPacketForThisEntity()
    {
        if (this.myEntity.isDead)
        {
            System.out.println("Fetching addPacket for removed entity");
        }

        if (this.myEntity instanceof EntityItem)
        {
            EntityItem var9 = (EntityItem)this.myEntity;
            Packet21PickupSpawn var10 = new Packet21PickupSpawn(var9);
            var9.posX = (double)var10.xPosition / 32.0D;
            var9.posY = (double)var10.yPosition / 32.0D;
            var9.posZ = (double)var10.zPosition / 32.0D;
            return var10;
        }
        else if (this.myEntity instanceof EntityPlayerMP)
        {
            return new Packet20NamedEntitySpawn((EntityPlayer)this.myEntity);
        }
        else
        {
            if (this.myEntity instanceof EntityMinecart)
            {
                EntityMinecart var1 = (EntityMinecart)this.myEntity;

                if (var1.minecartType == 0)
                {
                    return new Packet23VehicleSpawn(this.myEntity, 10);
                }

                if (var1.minecartType == 1)
                {
                    return new Packet23VehicleSpawn(this.myEntity, 11);
                }

                if (var1.minecartType == 2)
                {
                    return new Packet23VehicleSpawn(this.myEntity, 12);
                }
            }

            if (this.myEntity instanceof EntityBoat)
            {
                return new Packet23VehicleSpawn(this.myEntity, 1);
            }
            else if (!(this.myEntity instanceof IAnimals) && !(this.myEntity instanceof EntityDragon))
            {
                if (this.myEntity instanceof EntityFishHook)
                {
                    EntityPlayer var8 = ((EntityFishHook)this.myEntity).angler;
                    return new Packet23VehicleSpawn(this.myEntity, 90, var8 != null ? var8.entityId : this.myEntity.entityId);
                }
                else if (this.myEntity instanceof EntityArrow)
                {
                    Entity var7 = ((EntityArrow)this.myEntity).shootingEntity;
                    return new Packet23VehicleSpawn(this.myEntity, 60, var7 != null ? var7.entityId : this.myEntity.entityId);
                }
                else if (this.myEntity instanceof EntitySnowball)
                {
                    return new Packet23VehicleSpawn(this.myEntity, 61);
                }
                else if (this.myEntity instanceof EntityPotion)
                {
                    return new Packet23VehicleSpawn(this.myEntity, 73, ((EntityPotion)this.myEntity).getPotionDamage());
                }
                else if (this.myEntity instanceof EntityExpBottle)
                {
                    return new Packet23VehicleSpawn(this.myEntity, 75);
                }
                else if (this.myEntity instanceof EntityEnderPearl)
                {
                    return new Packet23VehicleSpawn(this.myEntity, 65);
                }
                else if (this.myEntity instanceof EntityEnderEye)
                {
                    return new Packet23VehicleSpawn(this.myEntity, 72);
                }
                else
                {
                    Packet23VehicleSpawn var2;

                    if (this.myEntity instanceof EntityFireball)
                    {
                        EntityFireball var6 = (EntityFireball)this.myEntity;
                        var2 = null;
                        byte var3 = 63;

                        if (this.myEntity instanceof EntitySmallFireball)
                        {
                            var3 = 64;
                        }
                        else if (this.myEntity instanceof EntityWitherSkull)
                        {
                            var3 = 66;
                        }

                        if (var6.shootingEntity != null)
                        {
                            var2 = new Packet23VehicleSpawn(this.myEntity, var3, ((EntityFireball)this.myEntity).shootingEntity.entityId);
                        }
                        else
                        {
                            var2 = new Packet23VehicleSpawn(this.myEntity, var3, 0);
                        }

                        var2.speedX = (int)(var6.accelerationX * 8000.0D);
                        var2.speedY = (int)(var6.accelerationY * 8000.0D);
                        var2.speedZ = (int)(var6.accelerationZ * 8000.0D);
                        return var2;
                    }
                    else if (this.myEntity instanceof EntityEgg)
                    {
                        return new Packet23VehicleSpawn(this.myEntity, 62);
                    }
                    else if (this.myEntity instanceof EntityTNTPrimed)
                    {
                        return new Packet23VehicleSpawn(this.myEntity, 50);
                    }
                    else if (this.myEntity instanceof EntityEnderCrystal)
                    {
                        return new Packet23VehicleSpawn(this.myEntity, 51);
                    }
                    else if (this.myEntity instanceof EntityFallingSand)
                    {
                        EntityFallingSand var5 = (EntityFallingSand)this.myEntity;
                        return new Packet23VehicleSpawn(this.myEntity, 70, var5.blockID | var5.field_70285_b << 16);
                    }
                    else if (this.myEntity instanceof EntityPainting)
                    {
                        return new Packet25EntityPainting((EntityPainting)this.myEntity);
                    }
                    else if (this.myEntity instanceof EntityItemFrame)
                    {
                        EntityItemFrame var4 = (EntityItemFrame)this.myEntity;
                        var2 = new Packet23VehicleSpawn(this.myEntity, 71, var4.field_82332_a);
                        var2.xPosition = MathHelper.floor_float((float)(var4.xPosition * 32));
                        var2.yPosition = MathHelper.floor_float((float)(var4.yPosition * 32));
                        var2.zPosition = MathHelper.floor_float((float)(var4.zPosition * 32));
                        return var2;
                    }
                    else if (this.myEntity instanceof EntityXPOrb)
                    {
                        return new Packet26EntityExpOrb((EntityXPOrb)this.myEntity);
                    }
                    else
                    {
                        throw new IllegalArgumentException("Don\'t know how to add " + this.myEntity.getClass() + "!");
                    }
                }
            }
            else
            {
                this.lastHeadMotion = MathHelper.floor_float(this.myEntity.func_70079_am() * 256.0F / 360.0F);
                return new Packet24MobSpawn((EntityLiving)this.myEntity);
            }
        }
    }

    public void removePlayerFromTracker(EntityPlayerMP par1EntityPlayerMP)
    {
        if (this.trackedPlayers.contains(par1EntityPlayerMP))
        {
            this.trackedPlayers.remove(par1EntityPlayerMP);
            par1EntityPlayerMP.destroyedItemsNetCache.add(Integer.valueOf(this.myEntity.entityId));
        }
    }
}
