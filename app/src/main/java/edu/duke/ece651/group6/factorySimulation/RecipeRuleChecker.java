package edu.duke.ece651.group6.factorySimulation;

/**
 * Class that checks recipe validation rules.
 */
public abstract class RecipeRuleChecker {
    private final RecipeRuleChecker next;

    /**
     * Creates a new RecipeRuleChecker with the given next rule checker
     * @param next rule checker in chain of responsibility 
     */
    public RecipeRuleChecker(RecipeRuleChecker next) {
        this.next = next;
    }

    /**
     * Checks specific recipe validation rule
     * @param recipe to check
     * @param allRecipes in simulation 
     */
    protected abstract String checkMyRule(Recipe recipe, Iterable<Recipe> recipesAll);
    /**
     * Checks recipe against all rules in chain
     * @param recipe is the recipe to check
     * @param allRecipes is all available recipes in the simulation
     * @return null if recipe validation checks have passed through 
     */
    public String checkRecipe(Recipe recipe, Iterable<Recipe> recipesAll) {
        String check = checkMyRule(recipe, recipesAll);
        if (check != null) {
            return check;
        }
        if (next != null) {
            return next.checkRecipe(recipe, recipesAll);
        }
        return null;
    }
}
