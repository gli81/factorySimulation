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
import edu.duke.ece651.group6.factorySimulation.Model.Type;

public class FactoryCanGetIngredientsRuleCheckerTest {
    private InputStream j = this.getClass()
            .getResourceAsStream("/inputs/doors1.json");
    private final ObjectMapper mapper = new ObjectMapper();
    private final InputParser parser = new InputParser();


    @Test
    void testCheckRule() throws IOException{
        JsonNode node = mapper.readTree(j);
        List<Recipe> recipes = parser.parseRecipes(node.get("recipes"));
        List<Type> types = parser.parseTypes(node.get("types"), recipes);
        FactoryCanGetIngredientsRuleChecker checker =
            new FactoryCanGetIngredientsRuleChecker(null, recipes, types);
        assertNull(checker.checkRule(node.get("buildings")));
    }
}
