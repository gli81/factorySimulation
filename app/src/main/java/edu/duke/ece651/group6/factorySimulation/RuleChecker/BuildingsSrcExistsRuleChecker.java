package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public class BuildingsSrcExistsRuleChecker extends RuleChecker {
    private String fieldName;
    private String srcFieldName;

    public BuildingsSrcExistsRuleChecker(RuleChecker next, String fieldName, String srcFieldName) {
        super(next);
        this.fieldName = fieldName;
        this.srcFieldName = srcFieldName;
    }

    @Override
    protected String checkRule(JsonNode root) {
        return null;
    }
}
