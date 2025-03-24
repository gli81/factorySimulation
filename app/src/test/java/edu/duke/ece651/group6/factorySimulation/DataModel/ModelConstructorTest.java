package edu.duke.ece651.group6.factorySimulation.DataModel;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.net.URL;

public class ModelConstructorTest {

  private String getResourcePath(String resourcePath) {
    URL url = getClass().getClassLoader().getResource(resourcePath);
    System.out.println("Looking for resource: " + resourcePath);
    System.out.println("URL found: " + url);
    if (url == null) {
      fail("Could not find resource: " + resourcePath);
    }
    return url.getPath();
  }

    @Test
    public void testConstructor1() throws IOException {
        private ModelManager modelManager = new ModelManager();
        private ModelConstructor constructor = new ModelConstructor(modelManager);
        constructor.constructFromJsonFile("src/resources/inputs/doors1.json");

        assertEquals(constructor.toString(),
                "Types:\n" +
                        "Type: { door, { door } }\n" +
                        "Type: { handle, { handle } }\n" +
                        "Type: { hinge, { hinge } }\n\n" +
                        "Recipes:\n" +
                        "Recipe: { door, 12, { wood: 1, handle: 1, hinge: 3 } }\n" +
                        "Recipe: { handle, 5, { metal: 1 } }\n" +
                        "Recipe: { hinge, 1, { metal: 1 } }\n" +
                        "Recipe: { wood, 1, { } }\n" +
                        "Recipe: { metal, 1, { } }\n\n" +
                        "Buildings:\n" +
                        "Factory: { D, door, { W, Hi, Ha } }\n" +
                        "Factory: { Ha, handle, { M } }\n" +
                        "Factory: { Hi, hinge, { M } }\n" +
                        "Mine: { W, wood }\n" +
                        "Mine: { M, metal }\n");
    }

    @Test
    public void testConstructor2() throws IOException {
        private ModelManager modelManager = new ModelManager();
        private ModelConstructor constructor = new ModelConstructor(modelManager);
        constructor.constructFromJsonFile("src/resources/inputs/doors2.json");
        assertEquals(constructor.toString(),
                "Types:\n" +
                        "Type: { door, { door } }\n" +
                        "Type: { Hw, { handle, hinge } }\n\n" +
                        "Recipes:\n" +
                        "Recipe: { door, 12, { wood: 1, handle: 1, hinge: 3 } }\n" +
                        "Recipe: { handle, 5, { metal: 1 } }\n" +
                        "Recipe: { hinge, 1, { metal: 1 } }\n" +
                        "Recipe: { wood, 1, { } }\n" +
                        "Recipe: { metal, 1, { } }\n\n" +
                        "Buildings:\n" +
                        "Factory: { D, door, { Hw1, Hw2, W } }\n" +
                        "Factory: { Hw1, Hw, { M1, M2 } }\n" +
                        "Factory: { Hw2, Hw, { M1, M2 } }\n" +
                        "Mine: { W, wood }\n" +
                        "Mine: { M1, metal }\n" +
                        "Mine: { M2, metal }\n");
    }

  @Test
  public void testRecipNameApostrophe() {
    ModelManager m = new ModelManager();
    ModelConstructor c = new ModelConstructor(m);
    String resourcePath = getResourcePath("inputs/invalid_recipe_name.json");
    
    String expected = "Recipe name 'door's' contains an apostrophe which is not allowed";
    
    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath)
    );
    
    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testDuplicateRecipName() {
    ModelManager m = new ModelManager();
    ModelConstructor c = new ModelConstructor(m);
    String resourcePath = getResourcePath("inputs/duplicate_recipe_name.json");
    
    String expected = "Recipe name 'door' is not unique";
    
    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath)
    );
    
    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testInvalidRecipLat() {
    ModelManager m = new ModelManager();
    ModelConstructor c = new ModelConstructor(m);
    String resourcePath = getResourcePath("inputs/invalid_recipe_latency.json");
    
    String expected = "Recipe latency must be at least 1, got 0 for recipe 'door'";
    
    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath)
    );
    
    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testUndefinedIngred() {
    ModelManager m = new ModelManager();
    ModelConstructor c = new ModelConstructor(m);
    String resourcePath = getResourcePath("inputs/undefined_ingredient.json");
    
    String expected = "Ingredient of recipe door not found: undefined_material";
    
    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath)
    );
    
    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testMissingRecipeLat() {
    ModelManager m = new ModelManager();
    ModelConstructor c = new ModelConstructor(m);
    String resourcePath = getResourcePath("inputs/missing_recipe_latency.json");
    
    String expected = "Missing latency of recipe door in JSON";
    
    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath)
    );
    
    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testMissingRecipOutput() {
    ModelManager m = new ModelManager();
    ModelConstructor c = new ModelConstructor(m);
    String resourcePath = getResourcePath("inputs/missing_recipe_output.json");
    
    assertThrows(
        Exception.class,
        () -> c.constructFromJsonFile(resourcePath)
    );
  }

  @Test
  public void testMissingRecipArr() {
    ModelManager m = new ModelManager();
    ModelConstructor c = new ModelConstructor(m);
    String resourcePath = getResourcePath("inputs/missing_recipes_array.json");
    
    String expected = "Missing recipes array in JSON";
    
    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath)
    );
    
    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testMissingRecipIngred() {
    ModelManager m = new ModelManager();
    ModelConstructor c = new ModelConstructor(m);
    String resourcePath = getResourcePath("inputs/missing_recipe_ingredients.json");
    
    String expected = "Missing ingredients of recipe door in JSON";
    
    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath)
    );
    
    assertEquals(expected, e.getMessage());
  }
}
