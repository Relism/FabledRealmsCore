package net.fabledrealms.shop.manager;

import net.fabledrealms.Core;
import net.fabledrealms.shop.Shop;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ShopManager {

    private final Core main;
    private final Set<Shop> shops = new HashSet<>();

    public ShopManager(Core main) {
        this.main = main;
    }

    public void loadShops(){
        if (this.main.getShopFileWrapper().getFile().getConfigurationSection("shops") == null) return;
        for (final String i : Objects.requireNonNull(this.main.getShopFileWrapper().getFile().getConfigurationSection("shops")).getKeys(false)) {
            shops.add(new Shop(this.main.getShopFileWrapper().getFile().getString("shops." + i + ".name"),
                    this.main.getShopFileWrapper().getFile().getString("shops." + i + ".title"),
                    this.main.getShopFileWrapper().getFile().getInt("shops." + i + ".size"), getItems(i)));
        }
    }

    private Map<ItemStack, Integer> getItems(String i){
        Map<ItemStack, Integer> itemStackMap = new HashMap<>();
        for (final String items : Objects.requireNonNull(this.main.getShopFileWrapper().getFile().getConfigurationSection("shops" + i + ".items")).getKeys(false)) {
            itemStackMap.put(this.main.getShopFileWrapper().getFile().getItemStack("shops." + i + ".items." + items + ".item"),
                    this.main.getShopFileWrapper().getFile().getInt("shops." + i + ".items." + items + ".price"));
        }
        return itemStackMap;
    }

    public Set<Shop> getShops() {
        return shops;
    }

    public void createShop(String shopName, String shopTitle, int shopSize) {
        this.shops.add(new Shop(shopName, shopTitle, shopSize, new HashMap<>()));
    }

    public void removeShop(String shopName) {

    }

    public Shop getShop(String shopName) {
        for (final Shop shop : shops) {
            if (shop.getShopName().equalsIgnoreCase(shopName)) {
                return shop;
            }
        }

        return null;
    }

    public Shop getShopByTitle(String shopTitle) {
        for (final Shop shop : shops) {
            if (shop.getShopTitle().equalsIgnoreCase(shopTitle)) {
                return shop;
            }
        }

        return null;
    }
}
