package fr.uga.ai.corridor.map;

public class WallSquare {

    public enum State {
        HORIZONTAL, VERTICAL, BOTH, NONE;
    }

    State state;

    public WallSquare() {
        this(State.NONE);
    }

    public WallSquare(State state) {
        this.state = state;
    }

    public void build(State state) {
        if (state == State.VERTICAL)
            setVertical();
        if (state == State.HORIZONTAL)
            setHorizontal();
    }

    void setHorizontal() {
        state = isVertical() ? State.BOTH : State.HORIZONTAL;
    }

    void setVertical() {
        state = isHorizontal() ? State.BOTH : State.VERTICAL;
    }

    public boolean isHorizontal() {
        return state == State.HORIZONTAL || state == State.BOTH;
    }

    public boolean isVertical() {
        return state == State.VERTICAL || state == State.BOTH;
    }

    public boolean hasWall() {
        return state != State.NONE;
    }

    @Override
    public String toString() {
        return hasWall() ? "|" : ".";
    }
}