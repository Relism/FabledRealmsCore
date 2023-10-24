package net.fabledrealms.dungeon.command;

import net.fabledrealms.Core;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DungeonCommand implements CommandExecutor {

    private final Core main;

    public DungeonCommand(Core main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (args.length == 0) {
            usage(sender);
        } else if (args[0].equalsIgnoreCase("addloot")) {

            if (!(sender instanceof Player player)) return false;
            if (!player.hasPermission("dungeon.command.addloot")) {
                player.sendMessage(this.main.getStringUtil().colorString("&cNo Permissions"));
                return false;
            }

            if (args.length == 1) {
                usage(sender);
            } else {
                int floor = Integer.parseInt(args[1]);

                if (player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                    player.sendMessage(this.main.getStringUtil().colorString("&cItem to be added cannot be null"));
                    return false;
                }

                this.main.getLootManager().addLoot(floor, player.getInventory().getItemInMainHand());
                player.sendMessage(this.main.getStringUtil().colorString("&aSuccessfully added loot item."));
            }
        }

        return true;
    }

    private void usage(CommandSender sender) {}
}
