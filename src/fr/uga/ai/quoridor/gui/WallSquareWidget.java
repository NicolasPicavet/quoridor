package fr.uga.ai.quoridor.gui;

import fr.uga.ai.quoridor.map.IsSquare;
import fr.uga.ai.quoridor.map.WallSquare;

public class WallSquareWidget extends SquareWidget implements IsSquareWidget {

    private WallSquare wallSquare;

    @Override
    public IsSquare getSquare() {
        return wallSquare;
    }
}
