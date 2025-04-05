package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BuildingsSrcExistsRuleCheckerTest {
    private InputStream j = this.getClass().getResourceAsStream(
        "/inputs/doors1.json"
    );
    private final ObjectMapper mapper = new ObjectMapper();


    @Test
    void testCheckRule() throws IOException {
        JsonNode node = mapper.readTree(j);
        BuildingsSrcExistsRuleChecker checker =
            new BuildingsSrcExistsRuleChecker(null);
        assertNull(checker.checkRule(node.get("buildings")));
    }
}
