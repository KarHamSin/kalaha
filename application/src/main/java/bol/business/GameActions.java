package bol.business;

import bol.exception.GameException;
import bol.model.Board;
import bol.model.Player;
import bol.model.Session;
import bol.service.MongoBoardService;
import bol.service.MongoSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameActions {

    private final BoardActions boardActions;
    private final SessionManager sessionManager;
    private final MongoBoardService mongoBoardService;
    private final MongoSessionService mongoSessionService;

    @Autowired
    public GameActions(BoardActions boardActions, SessionManager sessionManager, MongoBoardService mongoBoardService, MongoSessionService mongoSessionService) {

        this.boardActions = boardActions;
        this.sessionManager = sessionManager;
        this.mongoBoardService=mongoBoardService;
        this.mongoSessionService=mongoSessionService;
    }

    public Board fetchBoard(){
        return mongoBoardService.fetchBoard();
    }

    public Board newGame(){
        mongoSessionService.createNewSession();
        return mongoBoardService.createNewBoard();
    }

    // returns true if user joined successfully
    public Player joinGame(String email) {
        Session session = mongoSessionService.fetchSession();
        Player player = sessionManager.joinSession(session, email);
        mongoSessionService.saveSession(session);
        return player;
    }

    public Board doPlayerTurn(int selectedPit, String mail) {
        Session session = mongoSessionService.fetchSession();
        Board board = mongoBoardService.fetchBoard();
        blockPlayerIfNoTurn(board, session, mail);

        boardActions.distributeStones(selectedPit, board);
        boardActions.prepareNextTurn(board);

        mongoSessionService.saveSession(session);
        mongoBoardService.saveBoard(board);
        return board;
    }

    private void blockPlayerIfNoTurn(Board board, Session session, String mail){
        Player player = sessionManager.getPlayerBasedOnMail(session, mail);
        if(player==Player.NONE){
            throw new GameException("Mail not found in the game. Please join or restart the game.");
        }
        if(board.getTurn()!=player) {
            throw new GameException("Player should wait on next turn.");
        }
    }


}

