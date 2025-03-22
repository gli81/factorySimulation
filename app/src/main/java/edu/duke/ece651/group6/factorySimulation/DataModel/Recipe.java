package edu.duke.ece651.group6.factorySimulation.DataModel;

import java.util.Map;
import java.util.LinkedHashMap;

public class Recipe {
    private final String name;
    private final Map<Recipe, Integer> ingredients;
    private final int latency;

    public Recipe(String name, int latency) {
        this.name = name;
        this.ingredients = new LinkedHashMap<>();
        this.latency = latency;
    }

    public void addIngredient(Recipe ingredient, int quantity) {
        this.ingredients.put(ingredient, quantity);
    }

    public String getName() {
        return this.name;
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
            ingredientsString.append(ingredient.getName()).append(": ").append(quantity).append(", ");
        });
        // remove the last comma
        if (ingredientsString.length() > 0) {
            ingredientsString.deleteCharAt(ingredientsString.length() - 2);
        }
        return "Recipe: { " + this.name + ", " + this.latency + ", { " + ingredientsString.toString() + "} }\n";
    }

    /*
     * two recipes are equal if they have the same output item and latency
     * since output item string is unique for each recipe
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Recipe other = (Recipe) obj;
        boolean isLatencyEqual = this.latency == other.latency;
        boolean isOutputItemEqual = this.name == null ? other.name == null
                : this.name.equals(other.name);

        return isLatencyEqual &&
                isOutputItemEqual;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + latency;
        return result;
    }

}
