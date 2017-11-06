package fr.uga.ai.corridor;

public class PlayerSquare {

    final static String PLAYER_ONE = "O";
    final static String PLAYER_TWO = "X";

    int playerId;

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public String toString() {
        if (playerId == 1)
            return PLAYER_ONE;
        if (playerId == 2)
            return PLAYER_TWO;
        return " ";
    }
}
