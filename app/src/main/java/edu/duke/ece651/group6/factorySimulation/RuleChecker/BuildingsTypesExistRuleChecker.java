package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import java.util.Set;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import edu.duke.ece651.group6.factorySimulation.Exception.InvalidJsonFileException;

public class BuildingsTypesExistRuleChecker extends RuleChecker {

    public BuildingsTypesExistRuleChecker(RuleChecker next) {
        super(next);
    }

    @Override
    protected String checkRule(JsonNode root) {
        Set<String> allTypes;
        try {
            allTypes = this.getAllTypes(root);
        } catch (InvalidJsonFileException e) {
            return e.getMessage();
        }
        if (!root.has("buildings")) {
            return "buildings field is missing";
        }
        JsonNode buildings = root.get("buildings");
        if (buildings.getNodeType() != JsonNodeType.ARRAY) {
            return "buildings field is not the expected type";
        }
        for (JsonNode b : buildings) {
            String buildingType;
            if (
                b.has("type") &&
                !allTypes.contains((buildingType = b.get("type").asText()))
            ) {
                return "building type " + buildingType + " is not a type";
            }
        }
        return null;
    }
}
