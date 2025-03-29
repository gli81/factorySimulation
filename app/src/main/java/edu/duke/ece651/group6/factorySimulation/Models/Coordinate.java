package edu.duke.ece651.group6.factorySimulation.Models;

public class Coordinate {
    private final int x;
    private final int y;


    public Coordinate() {
        this(0, 0);
    }
    
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        Coordinate o = (Coordinate)obj;
        return this.x == o.x && this.y == o.y;
    }
}
