package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.fasterxml.jackson.databind.JsonNode;
import edu.duke.ece651.group6.factorySimulation.Model.Recipe;
import edu.duke.ece651.group6.factorySimulation.Model.Type;

public class FactoryCanGetIngredientsRuleChecker extends RuleChecker {
    private final List<Recipe> recipeLst;
    private final List<Type> typeLst;

    public FactoryCanGetIngredientsRuleChecker(
        RuleChecker next, List<Recipe> rLst, List<Type> tLst
    ) {
        super(next);
        this.recipeLst = rLst;
        this.typeLst = tLst;
    }

    @Override
    protected String checkRule(JsonNode bldgsNode) {
        // make a map of building name to list of
        // recipes the building is capable of making
        Map<String, List<Recipe>> buildingOutputs = new HashMap<>();
        // assume has building, building is an array
        for (JsonNode bldgNode : bldgsNode) {
            // assume building has name field
            String name = bldgNode.get("name").asText();
            if (bldgNode.has("type")) {
                // factory
                // assume factory has type & non empty sources ARRAY
                // assume all factory's type is in typeLst,
                // not going to return null
                Type type = Type.getTypeByName(
                    this.typeLst,
                    bldgNode.get("type").asText()
                );
                List<Recipe> recipes = new ArrayList<>();
                type.getRecipes().forEachRemaining(
                    recipes::add
                );
                buildingOutputs.put(name, recipes);
            } else if (bldgNode.has("mine")) {
                // mine
                // assume mine exists in recipes (BuildingsMineExistRuleChecker)
                String mineName = bldgNode.get("mine").asText();
                List<Recipe> recipes = new ArrayList<>();
                recipes.add(
                    Recipe.getRecipeFromListByOutput(this.recipeLst, mineName)
                );
                buildingOutputs.put(name, recipes);
            }
        }
        System.out.println(buildingOutputs);
        // a set of available ingredients from sources
        Set<Recipe> availableIngredients = new HashSet<>();
        Set<Recipe> neededIngredients = new HashSet<>();
        for (JsonNode bldgNode : bldgsNode) {
            // assume factory has type & non empty sources
            if (bldgNode.has("type")) {
                // factory
                // assume all factory's type is in typeLst,
                // not going to return null
                Type type = Type.getTypeByName(
                    this.typeLst,
                    bldgNode.get("type").asText()
                );
                type.getRecipes().forEachRemaining(
                    r -> {
                        r.getIngredients().forEachRemaining(
                            neededIngredients::add
                        );
                    }
                );
                for (JsonNode source : bldgNode.get("sources")) {
                    String srcName = source.asText();
                    // assume source is a building (BuildingsSrcExistsRuleChecker)
                    availableIngredients.addAll(buildingOutputs.get(srcName));
                }
            }
        }
        for (Recipe r : neededIngredients) {
            if (!availableIngredients.contains(r)) {
                return "factory can not get ingredients: " + r.getOutput();
            }
        }
        return null;
    }
}
