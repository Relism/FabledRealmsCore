package net.fabledrealms.shop.listener;

import net.fabledrealms.Core;
import net.fabledrealms.shop.Shop;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ShopListener implements Listener {

    private final Core main;

    public ShopListener(Core main) {
        this.main = main;
    }

    @EventHandler
    public void onShopInventoryClick(InventoryClickEvent event){
        for (Shop shop : this.main.getShopManager().getShops()) {
            if (event.getView().getTitle().equalsIgnoreCase(this.main.getStringUtil().colorString(shop.getShopTitle()))) {
                event.setCancelled(true);
            }
        }
    }
}
