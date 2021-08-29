package aero.system.comandos;

import aero.system.config.ConfigPrincipal;
import aero.system.utilidades.MetodosSimples;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static aero.system.System.*;

public class Spawn implements CommandExecutor {
    private final FileConfiguration config = getSystem().getConfig();
    private static final FileConfiguration locations = getSystem().getLocations();
    private final HashMap<Player,Long> cd = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
        if(!(s instanceof Player)) return false;
        Player p = (Player) s;

        // Quando o player nao tem permissao!
        if(!p.hasPermission("System.spawn")){
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
            cd.put(p,java.lang.System.currentTimeMillis()
                    + TimeUnit.SECONDS.toMillis(getSystem().getConfig().getInt("Configs-Spawn.cooldown")));

            // CASO O PLAYER DIGITE /SPAWN <TARGET> IRA TELEPORTAR UM JOGADOR PARA LA!
            if(args.length == 1 && p.hasPermission("System.teleportaroutros.spawn")){
                try {
                    //pega o alvo e teleporta
                    Player target = Bukkit.getPlayer(args[0]);
                    spawnTeleportMSG(target);
                    // mensagem do staff que teleportou
                    MetodosSimples.enviarMSGeSom(p, ConfigPrincipal.teleportar_outros_para_spawn_msg
                                    .replace("%target%", "" + target.getName()),
                            Sound.ENTITY_VILLAGER_YES, 1);
                    // mensagem do player que foi teleportado
                    MetodosSimples.enviarMSGeSom(target, ConfigPrincipal.teleportar_outros_para_spawn_msg_target
                                    .replace("%player%", "" + p.getName()),
                            Sound.ENTITY_VILLAGER_YES, 1);
                }catch (Exception error){ // CASO NAO ENCONTRE O TARGET
                    MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.target_nao_encontrado,Sound.ENTITY_VILLAGER_NO,1);
                }
                return true;
            }


            // verificador de skip delay
            if (!p.hasPermission("System.skipdelay")) {
                // Verificar c o jogador e vip ou nao (perm de skip delay do vip)
                if(!p.hasPermission("System.vipdelay")) {
                    // METODO COM DELAY DE JOGADORES NORMAIS
                    MetodosSimples.enviarMSGeSom(p, ConfigPrincipal.spawn_delay_menssagen, Sound.ENTITY_PLAYER_LEVELUP, 1);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(getSystem(), () -> {
                        spawnTeleportMSG(p);
                    }, 20L * getSystem().getConfig().getInt("Configs-Spawn.delay"));
                }else{
                    // METODO COM DELAY DE VIPS
                    MetodosSimples.enviarMSGeSom(p, ConfigPrincipal.spawn_vipdelay_menssagen, Sound.ENTITY_PLAYER_LEVELUP, 1);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(getSystem(), () -> {
                        spawnTeleportMSG(p);
                    }, 20L * getSystem().getConfig().getInt("Configs-Spawn.vip-delay"));
                }
            }else{
                // caso o player tenha a permissao para skipar o delay!
                spawnTeleportMSG(p);
            }
        return true;
    }
    // FUNCAO PRINCIPAL QUE PEGA A LOCALIZACAO DO SPAWN DA CONFIG!
    public static Location pegarSpawnLoc(){
        try {
            World world;
            double x, y, z;
            float pitch, yaw;
            world = MetodosSimples.pegarMundoPorNome(locations.getString("Lugar-Dos-Spawns.spawn.world"));
            pitch = (float)locations.getDouble("Lugar-Dos-Spawns.spawn.pitch");
            yaw = (float) locations.getDouble("Lugar-Dos-Spawns.spawn.yaw");
            x = locations.getDouble("Lugar-Dos-Spawns.spawn.x");
            y = locations.getDouble("Lugar-Dos-Spawns.spawn.y");
            z =locations.getDouble("Lugar-Dos-Spawns.spawn.z");
            return new Location(world,x,y,z,yaw,pitch);
        }catch (Exception error){ // CASO O SPAWN NAO ESTEJA CRIADO AINDA IRA DAR ESTE ERRO!
            Bukkit.getConsoleSender().sendMessage("§c[ESSENTIALS] O SPAWN NAO FOI CRIADO AINDA!!!!");
            Bukkit.getConsoleSender().sendMessage("§c[ESSENTIALS] O SPAWN NAO FOI CRIADO AINDA!!!!");
            return null;
        }

    }
    // Envia a msg de teleporte do spawn
   public static void spawnTeleportMSG(Player p){
       try {
           p.teleport(pegarSpawnLoc());
           p.spawnParticle(Particle.REVERSE_PORTAL,p.getLocation(),100);
           MetodosSimples.enviarTitleESom(p, ConfigPrincipal.title_spawn_teleport,
                   ConfigPrincipal.subtitle_spawn_teleport, Sound.ENTITY_PLAYER_LEVELUP, 1);
       }catch (Exception error){ // caso o SPAWN nao tenha sido setado ainda!
           p.sendMessage("§c§lNao foi possivel encontrar o Spawn!");
           MetodosSimples.tocarSom(p,Sound.ENTITY_BLAZE_DEATH,1);
       }
   }
   // PEGA O COOLDOWN E RETORNA EM SEGUNDOS
   private Long pegarCooldown(Player p){
        long temp = java.lang.System.currentTimeMillis() - cd.get(p);
        return 1+TimeUnit.MILLISECONDS.toSeconds(temp) * -1;
   }

}
