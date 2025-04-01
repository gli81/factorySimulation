package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NoApostropheRuleCheckerTest {
    private final ObjectMapper mapper = new ObjectMapper();
    private InputStream j;


    {
        j = this.getClass().getResourceAsStream("/inputs/doors1.json");
        assertNotNull(j);
        // new String(j.readAllBytes());
    }


    @Test
    void testCheckRule() throws IOException {
        JsonNode node = mapper.readTree(j);
        NoApostropheRuleChecker checker = new NoApostropheRuleChecker(
            null, new String[]{"recipes"}, "output", true
        );
        assertNull(checker.checkRule(node));
        InputStream apostropheRecipeOutput =
            this.getClass().getResourceAsStream(
                "/inputs/invalid_recipe_name.json"
            );
        assertNotNull(apostropheRecipeOutput);
        node = mapper.readTree(apostropheRecipeOutput);
        assertEquals(
            "output field contains apostrophe",
            checker.checkRule(node)
        );
    }
}
