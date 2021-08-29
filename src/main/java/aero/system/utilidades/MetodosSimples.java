package aero.system.utilidades;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import static aero.system.System.*;
public class MetodosSimples {

    public static void spawnarParticula(Player p, Particle particle,int quantidade){
        p.spawnParticle(particle,p.getLocation(),quantidade);
    }
    public static void tocarSom(Player p, Sound sound, float volume){
        p.playSound(p.getLocation(),sound,volume,volume);
    }
    public static void enviarMSGeSom(Player p,String msg,Sound sound,float volume){
        p.sendMessage(msg);
        tocarSom(p,sound,volume);
    }
    public static void enviarTitleESom(Player p,String title,String subtitle,Sound sound,float volume){
        title = title.replace("&","§");
        subtitle = subtitle.replace("&","§");
        p.sendTitle(title,subtitle,40,40,40);
        tocarSom(p,sound,volume);
    }
    public static void consoleLog(String msg){
        msg = "&9[Essentials] "+msg;
        Bukkit.getConsoleSender().sendMessage(msg.replace("&","§"));
    }
    public static World pegarMundoPorNome(String nome){
        return Bukkit.getWorld(nome);
    }

}
