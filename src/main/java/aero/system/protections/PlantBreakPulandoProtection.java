package aero.system.protections;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class PlantBreakPulandoProtection implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void aoQuebrarPlantacao(EntityChangeBlockEvent e) {
        if (e.getBlock().getType() == Material.FARMLAND)
            e.setCancelled(true);
    }

}
