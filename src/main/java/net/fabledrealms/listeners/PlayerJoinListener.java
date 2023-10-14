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

    private void sendCompass(Player player){
        main.getCompassManager().addCompass(player, new CompassBar(player));
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        sendCompass(player);

        if (this.main.getCharacterManager().getCharacter(player) == null) {
            this.main.getCharacterManager().createCharacter(player, 0, null);
        }
    }
}
