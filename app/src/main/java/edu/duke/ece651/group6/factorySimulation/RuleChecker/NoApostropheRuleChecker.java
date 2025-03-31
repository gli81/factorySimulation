package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import com.fasterxml.jackson.databind.JsonNode;

public class NoApostropheRuleChecker extends RuleChecker {
    private final String[] parent;
    private final String field;


    public NoApostropheRuleChecker(RuleChecker next) {
        super(next);
        this.parent = null;
        this.field = null;
    }

    public NoApostropheRuleChecker(RuleChecker next, String field) {
        super(next);
        this.parent = new String[]{};
        this.field = field;
    }


    @Override
    protected String checkRule(JsonNode root) {
        JsonNode cur = root;
        for (int i = 0; i < parent.length; ++i) {
            cur = cur.get(parent[i]); // e.g., recipes
        }
        for (JsonNode node : cur) {

        }
        return null;
    }
    
}
