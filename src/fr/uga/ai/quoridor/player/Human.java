package fr.uga.ai.quoridor.player;

import fr.uga.ai.quoridor.map.Coordinates;

public class Human extends Player {
    public Human(Integer playerId, String character, Coordinates initialPosition, Integer winX) {
        super(playerId, character, initialPosition, winX);
    }
}
