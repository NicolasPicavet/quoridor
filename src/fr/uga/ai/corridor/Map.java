package fr.uga.ai.corridor;

import java.util.HashMap;

public class Map {

    public final static int PLAYER_MAP_SIZE = 9;
    public final static int WALL_MAP_SIZE = PLAYER_MAP_SIZE - 1;

    public final static String PLAYER_MAP_LABELS = "ABCDEFGHI";
    public final static String WALL_MAP_LABELS = "abcdefgh";

    PlayerSquare[][] playerMap = new PlayerSquare[PLAYER_MAP_SIZE][PLAYER_MAP_SIZE];
    WallSquare[][] wallMap = new WallSquare[WALL_MAP_SIZE][WALL_MAP_SIZE];

    java.util.Map<Integer, Coordinates> playerPositions = new HashMap<>();

    public Map() {
        // initialize maps with empty squares
        for (int x = 0; x < PLAYER_MAP_SIZE; x++)
            for (int y = 0; y < PLAYER_MAP_SIZE; y++)
                playerMap[x][y] = new PlayerSquare();
        for (int x = 0; x < WALL_MAP_SIZE; x++)
            for (int y = 0; y < WALL_MAP_SIZE; y++)
                wallMap[x][y] = new WallSquare();
        // initialize player position
        playerPositions.put(1, new Coordinates(0, 4));
        playerPositions.put(2, new Coordinates(8, 4));
    }

    public boolean setPlayerPosition(int playerId, Coordinates coordinates) {
        // TODO check previous / new position : wall, distance
        // remove previous position
        playerMap[playerPositions.get(playerId).x][playerPositions.get(playerId).y].playerId = 0;
        // add new position
        playerPositions.put(playerId, coordinates);
        playerMap[coordinates.x][coordinates.y].playerId = playerId;
        return true;
    }

    public boolean buildWall(Coordinates coordinates, WallSquare.State state) {
        // TODO check wall buildable
        // build wall
        wallMap[coordinates.x][coordinates.y].build(state);
        return true;
    }

    public String draw() {
        String map = "";
        // write player map column labels
        map += " |";
        for (int xplayer = 0; xplayer < PLAYER_MAP_SIZE; xplayer++) {
            map += PLAYER_MAP_LABELS.charAt(xplayer);
            if (xplayer < PLAYER_MAP_SIZE - 1)
                map += " ";
        }
        map += "|\n";
        // write wall map column labels
        map += " |";
        for (int xwall = 0; xwall < WALL_MAP_SIZE; xwall++)
            map += " " + WALL_MAP_LABELS.charAt(xwall);
        map += " |\n";
        // separation
        map += "-|";
        for (int x = 0; x < WALL_MAP_SIZE + PLAYER_MAP_SIZE; x++)
            map += "-";
        map += "|\n";
        // write maps
        for (int yplayer = 0; yplayer < PLAYER_MAP_SIZE; yplayer++) {
            // line label
            map += String.valueOf(yplayer) + "|";
            // player map line
            for (int xplayer = 0; xplayer < PLAYER_MAP_SIZE; xplayer++) {
                int ywallTop = yplayer - 1;
                int ywallBottom = yplayer;
                int xwall = xplayer;
                // player square
                map += playerMap[xplayer][yplayer].toString();
                if (xwall < WALL_MAP_SIZE) {
                    // top right wall
                    if (ywallTop > 0 && wallMap[xwall][ywallTop].isVertical())
                        map += wallMap[xwall][ywallTop].toString();
                    // bottom right wall
                    else if (ywallBottom < WALL_MAP_SIZE && wallMap[xwall][ywallBottom].isVertical())
                        map += wallMap[xwall][ywallBottom].toString();
                    else
                        map += " ";
                }
            }
            map += "|\n";
            // wall map line
            if (yplayer < WALL_MAP_SIZE) {
                map += " |";
                int ywall = yplayer;
                for (int xwall = 0; xwall < WALL_MAP_SIZE; xwall++) {
                    if (wallMap[xwall][ywall].isHorizontal())
                        map += wallMap[xwall][ywall].toString() + wallMap[xwall][ywall].toString();
                    else if (wallMap[xwall][ywall].isVertical())
                        map += " " + wallMap[xwall][ywall].toString();
                    else
                        map += "  ";
                    if (xwall == WALL_MAP_SIZE - 1) {
                        if (wallMap[xwall][ywall].isHorizontal())
                            map += wallMap[xwall][ywall].toString();
                        else
                            map += " ";
                    }

                }
                map += "|\n";
            }
        }
        // separation
        map += "-|";
        for (int x = 0; x < WALL_MAP_SIZE + PLAYER_MAP_SIZE; x++)
            map += "-";
        map += "|\n";
        return map;
    }
}
