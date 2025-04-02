package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public abstract class RuleChecker {
    protected final RuleChecker next;


    public RuleChecker(RuleChecker next) {
        this.next = next;
    }


    /**
     * check own rule
     * 
     * @param root
     * @return
     */
    protected abstract String checkRule(JsonNode root);

    /**
     * checks if all rules are met
     * 
     * @return
     */
    public String checkJson(JsonNode root) {
        // check own rule
        String rslt = checkRule(root);
        if (rslt != null) {
            return rslt;
        }
        if (null != next) {
            // check next rule
            return next.checkJson(root);
        }
        return null;
    }

    // protected String noApostrophe(JsonNode root, String field) {
    //     if (root.get(field).asText().contains("'")) {
    //         return "Invalid json file - name contains apostrophe";
    //     }
    //     return null;
    // }

    protected JsonNode getNodeAt(
        JsonNode root, String[] path, StringBuilder err
    ) {
        if (null == path || path.length == 0) return root;
        JsonNode cur = root;
        for (int i = 0; i < path.length; ++i) {
            if (!cur.has(path[i])) {
                err.append(path[i]).append(" field is missing");
                if (i > 0) {
                    err.append(" from ").append(path[i - 1]);
                }
                return null;
            }
            cur = cur.get(path[i]);
        }
        return cur;
    }

    protected String applyElementCheck(
        JsonNode node, boolean isArray, Function<JsonNode, String> check
    ) {
        String ans;
        if (isArray) {
            for (JsonNode n : node) {
                if (null != (ans = check.apply(n))) {
                    return ans;
                }
            }
        } else {
            return check.apply(node);
        }
        return null;
    }

    protected Set<String> getAllRecipes(JsonNode root) throws Exception {
        if (!root.has("recipes")) {
            throw new Exception("recipes field is missing");
        }
        JsonNode recipes = root.get("recipes");
        if (recipes.getNodeType() != JsonNodeType.ARRAY) {
            throw new Exception("recipes field is not the expected type");
        }
        // create set of all outputs
        Set<String> allRecipes = new HashSet<>();
        for (JsonNode r : recipes) {
            allRecipes.add(r.get("output").asText());
        }
        return allRecipes;
    }
}
