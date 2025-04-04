package edu.duke.ece651.group6.factorySimulation.RuleChecker;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.duke.ece651.group6.factorySimulation.InputParser;
import edu.duke.ece651.group6.factorySimulation.Model.Recipe;

public class BuildingsMineExistRuleCheckerTest {
    private final InputStream j = this.getClass().getResourceAsStream(
        "/inputs/doors1.json"
    );
    private final ObjectMapper mapper = new ObjectMapper();
    private final InputParser parser = new InputParser();


    @Test
    void testCheckRule() throws IOException {
        JsonNode node = mapper.readTree(j);
        JsonNode buildingsNode = node.get("buildings");
        List<Recipe> rList = parser.parseRecipes(node.get("recipes"));
        BuildingsMineExistRuleChecker checker =
            new BuildingsMineExistRuleChecker(null, rList);
        assertNull(checker.checkRule(buildingsNode));
    }
}
