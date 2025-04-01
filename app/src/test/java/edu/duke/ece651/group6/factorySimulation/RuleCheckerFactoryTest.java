package edu.duke.ece651.group6.factorySimulation;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.duke.ece651.group6.factorySimulation.RuleChecker.RuleChecker;

public class RuleCheckerFactoryTest {
    private final RuleCheckerFactory f = new RuleCheckerFactory();
    private final RuleChecker checker = f.getRuleChecker();
    private InputStream j = this.getClass().getResourceAsStream("/inputs/doors1.json");
    private final ObjectMapper mapper = new ObjectMapper();


    @Test
    void testGetRuleChecker() throws IOException {
        JsonNode node = mapper.readTree(j);
        assertNull(checker.checkJson(node));
    }
}
