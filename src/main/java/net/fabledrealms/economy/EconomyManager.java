package net.fabledrealms.economy;

import net.fabledrealms.Core;
import org.bukkit.entity.Player;

public class EconomyManager {

    private final Core main;

    public EconomyManager(Core main){
        this.main = main;
    }

    public int getPlayerBalance(Player player, int CharacterID){
        String uuid = player.getUniqueId().toString();
        return (int) main.getPlayerDatabase().executeQuery("SELECT Balance FROM " + uuid + "WHERE CharacterID=" + CharacterID);
    }

    public void setPlayerBalance(Player player, int CharacterID, int newBalance){
        String uuid = player.getUniqueId().toString();
        main.getPlayerDatabase().execute("UPDATE " + uuid + " SET Balance " + newBalance + " WHERE CharacterID = " + CharacterID);
    }

}
