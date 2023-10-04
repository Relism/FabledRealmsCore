package net.fabledrealms.listeners;

import net.fabledrealms.Core;
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
        main.getPlayerDatabase().execute("CREATE TABLE IF NOT EXISTS "+ player.getUniqueId().toString() + "(CharacterID int" +
                ", Class string, Level-Main int, Experience float, Balance int)");
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        createPlayerDataTable(player);
    }


}
