package net.fabledrealms.itemgen;

import net.fabledrealms.Core;
import org.bukkit.Material;

public class FabledItem {

    private Core core;
    private String displayName;
    private String hash;
    private String loreTemplate;
    private Material material;
    private ItemType itemType;
    private ItemRarity itemRarity;
    private ItemSpawnReason itemSpawnReason;
    private int ID;
    private int amount;

    public FabledItem(Core core, String displayName, String hash, String loreTemplate, Material material,
                      ItemType itemType,ItemRarity itemRarity,ItemSpawnReason itemSpawnReason, int ID, int amount){
        this.core = core;
        this.displayName = displayName;
        this.hash = hash;
        this.loreTemplate = loreTemplate;
        this.material = material;
        this.itemType = itemType;
        this.itemRarity = itemRarity;
        this.itemSpawnReason = itemSpawnReason;
        this.ID = ID;
        this.amount = amount;
    }

}
