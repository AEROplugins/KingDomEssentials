package aero.system.comandos;

import aero.system.System;
import aero.system.guis.KitViewMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VerKit implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
        if(!(s instanceof Player)) return false;
        Player p = (Player) s;

        if(args.length == 0){
            p.sendMessage("Precisa digitar o nome do kit que voce quer ver");
            return false;
        }
        String kit = args[0].toLowerCase();
        new KitViewMenu(kit,p).open(p);
        System.gui_protection = true;
        return false;
    }
}
