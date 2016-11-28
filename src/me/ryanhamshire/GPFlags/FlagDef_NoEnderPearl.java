package me.ryanhamshire.GPFlags;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class FlagDef_NoEnderPearl extends FlagDefinition
{
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event)
    {
        if(event.getCause() != TeleportCause.ENDER_PEARL) return;
        
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
    
    public FlagDef_NoEnderPearl(FlagManager manager, GPFlags plugin)
    {
        super(manager, plugin);
    }
    
    @Override
    String getName()
    {
        return "NoEnderPearl";
    }

    @Override
    MessageSpecifier GetSetMessage(String parameters)
    {
        return new MessageSpecifier(Messages.EnableNoEnderPearl);
    }

    @Override
    MessageSpecifier GetUnSetMessage()
    {
        return new MessageSpecifier(Messages.DisableNoEnderPearl);
    }
}
