package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DuplicateValueRuleCheckerTest {
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
        DuplicateValueRuleChecker checker = new DuplicateValueRuleChecker(
            null,
            new String[]{"buildings"},
            "name"
        );
        assertNull(checker.checkRule(node));
        j = this.getClass().getResourceAsStream(
            "/inputs/duplicate_building_name.json"
        );
        node = mapper.readTree(j);
        assertEquals(
            "contains duplicate name",
            checker.checkRule(node)
        );
        checker = new DuplicateValueRuleChecker(
            null,
            new String[]{"recipes"},
            "name"
        );
        assertEquals(
            "name field is missing",
            checker.checkRule(node)
        );
        checker = new DuplicateValueRuleChecker(
            null,
            new String[]{"recipes", "buildings"},
            "name"
        );
        assertEquals(
            "buildings field is missing from recipes",
            checker.checkRule(node)
        );
    }
}
