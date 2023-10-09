package net.fabledrealms.gui;

import net.fabledrealms.Core;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class GUIManager {

    private final Core main;

    private HashMap<Player, Inventory> guiHashMap;

    public GUIManager(Core main){
        this.main = main;
        this.guiHashMap = new HashMap<>();
    }

    public void addPlayer(Player player, Inventory inventory){
        guiHashMap.put(player, inventory);
    }

    public void removePlayer(Player player){
        guiHashMap.remove(player);
    }

    public boolean isInventoryLocked(Player player){
        return guiHashMap.containsKey(player);
    }
}
