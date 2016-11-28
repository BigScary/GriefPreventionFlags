package me.ryanhamshire.GPFlags;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class FlagDef_CommandBlackList extends CommandListFlagDefinition
{
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event)
    {
        Player player = event.getPlayer();
        if(player.hasPermission("gpflags.bypass")) return;
        
        Flag flag = this.GetFlagInstanceAtLocation(player.getLocation(), player);
        if(flag == null) return;
        
        if(this.commandInList(flag.parameters, event.getMessage()))
        {
            event.setCancelled(true);
            GPFlags.sendMessage(player, TextMode.Err, Messages.CommandBlockedHere);
        }
    }
    
    public FlagDef_CommandBlackList(FlagManager manager, GPFlags plugin)
    {
        super(manager, plugin);
    }
    
    @Override
    String getName()
    {
        return "CommandBlackList";
    }

    @Override
    MessageSpecifier GetSetMessage(String parameters)
    {
        return new MessageSpecifier(Messages.EnableCommandBlackList);
    }

    @Override
    MessageSpecifier GetUnSetMessage()
    {
        return new MessageSpecifier(Messages.DisableCommandBlackList);
    }
}
