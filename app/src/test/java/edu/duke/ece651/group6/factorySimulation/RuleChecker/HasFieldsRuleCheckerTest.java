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
    private InputStream j;


    {
        j = this.getClass().getResourceAsStream("/inputs/doors1.json");
        assertNotNull(j);
        // new String(j.readAllBytes());
    }

    
    @Test
    void testCheckRule() throws IOException {
        // InputStream j = this.getClass().getResourceAsStream("/inputs/doors1.json");
        // assertNotNull(j);
        // // new String(j.readAllBytes());
        JsonNode node = mapper.readTree(j);
        HasFieldsRuleChecker checker = new HasFieldsRuleChecker(
            null,
            new HashSet<>(Arrays.asList("buildings", "types", "recipes"))
        );
        assertNull(checker.checkRule(node));
        assertNull(checker.checkJson(node));
        HasFieldsRuleChecker checker2 = new HasFieldsRuleChecker(
            null, null,
            new HashSet<>(Arrays.asList("buildings", "bad", "recipes")),
            false
        );
        assertEquals(
            "bad feild is missing",
            checker2.checkJson(node));
    }
    
    @Test
    void testCheckRecipeRule() throws IOException {
        // InputStream j = this.getClass().getResourceAsStream("/inputs/doors1.json");
        // assertNotNull(j);
        // // new String(j.readAllBytes());
        JsonNode node = mapper.readTree(j);
        HasFieldsRuleChecker checker = new HasFieldsRuleChecker(
            null,
            new String[]{"recipes"},
            new HashSet<>(Arrays.asList("output", "ingredients", "latency")),
            true
        );
        assertNull(checker.checkRule(node));
        assertNull(checker.checkJson(node));
        HasFieldsRuleChecker checker2 = new HasFieldsRuleChecker(
            null,
            new String[]{"recipes"},
            new HashSet<>(Arrays.asList("output", "bad", "latency")),
            true
        );
        assertEquals(
            "bad feild is missing from recipes",
            checker2.checkJson(node));
    }

    @Test
    void testSkipRoot() throws IOException {
        JsonNode node = mapper.readTree(j);
        HasFieldsRuleChecker checker = new HasFieldsRuleChecker(
            null,
            new String[]{"recipe"},
            new HashSet<>(Arrays.asList("aaa")),
            true
        );
        assertEquals(
            "recipe is missing",
            checker.checkRule(node)
        );
    }
}
