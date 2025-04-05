package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import edu.duke.ece651.group6.factorySimulation.Model.Recipe;

public class TypesRecipesHaveIngredientsRuleChecker extends RuleChecker {
    private final List<Recipe> recipeList;

    public TypesRecipesHaveIngredientsRuleChecker(
        RuleChecker next, List<Recipe> rLst
    ) {
        super(next);
        this.recipeList = rLst;
    }


    @Override
    protected String checkRule(JsonNode typesNode) {
        // typesNode is ARRAY
        for (JsonNode type : typesNode) {
            // assume every node has recipes node and is ARRAY
            JsonNode recipesNode = type.get("recipes");
            for (JsonNode recipeNode : recipesNode) {
                // guaranteed can find
                Recipe r = Recipe.getRecipeFromListByOutput(
                    recipeList, recipeNode.asText()
                );
                if (!r.hasIngredients()) {
                    return r.getOutput()
                        + " is produced by a factory"
                        + " but doesn't have ingredients";
                }
            }
        }
        return null;
    }
}
