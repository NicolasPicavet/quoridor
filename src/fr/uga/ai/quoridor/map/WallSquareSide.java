package fr.uga.ai.quoridor.map;

import java.util.HashSet;
import java.util.Set;

public class WallSquareSide {

    private boolean wall;

    private Set<WallSquare> owners = new HashSet<>();

    public WallSquareSide() {
    }

    public boolean hasWall() {
        return wall;
    }

    public void setWall(boolean wall) {
        this.wall = wall;
    }

    public WallSquare getSecondOwner(WallSquare firstOwner) {
        // surely there is a better way
        Set<WallSquare> ownersCopy = new HashSet<>(owners);
        ownersCopy.remove(firstOwner);
        for (WallSquare s : ownersCopy) {
            return s;
        }
        // is a side on edge
        return null;
    }

    public Set<WallSquareSide> searchContinuousWall(WallSquare start, WallSquare firstOwner) {
        Set<WallSquareSide> travel = new HashSet<>();
        if (hasWall()) {
            travel = getSecondOwner(firstOwner).searchContinuousWall(start,this);
            travel.add(this);
        }
        return travel;
    }

    public void addOwner(WallSquare owner) {
        if (owners.size() >= 2)
            throw new IllegalStateException("!!! addOwner call too many times (more than 2)");
        owners.add(owner);
    }
}
