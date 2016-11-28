package me.ryanhamshire.GPFlags;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

class SendPlayerMessageTask implements Runnable 
{
	private Player player;
	private ChatColor color;
	private String message;
	
	public SendPlayerMessageTask(Player player, ChatColor color, String message)
	{
		this.player = player;
		this.color = color;
		this.message = message;
	}

	@Override
	public void run()
	{
		if(player == null)
		{
		    GPFlags.AddLogEntry(color + message);
		    return;
		}
	    
	    GPFlags.sendMessage(this.player, this.color, this.message);
	}	
}
