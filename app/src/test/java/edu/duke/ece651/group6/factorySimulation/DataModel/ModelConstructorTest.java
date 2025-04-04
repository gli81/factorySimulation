package edu.duke.ece651.group6.factorySimulation.DataModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import com.google.gson.JsonParseException;

import org.junit.jupiter.api.Test;

public class ModelConstructorTest {

  private String getResourcePath(String resourcePath) {
    URL url = getClass().getClassLoader().getResource(resourcePath);
    System.out.println("Finding file: " + resourcePath);

    if (url == null) {
      System.out.println("URL wasn't found for: " + resourcePath);
      throw new RuntimeException("Resource not found: " + resourcePath);
    }

    System.out.println("URL found: " + url);
    return url.getPath();
  }

  @Test
  public void testResourceNotFound() {
    try {
      getResourcePath("inputs/non_existent_file.json");
    } catch (RuntimeException e) {
      assertTrue(e.getMessage().contains("Resource not found"));
    }
  }

  @Test
  public void testConstructor1() throws IOException {
    MapGrid mapGrid = new MapGrid(30, 30);
    ModelManager modelManager = new ModelManager(mapGrid);
    ModelConstructor constructor = new ModelConstructor(modelManager, mapGrid);
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

    assertEquals(mapGrid.toString(),
        "MapGrid:\n" +
            " 0|1|2|3|4|5|6|7|8|9|0|1|2|3|4|5|6|7|8|9|0|1|2|3|4|5|6|7|8|9|\n" +
            "0F| | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "1 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "2 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "3 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "4 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "5 | | | | |F| | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "6 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "7 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "8 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "9 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "0 | | | | | | | | | |F| | | | | | | | | | | | | | | | | | | |\n" +
            "1 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "2 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "3 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "4 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "5 | | | | | | | | | | | | | | |M| | | | | | | | | | | | | | |\n" +
            "6 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "7 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "8 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "9 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "0 | | | | | | | | | | | | | | | | | | | |M| | | | | | | | | |\n" +
            "1 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "2 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "3 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "4 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "5 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "6 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "7 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "8 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "9 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n");
  }

  @Test
  public void testConstructor2() throws IOException {
    MapGrid mapGrid = new MapGrid(30, 30);
    ModelManager modelManager = new ModelManager(mapGrid);
    ModelConstructor constructor = new ModelConstructor(modelManager, mapGrid);
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

    assertEquals(mapGrid.toString(),
        "MapGrid:\n" +
            " 0|1|2|3|4|5|6|7|8|9|0|1|2|3|4|5|6|7|8|9|0|1|2|3|4|5|6|7|8|9|\n" +
            "0F| | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "1 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "2 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "3 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "4 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "5 | | | | |F| | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "6 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "7 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "8 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "9 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "0 | | | | | | | | | |F| | | | | | | | | | | | | | | | | | | |\n" +
            "1 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "2 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "3 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "4 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "5 | | | | | | | | | | | | | | |M| | | | | | | | | | | | | | |\n" +
            "6 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "7 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "8 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "9 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "0 | | | | | | | | | | | | | | | | | | | |M| | | | | | | | | |\n" +
            "1 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "2 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "3 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "4 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "5 | | | | | | | | | | | | | | | | | | | | | | | | |M| | | | |\n" +
            "6 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "7 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "8 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n" +
            "9 | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |\n");
  }

