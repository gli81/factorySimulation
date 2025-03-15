package edu.duke.ece651.group6.factorySimulation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//TODO hashCode, equals() 

/**
 * This class implements Recipe interface
 */
public class BasicRecipe implements Recipe {
  private final String output;
  private final Map<String, Integer> ingredients;
  private final int latency;

  /**
   * BasicRecipe constructor with output, ingredients, and latency.
   * 
   * @param output the name of output item produced
   * @param list of ingredient names to quantities required
   * @param latency time in cycles required to complete a recipe
   * @throws IllegalArgumentException if output is null, empty, or if latency is less than 1
   */
  public BasicRecipe(String output, Map<String, Integer> ingredients, int latency) {
    if (output == null || output.isEmpty()) {
      throw new IllegalArgumentException("Recipe output can't be empty");
    }
    if (latency < 1) {
      throw new IllegalArgumentException("Recipe latency must be atleast 1, got " + latency);
    }
    
    this.output = output;
    this.ingredients = new HashMap<>();
    if (ingredients != null) {
      for (Map.Entry<String, Integer> e: ingredients.entrySet()) {
        this.ingredients.put(e.getKey(), e.getValue());
      }
    }
    this.latency = latency;
  }

  @Override
  public String getOutput() {
    return output;
  }

  @Override
  public Iterable<Map.Entry<String, Integer>> getAllIngredients() {
    return new HashMap<>(ingredients).entrySet(); //returning a copy 
  }

  @Override
  public int getLatency() {
    return latency;
  }

   /**
   * String representation of recipe.
   * Format: "Output: [output], Ingredients: [ingredients], Latency: [latency]"
   *
   * @return string representation of recipe
   */
  @Override
  public String toString() {
    return "Output: " + output + ", Ingredients: " + ingredients + ", Latency: " + latency;
  }

  
}
