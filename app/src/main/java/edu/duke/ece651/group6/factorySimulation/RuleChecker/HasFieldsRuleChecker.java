package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import java.util.Set;
import com.fasterxml.jackson.databind.JsonNode;

public class HasFieldsRuleChecker extends RuleChecker {
    private final String[] parent;
    private final Set<String> fields;
    private final boolean isArray;


    public HasFieldsRuleChecker(RuleChecker next, Set<String> fields) {
        super(next);
        this.parent = new String[]{};
        this.fields = fields;
        this.isArray = false;
    }

    public HasFieldsRuleChecker(
        RuleChecker next, String[] parent,
        Set<String> fields, boolean isArray
    ) {
        super(next);
        this.parent = parent;
        this.fields = fields;
        this.isArray = isArray;
    }

    
    @Override
    protected String checkRule(JsonNode root) {
        if (null == this.fields || fields.isEmpty()) {
            // why would you have null or empty
            return null;
        }
        StringBuilder ans = new StringBuilder();
        JsonNode cur = getNodeAt(root, this.parent, ans);
        if (null == cur) return ans.toString();
        if (isArray) {
            // check each element has the fields
            for (JsonNode node : cur) {
                for (String field : fields) {
                    if (!node.has(field)) {
                        return "" + field + " feild is missing from " +
                            parent[parent.length - 1];
                    }
                }
            }
        } else {
            for (String field : fields) {
                if (!cur.has(field)) {
                    return "" + field + " feild is missing";
                }
            }
        }
        return null;
    }
}