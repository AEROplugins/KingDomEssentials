package aero.system.comandos;

import aero.system.config.ConfigPrincipal;
import aero.system.utilidades.MetodosSimples;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static aero.system.System.*;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class EnderChest implements CommandExecutor {

    private HashMap<Player,Long> cd = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
        if(!(s instanceof Player)) return false;
        Player p = (Player) s;

        if(!p.hasPermission("System.enderchest")){
            MetodosSimples.enviarMSGeSom(p, ConfigPrincipal.sem_permissao, Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }

        if(cd.containsKey(p) && !(java.lang.System.currentTimeMillis() >= cd.get(p))){
            MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.menssagen_de_cooldown.replace("%cd%",""+pegarCD(p))
                    ,Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }else cd.remove(p);


        if(!p.hasPermission("System.skipcd"))
            cd.put(p, java.lang.System.currentTimeMillis()
                    + TimeUnit.SECONDS.toMillis(getSystem().getConfig().getInt("Comandos-config.cooldown-global")));

        switch (args.length){
            case 1:
                if(!p.hasPermission("System.enderchest.outros")){
                    MetodosSimples.enviarMSGeSom(p, ConfigPrincipal.sem_permissao, Sound.ENTITY_VILLAGER_NO,1);
                    return false;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if(target == null){
                    p.sendMessage("§cO §9"+args[0]+"§c Não existe ou não Está online!");
                    return false;
                }
                p.openInventory(target.getEnderChest());
                MetodosSimples.tocarSom(p,Sound.BLOCK_ENDER_CHEST_OPEN,1);
                break;
            default:
                p.openInventory(p.getEnderChest());
                MetodosSimples.tocarSom(p,Sound.BLOCK_ENDER_CHEST_OPEN,1);
                break;
        }
        return true;
    }

    private long pegarCD(Player p){
        long Temp = java.lang.System.currentTimeMillis() - cd.get(p);
        return 1 + TimeUnit.MILLISECONDS.toSeconds(Temp) * -1;
    }

}
