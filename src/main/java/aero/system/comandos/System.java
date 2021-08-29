package aero.system.comandos;

import aero.system.config.ConfigPrincipal;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static aero.system.System.*;
public class System implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {

        if(!(args.length == 1) && !(args[0].equalsIgnoreCase("Reload"))) return false;

        if(s instanceof Player){
            Player p = (Player) s;
            if(!(p.hasPermission("System.reload"))){
                p.sendMessage("§cVocê não pode fazer isso!");
                return false;
            }
            try {
                getSystem().reloadConfig();
                ConfigPrincipal.carregarDadosConfig();
                p.sendMessage("§2[System] Config.yml re-carregada com sucesso!");
                return true;
            }catch (Exception error){
                p.sendMessage("§c[System] Nao foi possivel re-carregar a Config.yml!");
                return false;
            }

        }
        try {
            getSystem().reloadConfig();
            ConfigPrincipal.carregarDadosConfig();
            Bukkit.getConsoleSender().sendMessage("§9[Essentials] Config principal Re-carregada!");
        }catch (Exception error){
            Bukkit.getConsoleSender().sendMessage("§c[Essentials] Erro! ao re-carregar a config principal");
        }
        return true;
    }
}
