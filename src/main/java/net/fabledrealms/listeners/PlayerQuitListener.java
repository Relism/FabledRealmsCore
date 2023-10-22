package net.fabledrealms.listeners;

import net.fabledrealms.Core;
import net.fabledrealms.bossbar.BossbarType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final Core core;

    public PlayerQuitListener(Core core){
        this.core = core;
        Bukkit.getPluginManager().registerEvents(this, core);
    }

    @EventHandler
    public void onPlayerQuitServer(PlayerQuitEvent event){
        Player player = event.getPlayer();
        bossbar.bbarManager bbarManager = new bossbar.bbarManager(core, player);
        bbarManager.remove(BossbarType.COMPASS);
    }

}
