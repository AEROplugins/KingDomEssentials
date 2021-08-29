package aero.system.config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.concurrent.ExecutionException;

import static aero.system.System.*;
public class Locations {

    private File file;
    private FileConfiguration fileConfiguration;

    public Locations(){
        file = new File(getSystem().getDataFolder(),"locations.yml");
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        if(!file.exists()){
            try{
                file.createNewFile();
                Bukkit.getConsoleSender().sendMessage("&2[Essentias] Sucesso ao criar locations.yml");
            }catch (Exception error){
                Bukkit.getConsoleSender().sendMessage("&c[Essentias] Erro ao criar locations.yml");
            }
        }else Bukkit.getConsoleSender().sendMessage("§9[Essentials] locations.yml encontrada... carregando locations..");
    }

    public FileConfiguration getConfig(){
        return this.fileConfiguration;
    }

    public void saveConfig(){
        try {
            getConfig().save(file);
            Bukkit.getConsoleSender().sendMessage("&2[Essentials] Sucesso ao salvar locations.yml");
        }catch (Exception error){
            Bukkit.getConsoleSender().sendMessage("&c[Essentials] Erro ao salvar locations.yml");
        }
    }

}
