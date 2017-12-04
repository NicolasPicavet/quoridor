package fr.uga.ai.quoridor.player.ai;

import fr.uga.ai.quoridor.map.Coordinates;
import fr.uga.ai.quoridor.map.Map;
import fr.uga.ai.quoridor.map.PlayerSquare;
import fr.uga.ai.quoridor.player.Action;
import fr.uga.ai.quoridor.player.Player;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class GoToSmallestManhattanDistance extends Player implements IsArtificial {

    private Set<PlayerSquare> winPossibilities;

    public GoToSmallestManhattanDistance(Integer playerId, String character, Coordinates initialPosition, Integer winX) {
        super(playerId, character, initialPosition, winX);
        this.winPossibilities = Map.getInstance().getWinPossibilities(this.winX);
    }

    @Override
    public Action evaluate() {
        Action action = null;
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
        // sort coordinates to go to by priority
        java.util.Map<Integer, Coordinates> movePossibilities = new HashMap<>();
        for (PlayerSquare playerSquare : actualPosition.getMovePossibilities()) {
            if (playerSquare != null) {
                int manhattanDistance = Map.getInstance().getManhattanDistance(playerSquare, closestPossibility);
                while (movePossibilities.containsKey(manhattanDistance)) {
                    manhattanDistance++;
                }
                movePossibilities.put(manhattanDistance, playerSquare.getCoordinates());
            }
        }
        // test move action in order of priority
        Iterator i = movePossibilities.entrySet().iterator();
        while (i.hasNext()) {
            java.util.Map.Entry<Integer, Coordinates> entry = (java.util.Map.Entry) i.next();
            action = new Action(Action.Type.MOVE, entry.getValue());
            if (action.isValid(this))
                break;
        }
        // return the best move action
        return action;
    }
}
