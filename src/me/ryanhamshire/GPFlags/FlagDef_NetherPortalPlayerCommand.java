package me.ryanhamshire.GPFlags;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class FlagDef_NetherPortalPlayerCommand extends FlagDefinition
{
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerTeleport(PlayerTeleportEvent event)
    {
        TeleportCause cause = event.getCause();
        
        if(cause == null || cause != TeleportCause.NETHER_PORTAL) return;
        
        Player player = event.getPlayer();
        
        Flag flag = this.GetFlagInstanceAtLocation(event.getFrom(), player);
        if(flag == null) return;
        
        event.setCancelled(true);
        player.performCommand(flag.parameters);
    }
    
    public FlagDef_NetherPortalPlayerCommand(FlagManager manager, GPFlags plugin)
    {
        super(manager, plugin);
    }
    
    @Override
    SetFlagResult ValidateParameters(String parameters)
    {
        if(parameters.isEmpty())
        {
            return new SetFlagResult(false, new MessageSpecifier(Messages.CommandRequired));
        }

        return new SetFlagResult(true, this.GetSetMessage(parameters));
    }
    
    @Override
    String getName()
    {
        return "NetherPortalPlayerCommand";
    }

    @Override
    MessageSpecifier GetSetMessage(String parameters)
    {
        return new MessageSpecifier(Messages.EnableNetherPortalPlayerCommand);
    }

    @Override
    MessageSpecifier GetUnSetMessage()
    {
        return new MessageSpecifier(Messages.DisableNetherPortalPlayerCommand);
    }
}
