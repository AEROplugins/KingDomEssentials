package aero.system.comandos;

import aero.system.config.ConfigPrincipal;
import aero.system.guis.WarpGui;
import aero.system.utilidades.MetodosSimples;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static aero.system.System.*;
public class Warp implements CommandExecutor {
    private static final FileConfiguration getWarp = getSystem().getWarpLocations(); // ACESSO DA WARPS.YML
    public final HashMap<Player,Long> cd = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
        if(!(s instanceof Player)) return false;
        Player p = (Player) s;

        // Quando o player nao tem permissao!
        if(!p.hasPermission("System.warp.usar")){
            MetodosSimples.enviarMSGeSom(p, ConfigPrincipal.sem_perm_warp, Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }
        // METODO QUE VERIFICA SE O PLAYER TA EM COOLDOWN
        if(cd.containsKey(p) && !(java.lang.System.currentTimeMillis() >= cd.get(p))){
            MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.menssagen_de_cooldown.replace("%cd%",""+pegarCooldown(p))
                    ,Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }else cd.remove(p);


        // METODO QUE ACIONA O COOLDOWN TODA VEZ QUE TELEPORTWARP E DISPARADO;
        if((!p.hasPermission("System.skipcd")))
        cd.put(p,java.lang.System.currentTimeMillis()
                + TimeUnit.SECONDS.toMillis(getSystem().getConfig().getInt("Warps-configs.cooldown")));

        // ABRE O MENU GUI COM LISTA DE WARPS CASO O PLAYER NAO DIGITE ARGUMENTOS
        if(args.length == 0){
            new WarpGui().open(p);
            gui_protection = true;
            MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.ao_abrir_menu_de_warps,Sound.ENTITY_PLAYER_LEVELUP,1);
            // ABRIR MENU GUI
            return false;
        }
        // CASO O ARGUMENTO 1 NAO SEJA NULO ELE VAI FAZER ISSO!
        try {
            // VERIFICA SE O PLAYER TEM PERMISSAO DAQUELA WARP
            if (args[0] != null && p.hasPermission(Objects.requireNonNull(getWarp.getString("warps." + args[0].toLowerCase() + ".warp-perm")))) {

                // caso o player escreva o comando com 2 argumentos e tenha a permissao de mover os outros
                if (args.length == 2 && p.hasPermission("System.warptp.outros")) {
                    return false;
                }
                // VERIFICA SE O JOGADOR PODE PULAR O DELAY
                if(!p.hasPermission("System.skipdelay")){
                    // CASO O JOGADOR NAO SEJA VIP TERA O DELAY NORMAL
                    if(!p.hasPermission("System.vipdelay")){
                        MetodosSimples.enviarMSGeSom(p, ConfigPrincipal.warps_delayteleport_msg, Sound.ENTITY_PLAYER_LEVELUP, 1);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(getSystem(),() -> {
                            teleportWarp(p, args[0].toLowerCase());
                        },20L*getSystem().getConfig().getInt("Warps-configs.delay"));

                    }else{ // CASO O JOGADOR SEJA VIP TERA DELAY MENOR
                        MetodosSimples.enviarMSGeSom(p, ConfigPrincipal.warps_vipdelayteleport_msg, Sound.ENTITY_PLAYER_LEVELUP, 1);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(getSystem(),() -> {
                            teleportWarp(p, args[0].toLowerCase());
                        },20L*getSystem().getConfig().getInt("Warps-configs.vip-delay"));

                    }
                }else {
                    // EXECUTANDO SEM DELAY QUANDO O JOGADOR TEM A PERMISSAO DE SKIPAR DELAY
                    teleportWarp(p, args[0].toLowerCase());
                    return true;
                }
            }else{ // CASO O JOGADOR NAO TENHO PERMISSAO PARA AQUELA WARP!
                MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.sem_perm_para_ir_a_warp,Sound.ENTITY_VILLAGER_NO,1);
                return false;
            }
        }catch (Exception ignored){ // CASO A WARP NAO EXISTA OU O ARGUMENTO SEJA NULO
            p.sendMessage(ConfigPrincipal.warp_nao_existe.replace("%warp%",args[0]));
            new WarpGui().open(p);
            gui_protection = true;
            MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.ao_abrir_menu_de_warps,Sound.ENTITY_PLAYER_LEVELUP,1);
        }

        return true;
    }

    // PEGA A LOCALIZACAO DA WARP
    public static Location pegarLocalWarp(String warpname){
        World world;
        double x,y,z;
        float pitch,yaw;
        x = getWarp.getDouble("warps."+warpname+".x");
        y = getWarp.getDouble("warps."+warpname+".y");
        z = getWarp.getDouble("warps."+warpname+".z");
        pitch = (float) getWarp.getDouble("warps."+warpname+".pitch");
        yaw = (float) getWarp.getDouble("warps."+warpname+".yaw");
        world = Bukkit.getWorld(Objects.requireNonNull(getWarp.getString("warps." + warpname + ".world")));
        return new Location(world,x,y,z,yaw,pitch);
    }
    // METODO QUE TELEPORTA O PLAYER PRA WARP E MANDA SOM,TITLE E MSG
    public static void teleportWarp(Player p,String warp){
        try {
            // CASO A WARP SEJA VALIDA (REALMENTE EXISTA)
            p.getWorld().spawnParticle(Particle.DRAGON_BREATH,p.getLocation(),250);
            p.teleport(pegarLocalWarp(warp));
            p.sendMessage(ConfigPrincipal.warp_teleport_msg
                    .replace("%warp%", Objects.requireNonNull(getWarp.getString("warps." + warp + ".display-name"))
                            .replace("&","§")));
            p.playSound(p.getLocation(), Objects.requireNonNull(getWarp.getString("warps." + warp + ".teleport-sound")),10,10);
            p.spawnParticle(Particle.REVERSE_PORTAL,p.getLocation(),100);
            p.sendTitle(Objects.requireNonNull(getWarp.getString("warps." + warp + ".title")).replace("&","§"),
                    Objects.requireNonNull(getWarp.getString("warps." + warp + ".sub-title")).replace("&","§"),
                    40, 40, 40);
        }catch (Exception error){ // caso a warp nao exista
            MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.warp_nao_existe.replace("%warp%",warp),
                    Sound.ENTITY_VILLAGER_NO,1);
        }
    }

    private Long pegarCooldown(Player p){
        long temp = java.lang.System.currentTimeMillis() - cd.get(p);
        return 1+TimeUnit.MILLISECONDS.toSeconds(temp) * -1;
    }

}
