package aero.system.guis;

import aero.system.comandos.Kit;
import aero.system.config.ConfigPrincipal;
import aero.system.utilidades.CooldownManager;
import aero.system.utilidades.MetodosSimples;
import aero.system.utilidades.criadordegui.menu.Menu;
import aero.system.utilidades.items.ItemBuilder;
import com.Acrobot.ChestShop.ChestShop;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import static aero.system.System.*;

import java.io.File;
import java.util.ArrayList;

public class KitViewMenu extends Menu {

    private final String kit;
    private final String section;
    private ArrayList<ItemStack> kitItemStacks = new ArrayList<>();
    private final CooldownManager cooldownManager = getSystem().getCooldownManager();
    private final Player player;

    public KitViewMenu(String kit, Player player,String section) {
        super("§8Conteúdo do Kit "+getSystem().getKits().getString("Kits."+kit+".display")+": ",5);
        this.kit = kit;
        this.player = player;
        this.section = section;
        CarregarKitItemStacks();
    }

    @Override
    public void handleClickAction(InventoryClickEvent event) {
        if(event.getCurrentItem() == null) return;
        if (!event.getCurrentItem().hasItemMeta()) return;
        if(event.getCurrentItem().getType() == Material.LIME_TERRACOTTA){
            Bukkit.dispatchCommand(player,"kit "+kit);
        }
        if(event.getCurrentItem().getType() == Material.RED_TERRACOTTA){
            if(section == null) new KitMenu(player).open(player);
            else new SectionKit(section,player).open(player);
            gui_protection = true;
            MetodosSimples.tocarSom(player,Sound.ENTITY_PLAYER_LEVELUP,1);
        }
    }

    @Override
    public void setContents() {
        CarregarBordas();
        CarregarBotoes();
        MostrarItemsDoKit();
    }

    private void CarregarBotoes(){ // 39 41
        ItemStack confirmar = new ItemBuilder(Material.LIME_TERRACOTTA)
                .setDisplayName(ConfigPrincipal.pegar_kit)
                .build();
        ItemStack cancelar = new ItemBuilder(Material.RED_TERRACOTTA)
                .setDisplayName(ConfigPrincipal.cancelar_kit)
                .build();
        getInventory().setItem(41,cancelar);
        getInventory().setItem(39,confirmar);
    }

    private void CarregarBordas(){
        ItemStack vidro_cinza = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                .setDisplayName("*")
                .build();
        for(int i=36;i < 45;i++){
            getInventory().setItem(i,vidro_cinza);
        }
    }

    private void CarregarKitItemStacks(){
        FileConfiguration config = getSystem().getKits();
        for(String patch : config.getStringList("Kits."+kit+".items")){
            String[] index = patch.split(";");
            ItemStack itemStack;
            try{
                itemStack = new ItemBuilder(Material.getMaterial(index[0])).build();
            }catch (Exception error){
                itemStack = ChestShop.getItemDatabase().getFromCode(index[0]);
            }
            itemStack.setAmount(Integer.parseInt(index[1]));
            kitItemStacks.add(itemStack);
        }
    }
    private void MostrarItemsDoKit(){
        kitItemStacks.forEach(inventory::addItem);
    }

}
