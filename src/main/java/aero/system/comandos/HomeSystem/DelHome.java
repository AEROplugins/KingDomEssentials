package aero.system.comandos.HomeSystem;

import aero.system.config.ConfigPrincipal;
import aero.system.utilidades.MetodosSimples;
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

public class DelHome implements CommandExecutor {

    private HashMap<Player,Long> cd = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
        if(!(s instanceof Player)) return false;
        Player p = (Player) s;
        UUID playerID = p.getUniqueId();

        // PERM DO COMANDO
        if(!p.hasPermission("System.delhome")){
            MetodosSimples.enviarMSGeSom(p, ConfigPrincipal.sem_permissao, Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }

        // FUNCAO QUE VERIFICA O COOLDOWN DO COMANDO!
        if(cd.containsKey(p) && !(java.lang.System.currentTimeMillis() >= cd.get(p))){
            MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.menssagen_de_cooldown.replace("%cd%",""+pegarCooldown(p))
                    ,Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }else cd.remove(p);
        // COLOCA CD OU SKIPA
        if((!p.hasPermission("System.skipcd")))
            cd.put(p,java.lang.System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(50));


        int quantidade_de_homes_do_player = getSystem().getPlayerData().getConfigurationSection(playerID + ".homes").getKeys(false).size();
        if(quantidade_de_homes_do_player == 0){
            MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.delhome_sem_homes,Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }

        FileConfiguration config = getSystem().getPlayerData();
        try {
            ConfigurationSection verificar_se_existe = config.getConfigurationSection(playerID+".homes."+args[0].toLowerCase());

            if(verificar_se_existe == null){
                MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.delhome_home_nao_existe
                        .replace("%home%",""+args[0]),Sound.ENTITY_VILLAGER_NO,1);
                return false;
            }

            config.set(playerID + ".homes." + args[0].toLowerCase(), null);
            getSystem().salvarPlayerData();
            MetodosSimples.enviarMSGeSom(p, ConfigPrincipal.delhome_sucesso.replace("%home%",""+args[0])
                    , Sound.ENTITY_PLAYER_LEVELUP, 1);
            return true;
        }catch (Exception error){
            MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.delhome_erro,Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }
    }
    // PEGA O COOLDOWN E RETORNA EM SEGUNDOS
    private Long pegarCooldown(Player p){
        long temp = java.lang.System.currentTimeMillis() - cd.get(p);
        return 1+ TimeUnit.MILLISECONDS.toSeconds(temp) * -1;
    }

}
