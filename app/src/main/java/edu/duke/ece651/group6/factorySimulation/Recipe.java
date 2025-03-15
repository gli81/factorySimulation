package edu.duke.ece651.group6.factorySimulation;

import java.util.Map;

/**
 * This interface represents recipe within simulation.
 */
public interface Recipe {
  /**
   * Get name of output item created by recipe.
   * @return name of output item
   */
  public String getOutput();

  /**
   * Get all ingredients required for recipe.
   * @return a iterable of ingredients
   */
  public Iterable<Map.Entry<String, Integer>> getAllIngredients();

  /**
   * Get time in cycles to complete recipe.
   * @return latency in time units/cycles
   */
  public int getLatency();
}
