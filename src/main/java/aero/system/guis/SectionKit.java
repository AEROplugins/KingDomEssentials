package aero.system.guis;

import aero.system.utilidades.criadordegui.menu.Menu;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

import static aero.system.System.*;
public class SectionKit extends Menu {

    private HashMap<ItemStack,Integer> itemMap = new HashMap<>();

    public SectionKit(String Section) {
        super("Section Menu Teste SectionAtual -> ("+Section+")",3);
    }


    @Override
    public void handleClickAction(InventoryClickEvent event) {

    }


    private void CarregarKits(){
        FileConfiguration config = getSystem().getSectionKit();

    }

    private void CarregarKitSkullks(String itemString,int Amount){

    }


    @Override
    public void setContents() {

    }
}
