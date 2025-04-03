package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BuildingsTypesExistRuleCheckerTest {
    private final ObjectMapper mapper = new ObjectMapper();


    @Test
    void testInvalidBuildingType() throws IOException {
        InputStream j = this.getClass().getResourceAsStream(
            "/inputs/undefined_building_type.json"
        );
        JsonNode node = mapper.readTree(j);
        BuildingsTypesExistRuleChecker checker =
            new BuildingsTypesExistRuleChecker(null);
        assertEquals(
            "building type undefined_type is not a type",
            checker.checkRule(node)
        );
    }
}
