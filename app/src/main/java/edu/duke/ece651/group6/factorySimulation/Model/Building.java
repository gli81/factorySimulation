package edu.duke.ece651.group6.factorySimulation.Model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public abstract class Building {
    protected final String name;
    protected final Coordinate coord;
    protected final List<Recipe> recipes;
    protected final Queue<Order> orders;

    public Building() {
        this("", new Coordinate(0, 0));
    }

    public Building(String name) {
        this(name, new Coordinate(0, 0));
    }

    public Building(String name, Coordinate coord) {
        this.name = name;
        this.coord = coord;
        this.recipes = new ArrayList<>();
        // ArrayDeque prohibit null, alternative: LinkedList
        this.orders = new ArrayDeque<>();
    }

    protected String getName() {return name;}

    protected static Building getBuildingFromListByName(
        List<Building> bLst, String name
    ) {
        for (Building building : bLst) {
            if (building.getName().equals(name)) {
                return building;
            }
        }
        return null;
    }

    public abstract void setBuildingWithBuilding(List<Building> bLst);
}
