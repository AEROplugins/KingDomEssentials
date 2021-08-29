package aero.system.comandos;

import aero.system.System;
import aero.system.config.ConfigPrincipal;
import aero.system.utilidades.MetodosSimples;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


import static aero.system.System.*;


// ESTE COMANDO SETA UM SPAWN PRINCIPAL DO SERVIDOR!
public class setSpawn implements CommandExecutor {

    private FileConfiguration locations = getSystem().getLocations();

    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
        if(!(s instanceof Player)) return false;
        Player p = (Player) s;

        // CASO O PLAYER NAO TENHA PERMISSAO!
        if(!p.hasPermission("System.setspawn")){
            MetodosSimples.enviarMSGeSom(p, ConfigPrincipal.sem_permissao,Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }
        try {
            locations.set("Lugar-Dos-Spawns.spawn.world", p.getWorld().getName());
            locations.set("Lugar-Dos-Spawns.spawn.x", p.getLocation().getX());
            locations.set("Lugar-Dos-Spawns.spawn.y", p.getLocation().getY());
            locations.set("Lugar-Dos-Spawns.spawn.z", p.getLocation().getZ());
            locations.set("Lugar-Dos-Spawns.spawn.pitch", p.getLocation().getPitch());
            locations.set("Lugar-Dos-Spawns.spawn.yaw", p.getLocation().getYaw());
            getSystem().salvarLocations();
            MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.sucesso_ao_criar_o_spawn,Sound.ENTITY_BLAZE_HURT,1);
            return true;
        }catch (Exception error){
            MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.erro_ao_fazer_o_comando,Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }
    }
}
