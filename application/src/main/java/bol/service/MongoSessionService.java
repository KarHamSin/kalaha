package bol.service;

import bol.model.Session;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MongoSessionService {

    private static final String COLLECTION_NAME = "session";
    private static final String SESSION_ID = "5bdd73a60a5f0bb0ef4eb6a2";
    private MongoService<Session> mongoService;

    @Autowired
    public MongoSessionService(MongoService mongoService) {
        this.mongoService=mongoService;
    }

    public void createNewSession() {
        Session session = new Session(new ObjectId(SESSION_ID));
        saveSession(session);
    }

    public Session fetchSession() {
        return this.mongoService.getDocument(SESSION_ID, Session.class, COLLECTION_NAME);
    }

    public void saveSession(Session session) {
        this.mongoService.removeDocument(SESSION_ID, Session.class, COLLECTION_NAME);
        this.mongoService.addDocument(session, SESSION_ID ,Session.class, COLLECTION_NAME);
    }

}
