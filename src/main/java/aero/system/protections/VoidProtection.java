package aero.system.protections;


import aero.system.comandos.Spawn;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Objects;

public class VoidProtection implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void aoSofrerDano(EntityDamageEvent e){
        if(e.getCause() == EntityDamageEvent.DamageCause.VOID
                && e.getEntity() instanceof Player
                && !e.getEntity().getWorld().getName().equals("world_the_end")) {
            Player p = (Player) e.getEntity();
            if(p.getLocation().getBlockY() < 0){
                e.setCancelled(true);
                p.setFallDistance(1);
                p.teleport(Objects.requireNonNull(Spawn.pegarSpawnLoc()), PlayerTeleportEvent.TeleportCause.PLUGIN);
            }
        }
    }

}
