package net.fabledrealms.shop.command;

import net.fabledrealms.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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

                        this.main.getShopManager().createShop(shopName, shopTitle, shopSize);
                    }
                }
            }
        }

        return true;
    }

    private void usage(CommandSender sender) {

    }
}
