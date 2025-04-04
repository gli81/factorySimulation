package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import edu.duke.ece651.group6.factorySimulation.Exception.InvalidJsonFileException;

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

    // protected String applyElementCheck2(
    //     JsonNode node, boolean isArray, Function<JsonNode, String> check
    // ) {
    //     String ans;
    //     if (isArray) {
    //         // TODO not necessary, ARRAY checked before get here
    //         // ============================================================
    //         if (node.getNodeType() == JsonNodeType.ARRAY) {
    //             return node.textValue() + " is not the expected type";
    //         }
    //         // ============================================================
    //         for (JsonNode n : node) {
    //             if (null != (ans = check.apply(n))) {
    //                 return ans;
    //             }
    //         }
    //     } else {
    //         return check.apply(node);
    //     }
    //     return null;
    // }
    
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

    protected Set<String> getAllRecipes(
        JsonNode root
    ) throws InvalidJsonFileException {
        if (!root.has("recipes")) {
            throw new InvalidJsonFileException("recipes field is missing");
        }
        JsonNode recipes = root.get("recipes");
        if (recipes.getNodeType() != JsonNodeType.ARRAY) {
            throw new InvalidJsonFileException(
                "recipes field is not the expected type"
            );
        }
        // create set of all outputs
        Set<String> allRecipes = new HashSet<>();
        for (JsonNode r : recipes) {
            allRecipes.add(r.get("output").asText());
        }
        return allRecipes;
    }

    protected Set<String> getAllTypes(
        JsonNode root
    ) throws InvalidJsonFileException {
        if (!root.has("types")) {
            throw new InvalidJsonFileException("types field is missing");
        }
        JsonNode types = root.get("types");
        if (types.getNodeType() != JsonNodeType.ARRAY) {
            throw new InvalidJsonFileException(
                "types field is not the expected type"
            );
        }
        // create set of all outputs
        Set<String> allTypes = new HashSet<>();
        for (JsonNode r : types) {
            allTypes.add(r.get("name").asText());
        }
        return allTypes;
    }
}
