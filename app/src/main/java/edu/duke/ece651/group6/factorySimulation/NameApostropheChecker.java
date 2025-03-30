package edu.duke.ece651.group6.factorySimulation;

public class NameApostropheChecker extends RecipeRuleChecker {
  public NameApostropheChecker(RecipeRuleChecker next) {
    super(next);
  }

  /**
   * Checks if recipe name contains apostrophes
   * @param recipe to check
   * @return null if recipe name is valid 
   */
  @Override
  protected String checkMyRule(Recipe recipe, Iterable<Recipe> recipesAll) {
    if (recipe.getOutput().contains("'")) {
      return "Recipe name '" + recipe.getOutput() + "' contains an apostrophe which is invalid";
    }
    return null;
  }
}
