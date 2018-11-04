package bol.service;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//TODO: make abstract
@Service
public class MongoService<T> {

    private final MongoDatabase mongoDatabase;

    @Autowired
    public MongoService(final MongoConnection mongoConnection){
        this.mongoDatabase = mongoConnection.getDatabaseConnection();
    }

    public void addDocument(T document, String id, Class<T> clazz, String collectionName) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        this.mongoDatabase.getCollection(collectionName, clazz).replaceOne(query, document, new UpdateOptions().upsert(true));
    }

    public void removeDocument(String id, Class<T> clazz, String collectionName) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        this.mongoDatabase.getCollection(collectionName, clazz).deleteOne(query);
    }

    public T getDocument(String id, Class<T> clazz, String collectionName) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        return this.mongoDatabase.getCollection(collectionName, clazz).find(query).first();
    }

}
