package bol.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.LinkedList;

public class Board {

    @JsonIgnore
    private ObjectId _id;
    private LinkedList<Integer> pits;
    private LinkedList<Integer> bigPits;
    private Player turn;

    //for mongodb
    public Board() {
    }

    public Board(ObjectId _id) {
        this._id=_id;
        Integer[] pitArray = new Integer[12];
        Arrays.fill(pitArray, 6);
        this.pits = new LinkedList<>(Arrays.asList(pitArray));
        Integer[] bigPitArray = new Integer[2];
        Arrays.fill(bigPitArray, 0);
        this.bigPits = new LinkedList<>(Arrays.asList(bigPitArray));

        this.turn =Player.PLAYERONE;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public LinkedList<Integer> getPits() {
        return pits;
    }

    public void setPits(LinkedList<Integer> pits) {
        this.pits = pits;
    }

    public LinkedList<Integer> getBigPits() {
        return bigPits;
    }

    public void setBigPits(LinkedList<Integer> bigPits) {
        this.bigPits = bigPits;
    }

    public Player getTurn() {
        return turn;
    }

    public void setTurn(Player turn) {
        this.turn = turn;
    }

}
