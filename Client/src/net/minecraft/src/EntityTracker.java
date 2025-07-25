package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class EntityTracker
{
    private final WorldServer theWorld;
    private Set trackedEntities = new HashSet();
    private IntHashMap trackedEntityIDs = new IntHashMap();
    private int entityViewDistance;

    public EntityTracker(WorldServer par1WorldServer)
    {
        this.theWorld = par1WorldServer;
        this.entityViewDistance = par1WorldServer.getMinecraftServer().getConfigurationManager().getEntityViewDistance();
    }

    /**
     * if entity is a player sends all tracked events to the player, otherwise, adds with a visibility and update arate
     * based on the class type
     */
    public void addEntityToTracker(Entity par1Entity)
    {
        if (par1Entity instanceof EntityPlayerMP)
        {
            this.addEntityToTracker(par1Entity, 512, 2);
            EntityPlayerMP var2 = (EntityPlayerMP)par1Entity;
            Iterator var3 = this.trackedEntities.iterator();

            while (var3.hasNext())
            {
                EntityTrackerEntry var4 = (EntityTrackerEntry)var3.next();

                if (var4.myEntity != var2)
                {
                    var4.tryStartWachingThis(var2);
                }
            }
        }
        else if (par1Entity instanceof EntityFishHook)
        {
            this.addEntityToTracker(par1Entity, 64, 5, true);
        }
        else if (par1Entity instanceof EntityArrow)
        {
            this.addEntityToTracker(par1Entity, 64, 20, false);
        }
        else if (par1Entity instanceof EntitySmallFireball)
        {
            this.addEntityToTracker(par1Entity, 64, 10, false);
        }
        else if (par1Entity instanceof EntityFireball)
        {
            this.addEntityToTracker(par1Entity, 64, 10, false);
        }
        else if (par1Entity instanceof EntitySnowball)
        {
            this.addEntityToTracker(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityEnderPearl)
        {
            this.addEntityToTracker(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityEnderEye)
        {
            this.addEntityToTracker(par1Entity, 64, 4, true);
        }
        else if (par1Entity instanceof EntityEgg)
        {
            this.addEntityToTracker(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityPotion)
        {
            this.addEntityToTracker(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityExpBottle)
        {
            this.addEntityToTracker(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityItem)
        {
            this.addEntityToTracker(par1Entity, 64, 20, true);
        }
        else if (par1Entity instanceof EntityMinecart)
        {
            this.addEntityToTracker(par1Entity, 80, 3, true);
        }
        else if (par1Entity instanceof EntityBoat)
        {
            this.addEntityToTracker(par1Entity, 80, 3, true);
        }
        else if (par1Entity instanceof EntitySquid)
        {
            this.addEntityToTracker(par1Entity, 64, 3, true);
        }
        else if (par1Entity instanceof EntityWither)
        {
            this.addEntityToTracker(par1Entity, 80, 3, false);
        }
        else if (par1Entity instanceof EntityBat)
        {
            this.addEntityToTracker(par1Entity, 80, 3, false);
        }
        else if (par1Entity instanceof IAnimals)
        {
            this.addEntityToTracker(par1Entity, 80, 3, true);
        }
        else if (par1Entity instanceof EntityDragon)
        {
            this.addEntityToTracker(par1Entity, 160, 3, true);
        }
        else if (par1Entity instanceof EntityTNTPrimed)
        {
            this.addEntityToTracker(par1Entity, 160, 10, true);
        }
        else if (par1Entity instanceof EntityFallingSand)
        {
            this.addEntityToTracker(par1Entity, 160, 20, true);
        }
        else if (par1Entity instanceof EntityPainting)
        {
            this.addEntityToTracker(par1Entity, 160, Integer.MAX_VALUE, false);
        }
        else if (par1Entity instanceof EntityXPOrb)
        {
            this.addEntityToTracker(par1Entity, 160, 20, true);
        }
        else if (par1Entity instanceof EntityEnderCrystal)
        {
            this.addEntityToTracker(par1Entity, 256, Integer.MAX_VALUE, false);
        }
        else if (par1Entity instanceof EntityItemFrame)
        {
            this.addEntityToTracker(par1Entity, 160, Integer.MAX_VALUE, false);
        }
    }

    public void addEntityToTracker(Entity par1Entity, int par2, int par3)
    {
        this.addEntityToTracker(par1Entity, par2, par3, false);
    }

    public void addEntityToTracker(Entity par1Entity, int par2, int par3, boolean par4)
    {
        if (par2 > this.entityViewDistance)
        {
            par2 = this.entityViewDistance;
        }

        if (this.trackedEntityIDs.containsItem(par1Entity.entityId))
        {
            throw new IllegalStateException("Entity is already tracked!");
        }
        else
        {
            EntityTrackerEntry var5 = new EntityTrackerEntry(par1Entity, par2, par3, par4);
            this.trackedEntities.add(var5);
            this.trackedEntityIDs.addKey(par1Entity.entityId, var5);
            var5.sendEventsToPlayers(this.theWorld.playerEntities);
        }
    }

    public void removeEntityFromAllTrackingPlayers(Entity par1Entity)
    {
        if (par1Entity instanceof EntityPlayerMP)
        {
            EntityPlayerMP var2 = (EntityPlayerMP)par1Entity;
            Iterator var3 = this.trackedEntities.iterator();

            while (var3.hasNext())
            {
                EntityTrackerEntry var4 = (EntityTrackerEntry)var3.next();
                var4.removeFromWatchingList(var2);
            }
        }

        EntityTrackerEntry var5 = (EntityTrackerEntry)this.trackedEntityIDs.removeObject(par1Entity.entityId);

        if (var5 != null)
        {
            this.trackedEntities.remove(var5);
            var5.informAllAssociatedPlayersOfItemDestruction();
        }
    }

    public void updateTrackedEntities()
    {
        ArrayList var1 = new ArrayList();
        Iterator var2 = this.trackedEntities.iterator();

        while (var2.hasNext())
        {
            EntityTrackerEntry var3 = (EntityTrackerEntry)var2.next();
            var3.sendLocationToAllClients(this.theWorld.playerEntities);

            if (var3.playerEntitiesUpdated && var3.myEntity instanceof EntityPlayerMP)
            {
                var1.add((EntityPlayerMP)var3.myEntity);
            }
        }

        var2 = var1.iterator();

        while (var2.hasNext())
        {
            EntityPlayerMP var7 = (EntityPlayerMP)var2.next();
            EntityPlayerMP var4 = var7;
            Iterator var5 = this.trackedEntities.iterator();

            while (var5.hasNext())
            {
                EntityTrackerEntry var6 = (EntityTrackerEntry)var5.next();

                if (var6.myEntity != var4)
                {
                    var6.tryStartWachingThis(var4);
                }
            }
        }
    }

    /**
     * does not send the packet to the entity if the entity is a player
     */
    public void sendPacketToAllPlayersTrackingEntity(Entity par1Entity, Packet par2Packet)
    {
        EntityTrackerEntry var3 = (EntityTrackerEntry)this.trackedEntityIDs.lookup(par1Entity.entityId);

        if (var3 != null)
        {
            var3.sendPacketToAllTrackingPlayers(par2Packet);
        }
    }

    /**
     * sends to the entity if the entity is a player
     */
    public void sendPacketToAllAssociatedPlayers(Entity par1Entity, Packet par2Packet)
    {
        EntityTrackerEntry var3 = (EntityTrackerEntry)this.trackedEntityIDs.lookup(par1Entity.entityId);

        if (var3 != null)
        {
            var3.sendPacketToAllAssociatedPlayers(par2Packet);
        }
    }

    public void removeAllTrackingPlayers(EntityPlayerMP par1EntityPlayerMP)
    {
        Iterator var2 = this.trackedEntities.iterator();

        while (var2.hasNext())
        {
            EntityTrackerEntry var3 = (EntityTrackerEntry)var2.next();
            var3.removePlayerFromTracker(par1EntityPlayerMP);
        }
    }
}
