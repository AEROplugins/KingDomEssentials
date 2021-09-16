package aero.system.comandos;

import aero.system.System;
import aero.system.config.ConfigPrincipal;
import aero.system.guis.KitViewMenu;
import aero.system.utilidades.MetodosSimples;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import static aero.system.System.*;
public class VerKit implements CommandExecutor {

    private String section;

    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
        if(!(s instanceof Player)) return false;
        Player p = (Player) s;

        if(args.length == 0){
            MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.verkit_sem_argumento,Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }
        FileConfiguration config = getSystem().getKits();
        String kit = args[0].toLowerCase();
        FileConfiguration sectionConfig = getSystem().getSectionKit();
        if(args.length == 2 && sectionConfig.getConfigurationSection("configs."+args[1]) != null){
            section = args[1];
        }else section = null;
        if(config.getConfigurationSection("Kits."+kit) == null){
            MetodosSimples.enviarMSGeSom(p, ConfigPrincipal.verkit_kit_nao_existe,Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }
        new KitViewMenu(kit,p,section).open(p);
        MetodosSimples.tocarSom(p, Sound.ENTITY_PLAYER_LEVELUP,1);
        System.gui_protection = true;
        return false;
    }
}
