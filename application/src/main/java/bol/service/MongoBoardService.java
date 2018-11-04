package bol.service;

import bol.model.Board;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MongoBoardService {

    private static final String COLLECTION_NAME = "board";
    private static final String SESSION_ID = "507f1f77bcf86cd799439011";
    private MongoService<Board> mongoService;

    @Autowired
    public MongoBoardService(MongoService mongoService) {
        this.mongoService=mongoService;
    }

    public Board createNewBoard() {
        Board board = new Board(new ObjectId(SESSION_ID));
        saveBoard(board);
        return board;
    }

    public Board fetchBoard(){
        return this.mongoService.getDocument(SESSION_ID, Board.class, COLLECTION_NAME);
    }

    public void saveBoard(Board board){
        this.mongoService.removeDocument(SESSION_ID, Board.class, COLLECTION_NAME);
        this.mongoService.addDocument(board, SESSION_ID, Board.class, COLLECTION_NAME);
    }
}
