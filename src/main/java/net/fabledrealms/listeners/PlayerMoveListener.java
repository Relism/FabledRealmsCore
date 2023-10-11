package net.fabledrealms.listeners;

import net.fabledrealms.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    private final Core main;

    public PlayerMoveListener(Core main){
        this.main = main;
        Bukkit.getPluginManager().registerEvents(this, main);
    }

    private void playerAntiSwim(Player player){
        String message = main.getLangFile().getFile().getString("player.enter-water");
        player.sendMessage(main.getStringUtil().colorString(message));
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO,1,1);
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Location toLoc = event.getTo();
        Location fromLoc = event.getFrom();
        if (toLoc.getBlock().getType().equals(Material.WATER)){
            event.setCancelled(true);
            playerAntiSwim(player);
            return;}
    }

}
