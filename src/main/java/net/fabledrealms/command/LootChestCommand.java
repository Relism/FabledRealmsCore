package net.fabledrealms.command;

import net.fabledrealms.Core;
import net.fabledrealms.command.base.CoreCommand;
import net.fabledrealms.itemgen.ItemRarity;
import net.fabledrealms.lootchests.droptables.DropTable;
import net.fabledrealms.util.Constants;
import net.fabledrealms.util.msg;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

public class LootChestCommand extends Constants implements CoreCommand {

    private Core core;

    public LootChestCommand(Core core) {
        this.core = core;
    }

    @Override
    public String getName() {
        return "lootchest";
    }

    @Override
    public String getPermission() {
        return "fr.admin";
    }

    @Override
    public String[] getUsage() {
        return new String[]{
                "/lootchest create <rarity>"
        };
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return;

        Player player = (Player) sender;
        if(!player.hasPermission(getPermission())) {
            msg.send(player, Objects.requireNonNull(core.getLangFile().getFile().getString("system.no-permission"))
                    .replace("%%prefix%", super.prefix));
            return;
        }
        ItemStack lootChestItem = core.getLootChestManager().getLootChestItem(player, DropTable.valueOf(args[0].toUpperCase()));
        player.getInventory().setItem(EquipmentSlot.HAND,lootChestItem);


        if (args.length == 0){
            for(String usage : getUsage()) {
                msg.send(player, usage);
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
