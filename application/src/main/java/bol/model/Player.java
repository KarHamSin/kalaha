package bol.model;

import java.util.HashMap;
import java.util.Map;

public enum Player {

    PLAYERONE(0),
    PLAYERTWO(1),
    NONE(2);

    private final int playerId;

    Player(final int playerId) {
        this.playerId = playerId;
    }

    public int getPlayerId() { return playerId; }


    private static final Map lookup = new HashMap<Integer, Player>();

    static {
        //Create reverse lookup hash map
        for(Player player : Player.values())
            lookup.put(player.getPlayerId(), player);
    }

    public static Player get(int value) {
        //the reverse lookup by simply getting
        //the value from the lookup HsahMap.
        return (Player) lookup.get(value);
    }

}
