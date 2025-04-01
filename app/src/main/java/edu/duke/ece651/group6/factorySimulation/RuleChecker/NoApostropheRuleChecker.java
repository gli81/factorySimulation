package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import com.fasterxml.jackson.databind.JsonNode;

public class NoApostropheRuleChecker extends RuleChecker {
    private final String[] parent;
    private final String field;
    private final boolean isArray;


    public NoApostropheRuleChecker(RuleChecker next, String field) {
        this(next, new String[]{}, field, false);
    }

    public NoApostropheRuleChecker(
        RuleChecker next, String[] parent,
        String field, boolean isArray
    ) {
        super(next);
        this.parent = parent;
        this.field = field;
        this.isArray = isArray;
    }


    @Override
    protected String checkRule(JsonNode root) {
        StringBuilder ans = new StringBuilder();
        JsonNode cur = getNodeAt(root, this.parent, ans);
        if (null == cur) {
            return ans.toString();
        }
        return applyElementCheck(cur, this.isArray, this::checkApostrophe);
    }

    protected String checkApostrophe(JsonNode node) {
        if (!node.has(field)) {
            return "" + field + " field is missing";
        }
        if (node.get(field).asText().contains("'")) {
            return field + " field contains apostrophe";
        }
        return null;
    }
}
