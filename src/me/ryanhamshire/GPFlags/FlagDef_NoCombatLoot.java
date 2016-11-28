package me.ryanhamshire.GPFlags;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;

public class FlagDef_NoCombatLoot extends FlagDefinition
{
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDeath(EntityDeathEvent event)
    {
        LivingEntity entity = event.getEntity();
        
        if(entity.getType() == EntityType.PLAYER) return;
        
        Flag flag = this.GetFlagInstanceAtLocation(entity.getLocation(), null);
        if(flag == null) return;
        
        event.getDrops().clear();
    }
    
    public FlagDef_NoCombatLoot(FlagManager manager, GPFlags plugin)
    {
        super(manager, plugin);
    }
    
    @Override
    String getName()
    {
        return "NoCombatLoot";
    }

    @Override
    MessageSpecifier GetSetMessage(String parameters)
    {
        return new MessageSpecifier(Messages.EnableNoCombatLoot);
    }

    @Override
    MessageSpecifier GetUnSetMessage()
    {
        return new MessageSpecifier(Messages.DisableNoCombatLoot);
    }
}
