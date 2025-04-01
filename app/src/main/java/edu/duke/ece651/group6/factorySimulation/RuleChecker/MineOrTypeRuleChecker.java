package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class MineOrTypeRuleChecker extends RuleChecker {
    private final String[] parent;
    private final boolean isArray;


    public MineOrTypeRuleChecker(RuleChecker next) {
        this(next, new String[]{}, false);
    }

    public MineOrTypeRuleChecker(
        RuleChecker next, String[] parent, boolean isArray
    ) {
        super(next);
        this.parent = parent;
        this.isArray = isArray;
    }


    @Override
    protected String checkRule(JsonNode root) {
        StringBuilder ans = new StringBuilder();
        JsonNode cur = getNodeAt(root, parent, ans);
        if (null == cur) return ans.toString();
        return applyElementCheck(cur, this.isArray, this::checkMineOrType);
    }

    protected String checkMineOrType(JsonNode node) {
        boolean hasMine = node.has("mine");
        boolean hasType = node.has("type");
        if (hasMine && hasType) {
            return "" + parent[parent.length - 1] + " has both mine and type";
        }
        if (!hasMine && !hasType) {
            return "" + parent[parent.length - 1] + " doesn't have mine or type";
        }
        // also checks the type
        if (
            hasMine &&
            node.get("mine").getNodeType() != JsonNodeType.STRING
        ) {
            return "mine field is not the expected type";
        }
        if (
            hasType &&
            node.get("type").getNodeType() != JsonNodeType.STRING
        ) {
            return "type field is not the expected type";
        }
        return null;
    }    
}
