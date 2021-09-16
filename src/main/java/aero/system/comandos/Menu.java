package aero.system.comandos;

import aero.system.System;
import aero.system.config.ConfigPrincipal;
import aero.system.guis.MenuServidor;
import aero.system.guis.WarpGui;
import aero.system.utilidades.MetodosSimples;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static aero.system.System.*;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Menu implements CommandExecutor {
    private static Player p;
    HashMap<Player,Long> cd = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
        if(!(s instanceof Player)) return false;
        p = (Player) s;


        if(cd.containsKey(p) && !(java.lang.System.currentTimeMillis() >= cd.get(p))){
            MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.menssagen_de_cooldown.replace("%cd%",""+pegarCD(p))
                    ,Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }else cd.remove(p);

        if(!(p.hasPermission("System.skipcd")))
        cd.put(p, java.lang.System.currentTimeMillis()
                + TimeUnit.SECONDS.toMillis(getSystem().getConfig().getInt("Menu-configs.cooldown")));

        if(p.hasPermission("System.skipdelay")){
            AbrirMenuDoServidor(p);
            return true;
        }
        MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.menu_principal_delay_msg, Sound.ENTITY_PLAYER_LEVELUP,1);
        Bukkit.getScheduler().scheduleSyncDelayedTask(getSystem(),
                () -> AbrirMenuDoServidor(p),20L*getSystem().getConfig().getInt("Warps-configs.delay"));

        return true;

    }

    private void AbrirMenuDoServidor(Player p){
        MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.menu_principal_abrir,Sound.ENTITY_PLAYER_LEVELUP,1);
        new MenuServidor(p).open(p);
        System.gui_protection = true;
    }

    private long pegarCD(Player p){
        long Temp = java.lang.System.currentTimeMillis() - cd.get(p);
        return 1 + TimeUnit.MILLISECONDS.toSeconds(Temp) * -1;
    }

    public static Player getP() {
        return p;
    }
}
