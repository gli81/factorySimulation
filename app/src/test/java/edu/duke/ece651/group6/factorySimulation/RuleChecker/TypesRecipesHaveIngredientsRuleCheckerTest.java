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

public class TypesRecipesHaveIngredientsRuleCheckerTest {
    private InputStream j = this.getClass().getResourceAsStream(
        "/inputs/doors1.json"
    );
    private final ObjectMapper mapper = new ObjectMapper();
    private final InputParser parser = new InputParser();


    @Test
    void testCheckRule() throws IOException {
        JsonNode node = mapper.readTree(j);
        List<Recipe> rLst = parser.parseRecipes(node.get("recipes"));
        TypesRecipesHaveIngredientsRuleChecker checker =
            new TypesRecipesHaveIngredientsRuleChecker(null, rLst);
        assertNull(checker.checkRule(node.get("types")));
        j = this.getClass().getResourceAsStream(
            "/inputs/no_ingredients_type_recipe.json"
        );
        node = mapper.readTree(j);
        rLst = parser.parseRecipes(node.get("recipes"));
        checker = new TypesRecipesHaveIngredientsRuleChecker(null, rLst);
        assertEquals(
            "wood is produced by factory but doesn't have ingredients",
            checker.checkRule(node.get("types"))
        );
    }
}
