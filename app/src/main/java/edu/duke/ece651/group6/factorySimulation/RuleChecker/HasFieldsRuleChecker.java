package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import java.util.Set;
import com.fasterxml.jackson.databind.JsonNode;

public class HasFieldsRuleChecker extends RuleChecker {
    private final Set<String> fields;


    public HasFieldsRuleChecker(RuleChecker next, Set<String> fields) {
        super(next);
        this.fields = fields;
    }

    public HasFieldsRuleChecker(RuleChecker next) {
        super(next);
        this.fields = null;
    }

    
    @Override
    protected String checkRule(JsonNode root) {
        if (null == this.fields) return null;
        for (String field : fields) {
            if (!root.has(field)) {
                return "Invalid json file - " + field + " feild is missing";
            }
        }
        return null;
    }
}