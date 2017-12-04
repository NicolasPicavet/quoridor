package fr.uga.ai.corridor.player;

import fr.uga.ai.corridor.map.Coordinates;

public class Human extends Player {
    public Human(Integer playerId, String character, Coordinates initialPosition, Integer winX) {
        super(playerId, character, initialPosition, winX);
    }
}
