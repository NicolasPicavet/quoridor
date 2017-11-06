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

        println("--- Corridor ---");
        while(game) {
            println(map.draw());

            /*println("(1) Build wall");
            println("(2) Move");
            boolean buildChoice = readInteger(1, 2) == 1;
            print("x = ");
            x = readInteger(1, map.getSizeX()) - 1;
            print("y = ");
            y = readInteger(1, map.getSizeY()) - 1;
            *//*int[] coordinates = readMapCoordinate(1, map.getSizeX(), 1, map.getSizeY());
            x = coordinates[0];
            y = coordinates[1];*//*

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
                }*/
            String read = br.readLine();
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
}
