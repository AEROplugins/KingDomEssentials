package aero.system.comandos.HomeSystem;

import aero.system.config.ConfigPrincipal;
import aero.system.utilidades.MetodosSimples;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static aero.system.System.*;
public class SetHome implements CommandExecutor {

    private final World Minerar = Bukkit.getWorld("Minerar");
    private HashMap<Player,Long> cd = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {

        if(!(s instanceof Player)) return false;
        Player p = (Player) s;

        // Quando o player nao tem permissao!
        if(!p.hasPermission("System.sethome")){
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
            cd.put(p,java.lang.System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(30));

        if(p.getWorld() == Minerar){
            MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.proibido_marcar_home,Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }

        // PEGA O ID DO PLAYER E VERIFICA QUANTAS HOMES ELE TEM
        UUID playerID = p.getUniqueId();
        int home_quantity = 0;
        if(getSystem().getPlayerData().getConfigurationSection(playerID+".homes") != null)
            home_quantity = getSystem().getPlayerData().getConfigurationSection(playerID + ".homes").getKeys(false).size();

        // CASO ELE PASSE 1 ARGUMENTO NO CASO O NOME PARA CRIAR A HOME
        if(args.length >= 1){
            Location local = p.getLocation();
            FileConfiguration config = getSystem().getPlayerData();

            // CASO ELE TENHA O MAXIMO DE HOMES AQUI ELE E BARRADO PARA NAO CONSEGUIR CRIAR MAIS HOMES
            int maximo_de_homes_config = getSystem().getConfig().getInt("Comandos-config.max-homes");
            if(home_quantity >= maximo_de_homes_config && !p.hasPermission("System.home.nolimit")){
                MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.max_home_msg,Sound.ENTITY_VILLAGER_NO,1);
                return false;
            }
            String homeNome = args[0].toLowerCase();
            // CASO A HOME JA EXISTA ELE CANCELA O SETHOME E PEDE PRA EXCLUIR E CRIAR NOVAMENTE CASO A PESSOA QUEIRA
            ConfigurationSection verificar_home_existente = config.getConfigurationSection(playerID+".homes."+homeNome);
            if(verificar_home_existente != null){
                MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.sethome_home_ja_existe,Sound.ENTITY_VILLAGER_NO,1);
                return false;
            }

            // SISTEMA DE SETA A HOME NO PLAYERDATA.YML
            try {
                config.createSection(playerID + ".homes." + homeNome);
                config.set(playerID + ".homes." + homeNome + ".nome", homeNome);
                config.set(playerID + ".homes." + homeNome + ".location", local);
                getSystem().salvarPlayerData();
            }catch (Exception error){ // CASO DE ERRADO AO SALVAR NO PLAYERDATA.YML
                MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.sethome_home_criada_erro,Sound.ENTITY_VILLAGER_NO,1);
                return false;
            }
            MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.sethome_home_criada
                    .replace("%home%",""+args[0]),Sound.ENTITY_PLAYER_LEVELUP,1);
            return true; // caso de tudo certo termina aqui..
        }else{
            MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.sethome_sem_argumentos,Sound.ENTITY_VILLAGER_NO,1);
            return false; // Esqueceu de passar argumentos termina aqui
        }
    }
    // PEGA O COOLDOWN E RETORNA EM SEGUNDOS
    private Long pegarCooldown(Player p){
        long temp = java.lang.System.currentTimeMillis() - cd.get(p);
        return 1+ TimeUnit.MILLISECONDS.toSeconds(temp) * -1;
    }
}
