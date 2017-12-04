package fr.uga.ai.quoridor.player.ai;

import fr.uga.ai.quoridor.IO;
import fr.uga.ai.quoridor.map.Coordinates;
import fr.uga.ai.quoridor.map.Map;
import fr.uga.ai.quoridor.map.PlayerSquare;
import fr.uga.ai.quoridor.map.Side;
import fr.uga.ai.quoridor.player.Action;
import fr.uga.ai.quoridor.player.Player;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

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
        java.util.Map<Integer, Coordinates> movePossibilities = new TreeMap<>();
        for (java.util.Map.Entry<Side, PlayerSquare> entry : actualPosition.getNeighbours().entrySet()) {
            if (entry.getValue() != null) {
                int manhattanDistance = Map.getInstance().getManhattanDistance(entry.getValue(), closestPossibility);
                while (movePossibilities.containsKey(manhattanDistance)) {
                    manhattanDistance++;
                }
                movePossibilities.put(manhattanDistance, entry.getValue().getCoordinates());
            }
        }
        // test move action in order of priority
        Iterator i = movePossibilities.entrySet().iterator();
        while (i.hasNext()) {
            java.util.Map.Entry<Integer, Coordinates> entry = (java.util.Map.Entry) i.next();
            IO.println(entry.getKey() + "");
            action = new Action(Action.Type.MOVE, entry.getValue());
            if (action.isValid(this))
                break;
        }
        // return the best move action
        return action;
    }
}
