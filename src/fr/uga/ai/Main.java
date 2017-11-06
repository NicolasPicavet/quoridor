package fr.uga.ai;

import fr.uga.ai.map.Map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        int x;
        int y;
        int playingPlayer = 1;
        boolean game = true;

        println("--- Corridor ---");

        /*print("Map.x = ");
        x = readInteger();
        print("Map.y = ");
        y = readInteger();
        Map map = new Map(x, y);*/
        Map map = new Map();

        while(game) {
            println(map.draw());

            println("(1) Build wall");
            println("(2) Move");
            boolean buildChoice = readInteger(1, 2) == 1;
            print("x = ");
            x = readInteger(1, map.getSizeX()) - 1;
            print("y = ");
            y = readInteger(1, map.getSizeY()) - 1;
            /*int[] coordinates = readMapCoordinate(1, map.getSizeX(), 1, map.getSizeY());
            x = coordinates[0];
            y = coordinates[1];*/

            boolean actionSuccess = false;
            if (buildChoice)
                actionSuccess = map.addWall(x , y, playingPlayer);
            else
                actionSuccess = map.placeWithConditionPlayer(x , y, playingPlayer);
            game = !map.playerInWinPosition(playingPlayer);
            if (game)
                if (actionSuccess) {
                    playingPlayer++;
                    if (playingPlayer > 2)
                        playingPlayer = 1;
                }
        }
        println("Player " + playingPlayer + " wins !");
        println("--- / ---");
    }

    static void println(String s) {
        System.out.println(s);
    }

    static void print(String s) {
        System.out.print(s);
    }

    static int readInteger(int min, int max) {
        boolean readNonNumber = true;
        int integer = min;
        while (readNonNumber || integer < min || integer > max) {
            try {
                String read = br.readLine();
                integer = Integer.valueOf(read);
                readNonNumber = false;
            } catch (NumberFormatException e) {
            } catch (IOException e) {
            }
        }
        return integer;
    }

    static int readPositiveInteger(int max) {
        return readInteger(0, max);
    }

    static int[] readMapCoordinate(int minX, int maxX, int minY, int maxY) {
        int x = minX;
        int y = minY;
        boolean badFormating = true;
        while (badFormating || x < minX || x > maxX || y < minY || y > maxY) {
            try {
                String read = br.readLine();
                if (read.length() == 2) {
                    x = Integer.valueOf(read.charAt(0)) - Integer.valueOf('A');
                    y = Integer.valueOf(read.charAt(1)) - Integer.valueOf('0');
                    badFormating = read.charAt(0) < Map.columnLabels.charAt(minX - 1) || read.charAt(0) > Map.columnLabels.charAt(maxX - 1);
                } else {
                    badFormating = true;
                }
            } catch (IOException e) {
            }
        }
        return new int[]{x, y};
    }
}
