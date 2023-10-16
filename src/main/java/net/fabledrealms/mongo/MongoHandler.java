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

import java.util.Objects;

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

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(Objects.requireNonNull(this.main.getConfigFile().getFile().getString("mongo.uri"))))
                .codecRegistry(pojoCodecRegistry)
                .build();

        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("core");

        //test push :(


        this.characters = database.getCollection("players");
        this.loader = database.getCollection("loader");
    }

    public MongoCollection<Document> getCharacters() {
        return characters;
    }

    public MongoCollection<Document> getLoader() {
        return loader;
    }
}
