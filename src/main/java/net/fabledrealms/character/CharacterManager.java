package net.fabledrealms.character;

import net.fabledrealms.Core;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CharacterManager {

    private final Core main;
    private final Set<Character> characterCache = new HashSet<>();

    public CharacterManager(Core main){
        this.main = main;

        this.loadAllCharacters();
    }

    public void loadAllCharacters(){
        String query = "SELECT * FROM players ORDER BY uuid";
        try (PreparedStatement statement = main.getPlayerDatabase().getConnection().prepareStatement(query,
                ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = statement.executeQuery();) {
            while (rs.next()) {
                /* TODO
                characterCache.add(
                        rs.getString("uuid"),
                        rs.getString("className")); */
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveAllCharacters() throws SQLException {
        // TODO
        PreparedStatement preparedStatement = this.main.getPlayerDatabase().getConnection().prepareStatement("UPDATE players");
        characterCache.forEach(character -> {

        });
    }

    public void createCharacter(Player player, String className){
        characterCache.add(new Character(player.getUniqueId().toString(), className,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0));
    }

    /* PROBABLY TO REMOVE
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

     */

    public Character getCharacter(Player player){
        for (Character character : characterCache) {
            if (character.getUuid().equals(player.getUniqueId())) return character;
        }
        return null;
    }

    public Character getCharacter(UUID uuid) {
        for (Character character : characterCache) {
            if (character.getUuid().equals(uuid)) return character;
        }
        return null;
    }

    /*
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
     */

    public Set<Character> getCharacterCache() {
        return characterCache;
    }
}
