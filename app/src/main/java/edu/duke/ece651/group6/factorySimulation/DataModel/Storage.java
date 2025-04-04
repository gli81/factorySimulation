package edu.duke.ece651.group6.factorySimulation.DataModel;

public class Storage extends Building {
    public Storage(String name, int x, int y) {
        super(name, x, y);
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
