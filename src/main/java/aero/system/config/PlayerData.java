package aero.system.config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

import static aero.system.System.getSystem;

public class PlayerData {
    private File file;
    private FileConfiguration fileConfiguration;

    public PlayerData(){
        file = new File(getSystem().getDataFolder(),"playerdata.yml");
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        if(!file.exists()){
            try {
                file.createNewFile();
                Bukkit.getConsoleSender().sendMessage("§2[Essentials] Sucesso ao Criar playerdata.yml!");
            }catch (Exception error){
                Bukkit.getConsoleSender().sendMessage("§c[Essentials] Erro ao Criar playerdata.yml!");
            }
        }else Bukkit.getConsoleSender().sendMessage("§9[Essentials] playerdata.yml encontrada... carregando warps..");
    }

    public FileConfiguration getConfig() {
        return fileConfiguration;
    }

    public void saveConfig(){
        try {
            getConfig().save(file);
            Bukkit.getConsoleSender().sendMessage("§2[Essentials] Sucesso ao Salvar playerdata.yml!");
        }catch (Exception error){
            Bukkit.getConsoleSender().sendMessage("§c[Essentials] Erro ao Salvar playerdata.yml!");
        }
    }


}
