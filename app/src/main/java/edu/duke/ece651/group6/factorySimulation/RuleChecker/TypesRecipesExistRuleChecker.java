package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.JsonNode;
import edu.duke.ece651.group6.factorySimulation.Model.Recipe;

public class TypesRecipesExistRuleChecker extends RuleChecker {
    private final List<Recipe> recipeList;


    public TypesRecipesExistRuleChecker(
        RuleChecker next, List<Recipe> recipeList
    ) {
        super(next);
        this.recipeList = recipeList;
    }


    @Override
    protected String checkRule(JsonNode typesNode) {
        Set<String> allRecipes = recipeList.stream()
            .map(r -> r.getOutput())
            .collect(Collectors.toSet());
        // assume types is ARRAY, each type has recipes field
        for (JsonNode type : typesNode) {
            JsonNode recipesNode = type.get("recipes");
            // assume recipes is ARRAY
            for (JsonNode recipe : recipesNode) {
                if (!allRecipes.contains(recipe.asText())) {
                    return "type's recipe " + recipe.asText() +
                        " is not an output from recipes";
                }
            }
        }
        return null;
    }

    // protected String checkRule2(JsonNode types) {
    //     Set<Recipe> allRecipes = new HashSet<>(recipeList);
    //     // not necessary TODO check ARRAY
    //     if (types.getNodeType() != JsonNodeType.ARRAY) {
    //         return "types field is not the expected type";
    //     }
    //     for (JsonNode type : types) {
    //         // not necessary
    //         if (!type.has("recipes")) {
    //             return "recipes field is missing from types";
    //         }
    //         JsonNode recipesNode = type.get("recipes");
    //         // not necessary
    //         if (recipesNode.getNodeType() != JsonNodeType.ARRAY) {
    //             return "recipes field is not the expected type";
    //         }
    //         for (JsonNode recipe : recipesNode) {
    //             if (!allRecipes.contains(recipe.asText())) {
    //                 return "type recipe " + recipe.asText() +
    //                     " is not an output from recipes";
    //             }
    //         }
    //     }
    //     return null;
    // }
}
