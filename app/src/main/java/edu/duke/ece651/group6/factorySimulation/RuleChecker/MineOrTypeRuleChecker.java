package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import com.fasterxml.jackson.databind.JsonNode;

public class MineOrTypeRuleChecker extends RuleChecker {
    private final String[] parent;
    private final boolean isArray;


    public MineOrTypeRuleChecker(RuleChecker next) {
        super(next);
        this.parent = new String[]{};
        this.isArray = false;
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
        return applyElementCheck(
            cur, this.isArray, this::checkElementForMineOrType
        );
    }

    protected String checkElementForMineOrType(JsonNode node) {
        boolean hasMine = node.has("mine");
        boolean hasType = node.has("type");
        if (hasMine && hasType) {
            return "" + parent[parent.length - 1] +
                " has both mine and type";
        }
        if (!hasMine && !hasType) {
            return "" + parent[parent.length - 1] +
                " doesn't have mine or type";
        }
        return null;
    }    
}
