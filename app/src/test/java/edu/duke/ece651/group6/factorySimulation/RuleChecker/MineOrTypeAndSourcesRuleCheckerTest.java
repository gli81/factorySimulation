package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MineOrTypeAndSourcesRuleCheckerTest {
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
        MineOrTypeAndSourcesRuleChecker checker =
            new MineOrTypeAndSourcesRuleChecker(
                null, new String[]{"buildings"}, true
        );
        assertNull(checker.checkRule(node));
        j = this.getClass().getResourceAsStream(
            "/inputs/building_both_factory_and_mine.json"
        );
        assertNotNull(j);
        node = mapper.readTree(j);
        assertEquals(
            "buildings has both mine and type",
            checker.checkRule(node)
        );
        j = this.getClass().getResourceAsStream(
            "/inputs/building_neither_factory_nor_mine.json"
        );
        assertNotNull(j);
        node = mapper.readTree(j);
        assertEquals(
            "buildings doesn't have mine or type",
            checker.checkRule(node)
        );
    }
}
