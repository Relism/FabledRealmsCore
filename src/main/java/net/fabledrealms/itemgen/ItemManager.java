package net.fabledrealms.itemgen;

import net.fabledrealms.Core;
import net.fabledrealms.itemgen.antidupe.HashGenerator;
import net.fabledrealms.util.msg;
import net.fabledrealms.wrappers.FileWrapper;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class ItemManager {

    private final Core core;
    private final HashMap<FabledItem, ItemStack> fabledItemMap;
    public ItemManager(Core core){
        this.core = core;
        this.fabledItemMap = new HashMap<>();
        generateItems();
    }

    private void generateItems(){
        //Generate Files + Load files to map
        for (ItemRarity rarity : ItemRarity.values()){
            for (ItemType type : ItemType.values()){
                FileWrapper itemFileWrapper = new FileWrapper(core,core.getDataFolder() + File.separator + "Items" +
                        File.separator + rarity.toString().toLowerCase() + File.separator + type.toString().toLowerCase(), "example-item.yml");
            }
        }
    }

    private void loadItems(ItemRarity rarity, ItemType type){
        File itemFile = new File(core.getDataFolder() + File.separator + "Items" + File.separator + rarity.toString().toLowerCase() + File.separator + type.toString().toLowerCase());
        int i = 1;
        for (File file : itemFile.listFiles()){
            FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
            FabledItem item = createItem(rarity,type,file.getName());
            ItemStack itemStack = createItemStack(fileConfig);
            fabledItemMap.put(item,itemStack);
            i++;
        }
    }

    private FabledItem createItem(ItemRarity rarity, ItemType type, String itemKey){
        String path = core.getDataFolder() + File.separator + "Items" + File.separator + rarity.toString().toLowerCase() + File.separator + type.toString().toLowerCase();
        FileWrapper itemWrapper = new FileWrapper(core,path,itemKey + ".yml");
        //TODO level scaling system -- Gets character's level and adds multiplyer to all attributes
        String displayName = itemWrapper.getFile().getString("display-name");
        String loreTemplate = itemWrapper.getFile().getString("lore-template");
        Material material = Material.valueOf(itemWrapper.getFile().getString("material").toUpperCase());
        FabledItem item = new FabledItem(core, displayName,loreTemplate,material,type, rarity);
        return item;
    }

    private ArrayList<String> getItemLore(String loreTemplate){
        FileWrapper loreFileWrapper = new FileWrapper(core, core.getDataFolder() + File.separator + "Items", "lore-templates.yml");
        ArrayList<String> lore = (ArrayList<String>) loreFileWrapper.getFile().getStringList(loreTemplate);
        for (String line : lore){
           line = msg.translateColorCodes(line);
           //TODO placeholder support
        }
        return lore;
    }

    private ItemStack createItemStack(FileConfiguration itemConfig){
        String displayName = itemConfig.getString("display-name");
        Material material = Material.valueOf(itemConfig.getString("material").toUpperCase());
        String loreTemplate = itemConfig.getString("lore-template");
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(msg.translateColorCodes(displayName));
        itemMeta.setLore(getItemLore(loreTemplate));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
