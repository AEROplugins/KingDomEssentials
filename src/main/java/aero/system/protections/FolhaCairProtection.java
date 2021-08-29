package aero.system.protections;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;

public class FolhaCairProtection implements Listener {

    @EventHandler(priority = EventPriority.HIGH,ignoreCancelled = true)
    public void aoCairFolha(LeavesDecayEvent event){
        event.setCancelled(true);
    }

}
