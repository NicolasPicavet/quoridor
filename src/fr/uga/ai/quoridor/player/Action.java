package fr.uga.ai.quoridor.player;

import fr.uga.ai.quoridor.map.Coordinates;
import fr.uga.ai.quoridor.map.Map;
import fr.uga.ai.quoridor.map.PlayerSquare;
import fr.uga.ai.quoridor.map.WallSquare;

public class Action {

    public enum Type {
        MOVE, BUILD_VERTICAL, BUILD_HORIZONTAL;

        public boolean isMove() {
            return this == MOVE;
        }

        public boolean isBuild() {
            return this == BUILD_HORIZONTAL || this == BUILD_VERTICAL;
        }
    }

    private Type type;
    private Coordinates coordinates;

    public Action(Type type, Coordinates coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }

    public boolean isValid(Player player) {
        // test if destination is a neighbour and has no obstacle
        if (type.isMove()) {
            //TODO jump over player
            PlayerSquare from = Map.getInstance().getPlayerSquareFromCoordinates(player.getCoordinates());
            PlayerSquare to = Map.getInstance().getPlayerSquareFromCoordinates(this.coordinates);
            boolean isNeighbour = from.isNeighbourOf(to);
            boolean hasWall = from.hasWallWith(to);
            boolean isFree = to.isEmpty();
            return isNeighbour && !hasWall && isFree;
        } else if (type.isBuild()) {
            if (player.getWallBank() > 0) {
                if (type == Action.Type.BUILD_HORIZONTAL)
                    return Map.getInstance().getWallSquareFromCoordinates(coordinates).isBuildable(WallSquare.State.HORIZONTAL);
                else if (type == Action.Type.BUILD_VERTICAL)
                    return Map.getInstance().getWallSquareFromCoordinates(coordinates).isBuildable(WallSquare.State.VERTICAL);
            }
        }
        return false;
    }

    public Coordinates getCoordinates() {
        if (coordinates == null)
            throw new IllegalStateException("!!! action coordinates must not be null");
        return coordinates;
    }

    public Type getType() {
        return type;
    }
}
