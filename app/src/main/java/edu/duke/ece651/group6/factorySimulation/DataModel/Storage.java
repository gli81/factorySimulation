package edu.duke.ece651.group6.factorySimulation.DataModel;

import java.util.ArrayList;

public class Storage extends Building {
    private int capacity;
    private double priority;
    /*
     * the sources list of the building
     */
    private ArrayList<Building> sources;

    public Storage(String name, int capacity, double priority) {
        super(name, -1, -1);
        this.capacity = capacity;
        this.priority = priority;
        this.sources = new ArrayList<>();
    }

    public Storage(String name, int capacity, double priority, int x, int y) {
        super(name, x, y);
        this.capacity = capacity;
        this.priority = priority;
        this.sources = new ArrayList<>();
    }

    public void addSource(Building source) {
        this.sources.add(source);
    }

    public Iterable<Building> getSourcesIterable() {
        return new ArrayList<Building>(this.sources);
    }

    @Override
    public boolean isRecipeSupported(Recipe recipe) {
        return false; // Storage doesn't support any recipes
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "Storage";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        return true;
    }
}
