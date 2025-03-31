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
        RuleChecker next, String[] parent, Set<String> fields, boolean isArray
    ) {
        super(next);
        this.parent = parent;
        this.fields = fields;
        this.isArray = isArray;
    }

    public HasFieldsRuleChecker(RuleChecker next) {
        super(next);
        this.parent = new String[]{};
        this.fields = null;
        this.isArray = false;
    }

    
    @Override
    protected String checkRule(JsonNode root) {
        if (null == this.fields) return null;
        JsonNode cur = root;
        if (null != parent) {
            for (int i = 0; i < parent.length; ++i) {
                if (!cur.has(parent[i])) {
                    String ans = "Invalid json file - " + parent[i] + " is missing";
                    if (i > 0) {
                        ans += " from " + parent[i - 1]; 
                    }
                    return ans;
                }
                cur = cur.get(parent[i]);
            }
        }
        if (isArray) {
            // check each element has the fields
            for (JsonNode node : cur) {
                for (String field : fields) {
                    if (!node.has(field)) {
                        return "Invalid json file - " + field +
                            " feild is missing from " + parent[parent.length - 1];
                    }
                }
            }
        } else {
            for (String field : fields) {
                if (!cur.has(field)) {
                    return "Invalid json file - " + field + " feild is missing";
                }
            }
        }
        return null;
    }
}