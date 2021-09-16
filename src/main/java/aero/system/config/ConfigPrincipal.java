package aero.system.config;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static aero.system.System.*;
public class ConfigPrincipal {

    public static String sem_permissao;
    public static String erro_ao_fazer_o_comando;
    public static String sucesso_ao_criar_o_spawn;
    public static String menssagen_de_cooldown;
    public static String spawn_delay_menssagen;
    public static String spawn_vipdelay_menssagen;
    public static String title_spawn_teleport;
    public static String subtitle_spawn_teleport;
    public static String teleportar_outros_para_spawn_msg;
    public static String teleportar_outros_para_spawn_msg_target;
    public static String target_nao_encontrado;
    public static String como_usar_setwarp;
    public static String warp_criada;
    public static String nao_foi_possivel_criar_warp;
    public static String warp_nao_existe;
    public static String warp_teleport_msg;
    public static String warps_delayteleport_msg;
    public static String warps_vipdelayteleport_msg;
    public static String sem_perm_para_ir_a_warp;
    public static String ao_abrir_menu_de_warps;
    public static String sem_perm_warp;
    public static String npc_warp_name;
    // WARP GUI
    public static List<String> end_lore = new ArrayList<>();
    public static List<String> lobby_lore = new ArrayList<>();
    public static List<String> minerar_lore = new ArrayList<>();
    public static List<String> nether_lore = new ArrayList<>();
    public static List<String> bricks_lore = new ArrayList<>();
    public static List<String> eventos_lore = new ArrayList<>();
    public static List<String> arena_lore = new ArrayList<>();
    public static String end_name;
    public static String lobby_nome;
    public static String minerar_nome;
    public static String nether_nome;
    public static String bricks_nome;
    public static String eventos_nome;
    public static String arena_nome;
    public static String menu_principal_delay_msg;
    public static String menu_principal_abrir;
    // MENU GUI
    public static List<String> player_lore;
    public static List<String> menu_lobby_lore;
    public static List<String> sethome_lore;
    public static List<String> clan_lore;
    public static List<String> jobs_lore;
    public static List<String> pets_Lore;
    public static List<String> enderchest_lore;
    public static List<String> kits_lore;
    public static List<String> backpack_lore;
    public static List<String> warpvips_lore;
    public static List<String> shopvip_lore;
    public static String player_nome;
    public static String menu_lobby_nome;
    public static String sethome_nome;
    public static String clan_nome;
    public static String jobs_nome;
    public static String pets_nome;
    public static String enderchest_nome;
    public static String kits_nome;
    public static String backpack_nome;
    public static String warpvips_nome;
    public static String shopvip_nome;
    //
    public static String max_home_msg;
    public static String sethome_sem_argumentos;
    public static String sethome_home_ja_existe;
    public static String sethome_home_criada;
    public static String sethome_home_criada_erro;
    public static String listhome_mostrando_lista;
    public static String listhome_sem_homes;
    public static String delhome_sem_homes;
    public static String delhome_sucesso;
    public static String delhome_erro;
    public static String delhome_home_nao_existe;
    public static String home_teleporte_msg;
    public static String home_nao_existe;
    public static String home_argumento_errado;
    public static String home_teleporte_sucesso;
    public static String proibido_marcar_home;
    public static String spawner_chunk_limit_msg;
    public static String boas_vindas_title;
    public static String boas_vindas_subtitle;
    public static String kit_nao_existe;
    public static String kit_em_cooldown;
    public static String kit_pego;
    public static String kit_sobre_peso;
    public static String kit_sem_perm;
    public static String inventario_cheio;
    public static String menu_kits_aberto;
    public static String verkit_sem_argumento;
    public static String verkit_kit_nao_existe;
    public static String kits_menu_title;
    public static String kit_membro;
    public static String kit_vip1;
    public static String kit_vip2;
    public static String kit_vip3;
    public static List<String> kit_vip1_lore = new ArrayList<>();
    public static List<String> kit_vip2_lore = new ArrayList<>();
    public static List<String> kit_vip3_lore = new ArrayList<>();
    public static List<String> kit_membro_lore = new ArrayList<>();
    public static String sem_cooldown;
    public static String com_cooldown;
    public static String com_perm;
    public static String sem_perm;
    public static String pegar_kit;
    public static String cancelar_kit;
    public static String sectionkit_voltar;
    public static String menuStyle1_exit;

