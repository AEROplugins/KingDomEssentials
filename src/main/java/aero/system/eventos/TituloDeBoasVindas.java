package aero.system.eventos;

import aero.system.config.ConfigPrincipal;
import aero.system.utilidades.MetodosSimples;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TituloDeBoasVindas implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void aoEntrar(PlayerJoinEvent event){
        MetodosSimples.enviarTitleESom(event.getPlayer(), ConfigPrincipal.boas_vindas_title,ConfigPrincipal.boas_vindas_subtitle,
                Sound.ENTITY_PLAYER_LEVELUP,1);
    }

}
