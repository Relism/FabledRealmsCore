package net.fabledrealms.dungeon.listener;

import net.fabledrealms.Core;
import net.fabledrealms.dungeon.DungeonLocation;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Map;
import java.util.UUID;

public class DungeonListener implements Listener {

    private final Core main;

    public DungeonListener(Core main) {
        this.main = main;

        Bukkit.getPluginManager().registerEvents(this, this.main);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        for (Map.Entry<UUID, World> world : this.main.getDungeonManager().getDungeonWorlds().entrySet()) {
            if (event.getPlayer().getWorld().getName().equalsIgnoreCase(world.getValue().getName())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBreak(BlockPlaceEvent event) {
        for (Map.Entry<UUID, World> world : this.main.getDungeonManager().getDungeonWorlds().entrySet()) {
            if (event.getPlayer().getWorld().getName().equalsIgnoreCase(world.getValue().getName())) {
                event.setCancelled(true);
            }
        }
    }
}
