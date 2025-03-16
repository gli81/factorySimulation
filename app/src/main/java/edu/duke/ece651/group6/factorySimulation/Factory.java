package edu.duke.ece651.group6.factorySimulation;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements a factory type of a building
 */
public class Factory implements Building {
  private final String name;
  private final String type;
  private final List<String> sources;

  /**
   * Constructor for a factory
   * 
   * @param name factory name
   * @param type factory type
   * @param sources list of source names(builidngs)
   * @throws IllegalArgumentException if name or type is empty
   */
  public Factory(String name, String type, List<String> sources) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Factory name can't be empty");
    }
    if (type == null || type.isEmpty()) {
      throw new IllegalArgumentException("Factory type can't be empty");
    }
    
    this.name = name;
    this.type = type;
    this.sources = new ArrayList<>();
    if (sources != null) {
      this.sources.addAll(sources);
    }
  }

  @Override
  public String getName() {
    return name;
  }

  /**
   * Get the type of this factory.
   * @return the type name
   */
  public String getType() {
    return type;
  }

  /**
   * Get sources for this factory.
   * @return an iterable of source building names
   */
  public Iterable<String> getSources() {
    return new ArrayList<>(sources);
  }
  
  @Override
  public boolean isFactory() {
    return true;
  }

  /**
   * String representation of factory.
   *
   * @return string representation for factory data
   */
  @Override
  public String toString() {
    return "Factory: " + name + ", Type: " + type + ", Sources: " + sources;
  }
}
