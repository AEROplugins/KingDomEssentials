package aero.system.guis;

import aero.system.utilidades.MenuStyls;
import aero.system.utilidades.MetodosSimples;
import aero.system.utilidades.criadordegui.menu.Menu;
import aero.system.utilidades.items.ItemBuilder;
import aero.system.utilidades.items.skullcreator.SkullCreator;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class KitMenu extends Menu {

    private final Player player;

    public KitMenu(Player player) {
        super("Zimbs menu kkk",6);
        this.player = player;
    }

    @Override
    public void handleClickAction(InventoryClickEvent event) {
        if(event.getCurrentItem() == null) return;
        if(!event.getCurrentItem().hasItemMeta()) return;
        if(event.getCurrentItem().getType() == Material.BARRIER){
            new MenuServidor().open(player);
            MetodosSimples.tocarSom(player, Sound.ENTITY_PLAYER_LEVELUP,1);
        }

    }

    private void CriarBotoes(){
        ItemStack kit_membro = new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/dda260fdc05722eebaab390aebc2f9392134440f3b1da5f30e5fe671238a8abb"))
                .setDisplayName("membro")
                .build();
        ItemStack kit_semanal = new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/a0e6af393ad3a80158e6ed235480b0bfc06ff4dfa0ddd0f9d057c532614a4a9"))
                .setDisplayName("semanal")
                .build();
        ItemStack kit_mensal = new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/afdc21340ed4887ed1cc59d9025a0bcbe62524d94a581f69932e8c9b6196a665"))
                .setDisplayName("mensal")
                .build();
        ItemStack kit_eterno = new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/a89b4c5e119a61773dd52d36b572bc0f9548d984ce0841417a301a65351a768d"))
                .setDisplayName("eterno")
                .build();
        inventory.setItem(22,kit_membro);
        inventory.setItem(30,kit_semanal);
        inventory.setItem(31,kit_mensal);
        inventory.setItem(32,kit_eterno);
    }

    @Override
    public void setContents() {
        MenuStyls.MenuStyle1(this.inventory);
        CriarBotoes();
    }
}
