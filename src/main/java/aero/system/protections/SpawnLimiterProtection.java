package aero.system.protections;

import aero.system.config.ConfigPrincipal;
import aero.system.utilidades.MetodosSimples;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SpawnLimiterProtection implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void checkChunk(BlockPlaceEvent event){
        Material material = event.getBlock().getType();

        if(material == Material.SPAWNER){
            Chunk chunk = event.getBlock().getChunk();
            int spawners = Arrays.stream(chunk.getTileEntities()).filter(b -> b.getType() == Material.SPAWNER).collect(Collectors.toList()).size();
            if(spawners >= 3){
                event.setCancelled(true);
                Player p = event.getPlayer();
                MetodosSimples.enviarMSGeSom(p, ConfigPrincipal.spawner_chunk_limit_msg, Sound.ENTITY_VILLAGER_NO,1);
            }

        }

    }

}
