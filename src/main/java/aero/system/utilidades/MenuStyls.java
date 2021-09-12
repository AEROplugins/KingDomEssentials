package aero.system.utilidades;

import aero.system.utilidades.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MenuStyls {

    public static void MenuStyle1(Inventory inventory){
        ItemStack azul_escuro = new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE)
                .setDisplayName("*")
                .build();
        ItemStack azul_claro = new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE)
                .setDisplayName("*")
                .build();
        ItemStack chain = new ItemBuilder(Material.CHAIN)
                .setDisplayName("*")
                .build();
        ItemStack end_crystal = new ItemBuilder(Material.END_CRYSTAL)
                .setDisplayName("*")
                .build();
        ItemStack soul_lantern = new ItemBuilder(Material.SOUL_LANTERN)
                .setDisplayName("*")
                .build();
        ItemStack barreira = new ItemBuilder(Material.BARRIER)
                .setDisplayName("exit")
                .build();
        inventory.setItem(0,azul_escuro);
        inventory.setItem(1,azul_claro);
        inventory.setItem(2,end_crystal);
        inventory.setItem(3,chain);
        inventory.setItem(4,soul_lantern);
        inventory.setItem(5,chain);
        inventory.setItem(6,end_crystal);
        inventory.setItem(7,azul_claro);
        inventory.setItem(8,azul_escuro);
        inventory.setItem(9,azul_claro);
        inventory.setItem(10,end_crystal);
        inventory.setItem(16,end_crystal);
        inventory.setItem(17,azul_claro);
        inventory.setItem(18,azul_claro);
        inventory.setItem(19,chain);
        inventory.setItem(25,chain);
        inventory.setItem(26,azul_claro);
        inventory.setItem(27,azul_claro);
        inventory.setItem(28,chain);
        inventory.setItem(34,chain);
        inventory.setItem(35,azul_claro);
        inventory.setItem(36,azul_claro);
        inventory.setItem(37,end_crystal);
        inventory.setItem(43,end_crystal);
        inventory.setItem(44,azul_claro);
        inventory.setItem(45,azul_escuro);
        inventory.setItem(46,azul_claro);
        inventory.setItem(47,end_crystal);
        inventory.setItem(48,chain);
        inventory.setItem(49,barreira);
        inventory.setItem(50,chain);
        inventory.setItem(51,end_crystal);
        inventory.setItem(52,azul_claro);
        inventory.setItem(53,azul_escuro);
    }

}
