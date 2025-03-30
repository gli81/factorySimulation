package edu.duke.ece651.group6.factorySimulation;

/**
 * This class implements a mine type of a building
 */
public class Mine implements Building {
  private final String name;
  private final String mineType;

  /**
   * Constructor for a mine
   * 
   * @param name mine name
   * @param mined what this mine will output
   * @throws IllegalArgumentException if name or mined is empty
   */
  public Mine(String name, String mine) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Mine name can't be empty");
    }
    if (mine == null || mine.isEmpty()) {
      throw new IllegalArgumentException("Mine output cannot be empty");
    }
    
    this.name = name;
    this.mineType = mine;
  }

  @Override
  public String getName() {
    return name;
  }

  /**
   * Get what this mine can output.
   * @return what is mined
   */
  public String getMineType() {
    return mineType;
  }
  
  @Override
  public boolean isFactory() {
    return false;
  }

  /**
   * String representation of mine.
   *
   * @return string representation for mine data
   */
  @Override
  public String toString() {
    return "Mine: " + name + ", Mines: " + mineType;
  }
}
