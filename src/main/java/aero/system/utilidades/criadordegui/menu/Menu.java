package aero.system.utilidades.criadordegui.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Menu implements InventoryHolder
{
    protected List<ItemStack> itemsMenu;
    protected final Inventory inventory;
    private final String title;
    private final int size;

    public Menu(String title)
    {
        this(title, 3);
    }

    public Menu(String title, int rows)
    {
        this.title = title;
        this.size = rows * 9;
        this.inventory = Bukkit.createInventory(this, size, title);
    }

    public String getTitle()
    {
        return title;
    }

    public int getSize()
    {
        return size;
    }

    public void open(Player player)
    {
        if (inventory.isEmpty())
            setContents();

        player.openInventory(this.inventory);
    }

    public abstract void setContents();

    @Override
    public Inventory getInventory()
    {
        return inventory;
    }

    public void handleClickAction(InventoryClickEvent event) {
    }

    public List<ItemStack> getItemsMenu() {
        return itemsMenu;
    }
}
