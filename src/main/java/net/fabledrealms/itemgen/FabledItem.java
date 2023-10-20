package net.fabledrealms.itemgen;

import net.fabledrealms.Core;
import org.bukkit.Material;

public class FabledItem {

    private Core core;
    private String displayName;
    private String loreTemplate;
    private Material material;
    private ItemType itemType;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLoreTemplate() {
        return loreTemplate;
    }

    public void setLoreTemplate(String loreTemplate) {
        this.loreTemplate = loreTemplate;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public ItemRarity getItemRarity() {
        return itemRarity;
    }

    public void setItemRarity(ItemRarity itemRarity) {
        this.itemRarity = itemRarity;
    }


    private ItemRarity itemRarity;
    private int amount;

    public FabledItem(Core core, String displayName, String loreTemplate, Material material,
                      ItemType itemType,ItemRarity itemRarity){
        this.core = core;
        this.displayName = displayName;
        this.loreTemplate = loreTemplate;
        this.material = material;
        this.itemType = itemType;
        this.itemRarity = itemRarity;
    }

}
