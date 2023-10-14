package net.fabledrealms.listeners;

import net.fabledrealms.Core;
import net.fabledrealms.gui.GUIItem;
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

    private void characterSelection(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        Inventory clicked = event.getClickedInventory();
        if (clicked.getItem(event.getRawSlot()).equals(new GUIItem(main).generateItemStack("locked"))){return;}
        if (clicked.getItem(event.getRawSlot()).equals(new GUIItem(main).generateItemStack("unlocked"))){
            //Start character creator
            return;
        }
        player.sendMessage(main.getStringUtil().colorString(main.getLangFile().getFile().getString("player.load-character")));
        }

    @EventHandler
    public void onLockedInventoryClick(InventoryClickEvent event){
        Inventory clicked = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();
        if (main.getGuiManager().isInventoryLocked(player)){
            if (clicked.equals(main.getGuiManager().getMappedInventory((player)))){
                event.setCancelled(true);
                if (event.getView().getTitle().equals(main.getStringUtil().colorString(main.getLangFile().getFile().getString("gui.character-selection-title")))){

                }
            }
        }
    }
}
