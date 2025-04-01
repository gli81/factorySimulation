package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class HasFieldsAndTypeRuleChecker extends RuleChecker {
    private final String[] parent;
    private final Map<String, JsonNodeType> fieldsTypesMap;
    private final boolean isArray;


    public HasFieldsAndTypeRuleChecker(
        RuleChecker next, Map<String, JsonNodeType> fieldsTypesMap
    ) {
        this(next, new String[]{}, fieldsTypesMap, false);
    }

    public HasFieldsAndTypeRuleChecker(
        RuleChecker next, String[] parent,
        Map<String, JsonNodeType> fieldsTypesMap, boolean isArray
    ) {
        super(next);
        this.parent = parent;
        this.fieldsTypesMap = fieldsTypesMap;
        this.isArray = isArray;
    }

    
    @Override
    protected String checkRule(JsonNode root) {
        if (null == this.fieldsTypesMap || this.fieldsTypesMap.isEmpty()) {
            // why would you have null or empty
            return null;
        }
        StringBuilder ans = new StringBuilder();
        JsonNode cur = getNodeAt(root, this.parent, ans);
        if (null == cur) return ans.toString();
        return applyElementCheck(cur, this.isArray, this::checkFieldsAndTypes);
    }

    protected String checkFieldsAndTypes(JsonNode node) {
        StringBuilder ans = new StringBuilder();
        for (String field : this.fieldsTypesMap.keySet()) {
            if (!node.has(field)) {
                 ans.append(field).append(" feild is missing");
                 if (null != this.parent && this.parent.length > 0) {
                    ans.append(" from ").append(
                        this.parent[this.parent.length - 1]
                    );
                 }
                 return ans.toString();
            }
            if (
                node.get(field).getNodeType() != this.fieldsTypesMap.get(field)
            ) {
                return "" + field + " field is not the expected type";
            }
        }
        return null;
    }
}