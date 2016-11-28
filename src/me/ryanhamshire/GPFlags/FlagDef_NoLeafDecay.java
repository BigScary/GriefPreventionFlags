package me.ryanhamshire.GPFlags;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.LeavesDecayEvent;

public class FlagDef_NoLeafDecay extends FlagDefinition
{
    @EventHandler(priority = EventPriority.LOWEST)
    public void onLeafDecay(LeavesDecayEvent event)
    {
        Block block = event.getBlock();
        
        Flag flag = this.GetFlagInstanceAtLocation(block.getLocation(), null);
        if(flag == null) return;
        
        event.setCancelled(true);
    }
    
    public FlagDef_NoLeafDecay(FlagManager manager, GPFlags plugin)
    {
        super(manager, plugin);
    }
    
    @Override
    String getName()
    {
        return "NoLeafDecay";
    }

    @Override
    MessageSpecifier GetSetMessage(String parameters)
    {
        return new MessageSpecifier(Messages.EnableNoLeafDecay);
    }

    @Override
    MessageSpecifier GetUnSetMessage()
    {
        return new MessageSpecifier(Messages.DisableNoLeafDecay);
    }
}
