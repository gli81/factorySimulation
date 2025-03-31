package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import com.fasterxml.jackson.databind.JsonNode;

public class RecipeOutputNoApostropheRuleChecker extends RuleChecker {
    public RecipeOutputNoApostropheRuleChecker(RuleChecker next) {
        super(next);
    }


    /**
     * Checks if recipe name contains apostrophes
     * 
     * @param recipe is the recipe node that is being checked
     * @return null if recipe name is valid, error message if invalid
     */
    @Override
    public String checkRule(JsonNode recipe) {
        return this.noApostrophe(recipe, "output");
    }
}
