package aero.system.comandos;

import aero.system.config.ConfigPrincipal;
import aero.system.guis.KitMenu;
import aero.system.utilidades.CooldownManager;
import aero.system.utilidades.MetodosSimples;
import aero.system.utilidades.items.ItemBuilder;
import com.Acrobot.ChestShop.ChestShop;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

import static aero.system.System.*;
public class Kit implements CommandExecutor {

    private final CooldownManager cooldownManager = getSystem().getCooldownManager();

    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
        if(!(s instanceof Player)) return false;
        Player p = (Player) s;

        if(!p.hasPermission("System.kits")){
            MetodosSimples.enviarMSGeSom(p, ConfigPrincipal.sem_permissao, Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }
        if(args.length == 0){
            new KitMenu(p).open(p);
            MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.menu_kits_aberto,Sound.ENTITY_PLAYER_LEVELUP,1);
            return false;
        }
        String kit_nome = args[0].toLowerCase();
        FileConfiguration config = getSystem().getKits();
        if(config.getConfigurationSection("Kits."+kit_nome) == null){
            MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.kit_nao_existe.replace("%kit_display%",""+args[0])
                    ,Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }
        if(p.getInventory().firstEmpty() == -1){
            MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.inventario_cheio,Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }
        if(!p.hasPermission("System.kit."+kit_nome)){
            MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.kit_sem_perm,Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }
        if(config.getBoolean("Kits."+kit_nome+".bloqueado")){
            p.sendMessage("Esse kit esta BLOQUEADO no momento!");
            return false;
        }
        String display = config.getString("Kits."+kit_nome+".display");
        int cooldown = config.getInt("Kits."+kit_nome+".cooldown");

        if(!cooldownManager.getCooldownList().containsKey(kit_nome)){
            cooldownManager.getCooldownList().put(kit_nome,cooldown);
        }

        if(cooldownManager.VerificarSePlayerEstaComCooldown(p,kit_nome) && !p.hasPermission("System.skipcd")){
            MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.kit_em_cooldown
                            .replace("%cooldown%",""+cooldownManager.PegarTempoRestanteFormatado(p,kit_nome))
                    ,Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }

        ArrayList<ItemStack> kit_items = new ArrayList<>();
        for(String patch : config.getStringList("Kits."+kit_nome+".items")){
            String[] index = patch.split(";");
            ItemStack itemStack;
            try{
                itemStack = new ItemBuilder(Material.getMaterial(index[0])).build();
            }catch (Exception error){
                itemStack = ChestShop.getItemDatabase().getFromCode(index[0]);
            }
            itemStack.setAmount(Integer.parseInt(index[1]));
            kit_items.add(itemStack);
        }
        DarKitAoPlayer(kit_items,p);
        MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.kit_pego.replace("%kit_display%",""+display)
                ,Sound.ENTITY_PLAYER_LEVELUP,1);
        cooldownManager.AdicionarCooldownPlayer(p,kit_nome);
        return true;
    }


    public static void DarKitAoPlayer(ArrayList<ItemStack> kit_items, Player p){
        kit_items.forEach(item -> {
            p.getInventory().addItem(item).forEach((integer, itemStack) ->{
                if(itemStack != null) {
                    MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.kit_sobre_peso
                                    .replace("%item%",""+itemStack.getType().name())
                                    .replace("_"," ")
                            ,Sound.ENTITY_ITEM_BREAK,1);
                    p.getWorld().dropItemNaturally(p.getLocation(), itemStack).setOwner(p.getUniqueId());
                }
            });
        });
    }
}
