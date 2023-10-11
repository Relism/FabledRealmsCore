package net.fabledrealms.gui;

import net.fabledrealms.Core;
import net.fabledrealms.character.Character;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;


public class CharacterSelectionGUI {

    private final Core main;
    private final Player player;

    public CharacterSelectionGUI(Core main, Player player){
        this.main = main;
        this.player = player;
    }

    public Inventory getInventory(){
        Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER,main.getStringUtil().colorString(main.getLangFile().getFile().getString("gui.character-creation-title")));
        if (!player.hasPermission("character.slot.4")){inventory.setItem(3,new GUIItem(main).generateItemStack("locked"));}
        if (!player.hasPermission("character.slot.staff")){inventory.setItem(4,new GUIItem(main).generateItemStack("locked"));}
        for (int i = 0; i < 3; i++){
            Character slotCharacter = main.getCharacterManager().getCharacter(player,i+1);
            ItemStack item;
            if (slotCharacter.equals(null)){item = new GUIItem(main).generateItemStack("unlocked");}
            else{
                item = new GUIItem(main).generateItemStack("character");
                ItemMeta meta = item.getItemMeta();
                String name = main.getStringUtil().characterPlaceholder(slotCharacter,"id", meta.getDisplayName());
                name = main.getStringUtil().colorString(name);
                meta.setDisplayName(name);
                ArrayList<String> lore = new ArrayList<>();
                for (String line : lore){
                    line = main.getStringUtil().characterPlaceholder(slotCharacter,"id", line);
                    line = main.getStringUtil().characterPlaceholder(slotCharacter,"level", line);
                    line = main.getStringUtil().characterPlaceholder(slotCharacter,"balance", line);
                    line = main.getStringUtil().characterPlaceholder(slotCharacter,"exp", line);
                    line = main.getStringUtil().characterPlaceholder(slotCharacter, "class", line);
                    line = main.getStringUtil().colorString(line);
                }
                meta.setLore(lore);
                item.setItemMeta(meta);
            }
            inventory.setItem(i,item);
        }
        return inventory;
    }

}
