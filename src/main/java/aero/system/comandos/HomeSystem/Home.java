package aero.system.comandos.HomeSystem;

import aero.system.config.ConfigPrincipal;
import aero.system.utilidades.MetodosSimples;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static aero.system.System.getSystem;

public class Home implements CommandExecutor {

    private HashMap<Player,Long> cd = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
        if(!(s instanceof Player)) return false;
        Player p = (Player) s;
        UUID playerID = p.getUniqueId();

        if(!p.hasPermission("System.home")){
            MetodosSimples.enviarMSGeSom(p, ConfigPrincipal.sem_permissao, Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }
        if(cd.containsKey(p) && !(java.lang.System.currentTimeMillis() >= cd.get(p))){
            MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.menssagen_de_cooldown.replace("%cd%",""+pegarCooldown(p))
                    ,Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }else cd.remove(p);
        // COLOCA CD OU SKIPA
        if((!p.hasPermission("System.skipcd")))
            cd.put(p,java.lang.System.currentTimeMillis()
                    + TimeUnit.SECONDS.toMillis(getSystem().getConfig().getInt("Comandos-config.cooldown-global")));

        int quantidade_de_homes_do_player = getSystem().getPlayerData().getConfigurationSection(playerID + ".homes").getKeys(false).size();
        if(quantidade_de_homes_do_player == 0){
            MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.listhome_sem_homes,Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }

        if(args.length >= 1) {
            FileConfiguration config = getSystem().getPlayerData();
            ConfigurationSection verificar_se_existe =
                    config.getConfigurationSection(playerID + ".homes." + args[0].toLowerCase());
            if(verificar_se_existe == null){
                MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.home_nao_existe.replace("%home%",""+args[0])
                        ,Sound.ENTITY_VILLAGER_NO,1);
                return false;
            }
            Location homeLocation = config.getLocation(playerID + ".homes." + args[0].toLowerCase() + ".location");

            if(!p.hasPermission("System.skipdelay")){
                if(p.hasPermission("System.vipdelay")){
                    MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.home_teleporte_msg
                            .replace("%cd%",""+getSystem().getConfig().getInt("Comandos-config.home-vipdelay")
                            ), Sound.ENTITY_PLAYER_LEVELUP, 1);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(getSystem(),() -> TeleportarParaHome(p,homeLocation,args),
                            20L*getSystem().getConfig().getInt("Comandos-config.home-vipdelay"));
                }else{
                    MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.home_teleporte_msg
                            .replace("%cd%",""+getSystem().getConfig().getInt("Comandos-config.home-delay")
                            ), Sound.ENTITY_PLAYER_LEVELUP, 1);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(getSystem(),() -> TeleportarParaHome(p,homeLocation,args),
                            20L*getSystem().getConfig().getInt("Comandos-config.home-delay"));
                }
            }else{
                TeleportarParaHome(p,homeLocation,args);
            }
            return true;
        }else{
            MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.home_argumento_errado,Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }
    }

    private void TeleportarParaHome(Player p,Location homeLocation,String[] args){
        p.teleport(homeLocation);
        MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.home_teleporte_sucesso.replace("%home%",""+args[0])
                ,Sound.ENTITY_PLAYER_LEVELUP,1);
    }

    // PEGA O COOLDOWN E RETORNA EM SEGUNDOS
    private Long pegarCooldown(Player p){
        long temp = java.lang.System.currentTimeMillis() - cd.get(p);
        return 1+TimeUnit.MILLISECONDS.toSeconds(temp) * -1;
    }

}
