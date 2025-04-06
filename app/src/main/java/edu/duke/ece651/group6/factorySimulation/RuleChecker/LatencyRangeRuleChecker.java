package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import com.fasterxml.jackson.databind.JsonNode;

public class LatencyRangeRuleChecker extends RuleChecker {

    public LatencyRangeRuleChecker(RuleChecker next) {
        super(next);
    }

    @Override
    protected String checkRule(JsonNode root) {
        // assume has recipes, recipes is an array
        // assume each recipe has latency field, latency is an int
        JsonNode recipesNode = root.get("recipes");
        for (JsonNode recipeNode : recipesNode) {
            int latency = recipeNode.get("latency").asInt();
            if (latency < 1 || latency >= Integer.MAX_VALUE) {
                return "latency out of range";
            }
        }
        return null;
    }
}
