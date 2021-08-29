package aero.system.comandos;

import aero.system.config.ConfigPrincipal;
import aero.system.utilidades.MetodosSimples;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import static aero.system.System.*;
public class SetWarp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
        if(!(s instanceof Player)) return false;
        Player p = (Player) s;

        // Quando o player nao tem permissao!
        if(!p.hasPermission("System.setwarp")){
            MetodosSimples.enviarMSGeSom(p, ConfigPrincipal.sem_permissao, Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }

        //metodo de entrada
        if(args.length == 1){
            try {
                //APLICANDO TODAS AS CONFIG DA WARP NA WARPS.YML
                FileConfiguration warplocation = getSystem().getWarpLocations();
                Location local = p.getLocation();
                String warpname = args[0].toLowerCase();
                warplocation.set("warps." + warpname + ".world", local.getWorld().getName());
                warplocation.set("warps." + warpname + ".x", local.getX());
                warplocation.set("warps." + warpname + ".y", local.getY());
                warplocation.set("warps." + warpname + ".z", local.getZ());
                warplocation.set("warps." + warpname + ".pitch", local.getPitch());
                warplocation.set("warps." + warpname + ".yaw", local.getYaw());
                warplocation.set("warps." + warpname + ".display-name", args[0]);
                warplocation.set("warps." + warpname + ".title", true);
                warplocation.set("warps." + warpname + ".title", "&e&l" + args[0]);
                warplocation.set("warps." + warpname + ".sub-title", "&7<arrume essa menssagem na config>");
                warplocation.set("warps." + warpname + ".teleport-sound", "minecraft:entity.player.levelup");
                warplocation.set("warps." + warpname + ".warp-perm", "System.warp." +warpname);
                getSystem().salvarWarpLocations();
                // AVISANDO O PLAYER QUE FOI UM SUCESSO CRIAR A WARP NOVA
                MetodosSimples.enviarMSGeSom(p, ConfigPrincipal.warp_criada
                        .replace("%warp%", args[0]), Sound.ENTITY_VILLAGER_YES, 1);
                return true;
            }catch (Exception error){ // CASO DE ERRO NA HORA DE CRIAR A WARP NOVA
                MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.nao_foi_possivel_criar_warp,Sound.ENTITY_VILLAGER_NO,1);
                return false;
            }
        }else  // CASO ARGUMENTO ARG[0] NAO SEJA PASSADO!
            MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.como_usar_setwarp,Sound.ENTITY_VILLAGER_NO,1);
        return false;
    }
}
