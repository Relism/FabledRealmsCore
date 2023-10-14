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

    public Set<Character> getCharacterCache() {
        return characterCache;
    }
}
