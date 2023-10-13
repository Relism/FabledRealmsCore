package net.fabledrealms.character;

import net.fabledrealms.Core;
import net.fabledrealms.wrappers.DatabaseWrapper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CharacterManager {

    private final Core main;
    private final DatabaseWrapper playerDatabase;
    private final HashMap<UUID, Character> characterHashMap;

    public CharacterManager(Core main){
        this.main = main;

        this.playerDatabase = main.getPlayerDatabase();
        this.characterHashMap = new HashMap<>();

        this.loadAllCharacters();
    }

    public void loadAllCharacters(){
        String query = "SELECT * FROM table_name ORDER BY CharacterID";
        try (PreparedStatement statement = main.getPlayerDatabase().getConnection().prepareStatement(query,
                ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = statement.executeQuery();) {
            while (rs.next()) {
                /*
                int id = rs.getInt("id");
                String name = rs.getString("name");
                System.out.printf("id=%d, name=%s%n", id, name);
                */
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveAllCharacters(){
        /*
        //todo loop thru map and add to db
         */
    }

    public void createCharacter(Player player,int id, String className){
        int balance = main.getConfigFile().getFile().getInt("module.character-creation.default-balance");
        int level = main.getConfigFile().getFile().getInt("module.character-creation.default-level");
        int exp = main.getConfigFile().getFile().getInt("module.character-creation.default-exp");
        Character create = new Character(id,className,level,exp,balance);
        characterHashMap.put(player.getUniqueId(),create);
        Bukkit.getLogger().info(player.getName() + " has created character " + create.getCharacterID());
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
        if (hasActiveCharacter(player)) characterHashMap.remove(player.getUniqueId());
        characterHashMap.put(player.getUniqueId(), load);
        syncPlayer(player);
    }

    public void unloadCharacter(Player player){
        if (!hasActiveCharacter(player)){return;}
        saveCharacter(player,getActive(player).getCharacterID());
        characterHashMap.remove(player.getUniqueId());
        player.setLevel(0);
        player.setExp(0);
    }

    public Character getCharacter(Player player, int id){
        String playerUUID = player.getUniqueId().toString();
        playerUUID = playerUUID.replaceAll("-", "_");
        String className = (String) playerDatabase.executeQuery("SELECT ClassName FROM " + playerUUID + " WHERE CharacterID = " + id+";");
        int level = (int) playerDatabase.executeQuery("SELECT PlayerLevel FROM " + playerUUID + " WHERE CharacterID = " + id+";");
        int exp = (int) playerDatabase.executeQuery("SELECT PlayerExp FROM " + playerUUID + " WHERE CharacterID = " + id+";");
        int balance = (int) playerDatabase.executeQuery("SELECT Balance FROM " + playerUUID + " WHERE CharacterID = " + id+";");
        return new Character(id,className,level,exp,balance);
    }

    public Character getCharacterFromMemory(UUID uuid) {
        for (Map.Entry<UUID, Character> characterEntry : characterHashMap.entrySet()) {
            if (characterEntry.getKey().toString().equalsIgnoreCase(uuid.toString())) {
                return characterEntry.getValue();
            }
        }
        return null;
    }

    private void saveCharacter(Player player, int characterID){
        if (!hasActiveCharacter(player)) return;
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
        return characterHashMap.containsKey(player.getUniqueId());
    }

    public Character getActive(Player player){
        if (!hasActiveCharacter(player)) return null;
        return characterHashMap.get(player.getUniqueId());
    }
    private void syncPlayer(Player player){
        Character active = getActive(player);
        player.setLevel(active.getCharacterLevel());
        player.setExp(active.getCharacterExp());
    }

}
