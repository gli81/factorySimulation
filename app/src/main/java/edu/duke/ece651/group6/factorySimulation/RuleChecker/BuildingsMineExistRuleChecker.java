package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import edu.duke.ece651.group6.factorySimulation.Model.Recipe;

public class BuildingsMineExistRuleChecker extends RuleChecker {
    private final List<Recipe> recipeList;


    public BuildingsMineExistRuleChecker(RuleChecker next, List<Recipe> rLst) {
        super(next);
        this.recipeList = rLst;
    }

    @Override
    protected String checkRule(JsonNode bldgNode) {
        for (JsonNode bldg : bldgNode) {
            if (bldg.has("mine")) { // assume mine / type checked
                String recipe = bldg.get("mine").asText();
                if (null == Recipe.getRecipeFromListByOutput(recipeList, recipe)) {
                    return "mine recipe " + recipe
                        + " is not an output from recipes";
                }
            }
        }
        return null;
    }
}
