package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import edu.duke.ece651.group6.factorySimulation.Model.Recipe;

public class MineRecipeHasNoIngredientsRuleChecker extends RuleChecker {
    private final List<Recipe> recipeList;


    public MineRecipeHasNoIngredientsRuleChecker(
        RuleChecker next, List<Recipe> rLst
    ) {
        super(next);
        this.recipeList = rLst;
    }

    @Override
    protected String checkRule(JsonNode bldgNode) {
        for (JsonNode bldg : bldgNode) {
            if (bldg.has("mine")) { // assume mine / type checked
                // assume mine is an output of recipes
                Recipe recipe = Recipe.getRecipeFromListByOutput(
                    recipeList, bldg.get("mine").asText()
                );
                if (recipe.hasIngredients()) {
                    return recipe.getOutput()
                        + " is produced by mine but has ingredients";
                }
            }
        }
        return null;
    }
}
