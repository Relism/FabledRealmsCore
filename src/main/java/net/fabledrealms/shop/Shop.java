package net.fabledrealms.shop;

import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class Shop {

    private final String shopName;
    private final String shopTitle;
    private final int shopSize;
    private final Map<ItemStack, Integer> shopItems;

    public Shop(String shopName, String shopTitle, int shopSize, Map<ItemStack, Integer> shopItems) {
        this.shopName = shopName;
        this.shopTitle = shopTitle;
        this.shopSize = shopSize;
        this.shopItems = shopItems;
    }

    public int getShopSize() {
        return shopSize;
    }

    public Map<ItemStack, Integer> getShopItems() {
        return shopItems;
    }

    public String getShopName() {
        return shopName;
    }

    public String getShopTitle() {
        return shopTitle;
    }
}
