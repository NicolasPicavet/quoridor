package fr.uga.ai.corridor.map;

import fr.uga.ai.corridor.IO;
import fr.uga.ai.corridor.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Map {

    public final static int PLAYER_MAP_SIZE = 9;
    public final static int WALL_MAP_SIZE = PLAYER_MAP_SIZE - 1;

    public final static String PLAYER_MAP_LABELS = "ABCDEFGHI";
    public final static String WALL_MAP_LABELS = "abcdefgh";

    private static Map instance = new Map();

    private PlayerSquare[][] playerMap = new PlayerSquare[PLAYER_MAP_SIZE][PLAYER_MAP_SIZE];
    private WallSquare[][] wallMap = new WallSquare[WALL_MAP_SIZE][WALL_MAP_SIZE];

    private Map() {
        // init with empty wall square
        for (int x = 0; x < WALL_MAP_SIZE; x++)
            for (int y = 0; y < WALL_MAP_SIZE; y++) {
                WallSquareSide left = x - 1 < 0 ? new WallSquareSide() : wallMap[x - 1][y].sides.get(Side.RIGHT);
                WallSquareSide top = y - 1 < 0 ? new WallSquareSide() : wallMap[x][y - 1].sides.get(Side.BOTTOM);
                WallSquareSide right =  new WallSquareSide();
                WallSquareSide bottom =  new WallSquareSide();
                wallMap[x][y] = new WallSquare(left, top, right, bottom);
            }
        // init with empty player square
        for (int x = 0; x < PLAYER_MAP_SIZE; x++)
            for (int y = 0; y < PLAYER_MAP_SIZE; y++) {
                Set<WallSquare> neighbourWalls = new HashSet<>();
                if (x < WALL_MAP_SIZE && y < WALL_MAP_SIZE)
                    neighbourWalls.add(wallMap[x][y]);
                if (x - 1 >= 0 && y < WALL_MAP_SIZE)
                    neighbourWalls.add(wallMap[x - 1][y]);
                if (x < WALL_MAP_SIZE && y - 1 >= 0)
                    neighbourWalls.add(wallMap[x][y - 1]);
                if (x - 1 >= 0 && y - 1 >= 0)
                    neighbourWalls.add(wallMap[x - 1][y - 1]);
                playerMap[x][y] = new PlayerSquare(new Coordinates(x, y), neighbourWalls);
            }
        // add neighbour references
        for (int x = 0; x < PLAYER_MAP_SIZE; x++)
            for (int y = 0; y < PLAYER_MAP_SIZE; y++) {
                java.util.Map<Side, PlayerSquare> neighbours = new HashMap<>();
                for (Side side : Side.values())
                    neighbours.put(side, getNeighbour(side, new Coordinates(x, y)));
                playerMap[x][y].setNeighbours(neighbours);
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
        boolean destinationEmpty = playerMap[coordinates.x][coordinates.y].isEmpty();
        if (destinationEmpty) {
            playerMap[coordinates.x][coordinates.y].setPlayer(player);
            return true;
        }
        return false;
    }

    public boolean buildWall(Coordinates coordinates, WallSquare.State state) {
        return wallMap[coordinates.x][coordinates.y].build(state);
    }

    public boolean searchOpenPath(Coordinates from, Coordinates to) {
        IO.println("from " + from.x + from.y + " to " + to.x + to.y);
        // create a new Path Object from the recursive search and return its openness
        return new Path(playerMap[from.x][from.y].findPath(new HashSet<>(), playerMap[to.x][to.y])).isOpen();
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
                        map += wallMap[xwall][ywallTop].toString(Side.BOTTOM);
                    else
                        // check bottom right wall
                        if (ywallBottom < WALL_MAP_SIZE)
                            map += wallMap[xwall][ywallBottom].toString(Side.TOP);
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
                    map += wallMap[xwall][ywall].toString(Side.LEFT);
                    map += "+";
                    if (xwall + 1 == WALL_MAP_SIZE)
                        map += wallMap[xwall][ywall].toString(Side.RIGHT);
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

    public PlayerSquare getPlayerSquareFromCoordinates(Coordinates coordinates) {
        return playerMap[coordinates.x][coordinates.y];
    }

    private PlayerSquare getNeighbour(Side side, Coordinates coordinates) {
        int x = coordinates.x;
        int y = coordinates.y;
        if (side == Side.LEFT)
            x -= 1;
        else if (side == Side.TOP)
            y -= 1;
        else if (side == Side.RIGHT)
            x += 1;
        else if (side == Side.BOTTOM)
            y += 1;
        if (x < PLAYER_MAP_SIZE && y < PLAYER_MAP_SIZE && x >= 0 && y >= 0)
            return playerMap[x][y];
        return null;
    }
}
