package me.ryanhamshire.GPFlags;

import org.bukkit.event.EventHandler;

import me.ryanhamshire.GriefPrevention.events.ProtectDeathDropsEvent;

public class FlagDef_NoLootProtection extends FlagDefinition
{
    @EventHandler
    public void onPlayerDeath(ProtectDeathDropsEvent event)
    {
        if(event.getClaim() != null)
        {
            Flag flag = this.GetFlagInstanceAtLocation(event.getClaim().getLesserBoundaryCorner(), null);
            if(flag == null) return;
            
            event.setCancelled(true);
        }
    }
    
    public FlagDef_NoLootProtection(FlagManager manager, GPFlags plugin)
    {
        super(manager, plugin);
    }
    
    @Override
    String getName()
    {
        return "NoLootProtection";
    }

    @Override
    MessageSpecifier GetSetMessage(String parameters)
    {
        return new MessageSpecifier(Messages.EnableNoLootProtection);
    }

    @Override
    MessageSpecifier GetUnSetMessage()
    {
        return new MessageSpecifier(Messages.DisableNoLootProtection);
    }
}
