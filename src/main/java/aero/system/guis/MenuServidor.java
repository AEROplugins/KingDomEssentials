package aero.system.guis;

import aero.system.System;
import aero.system.config.ConfigPrincipal;
import aero.system.utilidades.MetodosSimples;
import aero.system.utilidades.SkullBuilder;
import aero.system.utilidades.criadordegui.menu.InventoryListener;
import aero.system.utilidades.criadordegui.menu.Menu;
import aero.system.utilidades.items.ItemBuilder;
import aero.system.utilidades.items.skullcreator.SkullCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.EnderChest;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import static aero.system.System.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MenuServidor extends Menu {
    private static Player p = aero.system.comandos.Menu.getP();

    public MenuServidor() {
        super("          §9§lMenu principal      ",5);
    }

    @Override
    public void setContents() {
        criarBorda();
        criarItems();
    }

    @Override
    public void handleClickAction(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if(event.getCurrentItem() == null) return;
        if (!event.getCurrentItem().hasItemMeta()) return;
        if(Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equalsIgnoreCase(ConfigPrincipal.enderchest_nome)){
            p.openInventory(p.getEnderChest());
            MetodosSimples.tocarSom(p, Sound.BLOCK_ENDER_CHEST_OPEN,1);
        }
        if(Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equalsIgnoreCase(ConfigPrincipal.backpack_nome)){
            Bukkit.dispatchCommand(p,"backpack");
        }
        if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ConfigPrincipal.sethome_nome)){
            new HomeMenu(p).open(p);
            MetodosSimples.tocarSom(p,Sound.ENTITY_PLAYER_LEVELUP,1);
            gui_protection = true;
        }
    }

    private void criarItems(){
        ItemStack player_skull =
                SkullCreator.itemFromUuid(p.getUniqueId());
        ItemStack Sethome_skull =
                SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/8e3f9db7b4573171a2b5836e69bc6a6314514fff5bc79743319fd191f53444");
        ItemStack Clan_skull =
                SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/f7c17865c27934f8c8ec6c627e1fe2d99f783ec8ae414ca2d4fd3640a7f3c");
        ItemStack jobs_skull =
                SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/c4d08a9dda1a0243ab9a96994efd58c7c7488fa3b9d4ef9a76ce1e92c150b50a");
        ItemStack pets_skull =
                SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/8358856a18432f539040e40aa4526cb9370eb9cdf116f7b72454914367ec3cb1");
        ItemStack enderchest_skull =
                SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/a82641786a422088f75dcee70205d580600f69d6aa2f77d2678b58d89b6973a6");
        ItemStack Kits_skull =
                SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/241f8264c3d208bc6b5793b45ef766410d10de8a6cd24982e7d9460851b4442e");
        ItemStack shopvips_skull =
                SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/7e3deb57eaa2f4d403ad57283ce8b41805ee5b6de912ee2b4ea736a9d1f465a7");
        ItemStack Backpack_skull =
                SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/d087c65d7bde665b6e195e8dcfc21f4addcf92a907140c37d474c12accf7ab");
        ItemStack warpvips_skull =
                SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/438cf3f8e54afc3b3f91d20a49f324dca1486007fe545399055524c17941f4dc");

        ItemStack player = new SkullBuilder(player_skull)
                .setName(ConfigPrincipal.player_nome
                        .replace("%player%",p.getDisplayName()))
                .setLore(aplicarDados(ConfigPrincipal.player_lore))
                .getItemStack();
        ItemStack lobby = new ItemBuilder(Material.NETHER_STAR,1,0)
                .setDisplayName(ConfigPrincipal.menu_lobby_nome)
                .setLore(aplicarDados(ConfigPrincipal.menu_lobby_lore))
                .build();
        ItemStack sethome = new SkullBuilder(Sethome_skull)
                .setName(ConfigPrincipal.sethome_nome)
                .setLore(aplicarDados(ConfigPrincipal.sethome_lore))
                .getItemStack();
        ItemStack clan = new SkullBuilder(Clan_skull)
                .setName(ConfigPrincipal.clan_nome)
                .setLore(aplicarDados(ConfigPrincipal.clan_lore))
                .getItemStack();
        ItemStack jobs = new SkullBuilder(jobs_skull)
                .setName(ConfigPrincipal.jobs_nome)
                .setLore(aplicarDados(ConfigPrincipal.jobs_lore))
                .getItemStack();
        ItemStack pets = new SkullBuilder(pets_skull)
                .setName(ConfigPrincipal.pets_nome)
                .setLore(aplicarDados(ConfigPrincipal.pets_Lore))
                .getItemStack();
        ItemStack enderchest = new SkullBuilder(enderchest_skull)
                .setName(ConfigPrincipal.enderchest_nome)
                .setLore(aplicarDados(ConfigPrincipal.enderchest_lore))
                .getItemStack();
        ItemStack Kits = new SkullBuilder(Kits_skull)
                .setName(ConfigPrincipal.kits_nome)
                .setLore(aplicarDados(ConfigPrincipal.kits_lore))
                .getItemStack();
        ItemStack backpack = new SkullBuilder(Backpack_skull)
                .setName(ConfigPrincipal.backpack_nome)
                .setLore(aplicarDados(ConfigPrincipal.backpack_lore))
                .getItemStack();
        ItemStack warpvips = new SkullBuilder(warpvips_skull)
                .setName(ConfigPrincipal.warpvips_nome)
                .setLore(aplicarDados(ConfigPrincipal.warpvips_lore))
                .getItemStack();
        ItemStack shopvip = new SkullBuilder(shopvips_skull)
                .setName(ConfigPrincipal.shopvip_nome)
                .setLore(aplicarDados(ConfigPrincipal.shopvip_lore))
                .getItemStack();
        inventory.setItem(13,lobby);
        inventory.setItem(4,player);
        inventory.setItem(40,warpvips);
        inventory.setItem(12,sethome);
        inventory.setItem(14,clan);
        inventory.setItem(21,jobs);
        inventory.setItem(22,pets);
        inventory.setItem(23,enderchest);
        inventory.setItem(30,Kits);
        inventory.setItem(31,shopvip);
        inventory.setItem(32,backpack);


    }

    private void criarBorda(){
        ItemStack vidro_azul = new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE,1,0)
                .setDisplayName("*")
                .build();
        ItemStack vidro_azul_claro = new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE,1,0)
                .setDisplayName("*")
                .build();
        ItemStack corrente = new ItemBuilder(Material.CHAIN,1,0)
                .setDisplayName("*")
                .build();
        ItemStack soul_lantern = new ItemBuilder(Material.SOUL_LANTERN,1,0)
                .setDisplayName("*")
                .build();
        this.inventory.setItem(0,vidro_azul);
        this.inventory.setItem(1,vidro_azul_claro );
        this.inventory.setItem(7,vidro_azul_claro );
        this.inventory.setItem(8,vidro_azul);
        this.inventory.setItem(9,vidro_azul_claro );
        this.inventory.setItem(17,vidro_azul_claro );
        this.inventory.setItem(27,vidro_azul_claro );
        this.inventory.setItem(35,vidro_azul_claro );
        this.inventory.setItem(36,vidro_azul);
        this.inventory.setItem(37,vidro_azul_claro );
        this.inventory.setItem(43,vidro_azul_claro );
        this.inventory.setItem(44,vidro_azul);
        this.inventory.setItem(2,soul_lantern);
        this.inventory.setItem(6,soul_lantern);
        this.inventory.setItem(38,soul_lantern);
        this.inventory.setItem(42,soul_lantern);
        this.inventory.setItem(3,corrente);
        this.inventory.setItem(5,corrente);
        this.inventory.setItem(18,corrente);
        this.inventory.setItem(26,corrente);
        this.inventory.setItem(39,corrente);
        this.inventory.setItem(41,corrente);
    }

    private static List<String> aplicarDados(List<String> lores){
        List<String> lores_formatadas = new ArrayList<>();
        lores.forEach(s -> {
            lores_formatadas.add(s
                    .replace("%player%",p.getDisplayName())
                    .replace("%banco%",String.valueOf(getEconomy().getBalance(p)))
                    .replace("%grupo%",getChat().getGroupPrefix(p.getWorld(),getPerms().getPrimaryGroup(p))
                            .replace("&","§")));
        });
        return lores_formatadas;
    }

}
