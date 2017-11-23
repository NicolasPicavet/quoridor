package fr.uga.ai.corridor.map;

import java.util.*;
import java.util.Map;

public class WallSquare {

    public enum State {
        HORIZONTAL, VERTICAL, BOTH, NONE;
    }

    public final static String wallCharacter = "|";
    public final static String noWallCharacter = " ";

    State state;

    public Map<Side, WallSquareSide> sides = new HashMap<>();

    public WallSquare(WallSquareSide left, WallSquareSide top, WallSquareSide right, WallSquareSide bottom) {
        this.state = State.NONE;
        sides.put(Side.LEFT, left);
        sides.put(Side.TOP, top);
        sides.put(Side.RIGHT, right);
        sides.put(Side.BOTTOM, bottom);
    }

    public boolean build(State state) {
        if (state == State.VERTICAL)
            return setVertical();
        if (state == State.HORIZONTAL)
            return setHorizontal();
        return false;
    }

    public WallSquareSide getIntersectSides(WallSquare wallSquare) {
        Set<WallSquareSide> intersection = new HashSet<>();
        for (Map.Entry<Side, WallSquareSide> entry : sides.entrySet())
            intersection.add(entry.getValue());
        Set<WallSquareSide> wallSquareSides = new HashSet<>();
        for (Map.Entry<Side, WallSquareSide> entry : wallSquare.sides.entrySet())
            wallSquareSides.add(entry.getValue());
        intersection.retainAll(wallSquareSides);
        Iterator<WallSquareSide> iterator = intersection.iterator();
        return iterator.next();
    }

    private boolean setHorizontal() {
        if (sides.get(Side.LEFT).hasWall() || sides.get(Side.RIGHT).hasWall())
            return false;
        state = isVertical() ? State.BOTH : State.HORIZONTAL;
        sides.get(Side.LEFT).setWall(true);
        sides.get(Side.RIGHT).setWall(true);
        return true;
    }

    private boolean setVertical() {
        if (sides.get(Side.TOP).hasWall() || sides.get(Side.BOTTOM).hasWall())
            return false;
        state = isHorizontal() ? State.BOTH : State.VERTICAL;
        sides.get(Side.TOP).setWall(true);
        sides.get(Side.BOTTOM).setWall(true);
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

    public String toString(Side askedSide) {
        return sides.get(askedSide).hasWall() ? wallCharacter : noWallCharacter;
    }
}
