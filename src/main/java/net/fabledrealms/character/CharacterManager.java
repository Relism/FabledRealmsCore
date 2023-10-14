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

                characterCache.add(new Character(
                        rs.getString("uuid"),
                        rs.getInt("characterID"),
                        rs.getString("className"),
                        rs.getInt("levelMain"),
                        rs.getInt("levelWoodcutting"),
                        rs.getInt("levelFishing"),
                        rs.getInt("levelMining"),
                        rs.getInt("levelSmithing"),
                        rs.getInt("levelCrafting"),
                        rs.getInt("levelGathering"),
                        rs.getInt("expMain"),
                        rs.getInt("expWoodcutting"),
                        rs.getInt("expFishing"),
                        rs.getInt("expMining"),
                        rs.getInt("expSmithing"),
                        rs.getInt("expCrafting"),
                        rs.getInt("expGathering"),
                        rs.getInt("balance")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveAllCharacters() throws SQLException {
        PreparedStatement statement =
                this.main.getPlayerDatabase().getConnection()
                        .prepareStatement("UPDATE players SET characterID = ?, className = ?, levelMain = ?, levelWoodcuting = ?, levelFishing = ?, levelMining = ?, levelSmithing = ?, levelCrafting = ?, levelGathering = ?, expMain = ?, expWoodcutting = ?, expFishing = ?, expMining = ?, expSmithing = ?, expCrafting = ?, expGathering = ?, balance = ? WHERE uuid = ?");
        characterCache.forEach(character -> {
            try {
                statement.setInt(1, character.getCharacterID());
                statement.setString(2, character.getClassName());
                statement.setInt(3, character.getLevelMain());
                statement.setInt(4, character.getLevelWoodcutting());
                statement.setInt(5, character.getLevelFishing());
                statement.setInt(6, character.getLevelMining());
                statement.setInt(7, character.getLevelSmithing());
                statement.setInt(8, character.getLevelCrafting());
                statement.setInt(9, character.getLevelGathering());
                statement.setInt(10, character.getExpMain());
                statement.setInt(11, character.getExpWoodcutting());
                statement.setInt(12, character.getExpFishing());
                statement.setInt(13, character.getExpMining());
                statement.setInt(14, character.getExpSmithing());
                statement.setInt(15, character.getExpCrafting());
                statement.setInt(16, character.getExpGathering());
                statement.setInt(17, character.getBalance());
                statement.setString(18, character.getUuid());

                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        statement.close();
    }

    public void createCharacter(Player player, int characterID, String className){
        characterCache.add(new Character(player.getUniqueId().toString(),characterID, className,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0));
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
            if (character.getUuid().equals(player.getUniqueId().toString())) return character;
        }
        return null;
    }

    public Character getCharacter(UUID uuid, int slot) {
        for (Character character : characterCache) {
            if (character.getUuid().equals(uuid.toString())) return character;
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
