package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import com.fasterxml.jackson.databind.JsonNode;

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

    protected String noApostrophe(JsonNode root, String field) {
        if (root.get(field).asText().contains("'")) {
            return "Invalid json file - name contains apostrophe";
        }
        return null;
    }
}
