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
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static aero.system.System.getSystem;

public class Listhome implements CommandExecutor {

    private HashMap<Player,Long> cd = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
        if(!(s instanceof Player)) return false;
        Player p = (Player) s;
        UUID playerID = p.getUniqueId();
        // Quando o player nao tem permissao!
        if(!p.hasPermission("System.listhome")){
            MetodosSimples.enviarMSGeSom(p, ConfigPrincipal.sem_permissao, Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }

        // FUNCAO QUE VERIFICA O COOLDOWN DO COMANDO!
        if(cd.containsKey(p) && !(java.lang.System.currentTimeMillis() >= cd.get(p))){
            MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.menssagen_de_cooldown.replace("%cd%",""+pegarCooldown(p))
                    ,Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }else cd.remove(p);

        if((!p.hasPermission("System.skipcd")))
            cd.put(p,java.lang.System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(50));

        int quantidade_de_homes_do_player = Objects.requireNonNull(getSystem().getPlayerData().getConfigurationSection(playerID + ".homes")).getKeys(false).size();
        if(quantidade_de_homes_do_player == 0){
            MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.listhome_sem_homes,Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }

        FileConfiguration config = getSystem().getPlayerData();
        ConfigurationSection homes = config.getConfigurationSection(playerID + ".homes");
        if(homes == null) return false;
        p.sendMessage("");
        MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.listhome_mostrando_lista,Sound.ENTITY_PLAYER_LEVELUP,1);
        p.sendMessage("§2"+homes.getKeys(false).toString());
        p.sendMessage("");
        return false;
    }

    // PEGA O COOLDOWN E RETORNA EM SEGUNDOS
    private Long pegarCooldown(Player p){
        long temp = java.lang.System.currentTimeMillis() - cd.get(p);
        return 1+ TimeUnit.MILLISECONDS.toSeconds(temp) * -1;
    }
}
