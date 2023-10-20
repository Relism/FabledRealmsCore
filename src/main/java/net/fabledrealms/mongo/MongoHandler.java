package net.fabledrealms.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.fabledrealms.Core;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.FallingBlock;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoHandler {

    private final Core main;
    private MongoCollection<Document> characters;
    private MongoCollection<Document> loader;

    public MongoHandler(Core main){
        this.main = main;
    }

    public void init(){
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        FileConfiguration config = this.main.getConfigFile().getFile();
        ConnectionString connectionString = getConnectionString(config);

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(pojoCodecRegistry)
                .build();

        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("core");

        this.characters = database.getCollection("players");
        this.loader = database.getCollection("loader");
    }

    private ConnectionString getConnectionString(FileConfiguration config) {
        String devURI = "mongodb://fabledrealms-admin:sOh3dy5UZK4iQhzabvLY@129.152.5.2:4006";
        String prodURI = "prodURI";
        String environment = config.getString("environment");
        String mongoURI = (environment.equals("dev")) ? devURI : prodURI;
        return new ConnectionString(mongoURI);
    }

    public MongoCollection<Document> getCharacters() {
        return characters;
    }

    public MongoCollection<Document> getLoader() {
        return loader;
    }
}