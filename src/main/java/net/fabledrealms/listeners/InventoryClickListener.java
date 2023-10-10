package net.fabledrealms.listeners;

import net.fabledrealms.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryClickListener implements Listener {

    private final Core main;

    public InventoryClickListener(Core main){
        this.main = main;
        Bukkit.getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onLockedInventoryClick(InventoryClickEvent event){
        Inventory clicked = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();
        if (main.getGuiManager().isInventoryLocked(player)){
            if (clicked.equals(main.getGuiManager().getMappedInventory((player)))){
                event.setCancelled(true);
            }
        }
    }
}
