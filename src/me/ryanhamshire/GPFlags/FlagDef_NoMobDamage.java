package me.ryanhamshire.GPFlags;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.WaterMob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.projectiles.ProjectileSource;

public class FlagDef_NoMobDamage extends FlagDefinition
{
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageEvent event)
    {
        Entity entity = event.getEntity();
        
        DamageCause cause = event.getCause();
        if(cause == DamageCause.ENTITY_ATTACK || cause == DamageCause.PROJECTILE)
        {
            EntityDamageByEntityEvent event2 = (EntityDamageByEntityEvent)event;
            Entity attacker = event2.getDamager();
            
            if(attacker != null && attacker.getType() == EntityType.PLAYER) return;
            
            if(attacker instanceof Projectile)
            {
                ProjectileSource source = ((Projectile)attacker).getShooter();
                if(source != null && source instanceof Player) return;
            }
        }
        
        if(entity instanceof Animals || entity instanceof WaterMob || entity.getType() == EntityType.VILLAGER || entity.getCustomName() != null)
        {
            Flag flag = this.GetFlagInstanceAtLocation(entity.getLocation(), null);
            if(flag == null) return;
            event.setCancelled(true);
        }
    }
    
    public FlagDef_NoMobDamage(FlagManager manager, GPFlags plugin)
    {
        super(manager, plugin);
    }
    
    @Override
    String getName()
    {
        return "NoMobDamage";
    }

    @Override
    MessageSpecifier GetSetMessage(String parameters)
    {
        return new MessageSpecifier(Messages.DisableMobDamage);
    }

    @Override
    MessageSpecifier GetUnSetMessage()
    {
        return new MessageSpecifier(Messages.EnableMobDamage);
    }
}
