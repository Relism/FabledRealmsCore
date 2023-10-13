package net.fabledrealms.economy.command;

import net.fabledrealms.Core;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Objects;

public class EconomyCommand implements CommandExecutor {

    private final Core plugin;

    public EconomyCommand(Core plugin) {
        this.plugin = plugin;

        Objects.requireNonNull(this.plugin.getCommand("economy")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {

        if (args.length == 0) {
            usage(sender);
        } else if (args[0].equalsIgnoreCase("set")) {

            if (!(sender.hasPermission("economy.command.set"))) {
                sender.sendMessage(this.plugin.getStringUtil().colorString("&cNo Permissions."));
                return false;
            }

            if (args.length == 1) {
                usage(sender);
                return false;
            } else {
                OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
                if (args.length == 2) {
                    usage(sender);
                } else {
                    int amount = Integer.parseInt(args[2]);

                    this.plugin.getEconomyManager().setPlayerBalance(player.getUniqueId(), amount);
                    sender.sendMessage(this.plugin.getStringUtil().colorString("&aSuccessfully set balance of " + amount + " to player."));
                }
            }
        } else if (args[0].equalsIgnoreCase("add")) {

            if (!(sender.hasPermission("economy.command.add"))) {
                sender.sendMessage(this.plugin.getStringUtil().colorString("&cNo Permissions."));
                return false;
            }

            if (args.length == 1) {
                usage(sender);
                return false;
            } else {
                OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
                if (args.length == 2) {
                    usage(sender);
                } else {
                    int amount = Integer.parseInt(args[2]);

                    this.plugin.getEconomyManager().addPlayerBalance(player.getUniqueId(), amount);
                    sender.sendMessage(this.plugin.getStringUtil().colorString("&aSuccessfully added balance of " + amount + " to player."));
                }
            }
        } else if (args[0].equalsIgnoreCase("remove")) {

            if (!(sender.hasPermission("economy.command.remove"))) {
                sender.sendMessage(this.plugin.getStringUtil().colorString("&cNo Permissions."));
                return false;
            }

            if (args.length == 1) {
                usage(sender);
                return false;
            } else {
                OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
                if (args.length == 2) {
                    usage(sender);
                } else {
                    int amount = Integer.parseInt(args[2]);

                    this.plugin.getEconomyManager().removePlayerBalance(player.getUniqueId(), amount);
                    sender.sendMessage(this.plugin.getStringUtil().colorString("&aSuccessfully removed balance of " + amount + " from player."));
                }
            }
        } else if (args[0].equalsIgnoreCase("check")) {

            if (!(sender.hasPermission("economy.command.check"))) {
                sender.sendMessage(this.plugin.getStringUtil().colorString("&cNo Permissions."));
                return false;
            }

            if (args.length == 1) {
                usage(sender);
                return false;
            } else {
                OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);

                sender.sendMessage(this.plugin.getStringUtil().colorString("&fBalance of &a" + player.getName() + " &7is &a$" + this.plugin.getEconomyManager().getPlayerBalance(player.getUniqueId())));
            }
        }

        return true;
    }

    private void usage(CommandSender sender) {
        sender.sendMessage(this.plugin.getStringUtil().colorString("&f&m-----------------------------------"));
        sender.sendMessage(this.plugin.getStringUtil().colorString("&a/economy set <player> <amount>"));
        sender.sendMessage(this.plugin.getStringUtil().colorString("&a/economy add <player>"));
        sender.sendMessage(this.plugin.getStringUtil().colorString("&a/economy remove <player>"));
        sender.sendMessage(this.plugin.getStringUtil().colorString("&a/economy check <player>"));
        sender.sendMessage(this.plugin.getStringUtil().colorString("&f&m-----------------------------------"));
    }
}
