package net.fabledrealms.listeners;

import net.fabledrealms.Core;
import net.fabledrealms.gui.CharacterSelectionGUI;
import net.fabledrealms.lootchests.LootChest;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerInteractListener implements Listener {

    private final Core main;

    public PlayerInteractListener(Core main){
        this.main = main;
        Bukkit.getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void playerInteractWithNPC(PlayerInteractAtEntityEvent event){
        Player rightClicked = null;
        if (event.getRightClicked() instanceof Player){rightClicked = (Player) event.getRightClicked();}
        if (rightClicked.equals(null)){return;}
        if (rightClicked.getName().equals(main.getConfigFile().getFile().getString("module.character-creation.npc"))){
            main.getGuiManager().openInventory(event.getPlayer(), new CharacterSelectionGUI(main, event.getPlayer()).getInventory());
        }
    }

    @EventHandler
    public void onRightClickBlock(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Block block = event.getClickedBlock();
            if (event.getHand().equals(EquipmentSlot.OFF_HAND))return;
            if (block.getState() instanceof TileState) {
                if (!main.getLootChestManager().getChestByLocation(block.getLocation()).equals(null)) {
                    if (player.isSneaking() && player.hasPermission("fr.admin")) {
                        event.setCancelled(true);
                        LootChest lootChest = main.getLootChestManager().getChestByLocation(block.getLocation());
                        main.getLootChestManager().removeChest(lootChest);
                        block.setType(Material.AIR);
                    }
                    if (block.getType().equals(Material.CHEST) && !block.getMetadata("chestKey").equals(null)) {
                        event.setCancelled(true);
                        main.getLootChestManager().redeemLootChest(player, block);
                    }
                }
            }
        }
    }


}