  @Test
  public void testRecipNameApostrophe() {
    MapGrid mapGrid = new MapGrid();
    ModelManager modelManager = new ModelManager(mapGrid);
    ModelConstructor c = new ModelConstructor(modelManager, mapGrid);
    String resourcePath = getResourcePath("inputs/invalid_recipe_name.json");

    String expected = "Recipe name 'door's' contains an apostrophe which is not allowed";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testDuplicateRecipName() {
    MapGrid mapGrid = new MapGrid();
    ModelManager modelManager = new ModelManager(mapGrid);
    ModelConstructor c = new ModelConstructor(modelManager, mapGrid);
    String resourcePath = getResourcePath("inputs/duplicate_recipe_name.json");

    String expected = "Recipe name 'door' is not unique";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testInvalidRecipLat() {
    MapGrid mapGrid = new MapGrid();
    ModelManager modelManager = new ModelManager(mapGrid);
    ModelConstructor c = new ModelConstructor(modelManager, mapGrid);
    String resourcePath = getResourcePath("inputs/invalid_recipe_latency.json");

    String expected = "Recipe latency must be at least 1, got 0 for recipe 'door'";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testUndefinedIngred() {
    MapGrid mapGrid = new MapGrid();
    ModelManager modelManager = new ModelManager(mapGrid);
    ModelConstructor c = new ModelConstructor(modelManager, mapGrid);
    String resourcePath = getResourcePath("inputs/undefined_ingredient.json");

    String expected = "Ingredient of recipe door not found: undefined_material";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testMissingRecipeLat() {
    MapGrid mapGrid = new MapGrid();
    ModelManager modelManager = new ModelManager(mapGrid);
    ModelConstructor c = new ModelConstructor(modelManager, mapGrid);
    String resourcePath = getResourcePath("inputs/missing_recipe_latency.json");

    String expected = "Missing latency of recipe door in JSON";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testMissingRecipOutput() {
    MapGrid mapGrid = new MapGrid();
    ModelManager modelManager = new ModelManager(mapGrid);
    ModelConstructor c = new ModelConstructor(modelManager, mapGrid);
    String resourcePath = getResourcePath("inputs/missing_recipe_output.json");

    assertThrows(
        Exception.class,
        () -> c.constructFromJsonFile(resourcePath));
  }

  @Test
  public void testMissingRecipArr() {
    MapGrid mapGrid = new MapGrid();
    ModelManager modelManager = new ModelManager(mapGrid);
    ModelConstructor c = new ModelConstructor(modelManager, mapGrid);
    String resourcePath = getResourcePath("inputs/missing_recipes_array.json");

    String expected = "Missing recipes array in JSON";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testMissingRecipIngred() {
    MapGrid mapGrid = new MapGrid();
    ModelManager modelManager = new ModelManager(mapGrid);
    ModelConstructor c = new ModelConstructor(modelManager, mapGrid);
    String resourcePath = getResourcePath("inputs/missing_recipe_ingredients.json");

    String expected = "Missing ingredients of recipe door in JSON";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  // TYPE VALIDATION!!!!!
  @Test
  public void testTypeNameApostrophe() {
    MapGrid mapGrid = new MapGrid();
    ModelManager modelManager = new ModelManager(mapGrid);
    ModelConstructor c = new ModelConstructor(modelManager, mapGrid);
    String resourcePath = getResourcePath("inputs/invalid_type_name.json");

    String expected = "Type name 'door's type' contains an apostrophe which is not allowed";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testDuplicateName() {
    MapGrid mapGrid = new MapGrid();
    ModelManager modelManager = new ModelManager(mapGrid);
    ModelConstructor c = new ModelConstructor(modelManager, mapGrid);
    String resourcePath = getResourcePath("inputs/duplicate_type_name.json");

    String expected = "Type name 'door' is not unique";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testUndefinedRecip_Type() {
    MapGrid mapGrid = new MapGrid();
    ModelManager modelManager = new ModelManager(mapGrid);
    ModelConstructor c = new ModelConstructor(modelManager, mapGrid);
    String resourcePath = getResourcePath("inputs/undefined_type_recipe.json");

    String expected = "Recipe of type door not found: undefined_recipe";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testNoIngredTypeRecip() {
    MapGrid mapGrid = new MapGrid();
    ModelManager modelManager = new ModelManager(mapGrid);
    ModelConstructor c = new ModelConstructor(modelManager, mapGrid);
    String resourcePath = getResourcePath("inputs/no_ingredients_type_recipe.json");

    String expected = "Recipe 'wood' used in type 'doorType' must have at least one ingredient for factories";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testMissingRecip_type() {
    MapGrid mapGrid = new MapGrid();
    ModelManager modelManager = new ModelManager(mapGrid);
    ModelConstructor c = new ModelConstructor(modelManager, mapGrid);
    String resourcePath = getResourcePath("inputs/missing_type_recipes.json");

    String expected = "Missing recipes array of type door in JSON";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testmissingTypeName() {
    MapGrid mapGrid = new MapGrid();
    ModelManager modelManager = new ModelManager(mapGrid);
    ModelConstructor c = new ModelConstructor(modelManager, mapGrid);
    String resourcePath = getResourcePath("inputs/missing_type_name.json");

    assertThrows(
        Exception.class,
        () -> c.constructFromJsonFile(resourcePath));
  }

  @Test
  public void testMissingTypesArr() {
    MapGrid mapGrid = new MapGrid();
    ModelManager modelManager = new ModelManager(mapGrid);
    ModelConstructor c = new ModelConstructor(modelManager, mapGrid);
    String resourcePath = getResourcePath("inputs/missing_types_array.json");

    String expected = "Missing types array in JSON";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testParseJsonToObj() throws IOException {
    MapGrid mapGrid = new MapGrid();
    ModelManager modelManager = new ModelManager(mapGrid);
    ModelConstructor c = new ModelConstructor(modelManager, mapGrid);

    Path temp = Files.createTempFile("invalid", ".json");
    Files.writeString(temp, "{invalid JSON}");

    assertThrows(
        JsonParseException.class,
        () -> c.constructFromJsonFile(temp.toString()));

    Files.deleteIfExists(temp);
  }

  // Buildingv VALIDATION!!!!!
  @Test
  public void testBuildingApostrophe() {
    MapGrid mapGrid = new MapGrid();
    ModelManager modelManager = new ModelManager(mapGrid);
    ModelConstructor c = new ModelConstructor(modelManager, mapGrid);
    String resourcePath = getResourcePath("inputs/invalid_building_name.json");

    String expected = "Building name 'door's factory' contains an apostrophe which is not allowed";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testDuplicateBuildingName() {
    MapGrid mapGrid = new MapGrid();
    ModelManager modelManager = new ModelManager(mapGrid);
    ModelConstructor c = new ModelConstructor(modelManager, mapGrid);
    String resourcePath = getResourcePath("inputs/duplicate_building_name.json");

    String expected = "Building name 'doorFactory' is not unique";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void test_Mine_WithSources() {
    MapGrid mapGrid = new MapGrid();
    ModelManager modelManager = new ModelManager(mapGrid);
    ModelConstructor c = new ModelConstructor(modelManager, mapGrid);
    String resourcePath = getResourcePath("inputs/mine_with_sources.json");

    String expected = "Sources must be empty for mine type woodMine";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testMineRecipe_Ingred() {
    MapGrid mapGrid = new MapGrid();
    ModelManager modelManager = new ModelManager(mapGrid);
    ModelConstructor c = new ModelConstructor(modelManager, mapGrid);
    String resourcePath = getResourcePath("inputs/mine_recipe_with_ingredients.json");

    String expected = "Mine recipe 'door' for building 'doorMine' must have no ingredients";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testUndefinedBuildType() {
    MapGrid mapGrid = new MapGrid();
    ModelManager modelManager = new ModelManager(mapGrid);
    ModelConstructor c = new ModelConstructor(modelManager, mapGrid);
    String resourcePath = getResourcePath("inputs/undefined_building_type.json");

    String expected = "Type of building doorFactory not found: undefined_type";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testUndefinedSourceBuild() {
    MapGrid mapGrid = new MapGrid();
    ModelManager modelManager = new ModelManager(mapGrid);
    ModelConstructor c = new ModelConstructor(modelManager, mapGrid);
    String resourcePath = getResourcePath("inputs/undefined_source_building.json");

    String expected = "Source building undefined_source not found: undefined_source";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testBuildingFactory_Mine() {
    MapGrid mapGrid = new MapGrid();
    ModelManager modelManager = new ModelManager(mapGrid);
    ModelConstructor c = new ModelConstructor(modelManager, mapGrid);
    String resourcePath = getResourcePath("inputs/building_both_factory_and_mine.json");

    String expected = "Building doorFactoryMine must be either a factory, mine, or storage";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testBuildingNoFactory_mine() {
    MapGrid mapGrid = new MapGrid();
    ModelManager modelManager = new ModelManager(mapGrid);
    ModelConstructor c = new ModelConstructor(modelManager, mapGrid);
    String resourcePath = getResourcePath("inputs/building_neither_factory_nor_mine.json");

    String expected = "Building doorNothing must be either a factory, mine, or storage";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testMissingBuildingsAr() {
    MapGrid mapGrid = new MapGrid();
    ModelManager modelManager = new ModelManager(mapGrid);
    ModelConstructor c = new ModelConstructor(modelManager, mapGrid);
    String resourcePath = getResourcePath("inputs/missing_buildings_array.json");

    String expected = "Missing buildings array in JSON";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testFactory_Source_Ingred() {
    MapGrid mapGrid = new MapGrid();
    ModelManager modelManager = new ModelManager(mapGrid);
    ModelConstructor c = new ModelConstructor(modelManager, mapGrid);
    String resourcePath = getResourcePath("inputs/factory_missing_ingredient_source.json");

    String expected = "Factory 'doorFactory' cannot get ingredient 'metal' from any of its sources";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void test_toString() {
    MapGrid mapGrid = new MapGrid();
    ModelManager modelManager = new ModelManager(mapGrid);
    Recipe wood = new Recipe("wood", 1);
    modelManager.addRecipe(wood);

    ArrayList<Recipe> recipes = new ArrayList<>();
    recipes.add(wood);
    Type woodType = new Type("woodType", recipes);
    modelManager.addType(woodType);
    Mine woodMine = new Mine("woodMine", wood);
    modelManager.addBuilding(woodMine);
    ModelConstructor c = new ModelConstructor(modelManager, mapGrid);

    String expected = "Types:\n" +
        "Type: { woodType, { wood } }\n" +
        "\n" +
        "Recipes:\n" +
        "Recipe: { wood, 1, { } }\n" +
        "\n" +
        "Buildings:\n" +
        "Mine: { woodMine, wood }\n";

    assertEquals(expected, c.toString());
  }

}
