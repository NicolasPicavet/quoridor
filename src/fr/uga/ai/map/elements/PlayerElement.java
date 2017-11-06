package fr.uga.ai.map.elements;

public class PlayerElement extends Element {

    public static final String playerOneCharacter = "O";
    public static final String playerTwoCharacter = "X";

    public static final PlayerElement playerOne = new PlayerElement(1);
    public static final PlayerElement playerTwo = new PlayerElement(2);

    int x;
    int y;
    int winX;

    PlayerElement(int playerNumber) {
        super(playerNumber);
    }

    public String toString() {
        return playerNumber == 1 ? playerOneCharacter : playerTwoCharacter;
    }

    public static int[] getCoordinates(int playerNumber) {
        return getPlayer(playerNumber).getCoordinates();
    }

    int[] getCoordinates() {
        return new int[]{x, y};
    }

    public static void setCoordinates(int x, int y, int playerNumber) {
        getPlayer(playerNumber).setCoordinates(x, y);
    }

    void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static PlayerElement getPlayer(int playerNumber) {
        if (playerNumber == 1)
            return playerOne;
        return playerTwo;
    }

    public static void setWinX(int winX, int playerNumber) {
        getPlayer(playerNumber).setWinX(winX);
    }

    void setWinX(int winX) {
        this.winX = winX;
    }

    public static boolean hasWon(int playerNumber) {
        return getPlayer(playerNumber).hasWon();
    }

    boolean hasWon() {
        return winX == x;
    }
}
