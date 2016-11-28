package me.ryanhamshire.GPFlags;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.PlayerData;

public abstract class FlagDefinition implements Listener
{
    FlagManager flagManager;
    private int instances = 0;
    protected GPFlags plugin;
    
    FlagDefinition(FlagManager manager, GPFlags plugin)
    {
        this.flagManager = manager;
        this.plugin = plugin;
    }
    
    abstract String getName();
    
    SetFlagResult ValidateParameters(String parameters)
    {
        return new SetFlagResult(true, this.GetSetMessage(parameters));
    }
    
    abstract MessageSpecifier GetSetMessage(String parameters);
    
    abstract MessageSpecifier GetUnSetMessage();
    
    Flag GetFlagInstanceAtLocation(Location location, Player player)
    {
        Flag flag = null;
        if(GriefPrevention.instance.claimsEnabledForWorld(location.getWorld()))
        {
            Claim cachedClaim = null;
            PlayerData playerData = null;
            if(player != null)
            {
                playerData = GriefPrevention.instance.dataStore.getPlayerData(player.getUniqueId());
                cachedClaim = playerData.lastClaim;
            }
            
            Claim claim = GriefPrevention.instance.dataStore.getClaimAt(location, true, cachedClaim);
            if(claim != null)
            {
                if(playerData != null)
                {
                    playerData.lastClaim = claim;
                }
                
                flag = this.flagManager.GetFlag(claim.getID().toString(), this);
                if(flag != null && !flag.getSet()) return null;
                
                if(flag == null && claim.parent != null)
                {
                    flag = this.flagManager.GetFlag(claim.parent.getID().toString(), this);
                    if(flag != null && !flag.getSet()) return null;
                }
                
                if(flag == null)
                {
                    flag = this.flagManager.GetFlag(FlagManager.DEFAULT_FLAG_ID.toString(), this);
                    if(flag != null && !flag.getSet()) return null;
                }
            }
        }
        
        if(flag == null)
        {
            flag = this.flagManager.GetFlag(location.getWorld().getName(), this);
            if(flag != null && !flag.getSet()) return null;
        }
        
        if(flag == null)
        {
            flag = this.flagManager.GetFlag("everywhere", this);
            if(flag != null && !flag.getSet()) return null;
        }
        
        return flag;
    }

    void incrementInstances()
    {
        if(++this.instances == 1)
        {
            this.FirstTimeSetup();
        }
    }
    
    void FirstTimeSetup()
    {
        Bukkit.getServer().getPluginManager().registerEvents(this, this.plugin);
    }
}
