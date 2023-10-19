package net.fabledrealms.itemgen;

import net.fabledrealms.Core;
import net.fabledrealms.wrappers.FileWrapper;

import java.io.File;
import java.util.HashMap;

public class ItemManager {

    private final Core core;

    public ItemManager(Core core){
        this.core = core;
        generateItemFiles();
    }

    private void generateItemFiles(){
        for (ItemRarity rarity : ItemRarity.values()){
            for (ItemType type : ItemType.values()){
                FileWrapper itemFileWrapper = new FileWrapper(core,core.getDataFolder() + File.separator +
                        rarity.toString().toLowerCase() + File.separator + type.toString().toLowerCase(), "example-item.yml");
            }
        }
    }

    public void createItem(ItemRarity rarity, ItemType type, String itemKey){
        String path = rarity.toString().toLowerCase() + File.separator + type.toString().toLowerCase() + File.separator + itemKey + ".yml";
    }

}
