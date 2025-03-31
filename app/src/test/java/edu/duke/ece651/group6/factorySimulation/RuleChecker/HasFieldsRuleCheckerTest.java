package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HasFieldsRuleCheckerTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testCheckRule() throws IOException {
        InputStream j = this.getClass().getResourceAsStream("/inputs/doors1.json");
        assertNotNull(j);
        // new String(j.readAllBytes());
        JsonNode node = mapper.readTree(j);
        HasFieldsRuleChecker checker = new HasFieldsRuleChecker(
            null,
            new HashSet<>(Arrays.asList("buildings", "types", "recipes"))
        );
        assertNull(checker.checkRule(node));
        assertNull(checker.checkJson(node));
        HasFieldsRuleChecker checker2 = new HasFieldsRuleChecker(
            null,
            new HashSet<>(Arrays.asList("buildings", "bad", "recipes"))
        );
        assertEquals(
            "Invalid json file - bad feild is missing",
            checker2.checkJson(node));
    }
}
