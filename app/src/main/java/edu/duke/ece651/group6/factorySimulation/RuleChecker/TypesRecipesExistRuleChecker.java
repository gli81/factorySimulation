package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class TypesRecipesExistRuleChecker extends RuleChecker {
    public TypesRecipesExistRuleChecker(RuleChecker next) {
        super(next);
    }


    @Override
    protected String checkRule(JsonNode root) {
        Set<String> allRecipes;
        try {
            allRecipes = getAllRecipes(root);
        } catch (Exception e) {
            return e.getMessage();
        }
        if (!root.has("types")) {
            return "types field is missing";
        }
        JsonNode types = root.get("types");
        if (types.getNodeType() != JsonNodeType.ARRAY) {
            return "types field is not the expected type";
        }
        for (JsonNode type : types) {
            if (!type.has("recipes")) {
                return "recipes field is missing from types";
            }
            JsonNode recipesNode = type.get("recipes");
            if (recipesNode.getNodeType() != JsonNodeType.ARRAY) {
                return "recipes field is not the expected type";
            }
            for (JsonNode recipe : recipesNode) {
                if (!allRecipes.contains(recipe.asText())) {
                    return "type recipe " + recipe.asText() +
                        " is not an output from recipes";
                }
            }
        }
        return null;
    }
    
}
