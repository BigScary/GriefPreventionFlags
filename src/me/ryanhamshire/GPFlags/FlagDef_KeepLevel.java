package me.ryanhamshire.GPFlags;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;

public class FlagDef_KeepLevel extends FlagDefinition
{
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        Player player = event.getEntity();
        
        Flag flag = this.GetFlagInstanceAtLocation(player.getLocation(), null);
        if(flag == null) return;
        
        event.setKeepLevel(true);
        event.setDroppedExp(0);
    }
    
    public FlagDef_KeepLevel(FlagManager manager, GPFlags plugin)
    {
        super(manager, plugin);
    }
    
    @Override
    String getName()
    {
        return "KeepLevel";
    }

    @Override
    MessageSpecifier GetSetMessage(String parameters)
    {
        return new MessageSpecifier(Messages.EnableKeepLevel);
    }

    @Override
    MessageSpecifier GetUnSetMessage()
    {
        return new MessageSpecifier(Messages.DisableKeepLevel);
    }
}
