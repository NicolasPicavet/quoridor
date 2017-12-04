package fr.uga.ai.corridor.player.ai;

import fr.uga.ai.corridor.map.Coordinates;
import fr.uga.ai.corridor.map.Map;
import fr.uga.ai.corridor.map.PlayerSquare;
import fr.uga.ai.corridor.map.Side;
import fr.uga.ai.corridor.player.Action;
import fr.uga.ai.corridor.player.Player;

import java.util.Set;

public class GoToSmallestManhattanDistance extends Player implements IsArtificial {

    private Set<PlayerSquare> winPossibilities;

    public GoToSmallestManhattanDistance(Integer playerId, String character, Coordinates initialPosition, Integer winX) {
        super(playerId, character, initialPosition, winX);
        this.winPossibilities = Map.getInstance().getWinPossibilities(this.winX);
    }

    @Override
    public Action evaluate() {
        // calculate Manhattan distance to win possibilities, and take the smallest one
        PlayerSquare closestPossibility = null;
        int smallestManhattanDistance = Integer.MAX_VALUE;
        PlayerSquare actualPosition = Map.getInstance().getPlayerSquareFromCoordinates(coordinates);
        for (PlayerSquare s : winPossibilities) {
            int manhattanDistance = Map.getInstance().getManhattanDistance(actualPosition, s);
            if (smallestManhattanDistance > manhattanDistance) {
                closestPossibility = s;
                smallestManhattanDistance = manhattanDistance;
            }
        }
        // determine best coordinates to go to
        smallestManhattanDistance = Integer.MAX_VALUE;
        Coordinates bestCoordinates = null;
        for (java.util.Map.Entry<Side, PlayerSquare> entry : actualPosition.getNeighbours().entrySet()) {
            if (entry.getValue() != null) {
                int manhattanDistance = Map.getInstance().getManhattanDistance(entry.getValue(), closestPossibility);
                if (smallestManhattanDistance > manhattanDistance) {
                    bestCoordinates = entry.getValue().getCoordinates();
                    smallestManhattanDistance = manhattanDistance;
                }
            }
        }
        // create a move action
        return new Action(Action.Type.MOVE, bestCoordinates);
    }
}
