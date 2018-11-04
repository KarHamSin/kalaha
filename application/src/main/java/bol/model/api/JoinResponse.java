package bol.model.api;

public class JoinResponse {

    private String player;

    public JoinResponse(String player) {
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
