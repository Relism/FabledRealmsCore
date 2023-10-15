package net.fabledrealms.command;

import net.fabledrealms.Core;
import net.fabledrealms.command.base.CoreCommand;
import net.fabledrealms.util.Constants;
import net.fabledrealms.util.msg;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuestsCommand extends Constants implements CoreCommand {

    private Core core;
    public QuestsCommand(Core core) {
        this.core = core;
    }

    @Override
    public String getName() {
        return "quests";
    }

    @Override
    public String getPermission() {
        return "core.quests";
    }

    @Override
    public String[] getUsage() {
        return new String[] {
                "&c/quests create <name>",
                "&c/quests delete <name>",
                "&c/quests <name> points add",
                "&c/quests <name> points remove [id]",
                "&c/quests <name> objective create <name>",
                "&c/quests <name> objective command add <command>",
                "&c/quests <name> objective command remove <command>"
        };
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {

        if(!(sender instanceof Player)) return;
        Player p = (Player) sender;
        if(!p.hasPermission(getPermission())) {
            msg.send(p, Objects.requireNonNull(core.getLangFile().getFile().getString("system.no-permission"))
                    .replace("%%prefix%", super.prefix));
            return;
        }

        if(args.length == 0) {
            for(String usage : getUsage()) {
                msg.send(p, usage);
            }
        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
