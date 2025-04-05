package edu.duke.ece651.group6.factorySimulation.DataModel;

import java.util.ArrayList;

public class Storage extends Building {
    private Recipe storedRecipe;
    private int capacity;
    private double priority;
    /*
     * the sources list of the building
     */
    private ArrayList<Building> sources;

    public Storage(String name, Recipe storedRecipe, int capacity, double priority) {
        super(name, -1, -1);
        this.storedRecipe = storedRecipe;
        this.capacity = capacity;
        this.priority = priority;
        this.sources = new ArrayList<>();
    }

    public Storage(String name, Recipe storedRecipe, int capacity, double priority, int x, int y) {
        super(name, x, y);
        this.storedRecipe = storedRecipe;
        this.capacity = capacity;
        this.priority = priority;
        this.sources = new ArrayList<>();
    }

    public Recipe getStoredRecipe() {
        return this.storedRecipe;
    }

    public void addSource(Building source) {
        this.sources.add(source);
    }

    public Iterable<Building> getSourcesIterable() {
        return new ArrayList<Building>(this.sources);
    }

    @Override
    public boolean isRecipeSupported(Recipe recipe) {
        return this.storedRecipe.equals(recipe);
    }

    @Override
    public int hashCode() {
        return 31 * this.storedRecipe.hashCode() + this.capacity + (int) this.priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Storage storage = (Storage) o;

        boolean isRecipeEqual = this.storedRecipe.equals(storage.storedRecipe);
        boolean isCapacityEqual = this.capacity == storage.capacity;
        boolean isPriorityEqual = this.priority == storage.priority;

        return isRecipeEqual && isCapacityEqual && isPriorityEqual;
    }

    @Override
    public String toString() {
        StringBuilder sourcesString = new StringBuilder();
        this.sources.forEach(source -> {
            sourcesString.append(source.getName()).append(", ");
        });
        // remove the last comma
        if (sourcesString.length() > 0) {
            sourcesString.deleteCharAt(sourcesString.length() - 2);
        }
        return "Storage: { " + this.getName() + ", " + this.storedRecipe.getName() + ": " + this.capacity + ", "
                + this.priority + ", { " + sourcesString.toString() + "} }\n";
    }
}
