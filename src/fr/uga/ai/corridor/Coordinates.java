package fr.uga.ai.corridor;

public class Coordinates {

    int x;
    int y;

    Boolean moveAction;
    WallSquare.State state;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates(int x, int y, Boolean moveAction) {
        this.x = x;
        this.y = y;
        this.moveAction = moveAction;
    }

    public Coordinates(int x, int y, WallSquare.State state) {
        this.x = x;
        this.y = y;
        this.moveAction = false;
        this.state = state;
    }

    public Boolean isAMoveAction() {
        return moveAction;
    }
}
