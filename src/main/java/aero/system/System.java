package aero.system;

import aero.system.comandos.*;
import aero.system.comandos.HomeSystem.DelHome;
import aero.system.comandos.HomeSystem.Home;
import aero.system.comandos.HomeSystem.Listhome;
import aero.system.comandos.HomeSystem.SetHome;
import aero.system.config.*;
import aero.system.eventos.EntrarSairEventos;
import aero.system.eventos.NPCgui;
import aero.system.eventos.TituloDeBoasVindas;
import aero.system.protections.*;
import aero.system.utilidades.CooldownManager;
import aero.system.utilidades.criadordegui.menu.InventoryListener;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class System extends JavaPlugin {
    private CooldownManager cooldownManager;
    private static Chat chat = null;
    private static Permission perms = null;
    private static Economy econ = null;
    public static boolean gui_protection = false;
    private static System system;
    private PlayerData playerData;
    private Kits kits;
    private WarpLocations warpLocations;
    private Locations locations;
    private CooldownManagerConfig cooldownManagerConfig;
    @Override
    public void onEnable() {
        system = this;
        carregarConfig();
        cooldownManager = new CooldownManager();
        cooldownManagerConfig = new CooldownManagerConfig();
        PegarCooldownArmazenado();
        warpLocations = new WarpLocations();
        playerData = new PlayerData();
        locations = new Locations();
        kits = new Kits();
        ConfigPrincipal.carregarDadosConfig();
        carregarVaultDependencias();
        carregarComandos();
        carregarEventos();
        carregarStartMSG();
    }

    @Override
    public void onDisable() {
        SalvarCooldown();
    }

    public static System getSystem() {
        return system;
    }
    // CARREGA A CONFIG PRINCIPAL DO SYSTEM
    private void carregarConfig(){
        getConfig().options().copyDefaults(false);
        saveDefaultConfig();
    }
    private void carregarVaultDependencias(){
        try {
            setupEconomy();
            setupPermissions();
            setupChat();
            Bukkit.getConsoleSender().sendMessage("§9[Essentials] Dependencias VAULT carregadas com sucesso!");
        }catch (Exception error){
            Bukkit.getConsoleSender().sendMessage("§c[Essentials] Erro ao carregar as Dependencias VAULT!");
        }
    }
    public FileConfiguration getWarpLocations(){
        return warpLocations.getConfig();
    }
    public FileConfiguration getLocations() { return locations.getConfig(); }
    public FileConfiguration getPlayerData(){
        return playerData.getConfig();
    }
    public FileConfiguration getKits() { return kits.getConfig(); }
    public FileConfiguration getCooldownManagerConfig() { return this.cooldownManagerConfig.getConfig(); }
    public void salvarPlayerData() {
        playerData.saveConfig();
    }
    public void salvarCooldownManagerConfig() { this.cooldownManagerConfig.saveConfig(); }
    public void salvarLocations() { locations.saveConfig(); }
    public void salvarWarpLocations(){
        warpLocations.saveConfig();
    }
    public void salvarKits() { kits.saveConfig(); }
    // Responsavel por carregar os principais eventos do servidor
    private void carregarEventos(){
        Bukkit.getPluginManager().registerEvents(new EntrarSairEventos(),this);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(),this);
        Bukkit.getPluginManager().registerEvents(new NPCgui(),this);
        Bukkit.getPluginManager().registerEvents(new VoidProtection(), this);
        Bukkit.getPluginManager().registerEvents(new NameTagProtection(), this);
        Bukkit.getPluginManager().registerEvents(new PassarDaBordaProtection(), this);
        Bukkit.getPluginManager().registerEvents(new PlantBreakPulandoProtection(), this);
        Bukkit.getPluginManager().registerEvents(new NetherTetoProtection(),this);
        Bukkit.getPluginManager().registerEvents(new MinerarProtection(),this);
        Bukkit.getPluginManager().registerEvents(new SpawnLimiterProtection(),this);
        Bukkit.getPluginManager().registerEvents(new AntiFogoProtection(),this);
        Bukkit.getPluginManager().registerEvents(new FolhaCairProtection(), this);
        Bukkit.getPluginManager().registerEvents(new TituloDeBoasVindas(),this);
    }
    private void carregarComandos(){
        getCommand("setspawn").setExecutor(new setSpawn());
        getCommand("spawn").setExecutor(new Spawn());
        getCommand("setwarp").setExecutor(new SetWarp());
        getCommand("warp").setExecutor(new Warp());
        getCommand("system").setExecutor(new aero.system.comandos.System());
        getCommand("menu").setExecutor(new Menu());
        getCommand("painel").setExecutor(new PainelStaff());
        getCommand("enderchest").setExecutor(new EnderChest());
        getCommand("sethome").setExecutor(new SetHome());
        getCommand("listhome").setExecutor(new Listhome());
        getCommand("delhome").setExecutor(new DelHome());
        getCommand("home").setExecutor(new Home());
        getCommand("kit").setExecutor(new Kit());
        getCommand("verkit").setExecutor(new VerKit());
    }

    private void SalvarCooldown(){
        FileConfiguration config = getCooldownManagerConfig();
        List<String> cooldownInfo = new ArrayList<>();
        cooldownManager.getPlayerCooldownRemanecente().forEach((identificador,cooldown) -> {
            cooldownInfo.add(identificador+"/"+cooldown);
        });
        config.set("Cooldowns.kit",cooldownInfo);
        salvarCooldownManagerConfig();
    }

    private void PegarCooldownArmazenado(){
        FileConfiguration config = getCooldownManagerConfig();
        List<String> stringPatch = config.getStringList("Cooldowns.kit");
        for(String string : stringPatch){
            try {
                String[] index = string.split("/");
                cooldownManager.addPlayerCooldownRemanecente(index[0], Long.parseLong(index[1]));
            }catch (Exception ignore){
            }
        }
    }

    public CooldownManager getCooldownManager(){
        return this.cooldownManager;
    }

    private void carregarStartMSG(){
        getLogger().info(ChatColor.BLUE+"\n╭╮╭━╮╱╱╱╱╱╱╭━━━╮╱╱╱╱╱╭━━━╮╱╱╭━━━╮╱╱╱╱╱╱╭╮\n" +
                "┃┃┃╭╯╱╱╱╱╱╱╰╮╭╮┃╱╱╱╱╱┃╭━╮┃╱╱┃╭━╮┃╱╱╱╱╱╭╯╰╮\n" +
                "┃╰╯╯╭┳━╮╭━━╮┃┃┃┣━━┳╮╭┫╰━╯┣━━┫╰━━┳╮╱╭┳━┻╮╭╋━━┳╮╭╮\n" +
                "┃╭╮┃┣┫╭╮┫╭╮┃┃┃┃┃╭╮┃╰╯┃╭╮╭┫╭╮┣━━╮┃┃╱┃┃━━┫┃┃┃━┫╰╯┃\n" +
                "┃┃┃╰┫┃┃┃┃╰╯┣╯╰╯┃╰╯┃┃┃┃┃┃╰┫╰╯┃╰━╯┃╰━╯┣━━┃╰┫┃━┫┃┃┃\n" +
                "╰╯╰━┻┻╯╰┻━╮┣━━━┻━━┻┻┻┻╯╰━┻━━┻━━━┻━╮╭┻━━┻━┻━━┻┻┻╯\n" +
                "╱╱╱╱╱╱╱╱╭━╯┃╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╭━╯┃\n" +
                "╱╱╱╱╱╱╱╱╰━━╯╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╰━━╯");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    public static Permission getPerms() {
        return perms;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static Chat getChat() {
        return chat;
    }
}
