package fr.uga.ai.quoridor;

import fr.uga.ai.quoridor.gui.MapGui;
import fr.uga.ai.quoridor.map.Map;
import fr.uga.ai.quoridor.player.Human;
import fr.uga.ai.quoridor.player.Player;
import fr.uga.ai.quoridor.player.ai.GoToSmallestManhattanDistance;
import fr.uga.ai.quoridor.player.ai.IsArtificial;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static fr.uga.ai.quoridor.IO.println;
import static fr.uga.ai.quoridor.IO.readAction;

public class Quoridor {

    public static Player playerOne;
    public static Player playerTwo;

    public static void main(String[] args) {

        // GUI
        JFrame frame = new JFrame("Map");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(MapGui.getInstance().getView());
        frame.pack();
        frame.setVisible(true);


        Player playingPlayer;
        boolean game = true;
        boolean win = false;
        boolean actionSuccess;
        Map map = Map.getInstance();

        // Introduction
        println(" ---- Quoridor ----");
        println("Try to move to the opposite side of the checkerboard");
        println("Use commands to move or build a wall");
        println("You can build a maximum of 10 walls");
        println("Command examples");
        println("Build a vertical wall in column b line 3 : b3v");
        println("Move to column H line 7 : H7");
        println("");

        // Player types
        List<Class<? extends Player>> playerTypes = new ArrayList<>();
        playerTypes.add(Human.class);
        playerTypes.add(GoToSmallestManhattanDistance.class);
        // player one
        println("Player O is a .. ?");
        for (int i = 0; i < playerTypes.size(); i++)
            println(i + ". " + playerTypes.get(i).getSimpleName());
        playerOne = Player.initPlayerOne(playerTypes.get(IO.readNumericChoice()));
        // player two
        println("Player X is a .. ?");
        for (int i = 0; i < playerTypes.size(); i++)
            println(i + ". " + playerTypes.get(i).getSimpleName());
        playerTwo = Player.initPlayerTwo(playerTypes.get(IO.readNumericChoice()));

        playingPlayer = playerOne;

        while(game) {
            println(map.draw(playingPlayer));

            if (playingPlayer instanceof IsArtificial) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                actionSuccess = playingPlayer.execute(playingPlayer.evaluate());
            }
            else
                actionSuccess = playingPlayer.execute(readAction());

            if (actionSuccess)
                if (playingPlayer.hasWin()) {
                    // win
                    game = false;
                    win = true;
                } else {
                    // change playing player
                    if (playingPlayer == playerOne)
                        playingPlayer = playerTwo;
                    else
                        playingPlayer = playerOne;
                }
        }
        if (win)
            println("Player " + playingPlayer.toString() + " has won !\n");

        println(map.draw(playingPlayer));

        println("--- / ---");
    }
}
