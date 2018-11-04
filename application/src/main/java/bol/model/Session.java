package bol.model;

import org.bson.types.ObjectId;

public class Session {
    private String playerOne;
    private String playerTwo;

    private ObjectId _id;

    //for mongodb
    public Session() {
    }

    //for mongodb
    public Session(String playerOne, String playerTwo, ObjectId _id) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this._id = _id;
    }

    public Session(ObjectId _id) {
        this._id=_id;
    }

    public String getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(String playerOne) {
        this.playerOne = playerOne;
    }

    public String getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(String playerTwo) {
        this.playerTwo = playerTwo;
    }


    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }
}
