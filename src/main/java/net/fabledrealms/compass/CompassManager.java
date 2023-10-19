package net.fabledrealms.compass;

import net.fabledrealms.Core;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CompassManager {


    private Core main;
    private HashMap<Player,CompassBar> compassBarHashMap;
    public CompassManager(Core main){
        this.main = main;
        compassBarHashMap = new HashMap<>();
        compassLoop();
    }

    public void addCompass(Player player, CompassBar compassBar){
        compassBarHashMap.put(player, compassBar);
    }

    public CompassBar getCompass(Player player){
        return compassBarHashMap.get(player);
    }

    public void removeCompass(Player player){
        getCompass(player).getCompassBar().removePlayer(player);
        compassBarHashMap.remove(player);
    }

    private void compassLoop(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
            @Override
            public void run() {
                for (Player player : compassBarHashMap.keySet()){
                    getCompass(player).displayCompass();
                }
            }
        },0,1);
    }





}
