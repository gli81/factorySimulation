package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import java.util.Iterator;
import java.util.Set;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import edu.duke.ece651.group6.factorySimulation.Exception.InvalidJsonFileException;

public class RecipeIngredientsExistRuleChecker extends RuleChecker {
    public RecipeIngredientsExistRuleChecker(RuleChecker next) {
        super(next);
    }

    @Override
    protected String checkRule(JsonNode root) {
        Set<String> allRecipes;
        try {
            allRecipes = this.getAllRecipes(root);
        } catch (InvalidJsonFileException e) {
            return e.getMessage();
        }
        JsonNode recipes = root.get("recipes");
        for (JsonNode r : recipes) {
            JsonNode ingredientsNode = r.get("ingredients");
            // assume ingredients is OBJECT
            // json file guarantees that each key is STRING
            Iterator<String> ingredients = ingredientsNode.fieldNames();
            while (ingredients.hasNext()) {
                String ingredient = ingredients.next();
                if (!allRecipes.contains(ingredient)) {
                    return "recipe ingredient " + ingredient +
                        " is not an output";
                }
                if (
                    ingredientsNode.get(
                        ingredient
                    ).getNodeType() != JsonNodeType.NUMBER
                ) {
                    return "recipe ingredient " + ingredient +
                        " has non-integer amount";
                } // TODO check amount non negative
            }
        }
        return null;
    }
}