    // CARREGA O DADO DE TODAS AS STRINGS DA CONFIG
    public static void carregarDadosConfig(){
        //COLETADOS DA CONFIG

        sem_permissao = converter("Menssagens-do-sistema.sem-permissao");
        menssagen_de_cooldown = converter("Menssagens-do-sistema.menssagen-cooldown");
        spawn_delay_menssagen = converter("Configs-Spawn.spawn-delay-msg")
                .replace("%delay%",""+getSystem().getConfig().getInt("Configs-Spawn.delay"));
        spawn_vipdelay_menssagen = converter("Configs-Spawn.spawn-delay-msg")
                .replace("%delay%",""+getSystem().getConfig().getInt("Configs-Spawn.vip-delay"));
        title_spawn_teleport = converter("Configs-Spawn.spawn-title-msg");
        subtitle_spawn_teleport = converter("Configs-Spawn.spawn-subtitle-msg");
        warp_nao_existe = converter("Menssagens-do-sistema.warp-nao-existe");
        warp_teleport_msg = converter("Menssagens-do-sistema.warp-teleport-msg");
        warps_delayteleport_msg = converter("Warps-configs.delay-msg")
                .replace("%delay%",""+getSystem().getConfig().getInt("Warps-configs.delay"));;
        warps_vipdelayteleport_msg = converter("Warps-configs.delay-msg")
                .replace("%delay%",""+getSystem().getConfig().getInt("Warps-configs.vip-delay"));;
        sem_perm_para_ir_a_warp = converter("Warps-configs.warp-sem-perm");
        ao_abrir_menu_de_warps = converter("Menssagens-do-sistema.menu-warp-ao-abrir");
        sem_perm_warp = converter("Warps-configs.sem-perm-warp");
        npc_warp_name = converter("Npcs.warp-npc");
        menu_principal_delay_msg = converter("Menu-configs.delay-msg")
                .replace("%delay%",""+getSystem().getConfig().getInt("Menu-configs.delay"));
        menu_principal_abrir = converter("Menu-configs.abrir");
        max_home_msg = converter("Menssagens-do-sistema.sethome-maximo-homes");
        sethome_sem_argumentos = converter("Menssagens-do-sistema.sethome-sem-argumentos");
        sethome_home_ja_existe = converter("Menssagens-do-sistema.sethome-home-ja-existe");
        sethome_home_criada = converter("Menssagens-do-sistema.sethome-home-criada");
        sethome_home_criada_erro = converter("Menssagens-do-sistema.sethome-home-criada-erro");
        listhome_mostrando_lista = converter("Menssagens-do-sistema.listhome-mostrando-lista");
        listhome_sem_homes = converter("Menssagens-do-sistema.listhome-sem-homes");
        delhome_sem_homes = converter("Menssagens-do-sistema.delhome-sem-homes");
        delhome_sucesso = converter("Menssagens-do-sistema.delhome-sucesso");
        delhome_erro = converter("Menssagens-do-sistema.delhome-erro");
        delhome_home_nao_existe = converter("Menssagens-do-sistema.delhome-home-nao-existe");
        home_teleporte_msg = converter("Menssagens-do-sistema.home-teleporte-msg");
        home_nao_existe = converter("Menssagens-do-sistema.home-nao-existe");
        home_argumento_errado = converter("Menssagens-do-sistema.home-argumento-errado");
        home_teleporte_sucesso = converter("Menssagens-do-sistema.home-teleporte-sucesso");
        proibido_marcar_home = converter("Menssagens-do-sistema.proibido-marcar-home");
        spawner_chunk_limit_msg = converter("Menssagens-do-sistema.maximo-de-spawners");
        boas_vindas_title = converter("Boas-vindas.title");
        boas_vindas_subtitle = converter("Boas-vindas.sub-title");
        kit_nao_existe = converter("Menssagens-do-sistema.kit-nao-existe");
        kit_sobre_peso = converter("Menssagens-do-sistema.kit-sobre-peso");
        kit_em_cooldown = converter("Menssagens-do-sistema.kit-em-cooldown");
        kit_pego = converter("Menssagens-do-sistema.kit-pego");
        kit_sem_perm = converter("Menssagens-do-sistema.kit-sem-perm");
        inventario_cheio = converter("Menssagens-do-sistema.inventario-cheio");
        menu_kits_aberto = converter("Menssagens-do-sistema.menu-kits-aberto");
        verkit_kit_nao_existe = converter("Menssagens-do-sistema.verkit-kit-nao-existe");
        verkit_sem_argumento = converter("Menssagens-do-sistema.verkit-sem-argumento");
        kits_menu_title = converter("Kits-menu.kits-menu-title");
        // WARP GUI CARREGAMENTOS

        end_lore = converterLista("Gui-config.warp.end-lore");
        lobby_lore = converterLista("Gui-config.warp.lobby-lore");
        minerar_lore = converterLista("Gui-config.warp.minerar-lore");
        nether_lore = converterLista("Gui-config.warp.nether-lore");
        bricks_lore = converterLista("Gui-config.warp.bricks-lore");
        eventos_lore = converterLista("Gui-config.warp.eventos-lore");
        arena_lore = converterLista("Gui-config.warp.arena-lore");
        end_name = converter("Gui-config.warp.end-name");
        lobby_nome = converter("Gui-config.warp.lobby-name");;
        minerar_nome = converter("Gui-config.warp.minerar-name");;
        nether_nome = converter("Gui-config.warp.nether-name");;
        bricks_nome = converter("Gui-config.warp.bricks-name");;
        eventos_nome = converter("Gui-config.warp.eventos-name");;
        arena_nome = converter("Gui-config.warp.arena-name");;

        // Kits Gui
        String kits = "Kits-menu.";
        kit_membro = converter(kits+"kit-membro");
        kit_vip1 = converter(kits+"kit-vip1");
        kit_vip2 = converter(kits+"kit-vip2");
        kit_vip3 = converter(kits+"kit-vip3");
        kit_vip1_lore = converterLista(kits+"kit-vip1-lore");
        kit_vip2_lore = converterLista(kits+"kit-vip2-lore");;
        kit_vip3_lore = converterLista(kits+"kit-vip3-lore");;
        kit_membro_lore = converterLista(kits+"kit-membro-lore");
        sem_cooldown = converter(kits+"sem-cooldown");
        com_cooldown = converter(kits+"com-cooldown");
        sem_perm = converter(kits+"sem-perm");
        com_perm = converter(kits+"com-perm");
        pegar_kit = converter(kits+"pegar-kit");
        cancelar_kit = converter(kits+"cancelar-kit");
        sectionkit_voltar = converter(kits+"sectionkit-voltar");
        menuStyle1_exit = converter( kits+"menuStyle1-exit");

        // MENU GUI CARREGAMENTO
        player_lore = converterLista("Gui-config.menu.player_lore");
        menu_lobby_lore = converterLista("Gui-config.menu.lobby_lore");
        sethome_lore = converterLista("Gui-config.menu.sethome_lore");
        clan_lore = converterLista("Gui-config.menu.clan_lore");
        jobs_lore = converterLista("Gui-config.menu.jobs_lore");
        pets_Lore = converterLista("Gui-config.menu.pets_lore");
        enderchest_lore = converterLista("Gui-config.menu.enderchest_lore");
        kits_lore = converterLista("Gui-config.menu.kits_lore");
        backpack_lore = converterLista("Gui-config.menu.backpack_lore");
        warpvips_lore = converterLista("Gui-config.menu.warpvips_lore");
        shopvip_lore = converterLista("Gui-config.menu.shopvip_lore");
        player_nome = converter("Gui-config.menu.player_nome");
        menu_lobby_nome = converter("Gui-config.menu.lobby_nome");
        sethome_nome = converter("Gui-config.menu.sethome_nome");
        clan_nome = converter("Gui-config.menu.clan_nome");
        jobs_nome = converter("Gui-config.menu.jobs_nome");
        pets_nome = converter("Gui-config.menu.pets_nome");
        enderchest_nome = converter("Gui-config.menu.enderchest_nome");
        kits_nome = converter("Gui-config.menu.kits_nome");
        backpack_nome = converter("Gui-config.menu.backpack_nome");
        warpvips_nome = converter("Gui-config.menu.warpvips_nome");
        shopvip_nome = converter("Gui-config.menu.shopvip_nome");
        //

        // MENSAGENS DIRETAS

        erro_ao_fazer_o_comando = converterMSG("&c&lNao foi possivel executar o Comando!");
        sucesso_ao_criar_o_spawn = converterMSG("&2Sucesso ao criar o spawn!");
        teleportar_outros_para_spawn_msg = converterMSG("&2Voce Mandou o %target% para o spawn!");
        teleportar_outros_para_spawn_msg_target = converterMSG("&2Voce foi enviado para o spawn pelo %player%");
        target_nao_encontrado = converterMSG("&cNao foi possivel teleportar o jogador! (offline ou nome errado)");
        como_usar_setwarp = converterMSG("&cUtilize: /setwarp <warp-nome> - em cima do bloco, olhando para o local certo!");
        warp_criada = converterMSG("&2&lWarp %warp% criada com sucesso!");
        nao_foi_possivel_criar_warp = converterMSG("&c&lNao foi possivel criar a Warp! (Letras Invalidas)");
    }


    // METODO QUE DA REPLACE NAS CORES DIRETO DA CONFIG
    private static String converter(String config){
        return Objects.requireNonNull(getSystem().getConfig().getString(config)).replace("&","§");
    }
    // DIRETO DO LOCAL
    private static String converterMSG(String config){
        return config.replace("&","§");
    }
    private static List<String> converterLista(String config){
        List<String> lores = getSystem().getConfig().getStringList(config);
        List<String> lores_formatadas = new ArrayList<>();
        lores.forEach(s -> {
            lores_formatadas.add(s
                    .replace("&","§"));
        });
        return lores_formatadas;
    }


}
