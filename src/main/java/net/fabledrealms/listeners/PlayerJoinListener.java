package net.fabledrealms.listeners;

import net.fabledrealms.Core;
import net.fabledrealms.compass.CompassBar;
import net.fabledrealms.compass.CompassManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final Core main;

    public PlayerJoinListener(Core main){
        this.main = main;
        Bukkit.getPluginManager().registerEvents(this, main);
    }

    private void createPlayerDataTable(Player player){
        String tableName = player.getUniqueId().toString();
        tableName = tableName.replaceAll("-", "_");
        String sqlString = "CREATE TABLE IF NOT EXISTS "+ tableName + "(CharacterID int" +
                ", ClassName text, PlayerLevel int, PlayerExperience int, Balance int);";
        Bukkit.getLogger().info(sqlString); //Prints the SQL string to the console
        main.getPlayerDatabase().execute(sqlString);
    }

    private void sendCompass(Player player){
        main.getCompassManager().addCompass(player, new CompassBar(player));
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        createPlayerDataTable(player);
        sendCompass(player);
    }


}
