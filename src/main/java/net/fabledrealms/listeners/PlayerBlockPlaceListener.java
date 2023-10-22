package net.fabledrealms.listeners;

import net.fabledrealms.Core;
import net.fabledrealms.itemgen.ItemRarity;
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

import java.util.UUID;

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
        if (player.getInventory().getItemInMainHand().equals(core.getLootChestManager().getLootChestItem(player, ItemRarity.COMMON))){
            MetadataValue chestKeyValue = new FixedMetadataValue(core,core.getLootChestManager().getChestKey());
            place.setMetadata("chestKey", chestKeyValue);
            msg.send(player,"&e&lLoot Chest &f>>> &aYou placed a loot chest.");
        }
    }

}
