package net.fabledrealms.lootchests.droptables;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum DropTable {

    COMMON("&aCommon", new ItemStack[]{new ItemStack(Material.GOLD_NUGGET, 10), new ItemStack(Material.ACACIA_BUTTON)});


    private String label;
    private ItemStack[] drops;
    DropTable(String s, ItemStack[] items) {
        this.label = s;
        this.drops = items;
    }

    public String getLabel(){return label;}
    public ItemStack[] getDrops(){return drops;}
}
