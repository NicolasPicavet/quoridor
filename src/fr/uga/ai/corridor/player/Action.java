package fr.uga.ai.corridor.player;

import fr.uga.ai.corridor.map.Coordinates;

public class Action {

    public enum Type {
        MOVE, BUILD_VERTICAL, BUILD_HORIZONTAL;
    }

    private Type type;
    private Coordinates coordinates;

    public Action(Type type, Coordinates coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
