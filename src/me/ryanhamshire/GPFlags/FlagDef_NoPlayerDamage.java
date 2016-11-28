package me.ryanhamshire.GPFlags;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;

public class FlagDef_NoPlayerDamage extends FlagDefinition
{
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDamage(EntityDamageEvent event)
    {
        if(event.getEntityType() != EntityType.PLAYER) return;
        
        Player player = (Player)event.getEntity();
        
        Flag flag = this.GetFlagInstanceAtLocation(player.getLocation(), player);
        if(flag == null) return;
        
        event.setCancelled(true);
    }
    
    public FlagDef_NoPlayerDamage(FlagManager manager, GPFlags plugin)
    {
        super(manager, plugin);
    }
    
    @Override
    String getName()
    {
        return "NoPlayerDamage";
    }

    @Override
    MessageSpecifier GetSetMessage(String parameters)
    {
        return new MessageSpecifier(Messages.EnableNoPlayerDamage);
    }

    @Override
    MessageSpecifier GetUnSetMessage()
    {
        return new MessageSpecifier(Messages.DisableNoPlayerDamage);
    }
}
