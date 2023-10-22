package net.fabledrealms.command.manager;

import net.fabledrealms.Core;
import net.fabledrealms.command.LootChestCommand;
import net.fabledrealms.command.QuestsCommand;
import net.fabledrealms.command.base.CoreCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommandManager implements CommandExecutor, TabCompleter {

    private final Core core;
    private Set<CoreCommand> commands = new HashSet<>();

    public CommandManager(Core core) {
        this.core = core;

        commands.add(new QuestsCommand(core));
        commands.add(new LootChestCommand(core));
        for(CoreCommand command : commands) {
            core.getCommand(command.getName()).setExecutor(this);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        for(CoreCommand cmd : commands) {
            if(command.getName().equalsIgnoreCase(cmd.getName())) {
                cmd.executeCommand(sender, args);
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        for(CoreCommand cmd : commands) {
            if(command.getName().equalsIgnoreCase(cmd.getName())) {
                return cmd.onTabComplete(sender, args);
            }
        }
        return new ArrayList<>();
    }
}
