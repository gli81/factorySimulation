package edu.duke.ece651.group6.factorySimulation.DataModel;

import java.util.ArrayList;

public class Type {
    private String name;
    private ArrayList<Recipe> recipes;

    public Type(String name, ArrayList<Recipe> recipes) {
        this.name = name;
        this.recipes = recipes;
    }

    public String getName() {
        return this.name;
    }

    public Iterable<Recipe> getRecipesIterable() {
        return new ArrayList<>(this.recipes);
    }

}
