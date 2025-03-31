package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class RecipeOutputNoApostropheRuleCheckerTest {
    private JsonNodeFactory f = JsonNodeFactory.instance;


    @Test
    void testCheckRule() {
        RecipeOutputNoApostropheRuleChecker checker =
            new RecipeOutputNoApostropheRuleChecker(null);
        ObjectNode node = f.objectNode();
        node.put("output", "door");
        assertNull(checker.checkRule(node));
        node = f.objectNode();
        node.put("output", "Drew's best door");
        assertEquals(
            "Invalid json file - name contains apostrophe",
            checker.checkRule(node)
        );
    }
}
