package aero.system.utilidades;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class SkullBuilder {

    ItemStack itemStack;
    ItemMeta itemMeta;

    public SkullBuilder(ItemStack skull){
        itemStack = skull;
        itemMeta = skull.getItemMeta();
    }

    public SkullBuilder setName(String nome){
        itemMeta.setDisplayName(nome);
        itemStack.setItemMeta(itemMeta);
        return this;
    }
    public SkullBuilder setLore(List<String> lores){
        itemMeta.setLore(lores);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
