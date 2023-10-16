package net.fabledrealms.util.database;

import net.fabledrealms.character.Character;
import org.bson.Document;

public class MongoHelper {

    public static Document toCharacterDocument(Character character){
        Document document = new Document();
        document.put("uuid", character.getUuid());
        document.put("className", character.getClassName());
        document.put("characterID", character.getCharacterID());
        document.put("levelMain", character.getLevelMain());
        document.put("levelWoodcutting", character.getLevelWoodcutting());
        document.put("levelFishing", character.getLevelFishing());
        document.put("levelMining", character.getLevelMining());
        document.put("levelSmithing", character.getLevelSmithing());
        document.put("levelCrafting", character.getLevelCrafting());
        document.put("levelGathering", character.getLevelGathering());
        document.put("expMain", character.getExpMain());
        document.put("expWoodcutting", character.getExpWoodcutting());
        document.put("expFishing", character.getExpFishing());
        document.put("expMining", character.getExpMining());
        document.put("expCrafting", character.getExpCrafting());
        document.put("expGathering", character.getExpGathering());
        document.put("balance", character.getBalance());
        document.put("lastJoined", character.getLastJoined());
        return document;
    }

    public static Character toCharacter(Document next) {
        return new Character(next.getString("uuid"),
                next.getInteger("characterID"),
                next.getString("className"),
                next.getInteger("levelMain"),
                next.getInteger("levelWoodcutting"),
                next.getInteger("levelFishing"),
                next.getInteger("levelMining"),
                next.getInteger("levelSmithing"),
                next.getInteger("levelCrafting"),
                next.getInteger("levelGathering"),
                next.getInteger("expMain"),
                next.getInteger("expWoodcutting"),
                next.getInteger("expFishing"),
                next.getInteger("expMining"),
                next.getInteger("expSmithing"),
                next.getInteger("expCrafting"),
                next.getInteger("expGathering"),
                next.getInteger("balance"),
                next.getLong("lastJoined"));
    }
}