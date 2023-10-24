package net.fabledrealms.dungeon.command;

import net.fabledrealms.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class DungeonCommand implements CommandExecutor {

    private final Core main;

    public DungeonCommand(Core main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {



        return true;
    }
}
