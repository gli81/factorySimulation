package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import com.fasterxml.jackson.databind.JsonNode;

public class ListRuleChecker extends RuleChecker {
    public ListRuleChecker(RuleChecker next) {
        super(next);
    }


    @Override
    protected String checkRule(JsonNode root) {
        if (root.isArray()) {
            return null;
        }
        return "Invalid json file - one of the field is not a list";
    }

    @Override
    public String checkJson(JsonNode root) {
        // check own rule
        String rslt = checkRule(root);
        if (rslt != null) {
            return rslt;
        }
        if (null != next) {
            // check next rule for each element in the array
            for (JsonNode node : root) {
                if (!node.isObject()) {
                    return "Invalid json file - one of the field contains " +
                        "non-object element";
                }
                if (null != (rslt = next.checkJson(node))) {
                    return rslt;
                }
            }
        }
        return null;
    }
}
