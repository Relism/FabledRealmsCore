package net.fabledrealms.dungeon.listener;

import net.fabledrealms.Core;
import net.fabledrealms.dungeon.DungeonLocation;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class DungeonListener implements Listener {

    private final Core main;

    public DungeonListener(Core main) {
        this.main = main;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        for (World world : this.main.getDungeonManager().getCurrentWorlds()) {
            if (event.getPlayer().getWorld().getName().equalsIgnoreCase(world.getName())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBreak(BlockPlaceEvent event) {
        for (World world : this.main.getDungeonManager().getCurrentWorlds()) {
            if (event.getPlayer().getWorld().getName().equalsIgnoreCase(world.getName())) {
                event.setCancelled(true);
            }
        }
    }
}
