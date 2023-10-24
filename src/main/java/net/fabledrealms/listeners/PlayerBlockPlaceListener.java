package net.fabledrealms.listeners;

import net.fabledrealms.Core;
import net.fabledrealms.lootchests.LootChest;
import net.fabledrealms.lootchests.droptables.DropTable;
import net.fabledrealms.util.msg;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class PlayerBlockPlaceListener implements Listener {

    private final Core core;

    public PlayerBlockPlaceListener(Core core){
        this.core = core;
        Bukkit.getPluginManager().registerEvents(this, core);
    }

    @EventHandler
    public void onPlayerPlaceLootChest(BlockPlaceEvent event){
        Player player = event.getPlayer();
        Block place = event.getBlockPlaced();
        if (event.getHand().equals(EquipmentSlot.OFF_HAND))return;
        if (player.getInventory().getItemInMainHand().equals(core.getLootChestManager().getLootChestItem(player, DropTable.COMMON))){
            MetadataValue chestKeyValue = new FixedMetadataValue(core,core.getLootChestManager().getChestKey());
            place.setMetadata("chestKey", chestKeyValue);
            int id = core.getLootChestManager().getChestMap().size() + 1;
            LootChest lootChest = new LootChest(core,id,place.getType(),place.getLocation(),DropTable.COMMON);
            core.getLootChestManager().addChest(lootChest);
            msg.send(player,"&e&lLoot Chest &f>>> &aYou placed a loot chest.");
        }
    }

}
