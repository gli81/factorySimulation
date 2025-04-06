package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LatencyRangeRuleCheckerTest {
    private InputStream j = this.getClass()
            .getResourceAsStream("/inputs/doors1.json");
    private final ObjectMapper mapper = new ObjectMapper();

    
    @Test
    void testCheckRule() throws IOException {
        JsonNode node = mapper.readTree(j);
        LatencyRangeRuleChecker checker = new LatencyRangeRuleChecker(null);
        assertNull(checker.checkRule(node));
    }
}
