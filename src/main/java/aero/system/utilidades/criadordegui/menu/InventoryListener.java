package aero.system.utilidades.criadordegui.menu;

import aero.system.System;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class InventoryListener implements Listener
{

    @EventHandler
    public void onClick(InventoryClickEvent event)
    {
        Inventory inventory = event.getClickedInventory();

        if (inventory == null)
            return;

        InventoryHolder inventoryHolder = inventory.getHolder();
        if(System.gui_protection) event.setCancelled(true); // METODO DE PROTECAO NAO ESQUECER DE APLICAR EM TUDO!
        if (inventoryHolder instanceof Menu){
            Player p = (Player) event.getWhoClicked();
            Menu menu = (Menu) inventoryHolder;
            event.setCancelled(true);
            menu.handleClickAction(event);
        }

    }
    @EventHandler
    public void aoFechar(InventoryCloseEvent e){
        Player p = (Player) e.getPlayer();
        Inventory inventory = e.getInventory();
        InventoryHolder inventoryHolder = inventory.getHolder();
        if(inventoryHolder instanceof Menu){
            // nada por enquanto;
        }
        System.gui_protection = false;
    }
    @EventHandler
    public void tentativadebloquear(PlayerInteractEvent e){
        if(System.gui_protection) e.setCancelled(true);
    }
}
