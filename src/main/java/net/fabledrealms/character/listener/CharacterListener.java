package net.fabledrealms.character.listener;

import com.mongodb.client.model.Filters;
import net.fabledrealms.Core;
import net.fabledrealms.util.database.MongoHelper;
import org.bson.Document;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Date;

public class CharacterListener implements Listener {

    private final Core main;

    public CharacterListener(Core main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (this.main.getCharacterManager().getCharacterCache().contains(this.main.getCharacterManager().getCharacter(event.getPlayer()))) {
            this.main.getCharacterManager().getCharacter(event.getPlayer()).setLastJoined(new Date().getTime());
            return;
        }

        Document document = this.main.getMongoHandler().getCharacters().find(Filters.eq("uuid", event.getPlayer().getUniqueId().toString())).first();
        if (document != null) {
            this.main.getCharacterManager().getCharacterCache().add(MongoHelper.toCharacter(document));
        }
    }
}
