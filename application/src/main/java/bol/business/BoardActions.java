package bol.business;

import bol.model.Board;
import bol.model.Player;
import org.springframework.stereotype.Component;

@Component
public class BoardActions {

    private boolean lastStoneHitBigPit;
    private Board board;

    /*
 * Distributes the stones from the selectedPit to the following pits
 */
    public void distributeStones(int selectedPit, Board board){
        this.board=board; //TODO: remove
        int amountOfStones = takeStonesFromPit(selectedPit);

        boolean bigPitSet=false;
        for(int pitIndex=selectedPit+1; amountOfStones>0; amountOfStones--) {
            if(!bigPitSet && isOwnBigPit(pitIndex)){
                addStoneToBigPit();
                lastStoneHitBigPit=amountOfStones==1;
                bigPitSet=true;
                continue;
            } else {
                bigPitSet=false;
                pitIndex = resetPitIndexIfNeeded(pitIndex);
                addStoneToPit(pitIndex);
                if(amountOfStones==1) {
                    captureStonesIfApplicable(pitIndex);
                }
            }
            pitIndex++;
        }
    }

    public void prepareNextTurn(Board board){
        if(lastStoneHitBigPit) {
            board.setTurn(board.getTurn());
        } else {
            int playerId = board.getTurn().getPlayerId() ^ 1;
            board.setTurn(Player.get(playerId));
        }
    }

    private int takeStonesFromPit(int selectedPit) {
        int stones = board.getPits().get(selectedPit);
        board.getPits().set(selectedPit, 0);
        return stones;
    }

    private boolean isOwnBigPit(int pitIndex){
        switch(board.getTurn()) {
            case PLAYERONE:
                if(pitIndex==6){
                    return true;
                }
                break;

            case PLAYERTWO:
                if(pitIndex==12){
                    return true;
                }
                break;
        }
        return false;
    }

    private boolean isOwnPit(int pitIndex){
        switch(board.getTurn()) {
            case PLAYERONE:
                if(pitIndex<6){
                    return true;
                }
                break;

            case PLAYERTWO:
                if(pitIndex>5 && pitIndex<12){
                    return true;
                }
                break;
        }
        return false;
    }


    private void addStoneToPit(int index) {
        board.getPits().set(index, board.getPits().get(index)+1);
    }

    private void addStoneToBigPit() {
        board.getBigPits().set(board.getTurn().getPlayerId(), board.getBigPits().get(board.getTurn().getPlayerId())+1);
    }

    /*
   * when the last stone lands in an own empty pit,
   * the player captures his own stone and all stones in the opposite pit and puts them in his own pit.
    */
    public void captureStonesIfApplicable(int pitIndex){
        if(board.getPits().get(pitIndex)==1 && isOwnPit(pitIndex)){
            board.getPits().set(pitIndex, 0);
            int stones = 1;
            if(board.getTurn().getPlayerId()==0){
                stones += board.getPits().get(pitIndex+6);
                board.getPits().set(11-pitIndex, 0);
            } else {
                stones += board.getPits().get(pitIndex-6);
                board.getPits().set(11-pitIndex, 0);
            }
            board.getBigPits().add(board.getTurn().getPlayerId(), stones);
        }
    }


    private int resetPitIndexIfNeeded(int pitIndex){
        if(pitIndex==12){
            return 0;
        }
        return pitIndex;
    }



}
