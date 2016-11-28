package me.ryanhamshire.GPFlags;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import com.gmail.nossr50.events.skills.abilities.McMMOPlayerAbilityActivateEvent;
import com.gmail.nossr50.events.skills.secondaryabilities.SecondaryAbilityEvent;
import com.gmail.nossr50.events.skills.unarmed.McMMOPlayerDisarmEvent;

public class FlagDef_NoMcMMOSkills extends FlagDefinition
{
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerDisarm(McMMOPlayerDisarmEvent event)
    {
        this.handleEvent(event.getPlayer(), event);
    }
    
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerAbility(McMMOPlayerAbilityActivateEvent event)
    {
        this.handleEvent(event.getPlayer(), event);
    }
    
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerSecondaryAbility(SecondaryAbilityEvent event)
    {
        this.handleEvent(event.getPlayer(), event);
    }
    
    private void handleEvent(Player player, Cancellable event)
    {
        Flag flag = this.GetFlagInstanceAtLocation(player.getLocation(), player);
        if(flag != null)
        {
            event.setCancelled(true);
        }
    }
    
    public FlagDef_NoMcMMOSkills(FlagManager manager, GPFlags plugin)
    {
        super(manager, plugin);
    }
    
    @Override
    String getName()
    {
        return "NoMcMMOSkills";
    }

    @Override
    MessageSpecifier GetSetMessage(String parameters)
    {
        return new MessageSpecifier(Messages.EnableNoMcMMOSkills);
    }

    @Override
    MessageSpecifier GetUnSetMessage()
    {
        return new MessageSpecifier(Messages.DisableNoMcMMOSkills);
    }
}
