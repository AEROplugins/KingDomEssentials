package aero.system.eventos;

import aero.system.comandos.Spawn;
import aero.system.comandos.Warp;
import aero.system.guis.WarpGui;
import aero.system.utilidades.MetodosSimples;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

import java.util.UUID;

import static aero.system.System.*;
public class EntrarSairEventos implements Listener {


    // METODO ACIONADO QUANDO O JOGADOR ENTRA
    @EventHandler (priority = EventPriority.HIGHEST)
    public void aoEntrar(PlayerJoinEvent e){
        Player p = e.getPlayer();
        criarPlayerData(p);
        e.setJoinMessage("");
        // CHAMADA DE METODO PRINCIPAL
        try {
            if(p.getWorld() != Bukkit.getWorld("Homes")) // verifica c pode ou nao teleportar o jogador!
                p.teleport(Spawn.pegarSpawnLoc(), PlayerTeleportEvent.TeleportCause.PLUGIN);
        }catch (Exception ignored){ // CASO O PLUGIN NAO CONSIGA TELEPORTAR O JOGADOR
            MetodosSimples.consoleLog("&cErro ao teleportar player para o spawn");
        }
        // atraso de metodos para sincronizacao com login do player
        Bukkit.getScheduler().scheduleSyncDelayedTask(getSystem(),() -> {
            MetodosSimples.spawnarParticula(p, Particle.FIREWORKS_SPARK,250);
            MetodosSimples.tocarSom(p, Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST,1);
        },35);
    }
    // METODO ACIONADO QUANDO O JOGADOR SAI
    @EventHandler (priority = EventPriority.HIGHEST)
    public void aoSair(PlayerQuitEvent e){
        Player p = e.getPlayer();
        e.setQuitMessage("");
    }
    // quando o player morre
    @EventHandler (priority = EventPriority.HIGHEST)
    public void aoMorrer(PlayerDeathEvent e){
        Player p = e.getEntity();
        e.setDeathMessage(""); // <--- PRECISA SER ARRUMADO!
    }
    // quando o player renasce
    @EventHandler (priority = EventPriority.HIGHEST)
    public void aoRenascer(PlayerRespawnEvent e){
        Player p = e.getPlayer();
        // atraso de metodos para sincronizacao com renascimento do player
        Bukkit.getScheduler().scheduleSyncDelayedTask(getSystem(),() -> {
            MetodosSimples.enviarTitleESom(p,"&4&LQue azar...","&csorte na proxima",Sound.ITEM_TOTEM_USE,3);
            MetodosSimples.spawnarParticula(p,Particle.TOTEM,150);
        }, (long) 0.06);
        // CHAMADA DE METODO PRINCIPAL
        try {
            e.setRespawnLocation(Spawn.pegarSpawnLoc());
        }catch (Exception ignored){ // CASO O PLUGIN NAO CONSIGA TELEPORTAR O JOGADOR
            MetodosSimples.consoleLog("&cErro ao setar spawn de player para o spawn");
        }
    }

    private void criarPlayerData(Player p){
        // Pega o jogador que entrou e cria uma playerdata para ele caso ele nao tenha
        FileConfiguration config = getSystem().getPlayerData();
        if(config.getConfigurationSection(String.valueOf(p.getUniqueId())) == null) {
            UUID player_id = p.getUniqueId();
            config.createSection(player_id + ".nick");
            config.set(player_id + ".nick", p.getName());
            config.createSection(player_id + ".homes");
            getSystem().salvarPlayerData();
            Bukkit.getConsoleSender().sendMessage("§2[Essentials] Player:" + p.getName() + " ID:" + player_id + " Carregado!");
        }else{ // CASO ELE TENHA DADOS JA EXISTENTES COM O ID DELE
            Bukkit.getConsoleSender().sendMessage("§2[Essentials] Player:" + p.getName() + " Ja Existe.. Carregando seus Dados...");
        }
    }



}
