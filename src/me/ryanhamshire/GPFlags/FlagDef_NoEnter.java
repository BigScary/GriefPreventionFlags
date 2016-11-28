package me.ryanhamshire.GPFlags;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.PlayerData;

public class FlagDef_NoEnter extends PlayerMovementFlagDefinition
{
    @Override
    public boolean allowMovement(Player player, Location lastLocation)
    {
        if(player.hasPermission("gpflags.bypass")) return true;
        
        Location to = player.getLocation();
        Location from = lastLocation;
        
        Flag flag = this.GetFlagInstanceAtLocation(to, player);
        if(flag == null) return true;
        
        if(from == null || flag == this.GetFlagInstanceAtLocation(from, player)) return true;
        
        PlayerData playerData = GriefPrevention.instance.dataStore.getPlayerData(player.getUniqueId());
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(to, false, playerData.lastClaim);
        if(claim.allowAccess(player) != null)
        {
            GPFlags.sendMessage(player, TextMode.Err, flag.parameters);
            return false;
        }
        
        return true;
    }
    
    public FlagDef_NoEnter(FlagManager manager, GPFlags plugin)
    {
        super(manager, plugin);
    }
    
    @Override
    String getName()
    {
        return "NoEnter";
    }

    @Override
    SetFlagResult ValidateParameters(String parameters)
    {
        if(parameters.isEmpty())
        {
            return new SetFlagResult(false, new MessageSpecifier(Messages.MessageRequired));
        }

        return new SetFlagResult(true, this.GetSetMessage(parameters));
    }
    
    @Override
    MessageSpecifier GetSetMessage(String parameters)
    {
        return new MessageSpecifier(Messages.EnabledNoEnter, parameters);
    }

    @Override
    MessageSpecifier GetUnSetMessage()
    {
        return new MessageSpecifier(Messages.DisabledNoEnter);
    }
}
