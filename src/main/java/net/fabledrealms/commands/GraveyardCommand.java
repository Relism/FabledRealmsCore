package net.fabledrealms.commands;

import net.fabledrealms.Core;
import net.fabledrealms.graveyards.Graveyard;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class GraveyardCommand implements CommandExecutor {

    private final Core main;

    public GraveyardCommand(Core main){
        this.main = main;
        Objects.requireNonNull(Bukkit.getPluginCommand("graveyard")).setExecutor(this);
    }

    private void sendHelp(Player player){
        player.sendMessage(main.getStringUtil().colorString("&7*--------------{&8&lGraveyard&7}--------------*"));
        player.sendMessage(main.getStringUtil().colorString("&r"));
        player.sendMessage(main.getStringUtil().colorString("&f- /graveyards help &7&o(Displays the Graveyard module help text)"));
        player.sendMessage(main.getStringUtil().colorString("&f- /graveyards near &7&o(Displays the closest graveyard)"));
        player.sendMessage(main.getStringUtil().colorString("&f- /graveyards create <graveyardName> &7&o(creates a Graveyard at your location)"));
        player.sendMessage(main.getStringUtil().colorString("&f- /graveyards set <graveyardName> &7&o(Sets a graveyards location)"));
        player.sendMessage(main.getStringUtil().colorString("&f- /graveyards tp <graveyardName> &7&o(Teleports you to a Graveyard)"));
        player.sendMessage(main.getStringUtil().colorString("&f- /graveyards remove <graveyardName> &7&o(Removes a Graveyard)"));
        player.sendMessage(main.getStringUtil().colorString("&r"));
        player.sendMessage(main.getStringUtil().colorString("&7*----------------------------------------*"));
    }

    private void listGraveyards(Player player){
        for (Location location : main.getGraveyardManager().getGraveyardMap().values()){
            Graveyard graveyard = main.getGraveyardManager().getGraveyardByLocation(location);
            player.sendMessage(graveyard.getGraveyardName());
        }
    }

    private void createGraveyard(Player player, String id){
        Location newGraveyard = player.getLocation();
        String worldName = newGraveyard.getWorld().getName();
        int x = newGraveyard.getBlockX();
        int y = newGraveyard.getBlockY();
        int z = newGraveyard.getBlockZ();
        main.getGraveyardManager().createGraveyard(id,worldName,x,y,z);
        main.getGraveyardManager().saveToFile();
        String createMessage = main.getLangFile().getFile().getString("graveyards.graveyard-created");
        createMessage = createMessage.replaceAll("%prefix%", main.getLangFile().getFile().getString("graveyards.module-prefix"));
        createMessage = createMessage.replaceAll("%graveyard%", id);
        createMessage = createMessage.replaceAll("%locationx%", String.valueOf(x));
        createMessage = createMessage.replaceAll("%locationy%", String.valueOf(y));
        createMessage = createMessage.replaceAll("%locationz%", String.valueOf(z));
        createMessage = main.getStringUtil().colorString(createMessage);
        player.sendMessage(createMessage);
    }

    private void setGraveyard(Player player, String id){
        Location location = player.getLocation();
        String worldName = location.getWorld().getName();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        if (main.getGraveyardManager().getGraveyardByID(id).equals(null)){
            String errorMessage = main.getLangFile().getFile().getString("graveyards.graveyard-not-found");
            errorMessage = errorMessage.replaceAll("%prefix%", main.getLangFile().getFile().getString("graveyards.module-prefix"));
            errorMessage = main.getStringUtil().colorString(errorMessage);
            player.sendMessage(errorMessage);
            return;
        }
        main.getGraveyardManager().getGraveyardByID(id).setWorldName(worldName);
        main.getGraveyardManager().getGraveyardByID(id).setX(x);
        main.getGraveyardManager().getGraveyardByID(id).setY(y);
        main.getGraveyardManager().getGraveyardByID(id).setZ(z);
        main.getGraveyardManager().saveToFile();
        String setMessage = main.getLangFile().getFile().getString("graveyards.graveyard-set");
        setMessage = setMessage.replaceAll("%prefix%", main.getLangFile().getFile().getString("graveyards.module-prefix"));
        setMessage = setMessage.replaceAll("%graveyard%", id);
        setMessage = setMessage.replaceAll("%locationx%",String.valueOf(x));
        setMessage = setMessage.replaceAll("%locationy%", String.valueOf(y));
        setMessage = setMessage.replaceAll("%locationz", String.valueOf(z));
        setMessage = main.getStringUtil().colorString(setMessage);
        player.sendMessage(setMessage);
    }

    private void tpGraveyard(Player player, String id){
        if (main.getGraveyardManager().getGraveyardByID(id).equals(null)){
            String errorMessage = main.getLangFile().getFile().getString("graveyards.graveyard-not-found");
            errorMessage = errorMessage.replaceAll("%prefix%", main.getLangFile().getFile().getString("graveyards.module-prefix"));
            errorMessage = main.getStringUtil().colorString(errorMessage);
            player.sendMessage(errorMessage);
            return;
        }
        String worldName = main.getGraveyardManager().getGraveyardByID(id).getWorldName();
        int x = main.getGraveyardManager().getGraveyardByID(id).getX();
        int y = main.getGraveyardManager().getGraveyardByID(id).getY();
        int z = main.getGraveyardManager().getGraveyardByID(id).getZ();
        String tpMessage = main.getLangFile().getFile().getString("graveyards.graveyard-tp");
        tpMessage = tpMessage.replaceAll("%prefix%",main.getLangFile().getFile().getString("graveyards.module-prefix"));
        tpMessage = tpMessage.replaceAll("%graveyard%", id);
        tpMessage = main.getStringUtil().colorString(tpMessage);
        Location location = new Location(Bukkit.getWorld(worldName),x,y,z);
        player.teleport(location);
        player.sendMessage(tpMessage);
    }

    private void nearestGraveyard(Player player){
        Graveyard nearest = main.getGraveyardManager().getGraveyardByLocation(main.getGraveyardManager().getClosestGraveyard(player));
        String nearMessage = main.getLangFile().getFile().getString("graveyards.graveyard-nearest");
        nearMessage = nearMessage.replaceAll("%prefix%", main.getLangFile().getFile().getString("graveyards.module-prefix"));
        nearMessage = nearMessage.replaceAll("%graveyard%", nearest.getGraveyardName());
        nearMessage = main.getStringUtil().colorString(nearMessage);
        player.sendMessage(nearMessage);
    }

    private void removeGraveyard(Player player, String id){
        String deleteMessage = main.getLangFile().getFile().getString("graveyards.graveyard-delete");
        deleteMessage = deleteMessage.replaceAll("%prefix%", main.getLangFile().getFile().getString("graveyards.module-prefix"));
        deleteMessage = deleteMessage.replaceAll("%graveyard%",id);
        deleteMessage = main.getStringUtil().colorString(deleteMessage);
        player.sendMessage(deleteMessage);
        main.getGraveyardManager().getGraveyardFileWrapper().getFile().set(id,null);
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)){return true;}
        Player sender = (Player) commandSender;
        if (!command.getLabel().equalsIgnoreCase("graveyard")){return true;}
        command.getAliases().add("gy");
        if (args.length < 1 || args[0].equalsIgnoreCase("help")){sendHelp(sender);}
        if (args[0].equalsIgnoreCase("list")){listGraveyards(sender);}
        if (args[0].equalsIgnoreCase("near")){}
        if (args.length == 2){

            if (args[0].equalsIgnoreCase("create")){
                if (args[1].equals(null)){
                    sendHelp(sender);
                    return true;}
                String name = args[1];
                sender.sendMessage(name);
                createGraveyard(sender, name);
            }

            if (args[0].equalsIgnoreCase("set")){
                if (args[1].equals(null)){
                    sendHelp(sender);
                    return true;
                }
                setGraveyard(sender, args[1]);
            }

            if (args[0].equalsIgnoreCase("tp")){
                if (args[1].equals(null)){
                    sendHelp(sender);
                    return true;
                }
                tpGraveyard(sender, args[1]);
            }

            if (args[0].equalsIgnoreCase("remove")){
                if (args[1].equals(null)){
                    sendHelp(sender);
                    return true;
                }
                removeGraveyard(sender, args[1]);
            }
        }
        return true;
    }
}
