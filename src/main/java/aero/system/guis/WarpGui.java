package aero.system.guis;
import aero.system.comandos.Warp;
import aero.system.config.ConfigPrincipal;
import aero.system.eventos.NPCgui;
import aero.system.utilidades.MetodosSimples;
import aero.system.utilidades.SkullBuilder;
import aero.system.utilidades.criadordegui.menu.Menu;
import aero.system.utilidades.items.ItemBuilder;
import aero.system.utilidades.items.skullcreator.SkullCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static aero.system.System.*;



public class WarpGui extends Menu {

    private static ItemStack end;
    private static ItemStack lobby;
    private static ItemStack nether;
    private static ItemStack bricks;
    private static ItemStack minerar;
    private static ItemStack arena;
    private static ItemStack eventos;

    private static ItemStack vidro_azul;
    private static ItemStack vidro_azul_claro;
    private static ItemStack corrente;
    private static ItemStack soul_lantern;
    List<ItemStack> itemsMenu = null;

    public WarpGui() {
        super("      §5§lLista de Teleportes      ",5);
    }

    @Override
    public void setContents() {
        criarBorda();
        itemWarps();
    }
    @Override
    public void handleClickAction(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if(event.getCurrentItem() == null) return;
            if (!event.getCurrentItem().hasItemMeta()) return;
            try {
                if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equalsIgnoreCase(ConfigPrincipal.lobby_nome)) {
                    this.inventory.clear();
                    Bukkit.dispatchCommand(p, "spawn");
                    Bukkit.getScheduler().scheduleSyncDelayedTask(getSystem(), p::closeInventory, 1);
                    NPCgui.ReaplicarCDnoNPC(p);
                }
                if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equalsIgnoreCase(ConfigPrincipal.end_name)) {
                    this.inventory.clear();
                    WarpTeleporter(p, "end");
                    Bukkit.getScheduler().scheduleSyncDelayedTask(getSystem(), p::closeInventory, 1);
                    NPCgui.ReaplicarCDnoNPC(p);
                }
                if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equalsIgnoreCase(ConfigPrincipal.arena_nome)) {
                    this.inventory.clear();
                    WarpTeleporter(p, "arena");
                    Bukkit.getScheduler().scheduleSyncDelayedTask(getSystem(), p::closeInventory, 1);
                    NPCgui.ReaplicarCDnoNPC(p);
                }
                if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equalsIgnoreCase(ConfigPrincipal.nether_nome)) {
                    this.inventory.clear();
                    WarpTeleporter(p, "nether");
                    Bukkit.getScheduler().scheduleSyncDelayedTask(getSystem(), p::closeInventory, 1);
                    NPCgui.ReaplicarCDnoNPC(p);
                }
                if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equalsIgnoreCase(ConfigPrincipal.bricks_nome)) {
                    this.inventory.clear();
                    WarpTeleporter(p, "survival");
                    Bukkit.getScheduler().scheduleSyncDelayedTask(getSystem(), p::closeInventory, 1);
                    NPCgui.ReaplicarCDnoNPC(p);
                }
                if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equalsIgnoreCase(ConfigPrincipal.minerar_nome)) {
                    this.inventory.clear();
                    WarpTeleporter(p, "minerar");
                    Bukkit.getScheduler().scheduleSyncDelayedTask(getSystem(), p::closeInventory, 1);
                    NPCgui.ReaplicarCDnoNPC(p);
                }
                if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equalsIgnoreCase(ConfigPrincipal.eventos_nome)) {
                    this.inventory.clear();
                    WarpTeleporter(p, "eventos");
                    Bukkit.getScheduler().scheduleSyncDelayedTask(getSystem(), p::closeInventory, 1);
                    NPCgui.ReaplicarCDnoNPC(p);
                }
            }catch (Exception ignored){}
    }

    private void itemWarps(){
        ItemStack end_skull = SkullCreator.itemFromUrl
                ("http://textures.minecraft.net/texture/cd232ac65ab6d281bb66f62b36aef4d6d7277f874469159340a29e7b7154fae0");
        ItemStack evento_skull = SkullCreator.itemFromUrl
                ("http://textures.minecraft.net/texture/a6a8dc8ce100ad8d3b3bc9323861db23d18efcbd02963f4b4509cc8c94f1c308");
        ItemStack arena_skull = SkullCreator.itemFromUrl
                ("http://textures.minecraft.net/texture/5ecd9727e9d9f2c0739934ef1e63d1d89c92369421cafbaea733cce35c3b6768");
        ItemStack bricks_skull = SkullCreator.itemFromUrl
                ("http://textures.minecraft.net/texture/290d4fcb2ce03b94d920f0a9e7a54b32cfc7a1d33a6dfe9757d8678cbb591");
        ItemStack minerar_skull = SkullCreator.itemFromUrl
                ("http://textures.minecraft.net/texture/8449b9318e33158e64a46ab0de121c3d40000e3332c1574932b3c849d8fa0dc2");
        ItemStack nether_skull = SkullCreator.itemFromUrl
                ("http://textures.minecraft.net/texture/e126dfae3176f47bad3fae131a66d43a3b4eb7f46df611cae0bf5c382c2b4");
        end = new SkullBuilder(end_skull)
                .setName(ConfigPrincipal.end_name)
                .setLore(ConfigPrincipal.end_lore)
                .getItemStack();
        minerar = new SkullBuilder(minerar_skull)
                .setName(ConfigPrincipal.minerar_nome)
                .setLore(ConfigPrincipal.minerar_lore)
                .getItemStack();
        arena = new SkullBuilder(arena_skull)
                .setName(ConfigPrincipal.arena_nome)
                .setLore(ConfigPrincipal.arena_lore)
                .getItemStack();
        eventos = new SkullBuilder(evento_skull)
                .setName(ConfigPrincipal.eventos_nome)
                .setLore(ConfigPrincipal.eventos_lore)
                .getItemStack();
        bricks = new SkullBuilder(bricks_skull)
                .setName(ConfigPrincipal.bricks_nome)
                .setLore(ConfigPrincipal.bricks_lore)
                .getItemStack();
        nether = new SkullBuilder(nether_skull)
                .setLore(ConfigPrincipal.nether_lore)
                .setName(ConfigPrincipal.nether_nome)
                .getItemStack();
        lobby = new ItemBuilder(Material.NETHER_STAR,1,0)
                .setLore(ConfigPrincipal.lobby_lore)
                .setDisplayName(ConfigPrincipal.lobby_nome).build();
        this.inventory.setItem(13,end);
        this.inventory.setItem(22,lobby);
        this.inventory.setItem(21,minerar);
        this.inventory.setItem(31,nether);
        this.inventory.setItem(23,bricks);
        this.inventory.setItem(19,arena);
        this.inventory.setItem(25,eventos);
    }

    private void criarBorda(){
        vidro_azul = new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE,1,0)
                .setDisplayName("*")
                .build();
        vidro_azul_claro = new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE,1,0)
                .setDisplayName("*")
                .build();
        corrente = new ItemBuilder(Material.CHAIN,1,0)
                .setDisplayName("*")
                .build();
        soul_lantern = new ItemBuilder(Material.SOUL_LANTERN,1,0)
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
        this.inventory.setItem(2,corrente);
        this.inventory.setItem(4,corrente);
        this.inventory.setItem(6,corrente);
        this.inventory.setItem(16,corrente);
        this.inventory.setItem(10,corrente);
        this.inventory.setItem(28,corrente);
        this.inventory.setItem(34,corrente);
        this.inventory.setItem(38,corrente);
        this.inventory.setItem(40,corrente);
        this.inventory.setItem(42,corrente);
        this.inventory.setItem(3,soul_lantern);
        this.inventory.setItem(5,soul_lantern);
        this.inventory.setItem(18,soul_lantern);
        this.inventory.setItem(26,soul_lantern);
        this.inventory.setItem(39,soul_lantern);
        this.inventory.setItem(41,soul_lantern);
    }

    private void WarpTeleporter(Player p,String warp){
        if (!p.hasPermission("System.skipdelay")) {
            if(!p.hasPermission("System.vipdelay")){
                MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.warps_delayteleport_msg, Sound.ENTITY_PLAYER_LEVELUP,1);
                Bukkit.getScheduler().scheduleSyncDelayedTask(getSystem(),() -> Warp.teleportWarp(p,warp),
                        20L*getSystem().getConfig().getInt("Warps-configs.delay"));
            }else{
                MetodosSimples.enviarMSGeSom(p,ConfigPrincipal.warps_vipdelayteleport_msg, Sound.ENTITY_PLAYER_LEVELUP,1);
                Bukkit.getScheduler().scheduleSyncDelayedTask(getSystem(),() -> Warp.teleportWarp(p,warp),
                        20L*getSystem().getConfig().getInt("Warps-configs.vip-delay"));
            }
        }else{
            Warp.teleportWarp(p,warp);
        }
    }

}
