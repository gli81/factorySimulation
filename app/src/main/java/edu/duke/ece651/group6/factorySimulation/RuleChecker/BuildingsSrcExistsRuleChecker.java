package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.databind.JsonNode;

public class BuildingsSrcExistsRuleChecker extends RuleChecker {

    public BuildingsSrcExistsRuleChecker(RuleChecker next) {
        super(next);
    }

    @Override
    protected String checkRule(JsonNode bldgNode) {
        // assume bldgNode is ARRAY
        // get all names of buildings
        Set<String> allBldgs = new HashSet<>();
        for (JsonNode bldg : bldgNode) {
            // assume building has name field
            allBldgs.add(bldg.get("name").asText());
        }
        for (JsonNode bldg : bldgNode) {
            if (bldg.has("type")) {
                // assume type means sources field is ARRAY and non empty
                // (MineOrTypeAndSourcesRuleChecker)
                JsonNode src = bldg.get("sources");
                for (JsonNode s : src) {
                    String srcStr = s.asText();
                    if (!allBldgs.contains(srcStr)) {
                        return "source " + srcStr + " is not a building";
                    }
                }
            }
        }  
        return null;
    }
}
