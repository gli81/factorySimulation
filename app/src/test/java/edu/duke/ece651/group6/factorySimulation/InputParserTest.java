package edu.duke.ece651.group6.factorySimulation;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.duke.ece651.group6.factorySimulation.Model.Recipe;

public class InputParserTest {
    private final InputParser parser = new InputParser();
    private final InputStream doors1 = this.getClass().getResourceAsStream(
        "/inputs/doors1.json"
    );
    private final ObjectMapper mapper = new ObjectMapper();


    @Test
    void testParseBuildings() {

    }

    @Test
    void testParseJsonFile() {

    }

    @Test
    void testParseRecipes() throws IOException {
        JsonNode root = mapper.readTree(doors1);
        JsonNode recipes = root.get("recipes");
        List<Recipe> recipeList = parser.parseRecipes(recipes);
        for (Recipe r : recipeList) {
            // System.out.println(r.getOutput());
            System.out.println(r);
        }
    }

    @Test
    void testParseTypes() {

    }
}
