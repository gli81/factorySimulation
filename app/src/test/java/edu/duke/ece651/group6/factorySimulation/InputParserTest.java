package edu.duke.ece651.group6.factorySimulation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import org.junit.jupiter.api.Test;

public class InputParserTest {
  private Reader reader(String resource) {
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resource);
    assertNotNull(inputStream, "Can't find the input: " + resource);
    return new InputStreamReader(inputStream);
  }
  
  private Map<String, Integer> extractIngredients(Recipe recipe) {
    Map<String, Integer> ingredients = new HashMap<>();
    for (Map.Entry<String, Integer> item : recipe.getAllIngredients()) {
      ingredients.put(item.getKey(), item.getValue());
    }
    return ingredients;
  }
  
  @Test
  public void test_parseRecipes() throws Exception {
    InputParser parser = new InputParser();
    Reader reader = reader("simulation/test-recipes.json");
    JsonNode root = parser.parseFile(reader);
    List<Recipe> recipes = parser.recipesInputParser(root);
    
    assertEquals(4, recipes.size());
    Recipe doorRecipe = null;
    for (Recipe r : recipes) {
      if (r.getOutput().equals("door")) {
        doorRecipe = r;
        break;
      }
    }
    //Check and validate that door recipe was parsed
    assertNotNull(doorRecipe);
    assertEquals(5, doorRecipe.getLatency());
    Map<String, Integer> doorIngred = extractIngredients(doorRecipe);
    assertEquals(2, doorIngred.size());
    assertEquals(2, doorIngred.get("wood"));
    assertEquals(1, doorIngred.get("metal"));

    //Check mine type  recipe - raw resource  
    Recipe wood = null;
    for (Recipe r : recipes) {
      if (r.getOutput().equals("wood")) {
        wood = r;
        break;
      }
    }
    assertNotNull(wood);
    assertEquals(1, wood.getLatency());
    Map<String, Integer> woodIngred = extractIngredients(wood);
    assertEquals(0, woodIngred.size());
  }

}
