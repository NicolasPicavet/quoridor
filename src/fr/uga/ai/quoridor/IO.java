package fr.uga.ai.quoridor;

import fr.uga.ai.quoridor.map.Coordinates;
import fr.uga.ai.quoridor.map.Map;
import fr.uga.ai.quoridor.player.Action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IO {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static Action readAction() {
        // while input not well formed
        while (true) {
            try {
                String read = br.readLine();
                String firstChar = read.substring(0, 1);
                int x;
                int y = Integer.valueOf(read.substring(1, 2));
                if (Map.PLAYER_MAP_LABELS.contains(firstChar)) {
                    // move player
                    x = Map.PLAYER_MAP_LABELS.indexOf(firstChar);
                    return new Action(Action.Type.MOVE, new Coordinates(x, y));
                } else if (Map.WALL_MAP_LABELS.contains(firstChar)){
                    // build wall
                    x = Map.WALL_MAP_LABELS.indexOf(firstChar);
                    String thirdChar = read.substring(2, 3);
                    if (thirdChar.contains("h"))
                        return new Action(Action.Type.BUILD_HORIZONTAL, new Coordinates(x, y));
                    else if (thirdChar.contains("v"))
                        return new Action(Action.Type.BUILD_VERTICAL, new Coordinates(x, y));
                }
            } catch (IOException e) {
                // do nothing
            } catch (NumberFormatException e) {
                // do nothing
            }
        }
    }

    public static int readNumericChoice() {
        // while input not well formed
        while (true) {
            try {
                String read = br.readLine();
                return Integer.valueOf(read.substring(0, 1));
            } catch (IOException e) {
                // do nothing
            } catch (NumberFormatException e) {
                // do nothing
            }
        }
    }

    public static void println(String s) {
        System.out.println(s);
    }

    public static void print(String s) {
        System.out.print(s);
    }
}
