package net.fabledrealms.listeners;

import net.fabledrealms.Core;
import net.fabledrealms.animations.RespawnAnimation;
import net.fabledrealms.util.msg;
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

    private void playerAntiSwim(Player player, Location toLoc){
        //Finds the closest safe block within radius, if none then teleports player to the nearest graveyard
        String message = main.getLangFile().getFile().getString("player.enter-water");
        msg.send(player, message);
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO,1,1);
        int radius = 10;
        for (int x = -(radius); x <= radius; x ++) {
            for (int y = -(radius); y <= radius; y ++)
            {
                for (int z = -(radius); z <= radius; z ++)
                {
                    if (!player.getWorld().getBlockAt(x,y,z).getType().equals(Material.WATER) && !player.getWorld().getBlockAt(x,y,z).equals(Material.AIR)
                    && !player.getWorld().getBlockAt(x,y,z).getType().equals(Material.LAVA)){
                        player.teleport(player.getWorld().getBlockAt(x,y,z).getLocation());
                        return;
                    }
                    player.teleport(main.getGraveyardManager().getClosestGraveyard(player));
                }
            }
        }
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Location toLoc = event.getTo();
        Location fromLoc = event.getFrom();
        if (!player.hasPermission("fr.bypass.restrictedareas")) {
            if (toLoc.getBlock().getType().equals(Material.WATER)) {
                event.setCancelled(true);
                playerAntiSwim(player, toLoc);
                return;
            }
            if (toLoc.getBlock().getType().equals(Material.LAVA)){
                event.setCancelled(true);
                player.teleport(main.getGraveyardManager().getClosestGraveyard(player));
                new RespawnAnimation(main, player);
                return;
            }
        }
    }

}
