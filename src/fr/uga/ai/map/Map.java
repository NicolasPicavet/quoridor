package fr.uga.ai.map;

import fr.uga.ai.map.elements.WallElement;

import static fr.uga.ai.map.elements.PlayerElement.*;

public class Map {
    public static String columnLabels = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String lineLabels = "123456789";

    int sizeX;
    int sizeY;
    Square[][] map;

    public Map(int sizeX, int sizeY) {
        if (sizeX % 2 == 0 || sizeX < 3 || sizeX > columnLabels.length() || sizeY % 2 == 0 && sizeY <= 3 && sizeY > lineLabels.length())
            throw new IllegalArgumentException("Even/too low/too big numbers given");
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        map = new Square[sizeX][sizeY];

        for (int x = 0; x < sizeX; x++)
            for (int y = 0; y < sizeY; y++)
                map[x][y] = new Square();

        placePlayer(0, sizeY / 2, 1);
        setWinX(sizeX - 1, 1);
        placePlayer(sizeX - 1, sizeY / 2, 2);
        setWinX(0, 2);
    }

    public Map() {
        this(9,9);
    }

    public String draw() {
        String s = "---";
        for (int x = 0; x < sizeX; x++) {
            s += "-";
        }
        s += "\n| |";
        for (int x = 0; x < sizeX; x++) {
            s += " " + columnLabels.charAt(x);
        }
        s += "\n---";
        for (int x = 0; x < sizeX; x++) {
            s += "-";
        }
        s += "\n";
        for (int y = 0; y < sizeY; y ++) {
            s += "|" + lineLabels.charAt(y) + "|";
            for (int x = 0; x < sizeX; x++) {
                s += " " + getSquare(x, y).toString();
            }
            s += "\n";
        }
        return s;
    }

    public boolean placePlayer(int x, int y, int playerNumber) {
        setCoordinates(x, y, playerNumber);
        return getSquare(x, y).setElement(getPlayer(playerNumber));
    }

    public boolean placeWithConditionPlayer(int x, int y, int playerNumber) {
        int[] playerCoordinates = getCoordinates(playerNumber);
        if (playerCoordinates[0] <= x + 1 && playerCoordinates[0] >= x - 1 && playerCoordinates[1] <= y + 1 && playerCoordinates [1] >= y - 1) {
            getSquare(playerCoordinates[0], playerCoordinates[1]).removeElement();
            return placePlayer(x, y, playerNumber);
        }
        return false;
    }

    public boolean playerInWinPosition(int playerNumber) {
        return hasWon(playerNumber);
    }

    public boolean addWall(int x, int y, int playerNumber) {
        return getSquare(x, y).setElement(new WallElement(playerNumber));
    }

    Square getSquare(int x, int y) {
        return map[x][y];
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }
}
