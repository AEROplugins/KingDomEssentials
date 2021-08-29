package aero.system.eventos;

import aero.system.System;
import aero.system.config.ConfigPrincipal;
import aero.system.guis.WarpGui;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class NPCgui implements Listener {

    private static HashMap<Player,Long> cd = new HashMap<>();

    @EventHandler
    public void interagir(PlayerInteractEntityEvent e){
        Player p = e.getPlayer();
        String entity_name = e.getRightClicked().getName();

        if(cd.containsKey(p) && !(java.lang.System.currentTimeMillis() >= cd.get(p))){
            return;
        }else cd.remove(p);

        if(entity_name.equalsIgnoreCase(ConfigPrincipal.npc_warp_name)){
            ReaplicarCDnoNPC(p);
            p.playSound(p.getLocation(), Sound.ENTITY_EVOKER_PREPARE_SUMMON,1,1);
            new WarpGui().open(p);
            aero.system.System.gui_protection = true;
        }
    }

    public static HashMap<Player, Long> getCd() {
        return cd;
    }
    public static void ReaplicarCDnoNPC(Player p){
        cd.put(p,java.lang.System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(4));
    }

}
