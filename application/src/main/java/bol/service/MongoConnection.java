package bol.service;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import bol.config.MongoProperties;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.stereotype.Component;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Component
public class MongoConnection {

    private final MongoProperties mongoProperties;

    public MongoConnection(final MongoProperties mongoProperties) {
        this.mongoProperties=mongoProperties;
    }

    public MongoDatabase getDatabaseConnection(){
        MongoClient mongoClient = setupMongoClient(mongoProperties);
        return mongoClient.getDatabase(mongoProperties.getDatabaseName());
    }

    private MongoClient setupMongoClient(MongoProperties mongoProperties){
        MongoClientURI mongoClientURI = new MongoClientURI(urlBuilder(mongoProperties), buildConfigurations());
        return new MongoClient(mongoClientURI);
    }

    private String urlBuilder(MongoProperties mongoProperties){
        return "mongodb+srv://"+ mongoProperties.getUsername()+":"+ mongoProperties.getPassword()+"@"+mongoProperties.getUrl();
    }

    private MongoClientOptions.Builder buildConfigurations() {
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        //build the connection options
        builder.maxConnectionIdleTime(60000);//set the max wait time in (ms)
        builder.codecRegistry(pojoCodecRegistry);//for object to document translation
        builder.retryWrites(true);
        builder.sslEnabled(true);
        return builder;
    }
}
