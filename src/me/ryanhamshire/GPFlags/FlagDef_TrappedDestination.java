package me.ryanhamshire.GPFlags;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;

import me.ryanhamshire.GriefPrevention.events.SaveTrappedPlayerEvent;

public class FlagDef_TrappedDestination extends FlagDefinition
{
    @EventHandler
    public void onPlayerDeath(SaveTrappedPlayerEvent event)
    {
        Flag flag = this.GetFlagInstanceAtLocation(event.getClaim().getLesserBoundaryCorner(), null);
        if(flag == null) return;
        
        String [] params = flag.getParametersArray();
        World world = Bukkit.getServer().getWorld(params[0]); 
        Location location = new Location
        (
            world,
            Integer.valueOf(params[1]),
            Integer.valueOf(params[2]),
            Integer.valueOf(params[3])
        );
        
        event.setDestination(location);
    }
    
    public FlagDef_TrappedDestination(FlagManager manager, GPFlags plugin)
    {
        super(manager, plugin);
    }
    
    @Override
    String getName()
    {
        return "TrappedDestination";
    }
    
    @Override
    SetFlagResult ValidateParameters(String parameters)
    {
        String [] params = parameters.split(" ");
        
        if(params.length != 4)
        {
            return new SetFlagResult(false, new MessageSpecifier(Messages.LocationRequired));
        }
        
        World world = Bukkit.getWorld(params[0]);
        if(world == null)
        {
            return new SetFlagResult(false, new MessageSpecifier(Messages.WorldNotFound));
        }
        
        try
        {
            Integer.valueOf(params[1]);
            Integer.valueOf(params[2]);
            Integer.valueOf(params[3]);
        }
        catch(NumberFormatException e)
        {
            return new SetFlagResult(false, new MessageSpecifier(Messages.LocationRequired));
        }

        return new SetFlagResult(true, this.GetSetMessage(parameters));
    }

    @Override
    MessageSpecifier GetSetMessage(String parameters)
    {
        return new MessageSpecifier(Messages.EnableTrappedDestination);
    }

    @Override
    MessageSpecifier GetUnSetMessage()
    {
        return new MessageSpecifier(Messages.DisableTrappedDestination);
    }
}
