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

    @Override
    public String toString() {
        StringBuilder recipesString = new StringBuilder();
        this.recipes.forEach(recipe -> {
            recipesString.append(recipe.getOutputItem()).append(", ");
        });
        // remove the last comma
        if (recipesString.length() > 0) {
            recipesString.deleteCharAt(recipesString.length() - 2);
        }
        return "Type: { " + this.name + ", { " + recipesString.toString() + "} }\n";
    }
}
