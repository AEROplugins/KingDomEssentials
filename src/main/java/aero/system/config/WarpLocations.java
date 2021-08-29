package aero.system.config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import static aero.system.System.*;
import java.io.File;

public class WarpLocations {

    private File file;
    private FileConfiguration fileConfiguration;

    public WarpLocations(){
        file = new File(getSystem().getDataFolder(),"warps.yml");
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        if(!file.exists()){
            try {
                file.createNewFile();
                Bukkit.getConsoleSender().sendMessage("§2[Essentials] Sucesso ao Criar warps.yml!");
            }catch (Exception error){
                Bukkit.getConsoleSender().sendMessage("§c[Essentials] Erro ao Criar warps.yml!");
            }
        }else Bukkit.getConsoleSender().sendMessage("§9[Essentials] warps.yml encontrada... carregando warps..");
    }

    public FileConfiguration getConfig() {
        return fileConfiguration;
    }

    public void saveConfig(){
        try {
            getConfig().save(file);
            Bukkit.getConsoleSender().sendMessage("§2[Essentials] Sucesso ao Salvar warps.yml!");
        }catch (Exception error){
            Bukkit.getConsoleSender().sendMessage("§c[Essentials] Erro ao Salvar warps.yml!");
        }
    }


}
