package fr.uga.ai.corridor.map;

public class WallSquareSide {

    public enum Side {
        LEFT, TOP, RIGHT, BOTTOM;
    }

    private boolean wall;

    public WallSquareSide() {
    }

    public boolean hasWall() {
        return wall;
    }

    public void setWall(boolean wall) {
        this.wall = wall;
    }
}