package edu.duke.ece651.group6.factorySimulation;

/**
 * This interface represents building within simulation.
 */
public interface Building {
  /**
   * Get building name
   * @return name of building
   */
  public String getName();
  
  /**
   * Check if building is factory.
   * @return true if factory, false if mine
   */
  public boolean isFactory();
}
