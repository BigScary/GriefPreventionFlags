package me.ryanhamshire.GPFlags;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class FlagDef_NoChorusFruit extends FlagDefinition
{
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event)
    {
        if(event.getCause() != TeleportCause.CHORUS_FRUIT) return;
        
        Flag flag = this.GetFlagInstanceAtLocation(event.getFrom(), event.getPlayer());
        if(flag != null)
        {
            event.setCancelled(true);
        }
        
        flag = this.GetFlagInstanceAtLocation(event.getTo(), event.getPlayer());
        if(flag != null)
        {
            event.setCancelled(true);
        }
    }
    
    public FlagDef_NoChorusFruit(FlagManager manager, GPFlags plugin)
    {
        super(manager, plugin);
    }
    
    @Override
    String getName()
    {
        return "NoChorusFruit";
    }

    @Override
    MessageSpecifier GetSetMessage(String parameters)
    {
        return new MessageSpecifier(Messages.EnableNoChorusFruit);
    }

    @Override
    MessageSpecifier GetUnSetMessage()
    {
        return new MessageSpecifier(Messages.DisableNoChorusFruit);
    }
}
