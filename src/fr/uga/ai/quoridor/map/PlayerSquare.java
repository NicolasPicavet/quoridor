package fr.uga.ai.quoridor.map;

import fr.uga.ai.quoridor.player.Player;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PlayerSquare {

    private Coordinates coordinates;
    private Player player;
    private Set<WallSquare> neighbourWalls;
    private java.util.Map<Side, PlayerSquare> neighbours;

    public PlayerSquare(Coordinates coordinates, Set<WallSquare> neighbourWalls) {
        this.coordinates = coordinates;
        this.neighbourWalls = neighbourWalls;
    }

    public Set<PlayerSquare> findPath(Set<PlayerSquare> origin, PlayerSquare destination) {
        // if the set contains the current square its means we have done a loop
        if (origin.contains(this))
            return null;
        // add the current node
        origin.add(this);
        // if we reach destination return the set of PlayerSquare leading here
        if (this == destination)
            return origin;
        // do the searchContinuousWall for each side of the square
        Set<PlayerSquare> next = new HashSet<>();
        for (Side side : Side.values()) {
            // check that there is no wall between this Square and the next
            if (!hasWallWith(neighbours.get(side))) {
                next = neighbours.get(side).findPath(origin, destination);
                // if the searchContinuousWall return something else than null it mean it has reach destination
                if (next != null)
                    return next;
            }
        }
        return null;
    }

    public boolean hasWallWith(PlayerSquare playerSquare) {
        // get intersected walls
        Set<WallSquare> intersectWalls = new HashSet<>(neighbourWalls);
        intersectWalls.retainAll(playerSquare.neighbourWalls);
        // iterate the intersected walls
        Iterator<WallSquare> iterator = intersectWalls.iterator();
        WallSquare from = iterator.next();
        // on a border
        if (intersectWalls.size() == 1) {
            // same column
            if (coordinates.x == playerSquare.coordinates.x)
                return coordinates.x == 0 ? from.sides.get(Side.LEFT).hasWall() : from.sides.get(Side.RIGHT).hasWall();
            // same line
            else
                return coordinates.y == 0 ? from.sides.get(Side.TOP).hasWall() : from.sides.get(Side.BOTTOM).hasWall();
        }
        // get intersected sides and check them
        WallSquare to = iterator.next();
        return from.getIntersectSides(to).hasWall();
    }

    public void setNeighbours(java.util.Map<Side, PlayerSquare> neighbours) {
        this.neighbours = neighbours;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Map<Side, PlayerSquare> getNeighbours() {
        return neighbours;
    }

    public Set<PlayerSquare> getMovePossibilities() {
        //TODO return move possibilities
        return null;
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

    public Set<WallSquare> getNeighbourWalls() {
        return neighbourWalls;
    }

    public boolean isNeighbourOf(PlayerSquare playerSquare) {
        return neighbours.containsValue(playerSquare);
    }

    public Side getNeighbourSide(PlayerSquare playerSquare) {
        if (!isNeighbourOf(playerSquare))
            throw new IllegalArgumentException("!!! Not a neighbour player square");
        for (Map.Entry<Side, PlayerSquare> entry : neighbours.entrySet()) {
            if (entry.getValue() == playerSquare)
                return entry.getKey();
        }
        return null;
    }

    @Override
    public String toString() {
        return player != null ? player.toString() : " ";
    }
}
