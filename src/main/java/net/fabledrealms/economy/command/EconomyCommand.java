package net.fabledrealms.economy.command;

import net.fabledrealms.Core;
import net.fabledrealms.util.msg;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class EconomyCommand implements CommandExecutor {

    private final Core plugin;

    public EconomyCommand(Core plugin) {
        this.plugin = plugin;

        Objects.requireNonNull(plugin.getCommand("economy")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {

        Player psender = (Player) sender;
        if (args.length == 0) {
            usage(sender);
        } else if (args[0].equalsIgnoreCase("set")) {

            if (!(sender.hasPermission("economy.command.set"))) {
                msg.send(psender, "&cNo Permissions.");
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

                    this.plugin.getEconomyManager().setPlayerBalance(player.getUniqueId(),plugin.getCharacterManager().getCharacter((Player) player).getCharacterID(), amount);
                    msg.send(psender, "&aSuccessfully set balance of " + amount + " to player.");
                }
            }
        } else if (args[0].equalsIgnoreCase("add")) {

            if (!(sender.hasPermission("economy.command.add"))) {
                msg.send(psender, "&cNo Permissions.");
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

                    this.plugin.getEconomyManager().addPlayerBalance(player.getUniqueId(),plugin.getCharacterManager().getCharacter((Player) player).getCharacterID(), amount);
                    msg.send(psender, "&aSuccessfully added balance of " + amount + " to player.");
                }
            }
        } else if (args[0].equalsIgnoreCase("remove")) {

            if (!(sender.hasPermission("economy.command.remove"))) {
                msg.send(psender, "&cNo Permissions.");
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

                    this.plugin.getEconomyManager().removePlayerBalance(player.getUniqueId(),plugin.getCharacterManager().getCharacter((Player) player).getCharacterID(), amount);
                    msg.send(psender, "&aSuccessfully removed balance of " + amount + " from player.");
                }
            }
        } else if (args[0].equalsIgnoreCase("check")) {

            if (!(sender.hasPermission("economy.command.check"))) {
                msg.send(psender, "&cNo Permissions.");
                return false;
            }

            if (args.length == 1) {
                usage(sender);
                return false;
            } else {
                OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);

                msg.send(psender, "&fBalance of &a" + player.getName() + " &7is &a$" + this.plugin.getEconomyManager()
                        .getPlayerBalance(player.getUniqueId(), plugin.getCharacterManager()
                                .getCharacter((Player) player)
                                .getCharacterID())
                );
            }
        }

        return true;
    }

    private void usage(CommandSender sender) {
        Player psender = (Player) sender;
        msg.send(psender, "&f&m-----------------------------------");
        msg.send(psender, "&a/economy set <player> <amount>");
        msg.send(psender, "&a/economy add <player>");
        msg.send(psender, "&a/economy remove <player>");
        msg.send(psender, "&a/economy check <player>");
        msg.send(psender, "&f&m-----------------------------------");
    }
}
