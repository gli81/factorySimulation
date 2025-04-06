package edu.duke.ece651.group6.factorySimulation.DataModel;

public abstract class MapObject {
    private int x;
    private int y;

    public MapObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

}