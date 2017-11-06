package fr.uga.ai.corridor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        int playingPlayer = 1;
        boolean game = true;
        Map map = new Map();

        println(" ---- Corridor ----");
        while(game) {
            println(map.draw());

            Coordinates coordinates = readCoordinates();

            if (coordinates.isAMoveAction()) {
                map.setPlayerPosition(playingPlayer, coordinates);
            } else {
                map.buildWall(coordinates, coordinates.state);
            }

            // win

            // action success
            if (true) {
                playingPlayer++;
                if (playingPlayer > 2)
                    playingPlayer = 1;
            }
        }
        println("--- / ---");
    }

    static void println(String s) {
        System.out.println(s);
    }

    static void print(String s) {
        System.out.print(s);
    }

    static Coordinates readCoordinates() throws IOException {
        while (true) {
            try {
                String read = br.readLine();
                boolean moveAction = false;
                int x;
                String firstChar = read.substring(0, 1);
                int y = Integer.valueOf(read.substring(1, 2));
                if (Map.PLAYER_MAP_LABELS.contains(firstChar)) {
                    // move player
                    moveAction = true;
                    x = Map.PLAYER_MAP_LABELS.indexOf(firstChar);
                    return new Coordinates(x, y, moveAction);
                } else {
                    // build wall
                    x = Map.WALL_MAP_LABELS.indexOf(firstChar);
                    String thirdChar = read.substring(2, 3);
                    if (thirdChar.contains("h"))
                        return new Coordinates(x, y, WallSquare.State.HORIZONTAL);
                    if (thirdChar.contains("v"))
                        return new Coordinates(x, y, WallSquare.State.VERTICAL);
                }
            } catch (Exception e) {
                // do nothing
            }
        }
    }
}
