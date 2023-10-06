package net.fabledrealms.character;

import net.fabledrealms.Core;
import net.fabledrealms.wrappers.DatabaseWrapper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CharacterManager {

    private final Core main;
    private final DatabaseWrapper playerDatabase;
    private final HashMap<Player,Character> characterHashMap;

    public CharacterManager(Core main){
        this.main = main;
        this.playerDatabase = main.getPlayerDatabase();
        this.characterHashMap = new HashMap<>();
    }

    public void createCharacter(Player player,int id, String className){
        int balance = main.getConfigFile().getFile().getInt("module.character-creation.default-balance");
        int level = main.getConfigFile().getFile().getInt("module.character-creation.default-level");
        int exp = main.getConfigFile().getFile().getInt("module.character-creation.default-exp");
        Character create = new Character(id,className,level,exp,balance);
        characterHashMap.put(player,create);
        Bukkit.getLogger().info(player.getName().toString() + " has created character " + create.getCharacterID());
        String playerUUID = player.getUniqueId().toString();
        playerUUID = playerUUID.replaceAll("-", "_");
        playerDatabase.execute("INSERT INTO " + playerUUID + "(CharacterID, ClassName, PlayerLevel, PlayerExperience, Balance) VALUES ("
        +id+","+className+","+level+","+exp+","+balance+");");
        syncPlayer(player);
    }

    public void loadCharacter(Player player, int id){
        String playerUUID = player.getUniqueId().toString();
        playerUUID = playerUUID.replaceAll("-", "_");
        String className = (String) playerDatabase.executeQuery("SELECT ClassName FROM " + playerUUID + " WHERE CharacterID = " + id+";");
        int level = (int) playerDatabase.executeQuery("SELECT PlayerLevel FROM " + playerUUID + " WHERE CharacterID = " + id+";");
        int exp = (int) playerDatabase.executeQuery("SELECT PlayerExp FROM " + playerUUID + " WHERE CharacterID = " + id+";");
        int balance = (int) playerDatabase.executeQuery("SELECT Balance FROM " + playerUUID + " WHERE CharacterID = " + id+";");
        Character load = new Character(id,className,level,exp,balance);
        if (hasActiveCharacter(player)){characterHashMap.remove(player);}
        characterHashMap.put(player,load);
        syncPlayer(player);
    }

    public void unloadCharacter(Player player){
        if (!hasActiveCharacter(player)){return;}
        saveCharacter(player,getActive(player).getCharacterID());
        characterHashMap.remove(player);
        player.setLevel(0);
        player.setExp(0);
    }

    private void saveCharacter(Player player, int characterID){
        if (!hasActiveCharacter(player)){return;}
        Character active = getActive(player);
        String playerUUID = player.getUniqueId().toString();
        playerUUID = playerUUID.replaceAll("-", "_");
        playerDatabase.execute("UPDATE " + playerUUID + " SET CharacterID = " + active.getCharacterID()
                + ", ClassName = " + active.getClassName() + ", PlayerLevel = " + active.getCharacterLevel() + ", PlayerExperience = " +
                active.getCharacterExp() + ", Balance = " + active.getBalance() + " WHERE CharacterID=" + characterID + ";");
    }
    public void deleteCharacter(Player player, int characterID){
        String playerUUID = player.getUniqueId().toString();
        playerUUID = playerUUID.replaceAll("-", "_");
        playerDatabase.execute("DELETE FROM " + playerUUID + " WHERE CharacterID = " + characterID);
    }
    public Boolean hasActiveCharacter(Player player){
        return characterHashMap.containsKey(player);
    }

    public Character getActive(Player player){
        if (!hasActiveCharacter(player)){return null;}
        return characterHashMap.get(player);
    }
    private void syncPlayer(Player player){
        Character active = getActive(player);
        player.setLevel(active.getCharacterLevel());
        player.setExp(active.getCharacterExp());
    }

}
