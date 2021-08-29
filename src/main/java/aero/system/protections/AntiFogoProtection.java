package aero.system.protections;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;

public class AntiFogoProtection implements Listener {

    @EventHandler(priority = EventPriority.LOWEST,ignoreCancelled = true)
    public void aoEspalharFogo(BlockIgniteEvent e){
        if(e.getCause() == BlockIgniteEvent.IgniteCause.LAVA || e.getCause() == BlockIgniteEvent.IgniteCause.SPREAD){
            e.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void aoPegarFogo(BlockBurnEvent e) {
        e.setCancelled(true);
    }

}
