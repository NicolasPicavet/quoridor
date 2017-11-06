package fr.uga.ai.map.elements;

public class WallElement extends Element {

    public static final String playerOneWall = "|";
    public static final String playerTwoWall = "|";

    public WallElement(int playerNumber) {
        super(playerNumber);
    }

    public String toString() {
        return playerNumber == 1 ? playerOneWall : playerTwoWall;
    }
}
