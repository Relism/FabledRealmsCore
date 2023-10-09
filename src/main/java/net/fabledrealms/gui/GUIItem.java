package net.fabledrealms.gui;

import net.fabledrealms.Core;
import net.fabledrealms.wrappers.FileWrapper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GUIItem {

    private final Core main;
    private FileWrapper guiItemWrapper;

    public GUIItem(Core main){
        this.main = main;
        this.guiItemWrapper = main.getGuiItemWrapper();
    }

    public ItemStack generateItemStack(String key){
        ItemStack generated = new ItemStack(Material.valueOf(main.getGuiItemWrapper().getFile().getString(key + ".type").toUpperCase()));
        if (main.getGuiItemWrapper().getFile().getKeys(true).contains(key+"item-meta")){
            ItemMeta meta = generated.getItemMeta();
            meta.setDisplayName(main.getStringUtil().colorString(main.getGuiItemWrapper().getFile().getString(key+".item-meta.display-name")));
            ArrayList<String> lore = new ArrayList<>();
            for (String input : main.getGuiItemWrapper().getFile().getStringList(key+".item-meta.lore")){
                lore.add(main.getStringUtil().colorString(input));
            }
            meta.setLore(lore);
            meta.setCustomModelData(main.getGuiItemWrapper().getFile().getInt(key+".item-meta.custom-model-data"));
            generated.setItemMeta(meta);
        }
        return generated;
    }


}
