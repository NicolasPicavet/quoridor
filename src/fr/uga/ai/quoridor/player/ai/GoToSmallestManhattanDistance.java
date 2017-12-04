package fr.uga.ai.quoridor.player.ai;

import fr.uga.ai.quoridor.map.Coordinates;
import fr.uga.ai.quoridor.map.Map;
import fr.uga.ai.quoridor.map.PlayerSquare;
import fr.uga.ai.quoridor.map.Side;
import fr.uga.ai.quoridor.player.Action;
import fr.uga.ai.quoridor.player.Player;

import java.util.ArrayList;
import java.util.List;
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
        List<Coordinates> sortedMovePossibilities = new ArrayList<>();
        for (java.util.Map.Entry<Side, PlayerSquare> entry : actualPosition.getNeighbours().entrySet()) {
            if (entry.getValue() != null) {
                //sortedMovePossibilities.add();
                int manhattanDistance = Map.getInstance().getManhattanDistance(entry.getValue(), closestPossibility);
                if (smallestManhattanDistance > manhattanDistance) {
                    bestCoordinates = entry.getValue().getCoordinates();
                    smallestManhattanDistance = manhattanDistance;
                }
            }
        }
        // TODO create a List with potential destinations by priority
        // TODO test move action in order of priority
        // TODO execute the first valid one
        // create a move action
        return new Action(Action.Type.MOVE, bestCoordinates);
    }
}
