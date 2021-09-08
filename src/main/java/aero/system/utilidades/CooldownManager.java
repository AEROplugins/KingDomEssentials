package aero.system.utilidades;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class CooldownManager {

    private HashMap<String,Integer> cooldownList = new HashMap<>();
    private HashMap<String,Long> playerCooldownRemanecente = new HashMap<>();
    public CooldownManager(){
    }
    public void AdicionarCooldownPlayer(Player player,String cooldownName){
        String identificador = player.getName()+";"+cooldownName;
        Long cooldown = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(cooldownList.get(cooldownName));
        playerCooldownRemanecente.put(identificador,cooldown);
    }

    BiFunction<List<String>,String,Boolean> VerificarCooldowName = (list,cd) -> {
        for(String cds : list){
            if(cds.equalsIgnoreCase(cd)) {
                return true;
            }
        }
        return false;
    };

    public boolean VerificarSePlayerEstaComCooldown(Player player,String cooldownName){
        String identificador = player.getName()+";"+cooldownName;
        if(playerCooldownRemanecente.containsKey(identificador)
                && !(System.currentTimeMillis() >= playerCooldownRemanecente.get(identificador))){
            return true;
        }else playerCooldownRemanecente.remove(identificador);
        return false;
    }

   public long PegarTempoRestanteDeCooldownEmSegundos(Player player,String cooldownName){


        if(!VerificarSePlayerEstaComCooldown(player,cooldownName)) return 0;
       String identificador = player.getName()+";"+cooldownName;
        long tempo = System.currentTimeMillis() - playerCooldownRemanecente.get(identificador);
        return 1 +TimeUnit.MILLISECONDS.toSeconds(tempo) * -1;


    }
   public String PegarTempoRestanteFormatado(Player player,String cooldownName){
        if(!VerificarSePlayerEstaComCooldown(player,cooldownName)) return "0 Segundos";
        long segundos = PegarTempoRestanteDeCooldownEmSegundos(player,cooldownName);
        return calculateTime(segundos);
   }

    public static String calculateTime(long seconds) {
        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);

        return day + " dias " + hours + " horas " + minute + " minutos " + second + " segundos ";

    }


    public void AdicionarCooldownList(String cooldownName,Integer cooldownTime){
        cooldownList.put(cooldownName,cooldownTime);
    }

    public HashMap<String, Integer> getCooldownList() {
        return cooldownList;
    }


    public HashMap<String, Long> getPlayerCooldownRemanecente() {
        return playerCooldownRemanecente;
    }

    public void setCooldownList(HashMap<String, Integer> cooldownList) {
        this.cooldownList = cooldownList;
    }

    public void setPlayerCooldownRemanecente(HashMap<String,Long> playerCooldownRemanecente) {
        this.playerCooldownRemanecente = playerCooldownRemanecente;
    }
    public void addPlayerCooldownRemanecente(String identificador,long cooldown){
        playerCooldownRemanecente.put(identificador,cooldown);
    }
}
