package me.ryanhamshire.GPFlags;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class FlagDef_NoMobSpawns extends FlagDefinition
{
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntitySpawn(CreatureSpawnEvent event)
    {
        if(!(event.getEntity() instanceof LivingEntity)) return;
        
        if(event.getLocation() == null) return;
        
        EntityType type = event.getEntityType();
        if(type == EntityType.PLAYER) return;
        if(type == EntityType.ARMOR_STAND) return;
        
        SpawnReason reason = event.getSpawnReason();
        if(reason == SpawnReason.SPAWNER || reason == SpawnReason.SPAWNER_EGG) return;
        
        
        Flag flag = this.GetFlagInstanceAtLocation(event.getLocation(), null);
        if(flag == null) return;
        
        event.setCancelled(true);
    }
    
    public FlagDef_NoMobSpawns(FlagManager manager, GPFlags plugin)
    {
        super(manager, plugin);
    }
    
    @Override
    String getName()
    {
        return "NoMobSpawns";
    }

    @Override
    MessageSpecifier GetSetMessage(String parameters)
    {
        return new MessageSpecifier(Messages.DisableMobSpawns);
    }

    @Override
    MessageSpecifier GetUnSetMessage()
    {
        return new MessageSpecifier(Messages.EnableMobSpawns);
    }
}
