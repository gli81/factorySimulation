package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class HasFieldsAndTypeRuleCheckerTest {
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
        HasFieldsAndTypeRuleChecker checker = new HasFieldsAndTypeRuleChecker(
            null,
            Map.of(
                "buildings", JsonNodeType.ARRAY,
                "recipes", JsonNodeType.ARRAY,
                "types", JsonNodeType.ARRAY
            )
        );
        assertNull(checker.checkRule(node));
        assertNull(checker.checkJson(node));
        HasFieldsAndTypeRuleChecker checker2 = new HasFieldsAndTypeRuleChecker(
            null, null,
            Map.of(
                "buildings", JsonNodeType.ARRAY,
                "bad", JsonNodeType.ARRAY,
                "types", JsonNodeType.ARRAY
            ),
            false
        );
        assertEquals(
            "bad feild is missing",
            checker2.checkJson(node));
    }
    
    @Test
    void testCheckRecipeRule() throws IOException {
        JsonNode node = mapper.readTree(j);
        HasFieldsAndTypeRuleChecker checker = new HasFieldsAndTypeRuleChecker(
            null,
            new String[]{"recipes"},
            Map.of(
                "output", JsonNodeType.STRING,
                "ingredients", JsonNodeType.OBJECT,
                "latency", JsonNodeType.NUMBER
            ),
            true
        );
        assertNull(checker.checkRule(node));
        assertNull(checker.checkJson(node));
        HasFieldsAndTypeRuleChecker checker2 = new HasFieldsAndTypeRuleChecker(
            null,
            new String[]{"recipes"},
            Map.of(
                "output", JsonNodeType.STRING,
                "bad", JsonNodeType.NUMBER,
                "latency", JsonNodeType.NUMBER
            ),
            true
        );
        assertEquals(
            "bad feild is missing from recipes",
            checker2.checkJson(node));
    }

    @Test
    void testSkipRoot() throws IOException {
        JsonNode node = mapper.readTree(j);
        HasFieldsAndTypeRuleChecker checker = new HasFieldsAndTypeRuleChecker(
            null,
            new String[]{"recipe"},
            Map.of(
                "ingredients", JsonNodeType.OBJECT
            ),
            true
        );
        assertEquals(
            "recipe field is missing",
            checker.checkRule(node)
        );
    }

    @Test
    void testWrongType() throws IOException {
        j = this.getClass().getResourceAsStream("/inputs/invalid_latency.json");
        JsonNode node = mapper.readTree(j);
        HasFieldsAndTypeRuleChecker checker = new HasFieldsAndTypeRuleChecker(
            null,
            new String[]{"recipes"},
            Map.of(
                "output", JsonNodeType.STRING,
                "ingredients", JsonNodeType.OBJECT,
                "latency", JsonNodeType.NUMBER
            ),
            true
        );
        assertEquals(
            "latency field is not the expected type",
            checker.checkRule(node)
        );
    }

    
}
