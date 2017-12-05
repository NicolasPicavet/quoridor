package fr.uga.ai.quoridor.map;

import java.util.*;
import java.util.Map;

public class WallSquare implements IsSquare {

    public enum State {
        HORIZONTAL, VERTICAL, BOTH, NONE;
    }

    public final static String wallCharacter = "|";
    public final static String noWallCharacter = " ";

    private State state;

    public Map<Side, WallSquareSide> sides = new HashMap<>();

    public WallSquare(WallSquareSide left, WallSquareSide top, WallSquareSide right, WallSquareSide bottom) {
        this.state = State.NONE;
        addSide(Side.LEFT, left);
        addSide(Side.TOP, top);
        addSide(Side.RIGHT, right);
        addSide(Side.BOTTOM, bottom);
    }

    public boolean isBuildable(State state) {
        if (state == State.VERTICAL && isWallValid(Side.TOP, Side.BOTTOM))
            if (!sides.get(Side.TOP).hasWall() && !sides.get(Side.BOTTOM).hasWall())
                return true;
        if (state == State.HORIZONTAL && isWallValid(Side.LEFT, Side.RIGHT))
            if (!sides.get(Side.LEFT).hasWall() && !sides.get(Side.RIGHT).hasWall())
                return true;
        return false;
    }

    public boolean build(State state) {
        if (state == State.VERTICAL && isBuildable(state))
            return setVertical();
        if (state == State.HORIZONTAL && isBuildable(state))
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
        state = isVertical() ? State.BOTH : State.HORIZONTAL;
        sides.get(Side.LEFT).setWall(true);
        sides.get(Side.RIGHT).setWall(true);
        return true;
    }

    private boolean setVertical() {
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

    @Override
    @Deprecated
    public String toString() {
        return "";
    }

    public String toString(Side askedSide) {
        return sides.get(askedSide).hasWall() ? wallCharacter : noWallCharacter;
    }

    public Set<WallSquareSide> searchContinuousWall(WallSquare start, WallSquareSide origin) {
        if (this == start)
            throw new LoopWallException();
        Set<WallSquareSide> travel = new HashSet<>();
        for (Map.Entry<Side, WallSquareSide> entry: sides.entrySet()) {
            if (entry.getValue() != origin)
                travel.addAll(entry.getValue().searchContinuousWall(start,this));
        }
        return travel;
    }

    private boolean doesWallReachEdge(WallSquare origin) {
        boolean reachEdge = false;
        for (Map.Entry<Side, WallSquareSide> entry : sides.entrySet()) {
            if (reachEdge)
                return true;
            WallSquare secondOwner = entry.getValue().getSecondOwner(this);
            if (entry.getValue().hasWall() && secondOwner == null)
                return true;
            if (entry.getValue().hasWall() && secondOwner != origin)
                reachEdge |= secondOwner.doesWallReachEdge(this);
        }
        return reachEdge;
    }

    private boolean isWallValid(Side sideOne, Side sideTwo) {
        // search for edge to edge wall
        Map<Side, Boolean> reachEdges = new HashMap<>();
        int occurrence = 0;
        for (Side s : Side.values()) {
            if (sides.get(s).hasWall() || s == sideOne || s == sideTwo) {
                WallSquare sideSecondOwner = sides.get(s).getSecondOwner(this);
                reachEdges.put(s, sideSecondOwner == null || sideSecondOwner.doesWallReachEdge(this));
            }
        }
        for (Map.Entry<Side, Boolean> entry : reachEdges.entrySet())
            if (entry.getValue())
                occurrence ++;
        if (occurrence >= 2)
            return false;
        // search for continuous wall
        WallSquare sideOneSecondOwner = sides.get(sideOne).getSecondOwner(this);
        WallSquare sideTwoSecondOwner = sides.get(sideTwo).getSecondOwner(this);
        if (sideOneSecondOwner != null && sideTwoSecondOwner != null) {
            try {
                Set<WallSquareSide> firstSearch = sideOneSecondOwner.searchContinuousWall(this, sides.get(sideOne));
                Set<WallSquareSide> secondSearch = sideTwoSecondOwner.searchContinuousWall(this, sides.get(sideTwo));
                firstSearch.retainAll(secondSearch);
                if (firstSearch.size() > 0)
                    return false;
            } catch (LoopWallException e) {
                return false;
            }
        }
        return true;
    }

    private void addSide(Side side, WallSquareSide wallSquareSide) {
        wallSquareSide.addOwner(this);
        sides.put(side, wallSquareSide);
    }
}
