package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import com.fasterxml.jackson.databind.JsonNode;

public class HasFieldRuleChecker extends RuleChecker {
    private final String field;


    public HasFieldRuleChecker(RuleChecker next, String field) {
        super(next);
        this.field = field;
    }

    public HasFieldRuleChecker(RuleChecker next) {
        super(next);
        this.field = null;
    }

    
    @Override
    protected String checkRule(JsonNode root) {
        if (root.has(field)) {
            return null;
        }
        return field;
    }
    
}