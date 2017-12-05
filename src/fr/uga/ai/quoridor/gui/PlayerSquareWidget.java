package fr.uga.ai.quoridor.gui;

import fr.uga.ai.quoridor.map.IsSquare;
import fr.uga.ai.quoridor.map.PlayerSquare;

public class PlayerSquareWidget extends SquareWidget implements IsSquareWidget {

    private PlayerSquare playerSquare;

    @Override
    public IsSquare getSquare() {
        return playerSquare;
    }
}
