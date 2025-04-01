package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.databind.JsonNode;

public class DuplicateValueRuleChecker extends RuleChecker {
    private final String[] parent;
    private final String field;
    // must be an array


    public DuplicateValueRuleChecker(RuleChecker next, String field) {
        this(next, new String[]{}, field);
    }

    public DuplicateValueRuleChecker(
        RuleChecker next, String[] parent, String field
    ) {
        super(next);
        this.parent = parent;
        this.field = field;
    }


    @Override
    protected String checkRule(JsonNode root) {
        StringBuilder ans = new StringBuilder();
        JsonNode cur = getNodeAt(root, this.parent, ans);
        if (null == cur) return ans.toString();
        if (!cur.isArray()) {
            return "" + field + " is not an array";
        }
        Set<String> set = new HashSet<>();
        for (JsonNode node : cur) {
            if (!node.has(this.field)) {
                return field + " field is missing";
            }
            String value = node.get(this.field).asText();
            if (set.contains(value)) {
                return "contains duplicate " + field;
            }
            set.add(value);
        }
        return null;
    }
}
