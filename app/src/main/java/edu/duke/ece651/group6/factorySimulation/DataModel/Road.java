package edu.duke.ece651.group6.factorySimulation.DataModel;

import java.util.HashMap;
import java.util.Map;

public class Road extends MapObject {
    private Map<Integer, Boolean> direction;

    public Road(int x, int y) {
        super(x, y);
        this.direction = new HashMap<>();
        // 0: up, 1: down, 2: left, 3: right
        this.direction.put(0, false);
        this.direction.put(1, false);
        this.direction.put(2, false);
        this.direction.put(3, false);
    }

    public boolean isDirectionAvailable(int direction) {
        return this.direction.get(direction);
    }

    public void setDirectionAvailable(int direction, boolean available) {
        this.direction.put(direction, available);
    }

}