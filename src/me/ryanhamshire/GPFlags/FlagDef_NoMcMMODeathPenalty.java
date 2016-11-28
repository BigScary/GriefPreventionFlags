package me.ryanhamshire.GPFlags;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import com.gmail.nossr50.events.hardcore.McMMOPlayerDeathPenaltyEvent;

public class FlagDef_NoMcMMODeathPenalty extends FlagDefinition
{
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerDisarm(McMMOPlayerDeathPenaltyEvent event)
    {
        Player player = event.getPlayer();
        Flag flag = this.GetFlagInstanceAtLocation(player.getLocation(), player);
        if(flag != null)
        {
            event.setCancelled(true);
        }
    }
    
    public FlagDef_NoMcMMODeathPenalty(FlagManager manager, GPFlags plugin)
    {
        super(manager, plugin);
    }
    
    @Override
    String getName()
    {
        return "NoMcMMODeathPenalty";
    }

    @Override
    MessageSpecifier GetSetMessage(String parameters)
    {
        return new MessageSpecifier(Messages.EnableNoMcMMODeathPenalty);
    }

    @Override
    MessageSpecifier GetUnSetMessage()
    {
        return new MessageSpecifier(Messages.DisableNoMcMMODeathPenalty);
    }
}
