package fr.uga.ai.corridor.map;

import fr.uga.ai.corridor.Player;

public class Map {

    public final static int PLAYER_MAP_SIZE = 9;
    public final static int WALL_MAP_SIZE = PLAYER_MAP_SIZE - 1;

    public final static String PLAYER_MAP_LABELS = "ABCDEFGHI";
    public final static String WALL_MAP_LABELS = "abcdefgh";

    private static Map instance = new Map();

    private PlayerSquare[][] playerMap = new PlayerSquare[PLAYER_MAP_SIZE][PLAYER_MAP_SIZE];
    private WallSquare[][] wallMap = new WallSquare[WALL_MAP_SIZE][WALL_MAP_SIZE];

    private Map() {
        // init with empty player square
        for (int x = 0; x < PLAYER_MAP_SIZE; x++)
            for (int y = 0; y < PLAYER_MAP_SIZE; y++)
                playerMap[x][y] = new PlayerSquare();
        // init with empty wall square
        for (int x = 0; x < WALL_MAP_SIZE; x++)
            for (int y = 0; y < WALL_MAP_SIZE; y++) {
                WallSquareSide left = x - 1 < 0 ? new WallSquareSide() : wallMap[x - 1][y].sides.get(WallSquareSide.Side.RIGHT);
                WallSquareSide top = y - 1 < 0 ? new WallSquareSide() : wallMap[x][y - 1].sides.get(WallSquareSide.Side.BOTTOM);
                WallSquareSide right =  new WallSquareSide();
                WallSquareSide bottom =  new WallSquareSide();
                wallMap[x][y] = new WallSquare(left, top, right, bottom);
            }
    }

    public static Map getInstance() {
        return instance;
    }

    public boolean removePlayer(Player player) {
        Coordinates coordinates = player.getCoordinates();
        if (playerMap[coordinates.x][coordinates.y].hasPlayer(player)) {
            playerMap[coordinates.x][coordinates.y].removePlayer();
            return true;
        }
        return false;
    }

    public boolean addPlayer(Player player) {
        Coordinates coordinates = player.getCoordinates();
        if (playerMap[coordinates.x][coordinates.y].isEmpty()) {
            playerMap[coordinates.x][coordinates.y].setPlayer(player);
            return true;
        }
        return false;
    }

    public boolean buildWall(Coordinates coordinates, WallSquare.State state) {
        return wallMap[coordinates.x][coordinates.y].build(state);
    }

    public String draw(Player playingPlayer) {
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
        map += "|";
        // playing player
        map += " Player " + playingPlayer.toString() + " turn\n";
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
                    // check top right wall
                    if (ywallTop >= 0 && wallMap[xwall][ywallTop].isVertical())
                        map += wallMap[xwall][ywallTop].toString(WallSquareSide.Side.BOTTOM);
                    else
                        // check bottom right wall
                        if (ywallBottom < WALL_MAP_SIZE)
                            map += wallMap[xwall][ywallBottom].toString(WallSquareSide.Side.TOP);
                        else
                            map += WallSquare.noWallCharacter;
                }
            }
            map += "|\n";
            // wall map line
            if (yplayer < WALL_MAP_SIZE) {
                map += " |";
                int ywall = yplayer;
                for (int xwall = 0; xwall < WALL_MAP_SIZE; xwall++) {
                    map += wallMap[xwall][ywall].toString(WallSquareSide.Side.LEFT);
                    map += "+";
                    if (xwall + 1 == WALL_MAP_SIZE)
                        map += wallMap[xwall][ywall].toString(WallSquareSide.Side.RIGHT);
                }
                map += "|\n";
            }
        }
        // separation
        map += "-|";
        for (int x = 0; x < WALL_MAP_SIZE + PLAYER_MAP_SIZE; x++)
            map += "-";
        map += "|";
        // players wall bank
        map += " " +
                Player.PLAYER_ONE.toString() + " " + Integer.toString(Player.PLAYER_ONE.getWallBank()) +
                " - " +
                Player.PLAYER_TWO.toString() + " " + Integer.toString(Player.PLAYER_TWO.getWallBank()) + "\n";
        return map;
    }
}
