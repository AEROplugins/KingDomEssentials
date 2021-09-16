package aero.system.config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Arrays;

import static aero.system.System.getSystem;

public class SectionKitConfig {

    private File file;
    private FileConfiguration fileConfiguration;

    public SectionKitConfig(){
        file = new File(getSystem().getDataFolder(),"SectionKitConfig.yml");
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        if(!file.exists()){
            try{
                file.createNewFile();
                CarregarExemplo();
                Bukkit.getConsoleSender().sendMessage("§2[Essentias] Sucesso ao criar SectionKitConfig.yml");
            }catch (Exception error){
                Bukkit.getConsoleSender().sendMessage("§c[Essentias] Erro ao criar SectionKitConfig.yml");
            }
        }else Bukkit.getConsoleSender().sendMessage("§9[Essentials] SectionKitConfig.yml encontrada... carregando locations..");
    }

    public FileConfiguration getConfig(){
        return this.fileConfiguration;
    }

    private void CarregarExemplo(){
        getConfig().createSection("configs");
        getConfig().set("configs.exemplo.skulls-configs",Arrays.asList("Zip","Zop"));
        getConfig().set("configs.exemplo.exit-slot",10);
        getConfig().set("configs.exemplo.rows",3);
        saveConfig();
    }

    public void saveConfig(){
        try {
            getConfig().save(file);
            Bukkit.getConsoleSender().sendMessage("§2[Essentials] Sucesso ao salvar SectionKitConfig.yml");
        }catch (Exception error){
            Bukkit.getConsoleSender().sendMessage("§c[Essentials] Erro ao salvar SectionKitConfig.yml");
        }
    }

}