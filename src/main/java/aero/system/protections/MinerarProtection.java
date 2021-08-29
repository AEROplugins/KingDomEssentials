package aero.system.protections;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class MinerarProtection implements Listener {

    private HashMap<Player,Long> antiSpam = new HashMap<>();
    private final World minerar = Bukkit.getWorld("Minerar");

    @EventHandler
    public void aoColocarBloco(BlockPlaceEvent e){
        World BlockPlaceWorld = e.getBlock().getWorld();
        if(BlockPlaceWorld == minerar){
            Player player = e.getPlayer();
            if(antiSpam.containsKey(player) && !(System.currentTimeMillis() >= antiSpam.get(player))){
                return;
            }else antiSpam.remove(player);
            antiSpam.put(e.getPlayer(),System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(5));

            e.getPlayer().sendMessage(" §8§l* §cQualquer construção nesse mundo sera PERDIDO!");
        }
    }

}
