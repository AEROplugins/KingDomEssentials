package aero.system.protections;

import aero.system.comandos.Spawn;
import aero.system.config.Locations;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class NetherTetoProtection implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void aoTeleportar(PlayerTeleportEvent e) {
        if (e.getTo() != null && e.getTo().getWorld().getEnvironment() == World.Environment.NETHER && e.getTo().getY() > 124.0D) {
            e.getPlayer().teleport(Spawn.pegarSpawnLoc(), PlayerTeleportEvent.TeleportCause.PLUGIN);
            e.setCancelled(true);
        }
    }

}
