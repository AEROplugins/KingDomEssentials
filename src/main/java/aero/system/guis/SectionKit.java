package aero.system.guis;

import aero.system.config.ConfigPrincipal;
import aero.system.utilidades.CooldownManager;
import aero.system.utilidades.MetodosSimples;
import aero.system.utilidades.criadordegui.menu.Menu;
import aero.system.utilidades.items.ItemBuilder;
import aero.system.utilidades.items.skullcreator.SkullCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.naming.InterruptedNamingException;
import java.util.*;
import static aero.system.System.*;
public class SectionKit extends Menu {
    private final CooldownManager cooldownManager = getSystem().getCooldownManager();
    private final String Section;
    private final List<String> kits_list;
    private final Player player;
    private HashMap<Integer,ItemStack> itemMap = new HashMap<>();
    private HashMap<Integer,String> ItemComands = new HashMap<>();

    public SectionKit(String Section,Player player) {
        super("§9Kits "+Section, getSystem().getSectionKit().getInt("configs."+Section+".rows"));
        FileConfiguration config = getSystem().getSectionKit();
        this.Section = Section;
        kits_list = config.getStringList("configs."+Section+".skulls-configs");
        this.player = player;
        CarregarLoresItemsComandos(kits_list);
    }

    private void CarregarLoresItemsComandos(List<String> kits_list){
        itemMap.clear();
        ItemComands.clear();
        for(String strings : kits_list){
            String CooldownMsg;
            String havePermissionMsg;
            String[] index = strings.split(";");
            String name = index[0].toLowerCase();
            String skinurl = index[1];
            int amount = Integer.parseInt(index[2]);
            int slot = Integer.parseInt(index[3]);
            String comandoVinculado = index[4];
            String[] index2 = index[4].split(" ");
            if(cooldownManager.VerificarSePlayerEstaComCooldown(player,name)){
                CooldownMsg = ConfigPrincipal.com_cooldown
                        .replace("%cd%",""+cooldownManager.PegarTempoRestanteFormatado(player,name));
            }else CooldownMsg = ConfigPrincipal.sem_cooldown;
            if(player.hasPermission("System.kit."+name)){
                havePermissionMsg = ConfigPrincipal.com_perm;
            }else havePermissionMsg = ConfigPrincipal.sem_perm;
            ItemStack itemStack;
            itemStack = new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/"+skinurl))
                    .setDisplayName(Objects.requireNonNull(getSystem().getKits().getString("Kits." + name + ".gui-display")))
                    .setLore(FormatarLore(getSystem().getKits().getStringList("Kits."+name+".gui-lore"), CooldownMsg, havePermissionMsg))
                    .build();
            itemStack.setAmount(amount);
            itemMap.put(slot,itemStack);
            ItemComands.put(slot,comandoVinculado);
        }
    }

    @Override
    public void handleClickAction(InventoryClickEvent event) {
        if(event.getCurrentItem() == null) return;
        if(!event.getCurrentItem().hasItemMeta()) return;
        if(!event.getCurrentItem().getItemMeta().hasDisplayName()) return;

        if(ItemComands.containsKey(event.getSlot())){
            String comando = ItemComands.get(event.getSlot());
            Player player = (Player) event.getWhoClicked();
            Bukkit.dispatchCommand(player,comando);
        }
        if(event.getCurrentItem().getType() == Material.BARRIER){
            Player player = (Player) event.getWhoClicked();
            new KitMenu(player).open(player);
            MetodosSimples.tocarSom(player,Sound.ENTITY_PLAYER_LEVELUP,1);
            gui_protection = true;
        }
    }

    private void CarregarKits(){
        itemMap.forEach((slot,itemStack) -> {
            getInventory().setItem(slot,itemStack);
        });
        FileConfiguration config = getSystem().getSectionKit();
        ItemStack barreira = new ItemBuilder(Material.BARRIER).setDisplayName(ConfigPrincipal.sectionkit_voltar).build();
        getInventory().setItem(config.getInt("configs."+Section+".exit-slot"),barreira);
    }

    @Override
    public void setContents() {
        CarregarKits();
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(getSystem(),() -> {
            CarregarLoresItemsComandos(this.kits_list);
            CarregarKits();
        },20*3,20);
    }

    private List<String> FormatarLore(List<String> lore,String cooldownmsg,String permissionmsg){
        return lore.stream().map(string -> string
                .replace("%perm%",""+permissionmsg)
                .replace("%cooldown%",""+cooldownmsg)
                .replace("&","§")).toList();
    }


}
