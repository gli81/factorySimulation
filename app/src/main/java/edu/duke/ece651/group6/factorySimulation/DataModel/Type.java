package edu.duke.ece651.group6.factorySimulation.DataModel;

import java.util.ArrayList;

public class Type {
    private final String name;
    private final ArrayList<Recipe> recipes;

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

    public boolean isRecipeSupported(Recipe recipe) {
        return this.recipes.contains(recipe);
    }

    @Override
    public String toString() {
        StringBuilder recipesString = new StringBuilder();
        this.recipes.forEach(recipe -> {
            recipesString.append(recipe.getName()).append(", ");
        });
        // remove the last comma
        if (recipesString.length() > 0) {
            recipesString.deleteCharAt(recipesString.length() - 2);
        }
        return "Type: { " + this.name + ", { " + recipesString.toString() + "} }\n";
    }

    /*
     * two types are equal if they have the same name
     * since name is unique for each type
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Type other = (Type) obj;
        return this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
