package edu.duke.ece651.group6.factorySimulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BasicType implements Type {
  private final String name;
  private final List<String> recipes;

  /**
   * BasicType constructor with name and list of recipe names.
   * 
   * @param name factory
   * @param list of recipe names this type produce
   * @throws IllegalArgumentException if name is empty
   */
  public BasicType(String name, List<String> recipes) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Type name can't be empty");
    }
    
    this.name = name;
    this.recipes = new ArrayList<>();
    if (recipes != null) {
      this.recipes.addAll(recipes);
    }
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Iterable<String> getRecipes() {
    return new ArrayList<>(recipes); // Return a copy of the list
  }
  /**
   * String representation of type.
   * Format: "Type: [name], Recipes: [recipes]"
   *
   * @return string representation for 'type' data 
   */
  @Override
  public String toString() {
    return "Type: " + name + ", Recipes: " + recipes;
  }
}
