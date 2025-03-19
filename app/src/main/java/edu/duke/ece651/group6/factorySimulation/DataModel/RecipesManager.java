package edu.duke.ece651.group6.factorySimulation.DataModel;

import java.util.ArrayList;

public class RecipesManager {

    private ArrayList<Recipe> recipes;

    public RecipesManager() {
        this.recipes = new ArrayList<>();
    }

    public void addRecipe(Recipe recipe) {
        this.recipes.add(recipe);
    }

    /**
     * Get all recipes as an Iterable
     * Iterable is only used to iterate over the recipes
     * 
     * @return all recipes
     */
    public Iterable<Recipe> getAllRecipesIterable() {
        return new ArrayList<>(this.recipes);
    }

}