package fr.uga.ai.corridor.player;

import fr.uga.ai.corridor.map.Coordinates;
import fr.uga.ai.corridor.map.Map;
import fr.uga.ai.corridor.map.PlayerSquare;
import fr.uga.ai.corridor.map.WallSquare;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class Player {

    protected final int playerId;
    protected final String character;
    protected final int winX;

    protected Coordinates coordinates;
    protected int wallBank = 10;

    public Player(int playerId, String character, Coordinates initialPosition, int winX) {
        this.playerId = playerId;
        this.character = character;
        this.coordinates = initialPosition;
        this.winX = winX;

        Map.getInstance().addPlayer(this);
    }

    private static Player initPlayer(Class<? extends Player> T, int playerId, String character, Coordinates initialPosition, int winX) {
        try {
            Constructor<? extends Player> constructor = T.getConstructor(Integer.class, String.class, Coordinates.class, Integer.class);
            return constructor.newInstance(playerId, character, initialPosition, winX);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Player initPlayerOne(Class<? extends Player> T) {
        return initPlayer(T, 1, "O", new Coordinates(0, 4), 8);
    }

    public static Player initPlayerTwo(Class<? extends Player> T) {
        return initPlayer(T, 2, "X", new Coordinates(8, 4), 0);
    }

    @Override
    public String toString() {
        return character;
    }

    public int getWallBank() {
        return wallBank;
    }

    public boolean hasWin() {
        return coordinates.x == winX;
    }

    public boolean execute(Action action) {
        // TODO test action before executing
        // move
        if (action.getType() == Action.Type.MOVE)
            return setCoordinates(action.getCoordinates());
        // build wall
        boolean built = false;
        if (wallBank > 0) {
            if (action.getType() == Action.Type.BUILD_HORIZONTAL)
                built = Map.getInstance().buildWall(action.getCoordinates(), WallSquare.State.HORIZONTAL);
            else if (action.getType() == Action.Type.BUILD_VERTICAL)
                built =  Map.getInstance().buildWall(action.getCoordinates(), WallSquare.State.VERTICAL);
            if (built)
                wallBank--;
            return built;
        }
        return false;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    protected boolean setCoordinates(Coordinates newCoordinates) {
        if (newCoordinates == null)
            return false;
        PlayerSquare from = Map.getInstance().getPlayerSquareFromCoordinates(this.coordinates);
        PlayerSquare to = Map.getInstance().getPlayerSquareFromCoordinates(newCoordinates);
        boolean removed = false;
        boolean added = false;
        boolean pathClear = !from.hasWallWith(to);
        if (pathClear) {
            removed = Map.getInstance().removePlayer(this);
            this.coordinates = newCoordinates;
            added = Map.getInstance().addPlayer(this);
        }
        if (removed != added)
            throw new RuntimeException("!!! remove != added");
        return removed && added && pathClear;
    }

    public Action evaluate() {
        // do nothing
        return null;
    }
}
