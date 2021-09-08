package aero.system.config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Arrays;

import static aero.system.System.getSystem;

public class Kits {

    private File file;
    private FileConfiguration fileConfiguration;

    public Kits(){
        file = new File(getSystem().getDataFolder(),"kits.yml");
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        if(!file.exists()){
            try{
                file.createNewFile();
                getConfig().createSection("Kits");
                saveConfig();
                criarKitBase();
                Bukkit.getConsoleSender().sendMessage("&2[Essentias] Sucesso ao criar kits.yml");
            }catch (Exception error){
                Bukkit.getConsoleSender().sendMessage("&c[Essentias] Erro ao criar kits.yml");
            }
        }else Bukkit.getConsoleSender().sendMessage("§9[Essentials] kits.yml encontrada... carregando kits..");
    }

    public FileConfiguration getConfig(){
        return this.fileConfiguration;
    }

   private void criarKitBase(){
        getConfig().createSection("Kits.exemplo");
        getConfig().set("Kits.exemplo.display","&cExemplo");
        getConfig().set("Kits.exemplo.cooldown", 30);
        getConfig().set("Kits.exemplo.items", Arrays.asList("STONE;25","1b;1"));
        getConfig().set("Kits.exemplo.utilizavel", 3);
        saveConfig();
   }

    public void saveConfig(){
        try {
            getConfig().save(file);
            Bukkit.getConsoleSender().sendMessage("&2[Essentials] Sucesso ao salvar kits.yml");
        }catch (Exception error){
            Bukkit.getConsoleSender().sendMessage("&c[Essentials] Erro ao salvar kits.yml");
        }
    }

}

