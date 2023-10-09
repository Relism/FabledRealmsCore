package net.fabledrealms.gui;

import net.fabledrealms.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import javax.naming.InvalidNameException;

public class CharacterCreationGUI {

    private final Core main;

    public CharacterCreationGUI(Core main, Player player){
        this.main = main;
    }

    public Inventory getInventory(){
        Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER,main.getStringUtil().colorString(main.getLangFile().getFile().getString("gui.character-creation-title")));
        for (int i = 0; i <= inventory.getSize() - 1; i++){
            inventory.setItem(i, new GUIItem(main).generateItemStack("locked"));
        }
        return inventory;
    }

}
