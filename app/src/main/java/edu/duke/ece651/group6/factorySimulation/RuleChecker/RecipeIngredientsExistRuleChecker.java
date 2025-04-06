package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import java.util.HashSet;
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
                JsonNode ingredientValue = ingredientsNode.get(ingredient);
                if (ingredientValue.getNodeType() != JsonNodeType.NUMBER
                ) {
                    return "recipe ingredient " + ingredient +
                        " has non-integer amount";
                }
                int amount = ingredientValue.asInt();
                if (amount < 0) {
                    return "recipe ingredient" + ingredient + "has negative amount";
                }
            }
        }
        return null;
    }

    // private Set<String> getAllRecipes2(
    //     JsonNode root
    // ) throws InvalidJsonFileException {
    //     // assume recipes exist and is array
    //     // if (!root.has("recipes")) {
    //     //     throw new InvalidJsonFileException("recipes field is missing");
    //     // }
    //     JsonNode recipes = root.get("recipes");
    //     // if (recipes.getNodeType() != JsonNodeType.ARRAY) {
    //     //     throw new InvalidJsonFileException(
    //     //         "recipes field is not the expected type"
    //     //     );
    //     // }
    //     // create set of all outputs
    //     Set<String> allRecipes = new HashSet<>();
    //     for (JsonNode r : recipes) {
    //         allRecipes.add(r.get("output").asText());
    //     }
    //     return allRecipes;
    // }

    private Set<String> getAllRecipes(
        JsonNode root
    ) throws InvalidJsonFileException {
        // assume recipes exist and is array
        JsonNode recipes = root.get("recipes");
        // create set of all outputs
        Set<String> allRecipes = new HashSet<>();
        for (JsonNode r : recipes) {
            allRecipes.add(r.get("output").asText());
        }
        return allRecipes;
    }
}
