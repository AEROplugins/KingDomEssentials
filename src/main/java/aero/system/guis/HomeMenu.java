package aero.system.guis;

import aero.system.comandos.HomeSystem.Home;
import aero.system.utilidades.criadordegui.menu.Menu;
import aero.system.utilidades.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static aero.system.System.*;
public class HomeMenu extends Menu {

    private Player p;

    public HomeMenu(Player p) {
        super("         §9§lLista de Homes      ", 3);
        this.p = p;
    }


    @Override
    public void handleClickAction(InventoryClickEvent event) {
        if(event.getCurrentItem() == null) return;
        if(!event.getCurrentItem().hasItemMeta()) return;

        if(event.getCurrentItem().getType() == Material.GRASS_BLOCK){
            FileConfiguration config = getSystem().getPlayerData();
            Player p = (Player) event.getWhoClicked();
            UUID playerID = p.getUniqueId();
            String block_name = event.getCurrentItem().getItemMeta().getDisplayName();
            block_name = block_name.substring(4);
            Bukkit.dispatchCommand(p,"home "+block_name);
            p.closeInventory();
        }

    }

    @Override
    public void setContents() {
        CarregarBordas();
        CarregarHomes();
    }

    public void CarregarHomes(){
        FileConfiguration config = getSystem().getPlayerData();
        ConfigurationSection section = config.getConfigurationSection(p.getUniqueId()+".homes");
        int quantidade_de_homes = section.getKeys(false).size();
        if(quantidade_de_homes == 0){
            ItemStack sem_homes = new ItemBuilder(Material.BARRIER).setDisplayName("§cVocê ainda não marcou uma Home!").build();
            getInventory().setItem(13,sem_homes);
            return;
        }
        if(quantidade_de_homes > 5){
            ItemStack sem_homes = new ItemBuilder(Material.BARRIER).setDisplayName("§cVoce excedeu o limite de homes!").build();
            getInventory().setItem(13,sem_homes);
            return;
        }

        ItemStack home_vaga = new ItemBuilder(Material.BEDROCK).setDisplayName("§8§l* Slot Vago *").build();

        getInventory().setItem(11,home_vaga);
        getInventory().setItem(12,home_vaga);
        getInventory().setItem(13,home_vaga);
        getInventory().setItem(14,home_vaga);
        getInventory().setItem(15,home_vaga);

        ArrayList<String> home_names = new ArrayList<String>(section.getKeys(false));


        switch (quantidade_de_homes){
            case 5:
                ItemStack home5 = new ItemBuilder(Material.GRASS_BLOCK)
                        .setDisplayName("§e§l"+home_names.get(4))
                        .setLore(setarLore(home_names.get(4)))
                        .build();
                getInventory().setItem(15,home5);
            case 4:
                ItemStack home4 = new ItemBuilder(Material.GRASS_BLOCK)
                        .setDisplayName("§e§l"+home_names.get(3))
                        .setLore(setarLore(home_names.get(3)))
                        .build();
                getInventory().setItem(14,home4);
            case 3:
                ItemStack home3 = new ItemBuilder(Material.GRASS_BLOCK)
                        .setDisplayName("§e§l"+home_names.get(2))
                        .setLore(setarLore(home_names.get(2)))
                        .build();
                getInventory().setItem(13,home3);
            case 2:
                ItemStack home2 = new ItemBuilder(Material.GRASS_BLOCK)
                        .setDisplayName("§e§l"+home_names.get(1))
                        .setLore(setarLore(home_names.get(1)))
                        .build();
                getInventory().setItem(12,home2);
            case 1:
                ItemStack home1 = new ItemBuilder(Material.GRASS_BLOCK)
                        .setDisplayName("§e§l"+home_names.get(0))
                        .setLore(setarLore(home_names.get(0)))
                        .build();
                getInventory().setItem(11,home1);
                break;
        }


    }

    private List<String> setarLore(String homename){
        List<String> formatado = getSystem().getConfig().getStringList("Gui-config.listhomes.homes_lores");
        List<String> formatado_2 = new ArrayList<>();
        formatado.forEach(s -> {
            formatado_2.add(s.replace("%home%",""+homename).replace("&","§"));
        });
        return formatado_2;
    }

    private void CarregarBordas(){
        ItemStack painel_cinza = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                .setDisplayName("*")
                .build();
        ItemStack grade = new ItemBuilder(Material.IRON_BARS)
                .setDisplayName("*")
                .build();
        for(int i=0;i < 9;i++){
            getInventory().setItem(i,painel_cinza);
        }
        for(int i=18;i < 27;i++){
            getInventory().setItem(i,painel_cinza);
        }
        getInventory().setItem(9,grade);
        getInventory().setItem(17,grade);


    }

}
