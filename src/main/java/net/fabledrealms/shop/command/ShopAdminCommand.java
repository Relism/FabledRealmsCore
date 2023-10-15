package net.fabledrealms.shop.command;

import net.fabledrealms.Core;
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

        if (args.length == 0) {
            usage(sender);
        } else if (args[0].equalsIgnoreCase("create")) {

            if (!(sender.hasPermission("shop.command.create"))) {
                sender.sendMessage(this.main.getStringUtil().colorString("&cNo Permissions."));
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
                            sender.sendMessage(this.main.getStringUtil().colorString("&cShop with name " + shopName + " already exists."));
                            return false;
                        }

                        if (this.main.getShopManager().getShopByTitle(shopTitle) != null) {
                            sender.sendMessage(this.main.getStringUtil().colorString("&cShop with title " + shopTitle + " already exists."));
                            return false;
                        }

                        this.main.getShopManager().createShop(shopName, shopTitle, shopSize);
                        sender.sendMessage(this.main.getStringUtil().colorString("&aSuccessfully created " + shopName + " shop."));
                    }
                }
            }
        } else if (args[0].equalsIgnoreCase("delete")) {

            if (!(sender.hasPermission("shop.command.delete"))) {
                sender.sendMessage(this.main.getStringUtil().colorString("&cNo Permissions."));
                return false;
            }

            if (args.length == 1) {
                usage(sender);
            } else {
                String shopName = args[1];

                if (this.main.getShopManager().getShop(shopName) == null) {
                    sender.sendMessage(this.main.getStringUtil().colorString("&cShop with name " + shopName + " doesnt exists."));
                    return false;
                }

                this.main.getShopManager().removeShop(shopName);
                sender.sendMessage(this.main.getStringUtil().colorString("&aSuccessfully deleted " + shopName + " shop."));
            }
        } else if (args[0].equalsIgnoreCase("additem")) {

            if (!(sender.hasPermission("shop.command.additem"))) {
                sender.sendMessage(this.main.getStringUtil().colorString("&cNo Permissions."));
                return false;
            }

            if (!(sender instanceof Player player)) {
                sender.sendMessage(this.main.getStringUtil().colorString("&cYou must be player to execute this command argument."));
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
                        sender.sendMessage(this.main.getStringUtil().colorString("&cShop with name " + shopName + " doesnt exists."));
                        return false;
                    }

                    this.main.getShopManager().getShop(shopName).getShopItems().put(player.getInventory().getItemInMainHand(), price);
                    sender.sendMessage(this.main.getStringUtil().colorString("&aSuccessfully added item to " + shopName + " shop."));
                }
            }
        }

        return true;
    }

    private void usage(CommandSender sender) {
        sender.sendMessage(this.main.getStringUtil().colorString("&f&m-----------------------------------"));
        sender.sendMessage(this.main.getStringUtil().colorString("&a/shop create <name> <title> <size>"));
        sender.sendMessage(this.main.getStringUtil().colorString("&a/shop delete <name>"));
        sender.sendMessage(this.main.getStringUtil().colorString("&a/shop additem <price>"));
        sender.sendMessage(this.main.getStringUtil().colorString("&f&m-----------------------------------"));
    }
}
