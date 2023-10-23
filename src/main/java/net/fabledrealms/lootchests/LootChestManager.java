package net.fabledrealms.lootchests;

import net.fabledrealms.Core;
import net.fabledrealms.itemgen.ItemRarity;
import net.fabledrealms.lootchests.droptables.DropTable;
import net.fabledrealms.util.msg;
import net.fabledrealms.wrappers.FileWrapper;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class LootChestManager {


    private final Core core;
    private final NamespacedKey chestKey;
    private final HashMap<LootChest,Location> chestMap;
    private final FileWrapper chestWrapper;
    private final FileWrapper chestDropTableWrapper;

    public LootChestManager(Core core){
        this.core = core;
        this.chestKey = new NamespacedKey(core,"chestKey");
        this.chestMap = new HashMap<>();
        this.chestWrapper = new FileWrapper(core,core.getDataFolder().getPath(), "loot-chests.yml");
        this.chestDropTableWrapper = new FileWrapper(core,core.getDataFolder().getPath(), "loot-chest-drop-tables.yml");
        loadChests();
    }

    public void addChest(LootChest lootChest){
        chestMap.put(lootChest, lootChest.getLocation());
    }

    public void removeChest(LootChest lootChest){
        chestMap.remove(lootChest);
        chestWrapper.getFile().getKeys(false).remove(String.valueOf(lootChest.getID()));
    }

    public NamespacedKey getChestKey(){return chestKey;}

    private void loadChests(){
        for (String keys : chestWrapper.getFile().getKeys(false)){
            int ID = Integer.parseInt(keys);
            Material material = Material.valueOf(chestWrapper.getFile().getString(keys + ".material"));
            World world = Bukkit.getWorld(chestWrapper.getFile().getString(keys + ".location.world"));
            int x = chestWrapper.getFile().getInt(keys +".location.x");
            int y = chestWrapper.getFile().getInt(keys +".location.y");
            int z = chestWrapper.getFile().getInt(keys +".location.z");
            Location location = new Location(world,x,y,z);
            DropTable dropTable = DropTable.valueOf(chestWrapper.getFile().getString(keys+".drop-table").toUpperCase());
            LootChest lootChest = new LootChest(core,ID,material,location,dropTable);
            chestMap.put(lootChest,location);
        }
    }

    public HashMap<LootChest,Location> getChestMap(){return chestMap;}

    public LootChest getChestByLocation(Location location){
        for (LootChest lootChest : chestMap.keySet()){
            if (lootChest.getLocation().equals(location)){
                return lootChest;
            }
        }
        return null;
    }

    public ItemStack getLootChestItem(Player player, DropTable dropTable){
        ItemStack itemStack = new ItemStack(Material.CHEST);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(msg.translateColorCodes("&e&lLoot Chest"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(dropTable.getLabel());
        itemMeta.setLore(lore);
        itemMeta.getPersistentDataContainer().set(chestKey, PersistentDataType.STRING, dropTable.getLabel());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public Inventory getLootChestInventory(Player player, LootChest lootChest){
        Inventory inventory = Bukkit.createInventory(null, InventoryType.CHEST, msg.translateColorCodes("&eLoot Chest &f- "));
        int contentAmount = new Random().nextInt(1, inventory.getSize() - 1);
        DropTable dropTable = lootChest.getDropTable();
        ArrayList<ItemStack> dropList = new ArrayList();
        for (ItemStack item : dropTable.getDrops()){
            dropList.add(item);
        }
        for (int i = 0; i < contentAmount; i++) {
            int randomItemInt = new Random().nextInt(dropList.size());
            ItemStack randomItem = dropList.get(randomItemInt);
            inventory.setItem(i,randomItem);
        }
        return inventory;
    }

    public void redeemLootChest(Player player, Block lootChest){
        player.sendBlockChange(lootChest.getLocation(), Material.AIR.createBlockData());
        player.openInventory(getLootChestInventory(player,getChestByLocation(lootChest.getLocation())));
        chestRespawnRunnable(player,lootChest);
    }

    private void chestRespawnRunnable(Player player,Block lootChest){
        long cooldown = 300l;
        Bukkit.getScheduler().scheduleSyncDelayedTask(core, new Runnable() {
            @Override
            public void run() {
                lootChest.setType(getChestByLocation(lootChest.getLocation()).getMaterial());
            }
        },cooldown);
    }


}
