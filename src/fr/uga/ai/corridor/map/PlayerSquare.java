package fr.uga.ai.corridor.map;

import fr.uga.ai.corridor.Player;

public class PlayerSquare {

    private Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void removePlayer() {
        this.player = null;
    }

    public boolean hasPlayer(Player player) {
        return this.player == player;
    }

    public boolean isEmpty() {
        return player == null;
    }

    @Override
    public String toString() {
        return player != null ? player.toString() : " ";
    }
}
