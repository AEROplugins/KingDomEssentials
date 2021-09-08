package aero.system.comandos;

import aero.system.config.ConfigPrincipal;
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
import java.util.List;

import static aero.system.System.*;
public class Kit implements CommandExecutor {

    private final CooldownManager cooldownManager = getSystem().getCooldownManager();

    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
        if(!(s instanceof Player)) return false;
        Player p = (Player) s;

        if(!p.hasPermission("System.criarkit")){
            MetodosSimples.enviarMSGeSom(p, ConfigPrincipal.sem_permissao, Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }
        if(args.length == 0){
            MetodosSimples.enviarMSGeSom(p,"§cVocê precisa dar o nome do kit! ex:/criarkit <nome>",Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }
        String kit_nome = args[0].toLowerCase();
        FileConfiguration config = getSystem().getKits();
        if(config.getConfigurationSection("Kits."+kit_nome) == null){
            MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.kit_nao_existe.replace("%kit_display%",""+args[0])
                    ,Sound.ENTITY_VILLAGER_NO,1);
            return false;
        }
        String diplay = config.getString("Kits."+kit_nome+".display");
        int cooldown = config.getInt("Kits."+kit_nome+".cooldown");

        if(!cooldownManager.getCooldownList().containsKey(kit_nome)){
            cooldownManager.getCooldownList().put(kit_nome,cooldown);
        }

        if(cooldownManager.VerificarSePlayerEstaComCooldown(p,kit_nome)){
            p.sendMessage("Voce esta em cooldown! faltam "+cooldownManager.PegarTempoRestanteFormatado(p,kit_nome));
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

        kit_items.forEach(item -> {
            p.getInventory().addItem(item).forEach((integer, itemStack) ->{
                if(itemStack != null) {
                    p.sendMessage("&e"+itemStack.toString().replace("_","")+" Foram dropados no chao!");
                    p.getWorld().dropItemNaturally(p.getLocation(), itemStack).setOwner(p.getUniqueId());
                }
            });
        });
        cooldownManager.AdicionarCooldownPlayer(p,kit_nome);
        return true;
    }
}
