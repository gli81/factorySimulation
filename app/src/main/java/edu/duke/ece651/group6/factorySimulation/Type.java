package edu.duke.ece651.group6.factorySimulation;

/**
 * This interface represents factory type within simulation.
 */
public interface Type {
  /**
   * Get name of factory.
   * @return name of factory
   */
  public String getName();

  /**
   * Get recipes factory type can produce.
   * @return an iterable of recipe names
   */
  public Iterable<String> getRecipes();
}
