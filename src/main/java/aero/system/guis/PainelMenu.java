package aero.system.guis;

import aero.system.comandos.Warp;
import aero.system.utilidades.criadordegui.menu.Menu;
import aero.system.utilidades.items.ItemBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static aero.system.System.*;
import java.util.Objects;
import java.util.function.Predicate;

public class PainelMenu extends Menu {


    public PainelMenu() {
        super("§c§lPainel da STAFF", 3);
    }

    @Override
    public void setContents() {
        criarItems();
    }

    @Override
    public void handleClickAction(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if(event.getCurrentItem() == null) return;
        if (!event.getCurrentItem().hasItemMeta()) return;
        try{
        switch (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName()){
            case "§e§lCreativo":
                p.setGameMode(GameMode.CREATIVE);
                break;
            case "§e§lSurvival":
                p.setGameMode(GameMode.SURVIVAL);
                break;
            case "§e§lSpectador":
                p.setGameMode(GameMode.SPECTATOR);
                break;
            case "§e§lTeleporte Rapido":
                teleportRapidoWarps();
                break;
            case "§e§lAumentar SPEED":
                    p.setWalkSpeed(p.getFlySpeed()+0.05F);
                    p.setFlySpeed(p.getFlySpeed()+0.05F);
                    p.sendMessage("SPEED AUMENTADO +0.05");
                break;
            case "§e§lDiminuir SPEED":
                p.setWalkSpeed(p.getFlySpeed()-0.05F);
                p.setFlySpeed(p.getFlySpeed()-0.05F);
                p.sendMessage("SPEED DIMINUIDO +0.05");
                break;
            default:
                Warp.teleportWarp(p, event.getCurrentItem().getItemMeta().getDisplayName());
        }
        }catch (Exception ignored){}
    }


    private void criarItems(){
        ItemStack Creativo = new ItemBuilder(Material.GRAY_CONCRETE_POWDER).setDisplayName("§e§lCreativo").build();
        ItemStack Survival = new ItemBuilder(Material.BLUE_CONCRETE_POWDER).setDisplayName("§e§lSurvival").build();
        ItemStack Spectador = new ItemBuilder(Material.RED_CONCRETE_POWDER).setDisplayName("§e§lSpectador").build();
        ItemStack Chuva_Settings = new ItemBuilder(Material.YELLOW_CONCRETE_POWDER).setDisplayName("§e§lDiminuir SPEED").build();
        ItemStack warp_teleport = new ItemBuilder(Material.WHITE_CONCRETE_POWDER).setDisplayName("§e§lTeleporte Rapido").build();
        ItemStack speed_settings = new ItemBuilder(Material.GREEN_CONCRETE_POWDER).setDisplayName("§e§lAumentar SPEED").build();

        inventory.setItem(10,Creativo);
        inventory.setItem(11,Survival);
        inventory.setItem(12,Spectador);
        inventory.setItem(14,warp_teleport);
        inventory.setItem(15,speed_settings);
        inventory.setItem(16,Chuva_Settings);
    }
    private void teleportRapidoWarps(){
        inventory.clear();
        getSystem().getWarpLocations().getConfigurationSection("warps").getKeys(false).forEach(s ->{
            ItemStack mundo = new ItemBuilder(Material.GRASS_BLOCK,1,0).setDisplayName(s).build();
            inventory.addItem(mundo);
        });
    }



}
