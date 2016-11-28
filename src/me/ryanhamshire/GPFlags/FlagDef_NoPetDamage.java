package me.ryanhamshire.GPFlags;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;

public class FlagDef_NoPetDamage extends FlagDefinition
{
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event)
    {
        Entity entity = event.getEntity();
        if(!(entity instanceof Tameable)) return;
        
        Tameable tameable = (Tameable)entity;
        if(!tameable.isTamed() || tameable.getOwner() == null) return;
        
        Flag flag = this.GetFlagInstanceAtLocation(entity.getLocation(), null);
        if(flag != null)
        {
            event.setCancelled(true);
        }
    }
    
    public FlagDef_NoPetDamage(FlagManager manager, GPFlags plugin)
    {
        super(manager, plugin);
    }
    
    @Override
    String getName()
    {
        return "NoPetDamage";
    }

    @Override
    MessageSpecifier GetSetMessage(String parameters)
    {
        return new MessageSpecifier(Messages.EnableNoPetDamage);
    }

    @Override
    MessageSpecifier GetUnSetMessage()
    {
        return new MessageSpecifier(Messages.DisableNoPetDamage);
    }
}
