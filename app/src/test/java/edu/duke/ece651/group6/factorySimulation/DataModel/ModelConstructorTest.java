package edu.duke.ece651.group6.factorySimulation.DataModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.net.URL;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import com.google.gson.JsonParseException;
import org.junit.jupiter.api.Test;
import java.net.URL;

public class ModelConstructorTest {

  private String getResourcePath(String resourcePath) {
    URL url = getClass().getClassLoader().getResource(resourcePath);
    System.out.println("Finding file: " + resourcePath);
    System.out.println("URL wasn't found: " + url);
    return url.getPath();
  }

  @Test
  public void testConstructor1() throws IOException {
    ModelManager modelManager = new ModelManager();
    ModelConstructor constructor = new ModelConstructor(modelManager);
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
    ModelManager modelManager = new ModelManager();
    ModelConstructor constructor = new ModelConstructor(modelManager);
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
        () -> c.constructFromJsonFile(resourcePath));

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
        () -> c.constructFromJsonFile(resourcePath));

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
        () -> c.constructFromJsonFile(resourcePath));

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
        () -> c.constructFromJsonFile(resourcePath));

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
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testMissingRecipOutput() {
    ModelManager m = new ModelManager();
    ModelConstructor c = new ModelConstructor(m);
    String resourcePath = getResourcePath("inputs/missing_recipe_output.json");

    assertThrows(
        Exception.class,
        () -> c.constructFromJsonFile(resourcePath));
  }

  @Test
  public void testMissingRecipArr() {
    ModelManager m = new ModelManager();
    ModelConstructor c = new ModelConstructor(m);
    String resourcePath = getResourcePath("inputs/missing_recipes_array.json");

    String expected = "Missing recipes array in JSON";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

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
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  // TYPE VALIDATION!!!!!
  @Test
  public void testTypeNameApostrophe() {
    ModelManager m = new ModelManager();
    ModelConstructor c = new ModelConstructor(m);
    String resourcePath = getResourcePath("inputs/invalid_type_name.json");

    String expected = "Type name 'door's type' contains an apostrophe which is not allowed";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testDuplicateName() {
    ModelManager m = new ModelManager();
    ModelConstructor c = new ModelConstructor(m);
    String resourcePath = getResourcePath("inputs/duplicate_type_name.json");

    String expected = "Type name 'door' is not unique";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testUndefinedRecip_Type() {
    ModelManager m = new ModelManager();
    ModelConstructor c = new ModelConstructor(m);
    String resourcePath = getResourcePath("inputs/undefined_type_recipe.json");

    String expected = "Recipe of type door not found: undefined_recipe";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testNoIngredTypeRecip() {
    ModelManager m = new ModelManager();
    ModelConstructor c = new ModelConstructor(m);
    String resourcePath = getResourcePath("inputs/no_ingredients_type_recipe.json");

    String expected = "Recipe 'wood' used in type 'doorType' must have at least one ingredient for factories";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testMissingRecip_type() {
    ModelManager m = new ModelManager();
    ModelConstructor c = new ModelConstructor(m);
    String resourcePath = getResourcePath("inputs/missing_type_recipes.json");

    String expected = "Missing recipes array of type door in JSON";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testmissingTypeName() {
    ModelManager m = new ModelManager();
    ModelConstructor c = new ModelConstructor(m);
    String resourcePath = getResourcePath("inputs/missing_type_name.json");

    assertThrows(
        Exception.class,
        () -> c.constructFromJsonFile(resourcePath));
  }

  @Test
  public void testMissingTypesArr() {
    ModelManager m = new ModelManager();
    ModelConstructor c = new ModelConstructor(m);
    String resourcePath = getResourcePath("inputs/missing_types_array.json");

    String expected = "Missing types array in JSON";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testParseJsonToObj() throws IOException {
    ModelManager m = new ModelManager();
    ModelConstructor c = new ModelConstructor(m);

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
    ModelManager m = new ModelManager();
    ModelConstructor c = new ModelConstructor(m);
    String resourcePath = getResourcePath("inputs/invalid_building_name.json");

    String expected = "Building name 'door's factory' contains an apostrophe which is not allowed";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testDuplicateBuildingName() {
    ModelManager m = new ModelManager();
    ModelConstructor c = new ModelConstructor(m);
    String resourcePath = getResourcePath("inputs/duplicate_building_name.json");

    String expected = "Building name 'doorFactory' is not unique";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void test_Mine_WithSources() {
    ModelManager m = new ModelManager();
    ModelConstructor c = new ModelConstructor(m);
    String resourcePath = getResourcePath("inputs/mine_with_sources.json");

    String expected = "Sources must be empty for mine type woodMine";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testMineRecipe_Ingred() {
    ModelManager m = new ModelManager();
    ModelConstructor c = new ModelConstructor(m);
    String resourcePath = getResourcePath("inputs/mine_recipe_with_ingredients.json");

    String expected = "Mine recipe 'door' for building 'doorMine' must have no ingredients";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testUndefinedBuildType() {
    ModelManager m = new ModelManager();
    ModelConstructor c = new ModelConstructor(m);
    String resourcePath = getResourcePath("inputs/undefined_building_type.json");

    String expected = "Type of building doorFactory not found: undefined_type";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testUndefinedSourceBuild() {
    ModelManager m = new ModelManager();
    ModelConstructor c = new ModelConstructor(m);
    String resourcePath = getResourcePath("inputs/undefined_source_building.json");

    String expected = "Source building undefined_source not found: undefined_source";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testBuildingFactory_Mine() {
    ModelManager m = new ModelManager();
    ModelConstructor c = new ModelConstructor(m);
    String resourcePath = getResourcePath("inputs/building_both_factory_and_mine.json");

    String expected = "Building doorFactoryMine cannot be both a factory and a mine";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testBuildingNoFactory_mine() {
    ModelManager m = new ModelManager();
    ModelConstructor c = new ModelConstructor(m);
    String resourcePath = getResourcePath("inputs/building_neither_factory_nor_mine.json");

    String expected = "Building doorNothing must be either a factory or a mine";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testMissingBuildingsAr() {
    ModelManager mm = new ModelManager();
    ModelConstructor c = new ModelConstructor(mm);
    String resourcePath = getResourcePath("inputs/missing_buildings_array.json");

    String expected = "Missing buildings array in JSON";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testFactory_Source_Ingred() {
    ModelManager m = new ModelManager();
    ModelConstructor c = new ModelConstructor(m);
    String resourcePath = getResourcePath("inputs/factory_missing_ingredient_source.json");

    String expected = "Factory 'doorFactory' cannot get ingredient 'metal' from any of its sources";

    InvalidInputException e = assertThrows(
        InvalidInputException.class,
        () -> c.constructFromJsonFile(resourcePath));

    assertEquals(expected, e.getMessage());
  }

  @Test
  public void test_toString() {
    ModelManager m = new ModelManager();
    Recipe wood = new Recipe("wood", 1);
    m.addRecipe(wood);

    ArrayList<Recipe> recipes = new ArrayList<>();
    recipes.add(wood);
    Type woodType = new Type("woodType", recipes);
    m.addType(woodType);
    Mine woodMine = new Mine("woodMine", wood);
    m.addBuilding(woodMine);
    ModelConstructor c = new ModelConstructor(m);

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
