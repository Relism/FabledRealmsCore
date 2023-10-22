package net.fabledrealms.lootchests;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import io.lumine.mythic.api.volatilecode.virtual.PacketEntity;
import io.lumine.mythic.bukkit.utils.protocol.Protocol;
import net.fabledrealms.Core;
import net.fabledrealms.itemgen.ItemRarity;
import net.fabledrealms.util.msg;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class LootChestManager {


    private final Core core;
    private final NamespacedKey chestKey;
    private final HashMap<Player, Block> chestRespawnMap;

    public LootChestManager(Core core){
        this.core = core;
        this.chestKey = new NamespacedKey(core,"chestKey");
        this.chestRespawnMap = new HashMap<>();
    }

    public NamespacedKey getChestKey(){return chestKey;}

    public ItemStack getLootChestItem(Player player, ItemRarity rarity){
        ItemStack itemStack = new ItemStack(Material.CHEST);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(msg.translateColorCodes("&e&lLoot Chest"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(rarity.toString());
        itemMeta.setLore(lore);
        itemMeta.getPersistentDataContainer().set(chestKey, PersistentDataType.STRING, rarity.toString());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public Inventory getLootChestInventory(Player player){
        Inventory inventory = Bukkit.createInventory(null, InventoryType.CHEST, msg.translateColorCodes("&eLoot Chest &f- "));
        int contentAmount = new Random().nextInt(1, inventory.getSize() - 1);
        ArrayList<ItemStack> dropTable = new ArrayList<>();//Populate from configured drop tables
        dropTable.add(new ItemStack(Material.AIR));
        dropTable.add(new ItemStack(Material.COOKED_BEEF));
        dropTable.add(new ItemStack(Material.GOLD_NUGGET));
        for (int i = 0; i < contentAmount; i++) {
            int randomItemInt = new Random().nextInt(dropTable.size());
            ItemStack randomItem = dropTable.get(randomItemInt);
            inventory.setItem(i,randomItem);
        }
        return inventory;
    }

    public void redeemLootChest(Player player, Block lootChest){
        player.sendBlockChange(lootChest.getLocation(), Material.AIR.createBlockData());
        player.openInventory(getLootChestInventory(player));
        chestRespawnRunnable(player,lootChest);
    }

    private void chestRespawnRunnable(Player player,Block lootChest){
        long cooldown = 300l;
        Bukkit.getScheduler().scheduleSyncDelayedTask(core, new Runnable() {
            @Override
            public void run() {
                //Send player packets revealing the chest
                player.sendBlockChange(lootChest.getLocation(),Material.CHEST.createBlockData());
            }
        },cooldown);
    }


}
