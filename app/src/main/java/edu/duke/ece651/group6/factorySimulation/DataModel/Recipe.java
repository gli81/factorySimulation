package edu.duke.ece651.group6.factorySimulation.DataModel;

import java.util.Map;
import java.util.LinkedHashMap;

public class Recipe {
    private String outputItem;
    private Map<Recipe, Integer> ingredients;
    private int latency;

    public Recipe(String outputItem, int latency) {
        this.outputItem = outputItem;
        this.ingredients = new LinkedHashMap<>();
        this.latency = latency;
    }

    public void addIngredient(Recipe ingredient, int quantity) {
        this.ingredients.put(ingredient, quantity);
    }

    public String getOutputItem() {
        return this.outputItem;
    }

    public Iterable<Map.Entry<Recipe, Integer>> getIngredientsIterable() {
        return new LinkedHashMap<>(this.ingredients).entrySet();
    }

    public int getLatency() {
        return this.latency;
    }

    @Override
    public String toString() {
        StringBuilder ingredientsString = new StringBuilder();
        this.ingredients.forEach((ingredient, quantity) -> {
            ingredientsString.append(ingredient.getOutputItem()).append(": ").append(quantity).append(", ");
        });
        // remove the last comma
        if (ingredientsString.length() > 0) {
            ingredientsString.deleteCharAt(ingredientsString.length() - 2);
        }
        return "Recipe: { " + this.outputItem + ", " + this.latency + ", { " + ingredientsString.toString() + "} }\n";
    }

}
