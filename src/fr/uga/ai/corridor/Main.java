package fr.uga.ai.corridor;

import fr.uga.ai.corridor.map.Map;

import static fr.uga.ai.corridor.IO.println;
import static fr.uga.ai.corridor.IO.readAction;

public class Main {

    public static void main(String[] args) {
        Player playingPlayer = Player.PLAYER_ONE;
        boolean game = true;
        boolean win = false;
        boolean actionSuccess;
        Map map = Map.getInstance();

        println(" ---- Corridor ----");
        println("Try to move to the opposite side of the checkerboard");
        println("Use commands to move or build a wall, you can build a maximum of 10 wall");
        println("Command examples");
        println("Build a vertical wall in column b line 3 : b3v");
        println("Move to column H line 7 : H7");
        println("");

        while(game) {
            println(map.draw(playingPlayer));

            actionSuccess = playingPlayer.execute(readAction());

            if (actionSuccess)
                if (playingPlayer.hasWin()) {
                    // win
                    game = false;
                    win = true;
                } else {
                    // change playing player
                    if (playingPlayer == Player.PLAYER_ONE)
                        playingPlayer = Player.PLAYER_TWO;
                    else
                        playingPlayer = Player.PLAYER_ONE;
                }
        }
        if (win)
            println("Player " + playingPlayer.toString() + " has won !");

        println("--- / ---");
    }
}
