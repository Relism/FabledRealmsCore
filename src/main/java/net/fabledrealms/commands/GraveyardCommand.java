package net.fabledrealms.commands;

import net.fabledrealms.Core;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GraveyardCommand implements CommandExecutor {

    private final Core main;

    public GraveyardCommand(Core main){
        this.main = main;
        Bukkit.getPluginCommand("graveyard").setExecutor(this);
    }

    private void sendHelp(Player player){
        player.sendMessage(main.getStringUtil().colorString("&7*--------------{&8&lGraveyards&7}--------------*"));
        player.sendMessage("&r");
        player.sendMessage(main.getStringUtil().colorString("&f- /graveyards help"));
        player.sendMessage(main.getStringUtil().colorString("&f- /graveyards set <graveyardName>"));
        player.sendMessage(main.getStringUtil().colorString("&f- /graveyards tp <graveyardName>"));
        player.sendMessage(main.getStringUtil().colorString("&f- /graveyards remove <graveyardName>"));
        player.sendMessage("&r");
        player.sendMessage("&7*----------------------------------------*");
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)){return true;}
        Player sender = (Player) commandSender;
        if (!command.getLabel().equalsIgnoreCase("graveyard")){return true;}
        command.getAliases().add("gy");
        if (args.length < 1 || args[0].equalsIgnoreCase("help")){sendHelp(sender);}
        return true;
    }
}
