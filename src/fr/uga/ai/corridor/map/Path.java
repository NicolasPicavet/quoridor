package fr.uga.ai.corridor.map;

import java.util.HashSet;
import java.util.Set;

public class Path {

    private Set<PlayerSquare> playerSquares;

    public Path(Set<PlayerSquare> playerSquares) {
        if (playerSquares == null)
            throw new IllegalArgumentException("!!! playerSquares must not be null");
        if (playerSquares.size() < 2)
            throw new IllegalArgumentException("!!! playerSquares must contains at least 2 elements");
        this.playerSquares = playerSquares;
    }

    public boolean isOpen() {
        PlayerSquare previous = null;
        Set<WallSquareSide> sidesToCheck = new HashSet<>();
        for (PlayerSquare ps : playerSquares) {
            if (previous != null) {
                // get wall squares in common between previous and current
                Set<WallSquare> wallSquareIntersection = new HashSet<>(ps.getNeighbourWalls());
                wallSquareIntersection.retainAll(previous.getNeighbourWalls());
                // get wall square sides in common
                Set<WallSquareSide> sidesIntersection = new HashSet<>();
                Set<WallSquareSide> allSides = new HashSet<>();
                for (WallSquare ws : wallSquareIntersection)
                    for (WallSquareSide s : ws.sides.values())
                        if (allSides.contains(s))
                            if (!sidesIntersection.contains(s))
                                sidesIntersection.add(s);
                        else
                            allSides.add(s);
                sidesToCheck.addAll(sidesIntersection);
            }
            previous = ps;
        }
        // check sides
        for (WallSquareSide s : sidesToCheck)
            if (s.hasWall())
                return false;
        return true;
    }
}
