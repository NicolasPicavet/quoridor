package fr.uga.ai.corridor;

import fr.uga.ai.corridor.map.Coordinates;
import fr.uga.ai.corridor.map.Map;
import fr.uga.ai.corridor.map.WallSquare;

public class Player {

    public final static Player PLAYER_ONE = new Player(1, "O", new Coordinates(0, 4), 8);
    public final static Player PLAYER_TWO = new Player(2, "X", new Coordinates(8, 4), 0);

    private final int playerId;
    private final String character;
    private final int winX;

    private Coordinates coordinates;
    private int wallBank = 10;

    private Player(int playerId, String character, Coordinates initialPosition, int winX) {
        this.playerId = playerId;
        this.character = character;
        this.coordinates = initialPosition;
        this.winX = winX;

        Map.getInstance().addPlayer(this);
    }

    @Override
    public String toString() {
        return character;
    }

    public boolean hasWin() {
        return coordinates.x == winX;
    }

    public boolean execute(Action action) {
        // move
        if (action.getType() == Action.Type.MOVE)
            return setCoordinates(action.getCoordinates());
        // build wall
        boolean built = false;
        if (wallBank > 0) {
            if (action.getType() == Action.Type.BUILD_HORIZONTAL)
                built = Map.getInstance().buildWall(action.getCoordinates(), WallSquare.State.HORIZONTAL);
            else if (action.getType() == Action.Type.BUILD_VERTICAL)
                built =  Map.getInstance().buildWall(action.getCoordinates(), WallSquare.State.VERTICAL);
            if (built)
                wallBank--;
            return built;
        }
        return false;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    private boolean setCoordinates(Coordinates coordinates) {
        boolean removed = false;
        boolean added = false;
        boolean validMove = coordinates.x == this.coordinates.x && (coordinates.y == this.coordinates.y - 1 || coordinates.y == this.coordinates.y + 1)
                || coordinates.y == this.coordinates.y && (coordinates.x == this.coordinates.x - 1 || coordinates.x == this.coordinates.x + 1);
        if (validMove) {
            removed = Map.getInstance().removePlayer(this);
            this.coordinates = coordinates;
            added = Map.getInstance().addPlayer(this);
        }
        return (coordinates == null || removed) && added && validMove;
    }

    public int getWallBank() {
        return wallBank;
    }
}
