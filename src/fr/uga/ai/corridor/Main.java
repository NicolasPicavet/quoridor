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

        while(game) {
            println(map.draw(playingPlayer));

            actionSuccess = playingPlayer.execute(readAction());

            if (actionSuccess)
                // win
                if (playingPlayer.hasWin()) {
                    game = false;
                    win = true;
                // change playing player
                } else
                    if (playingPlayer == Player.PLAYER_ONE)
                        playingPlayer = Player.PLAYER_TWO;
                    else
                        playingPlayer = Player.PLAYER_ONE;
        }
        if (win)
            println("Player " + playingPlayer.toString() + " has won !");

        println("--- / ---");
    }
}
