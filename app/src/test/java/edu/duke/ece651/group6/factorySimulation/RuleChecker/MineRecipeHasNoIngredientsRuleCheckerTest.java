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

public class MineRecipeHasNoIngredientsRuleCheckerTest {
    private InputStream j = this.getClass().getResourceAsStream(
        "/inputs/doors1.json"
    );
    private final InputParser parser = new InputParser();
    ObjectMapper mapper = new ObjectMapper();
    
    
    @Test
    void testCheckRule() throws IOException {
        JsonNode node = mapper.readTree(j);
        List<Recipe> rLst = parser.parseRecipes(node.get("recipes"));
        MineRecipeHasNoIngredientsRuleChecker checker =
            new MineRecipeHasNoIngredientsRuleChecker(null, rLst);
        assertNull(checker.checkRule(node.get("buildings")));
    }
}
