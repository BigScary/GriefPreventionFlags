package me.ryanhamshire.GPFlags;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public abstract class TimedPlayerFlagDefinition extends FlagDefinition implements Listener, Runnable
{
    private static long tickOffset = 0L;
    private ConcurrentLinkedQueue<ConcurrentLinkedQueue<Player>> playerQueueQueue = new ConcurrentLinkedQueue<ConcurrentLinkedQueue<Player>>();
    private long taskIntervalTicks;
    
    TimedPlayerFlagDefinition(FlagManager manager, GPFlags plugin)
    {
        super(manager, plugin);
    }
    
    abstract long getPlayerCheckFrequency_Ticks();
    abstract void processPlayer(Player player);
    
    void FirstTimeSetup()
    {
        super.FirstTimeSetup();

        this.taskIntervalTicks = this.getPlayerCheckFrequency_Ticks() / Bukkit.getServer().getMaxPlayers();
        if(this.taskIntervalTicks < 1) this.taskIntervalTicks = 1;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, this, TimedPlayerFlagDefinition.tickOffset++, Math.max(this.taskIntervalTicks, 1));
    }
    
    @Override
    public void run()
    {
        ConcurrentLinkedQueue<Player> playerQueue = this.playerQueueQueue.poll();
        if(playerQueue == null)
        {
            long iterationsToProcessAllPlayers = this.getPlayerCheckFrequency_Ticks() / this.taskIntervalTicks;
            if(iterationsToProcessAllPlayers < 1) iterationsToProcessAllPlayers = 1;
            for(int i = 0; i < iterationsToProcessAllPlayers; i++)
            {
                this.playerQueueQueue.add(new ConcurrentLinkedQueue<Player>());
            }
            
            @SuppressWarnings("unchecked")
            Collection<Player> players = (Collection<Player>)Bukkit.getServer().getOnlinePlayers();
            for(Player player : players)
            {
                ConcurrentLinkedQueue<Player> queueToFill = this.playerQueueQueue.poll();
                queueToFill.add(player);
                this.playerQueueQueue.add(queueToFill);
            }
            
            playerQueue = this.playerQueueQueue.poll();
        }
        
        Player player;
        while((player = playerQueue.poll()) != null)
        {
            try
            {
                this.processPlayer(player);
            }
            catch(Exception e)
            {
                if(player.isOnline())
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
