package aero.system.comandos;

import aero.system.config.ConfigPrincipal;
import aero.system.guis.PainelMenu;
import aero.system.utilidades.MetodosSimples;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PainelStaff implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
        if(!(s instanceof Player)) return false;
        Player p = (Player) s;

        if(!p.hasPermission("System.spawn")){
            MetodosSimples.enviarMSGeSom(p, ConfigPrincipal.sem_permissao, Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }

        new PainelMenu().open(p);


        return true;
    }
}
