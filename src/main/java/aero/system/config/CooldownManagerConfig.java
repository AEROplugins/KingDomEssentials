package aero.system.config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

import static aero.system.System.getSystem;

public class CooldownManagerConfig {
    private File file;
    private FileConfiguration fileConfiguration;

    public CooldownManagerConfig(){
        file = new File(getSystem().getDataFolder(),"CooldownManagerConfig.yml");
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        if(!file.exists()){
            try {
                file.createNewFile();
                getConfig().createSection("Cooldowns");
                saveConfig();
                Bukkit.getConsoleSender().sendMessage("§2[Essentials] Sucesso ao Criar CooldownManagerConfig.yml!");
            }catch (Exception error){
                Bukkit.getConsoleSender().sendMessage("§c[Essentials] Erro ao Criar CooldownManagerConfig.yml!");
            }
        }else Bukkit.getConsoleSender().sendMessage("§9[Essentials] CooldownManagerConfig.yml encontrada... carregando warps..");
    }

    public FileConfiguration getConfig() {
        return fileConfiguration;
    }

    public void saveConfig(){
        try {
            getConfig().save(file);
            Bukkit.getConsoleSender().sendMessage("§2[Essentials] Sucesso ao Salvar CooldownManagerConfig.yml!");
        }catch (Exception error){
            Bukkit.getConsoleSender().sendMessage("§c[Essentials] Erro ao Salvar CooldownManagerConfig.yml!");
        }
    }


}
