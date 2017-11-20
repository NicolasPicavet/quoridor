package fr.uga.ai.corridor.map;

import java.util.HashMap;
import java.util.Map;

public class WallSquare {

    public enum State {
        HORIZONTAL, VERTICAL, BOTH, NONE;
    }

    public final static String wallCharacter = "|";
    public final static String noWallCharacter = " ";

    State state;

    public Map<WallSquareSide.Side, WallSquareSide> sides = new HashMap<>();

    public WallSquare(WallSquareSide left, WallSquareSide top, WallSquareSide right, WallSquareSide bottom) {
        this.state = State.NONE;
        sides.put(WallSquareSide.Side.LEFT, left);
        sides.put(WallSquareSide.Side.TOP, top);
        sides.put(WallSquareSide.Side.RIGHT, right);
        sides.put(WallSquareSide.Side.BOTTOM, bottom);
    }

    public boolean build(State state) {
        if (state == State.VERTICAL)
            return setVertical();
        if (state == State.HORIZONTAL)
            return setHorizontal();
        return false;
    }

    private boolean setHorizontal() {
        if (sides.get(WallSquareSide.Side.LEFT).hasWall() || sides.get(WallSquareSide.Side.RIGHT).hasWall())
            return false;
        state = isVertical() ? State.BOTH : State.HORIZONTAL;
        sides.get(WallSquareSide.Side.LEFT).setWall(true);
        sides.get(WallSquareSide.Side.RIGHT).setWall(true);
        return true;
    }

    private boolean setVertical() {
        if (sides.get(WallSquareSide.Side.TOP).hasWall() || sides.get(WallSquareSide.Side.BOTTOM).hasWall())
            return false;
        state = isHorizontal() ? State.BOTH : State.VERTICAL;
        sides.get(WallSquareSide.Side.TOP).setWall(true);
        sides.get(WallSquareSide.Side.BOTTOM).setWall(true);
        return true;
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
    @Deprecated
    public String toString() {
        return "";
    }

    public String toString(WallSquareSide.Side askedSide) {
        return sides.get(askedSide).hasWall() ? wallCharacter : noWallCharacter;
    }
}
