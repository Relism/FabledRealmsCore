package net.fabledrealms.character;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import net.fabledrealms.Core;
import net.fabledrealms.util.database.MongoHelper;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.*;

public class CharacterManager {

    private final Core main;
    private final Set<Character> characterCache = new HashSet<>();

    public CharacterManager(Core main){
        this.main = main;

        this.loadAllCharacters();
    }

    public void loadAllCharacters(){
        // TODO use ModuleHelper
        FindIterable<Document> findIterable = this.main.getMongoHandler().getCharacters().find();
        try (MongoCursor<Document> cursor = findIterable.iterator()) {
            while (cursor.hasNext()) {
                characterCache.add(MongoHelper.toCharacter(cursor.next()));
            }
        }
    }

    public void saveAllCharacters() {
        characterCache.forEach(character -> {
            this.main.getMongoHandler().getCharacters().replaceOne(Filters.eq("uuid", character.getUuid()), MongoHelper.toDocument(character), new UpdateOptions().upsert(true));
        });
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
