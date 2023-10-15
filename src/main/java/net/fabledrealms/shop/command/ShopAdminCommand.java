package net.fabledrealms.shop.command;

import net.fabledrealms.Core;
import net.fabledrealms.util.msg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class ShopAdminCommand implements CommandExecutor {

    private final Core main;

    public ShopAdminCommand(Core main){
        this.main = main;

        Objects.requireNonNull(this.main.getCommand("shopadmin")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        Player psender = (Player) sender;
        if (args.length == 0) {
            usage(sender);
        } else if (args[0].equalsIgnoreCase("create")) {

            if (!(sender.hasPermission("shop.command.create"))) {
                msg.send(psender, "&cNo Permissions.");
                return false;
            }

            if (args.length == 1) {
                usage(sender);
            } else {
                String shopName = args[1];
                if (args.length == 2) {
                    usage(sender);
                } else {
                    String shopTitle = args[2];
                    if (args.length == 3) {
                        usage(sender);
                    } else {
                        int shopSize = Integer.parseInt(args[3]);

                        if (this.main.getShopManager().getShop(shopName) != null) {
                            msg.send(psender, "&cShop with name " + shopName + " already exists.");
                            return false;
                        }

                        if (this.main.getShopManager().getShopByTitle(shopTitle) != null) {
                            msg.send(psender, "&cShop with title " + shopTitle + " already exists.");
                            return false;
                        }

                        this.main.getShopManager().createShop(shopName, shopTitle, shopSize);
                        msg.send(psender, "&aSuccessfully created " + shopName + " shop.");
                    }
                }
            }
        } else if (args[0].equalsIgnoreCase("delete")) {

            if (!(sender.hasPermission("shop.command.delete"))) {
                msg.send(psender, "&cNo Permissions.");
                return false;
            }

            if (args.length == 1) {
                usage(sender);
            } else {
                String shopName = args[1];

                if (this.main.getShopManager().getShop(shopName) == null) {
                    msg.send(psender, "&cShop with name " + shopName + " doesnt exist.");
                    return false;
                }

                this.main.getShopManager().removeShop(shopName);
                msg.send(psender, "&aSuccessfully deleted " + shopName + " shop.");
            }
        } else if (args[0].equalsIgnoreCase("additem")) {

            if (!(sender.hasPermission("shop.command.additem"))) {
                msg.send(psender, "&cNo Permissions.");
                return false;
            }

            if (!(sender instanceof Player player)) {
                msg.send(psender, "&cYou must be player to execute this command argument.");
                return false;
            }

            if (args.length == 1) {
                usage(sender);
            } else {
                String shopName = args[1];

                if (args.length == 2) {
                    usage(sender);
                } else {
                    int price = Integer.parseInt(args[2]);

                    if (this.main.getShopManager().getShop(shopName) == null) {
                        msg.send(psender, "&cShop with name " + shopName + " doesnt exists.");
                        return false;
                    }

                    this.main.getShopManager().getShop(shopName).getShopItems().put(player.getInventory().getItemInMainHand(), price);
                    msg.send(psender, "&aSuccessfully added item to " + shopName + " shop.");
                }
            }
        }

        return true;
    }

    private void usage(CommandSender sender) {
        Player psender = (Player) sender;
        msg.send(psender, "&f&m-----------------------------------");
        msg.send(psender, "&a/shop create <name> <title> <size>");
        msg.send(psender, "&a/shop delete <name>");
        msg.send(psender, "&a/shop additem <price>");
        msg.send(psender, "&f&m-----------------------------------");
    }
}
