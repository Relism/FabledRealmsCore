package net.fabledrealms.command.base;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface CoreCommand {

    String getName();
    String getPermission();
    String[] getUsage();
    void executeCommand(CommandSender sender, String[] args);
    List<String> onTabComplete(CommandSender sender, String[] args);
}
