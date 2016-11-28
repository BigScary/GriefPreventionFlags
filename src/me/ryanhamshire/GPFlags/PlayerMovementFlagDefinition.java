package me.ryanhamshire.GPFlags;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class PlayerMovementFlagDefinition extends TimedPlayerFlagDefinition implements Runnable
{
    private ConcurrentHashMap<UUID, Location> lastLocationMap = new ConcurrentHashMap<UUID, Location>();
    
    PlayerMovementFlagDefinition(FlagManager manager, GPFlags plugin)
    {
        super(manager, plugin);
    }
    
    abstract boolean allowMovement(Player player, Location lastLocation);
    
    @Override
    long getPlayerCheckFrequency_Ticks()
    {
        return 20L;
    }
    
    @Override
    void processPlayer(Player player)
    {
        UUID playerID = player.getUniqueId();
        Location lastLocation = this.lastLocationMap.get(playerID);
        Location location = player.getLocation();
        if(lastLocation != null && location.getBlockX() == lastLocation.getBlockX() && location.getBlockY() == lastLocation.getBlockY() && location.getBlockZ() == lastLocation.getBlockZ()) return;
        if(!this.allowMovement(player, lastLocation))
        {
            this.undoMovement(player, lastLocation);
        }
        else
        {
            this.lastLocationMap.put(playerID, location);
        }
    }
    
    protected void undoMovement(Player player, Location lastLocation)
    {
        if(lastLocation != null)
        {
            player.teleport(lastLocation);
        }
        else if(player.getBedSpawnLocation() != null)
        {
            player.teleport(player.getBedSpawnLocation());
        }
        else
        {
            player.teleport(player.getWorld().getSpawnLocation());
        }
    }
}
