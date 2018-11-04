package bol.endpoint;

import bol.business.BoardActions;
import bol.model.Board;
import bol.model.Player;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BoardActionsTest {

    @Autowired
    private BoardActions boardActions;

    private static final String SESSION_ID = "507f1f77bcf86cd799439011";


    @Test
    public void distributeStonesPlayerOne() throws Exception {
        int pitIndex = 3;
        Board board = new Board(new ObjectId(SESSION_ID));
        boardActions.distributeStones(pitIndex, board);

        assertEquals(0, (int) board.getPits().get(3));
        assertEquals(7, (int) board.getPits().get(4));
        assertEquals(7, (int) board.getPits().get(5));
        assertEquals(new Integer(1), board.getBigPits().get(0));
        assertEquals(7, (int) board.getPits().get(6));
        assertEquals(7, (int) board.getPits().get(7));
        assertEquals(7, (int) board.getPits().get(8));
        assertEquals(6, (int) board.getPits().get(9));
    }

    @Test
    public void distributeStonesPlayerTwo() throws Exception {
        int pitIndex = 11;

        Board board = new Board(new ObjectId(SESSION_ID));
        board.setTurn(Player.PLAYERTWO);

        boardActions.distributeStones(pitIndex, board);

        assertEquals(0, (int) board.getPits().get(11));
        assertEquals(new Integer(1), board.getBigPits().get(1));
        assertEquals(7, (int) board.getPits().get(0));
        assertEquals(7, (int) board.getPits().get(1));
        assertEquals(7, (int) board.getPits().get(2));
        assertEquals(7, (int) board.getPits().get(3));
        assertEquals(7, (int) board.getPits().get(4));
        assertEquals(6, (int) board.getPits().get(5));
    }


    @Test
    public void prepareNextTurnPlayerOne() throws Exception {
        Board board = new Board(new ObjectId(SESSION_ID));
        boardActions.prepareNextTurn(board);
        assertEquals(Player.PLAYERTWO, board.getTurn());
    }

    @Test
    public void prepareNextTurnPlayerTwo() throws Exception {
        Board board = new Board(new ObjectId(SESSION_ID));
        board.setTurn(Player.PLAYERTWO);
        boardActions.prepareNextTurn(board);
        assertEquals(Player.PLAYERONE, board.getTurn());
    }

    @Test
    public void captureStonesIfApplicablePlayerOne() {
        int pitIndex = 3;
        Board board = new Board(new ObjectId(SESSION_ID));
        boardActions.distributeStones(pitIndex, board);
        board.getPits().set(pitIndex,1);
        boardActions.captureStonesIfApplicable(pitIndex);
        assertEquals(new Integer(9), board.getBigPits().get(0));
        assertEquals(0, (int) board.getPits().get(pitIndex));
        assertEquals(0, (int) board.getPits().get(11-pitIndex));
    }

    @Test
    public void captureStonesIfApplicablePlayerTwo() {
        Board board = new Board(new ObjectId(SESSION_ID));
        int pitIndex = 8;
        board.setTurn(Player.PLAYERTWO);
        boardActions.distributeStones(pitIndex, board); //make independent
        board.getPits().set(pitIndex,1);
        boardActions.captureStonesIfApplicable(pitIndex);

        assertEquals(new Integer(8), board.getBigPits().get(1));
        assertEquals(0, (int) board.getPits().get(pitIndex));
        assertEquals(0, (int) board.getPits().get(11-pitIndex));
    }


}