package edu.duke.ece651.group6.factorySimulation.DataModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.duke.ece651.group6.factorySimulation.ProductionController;

public class ModelManagerTest {
  private ModelManager modelManager;
  private ModelConstructor c;

  private String capsystem_Output(Runnable runnable) {
    PrintStream out = System.out;
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    System.setOut(printStream);
    try {
      runnable.run();
      return outputStream.toString();
    } finally {
      System.setOut(out);
    }
  }

  @BeforeEach
  public void model() throws IOException {
    ProductionController.setVerbose(2);
    ProductionController.resetTimeStep();
    ProductionController.resetCurrRequestIndex();

    modelManager = new ModelManager();
    c = new ModelConstructor(modelManager);
  }

  @Test
  public void testAddUserRequest1() throws IOException {
    this.c.constructFromJsonFile("src/resources/inputs/doors1.json");

    Runnable runnable = () -> modelManager.addUserRequest("door", "D");
    String output = capsystem_Output(runnable);
    assertEquals(1, modelManager.getUserRequestQueueSize());

    String expectedOutput = "[source selection]: D has request for door on 0\n" +
        "[D:door:0] For ingredient wood\n" +
        "    W:0\n" +
        "    Selecting W\n" +
        "[ingredient assignment]: wood assigned to W to deliver to D\n" +
        "[D:door:1] For ingredient handle\n" +
        "    Ha:0\n" +
        "    Selecting Ha\n" +
        "[ingredient assignment]: handle assigned to Ha to deliver to D\n" +
        "[source selection]: Ha has request for handle on 0\n" +
        "[Ha:handle:0] For ingredient metal\n" +
        "    M:0\n" +
        "    Selecting M\n" +
        "[ingredient assignment]: metal assigned to M to deliver to Ha\n" +
        "[D:door:2] For ingredient hinge\n" +
        "    Hi:0\n" +
        "    Selecting Hi\n" +
        "[ingredient assignment]: hinge assigned to Hi to deliver to D\n" +
        "[source selection]: Hi has request for hinge on 0\n" +
        "[Hi:hinge:0] For ingredient metal\n" +
        "    M:1\n" +
        "    Selecting M\n" +
        "[ingredient assignment]: metal assigned to M to deliver to Hi\n" +
        "[D:door:3] For ingredient hinge\n" +
        "    Hi:1\n" +
        "    Selecting Hi\n" +
        "[ingredient assignment]: hinge assigned to Hi to deliver to D\n" +
        "[source selection]: Hi has request for hinge on 0\n" +
        "[Hi:hinge:0] For ingredient metal\n" +
        "    M:2\n" +
        "    Selecting M\n" +
        "[ingredient assignment]: metal assigned to M to deliver to Hi\n" +
        "[D:door:4] For ingredient hinge\n" +
        "    Hi:2\n" +
        "    Selecting Hi\n" +
        "[ingredient assignment]: hinge assigned to Hi to deliver to D\n" +
        "[source selection]: Hi has request for hinge on 0\n" +
        "[Hi:hinge:0] For ingredient metal\n" +
        "    M:3\n" +
        "    Selecting M\n" +
        "[ingredient assignment]: metal assigned to M to deliver to Hi\n";
    assertEquals(expectedOutput, output);

  }

  @Test
  public void testAddUserRequest2() throws IOException {
    this.c.constructFromJsonFile("src/resources/inputs/doors2.json");

    Runnable runnable = () -> modelManager.addUserRequest("door", "D");
    String output = capsystem_Output(runnable);

    String expectedOutput = "[source selection]: D has request for door on 0\n" +
        "[D:door:0] For ingredient wood\n" +
        "    W:0\n" +
        "    Selecting W\n" +
        "[ingredient assignment]: wood assigned to W to deliver to D\n" +
        "[D:door:1] For ingredient handle\n" +
        "    Hw1:0\n" +
        "    Hw2:0\n" +
        "    Selecting Hw1\n" +
        "[ingredient assignment]: handle assigned to Hw1 to deliver to D\n" +
        "[source selection]: Hw1 has request for handle on 0\n" +
        "[Hw1:handle:0] For ingredient metal\n" +
        "    M1:0\n" +
        "    M2:0\n" +
        "    Selecting M1\n" +
        "[ingredient assignment]: metal assigned to M1 to deliver to Hw1\n" +
        "[D:door:2] For ingredient hinge\n" +
        "    Hw1:1\n" +
        "    Hw2:0\n" +
        "    Selecting Hw2\n" +
        "[ingredient assignment]: hinge assigned to Hw2 to deliver to D\n" +
        "[source selection]: Hw2 has request for hinge on 0\n" +
        "[Hw2:hinge:0] For ingredient metal\n" +
        "    M1:1\n" +
        "    M2:0\n" +
        "    Selecting M2\n" +
        "[ingredient assignment]: metal assigned to M2 to deliver to Hw2\n" +
        "[D:door:3] For ingredient hinge\n" +
        "    Hw1:1\n" +
        "    Hw2:1\n" +
        "    Selecting Hw1\n" +
        "[ingredient assignment]: hinge assigned to Hw1 to deliver to D\n" +
        "[source selection]: Hw1 has request for hinge on 0\n" +
        "[Hw1:hinge:0] For ingredient metal\n" +
        "    M1:1\n" +
        "    M2:1\n" +
        "    Selecting M1\n" +
        "[ingredient assignment]: metal assigned to M1 to deliver to Hw1\n" +
        "[D:door:4] For ingredient hinge\n" +
        "    Hw1:2\n" +
        "    Hw2:1\n" +
        "    Selecting Hw2\n" +
        "[ingredient assignment]: hinge assigned to Hw2 to deliver to D\n" +
        "[source selection]: Hw2 has request for hinge on 0\n" +
        "[Hw2:hinge:0] For ingredient metal\n" +
        "    M1:2\n" +
        "    M2:1\n" +
        "    Selecting M2\n" +
        "[ingredient assignment]: metal assigned to M2 to deliver to Hw2\n";

    assertEquals(expectedOutput, output);
  }

  @Test
  public void testallTypeIterable() {
    Type doorType = new Type("doorType", new ArrayList<>());
    Type windType = new Type("windowType", new ArrayList<>());
    modelManager.addType(doorType);
    modelManager.addType(windType);
    int count = 0;
    for (Type t : modelManager.getAllTypesIterable()) {
      count++;
      assertTrue(t.equals(doorType) || t.equals(windType));
    }
    assertEquals(2, count);
    ArrayList<Type> typeList = new ArrayList<>();
    modelManager.getAllTypesIterable().forEach(type -> typeList.add(type));
    assertEquals(2, typeList.size());

    Type chair = new Type("chairType", new ArrayList<>());
    modelManager.addType(chair);
    assertEquals(2, typeList.size());
    count = 0;
    for (Type t : modelManager.getAllTypesIterable()) {
      count++;
    }
    assertEquals(3, count);
  }

  @Test
  public void testaddUser_requestExp() {
    Recipe w = new Recipe("wood", 1);
    Recipe door = new Recipe("door", 5);
    door.addIngredient(w, 2);
    Type mineType = new Type("mineType", new ArrayList<>());
    Type doorType = new Type("doorType", new ArrayList<>());
    ArrayList<Recipe> doorRecipes = new ArrayList<>();
    doorRecipes.add(door);
    Type factoryType = new Type("factoryType", doorRecipes);
    Mine woodMine = new Mine("woodMine", w);
    Factory doorF = new Factory("doorFactory", factoryType);
    doorF.addSource(woodMine);

    modelManager.addRecipe(w);
    modelManager.addRecipe(door);
    modelManager.addType(mineType);
    modelManager.addType(doorType);
    modelManager.addType(factoryType);
    modelManager.addBuilding(woodMine);
    modelManager.addBuilding(doorF);
    InvalidInputException e1 = assertThrows(InvalidInputException.class, () -> {
      modelManager.addUserRequest("nonexistentRecipe", "doorFactory");
    });
    assertEquals("Recipe not found: nonexistentRecipe", e1.getMessage());

    InvalidInputException ex2 = assertThrows(InvalidInputException.class, () -> {
      modelManager.addUserRequest("door", "nonexistentBuilding");
    });
    assertEquals("Building not found: nonexistentBuilding", ex2.getMessage());

    ArrayList<Recipe> empRecipes = new ArrayList<>();
    Type empType = new Type("emptyType", empRecipes);
    Factory empFactory = new Factory("emptyFactory", empType);
    modelManager.addType(empType);
    modelManager.addBuilding(empFactory);
    InvalidInputException e3 = assertThrows(InvalidInputException.class, () -> {
      modelManager.addUserRequest("door", "emptyFactory");
    });
    assertEquals("Building emptyFactory does not support recipe: door", e3.getMessage());
  }

  @Test
  public void teststr() {
    Recipe wood = new Recipe("wood", 1);
    Type mineType = new Type("mineType", new ArrayList<>());
    Mine woodMine = new Mine("woodMine", wood);
    modelManager.addRecipe(wood);
    modelManager.addType(mineType);
    modelManager.addBuilding(woodMine);
    String r = modelManager.toString();
    assertTrue(r.contains("Types:"));
    assertTrue(r.contains("Recipes:"));
    assertTrue(r.contains("Buildings:"));
    assertTrue(r.contains("mineType"));
    assertTrue(r.contains("wood"));
    assertTrue(r.contains("woodMine"));
  }

  /*
   * @Test
   * public void testProcessOneTimeStep() throws IOException {
   * 
   * Recipe wood = new Recipe("wood", 1);
   * Recipe door = new Recipe("door", 2);
   * door.addIngredient(wood, 1);
   * 
   * ArrayList<Recipe> doorRecipes = new ArrayList<>();
   * doorRecipes.add(door);
   * Type factoryType = new Type("factoryType", doorRecipes);
   * 
   * Mine woodMine = new Mine("woodMine", wood);
   * Factory doorFactory = new Factory("doorFactory", factoryType);
   * doorFactory.addSource(woodMine);
   * 
   * modelManager.addRecipe(wood);
   * modelManager.addRecipe(door);
   * modelManager.addType(factoryType);
   * modelManager.addBuilding(woodMine);
   * modelManager.addBuilding(doorFactory);
   * 
   * modelManager.addUserRequest("door", "doorFactory");
   * 
   * String output = captureSystemOut(() -> {
   * ProductionController.currTimeStep++;
   * modelManager.processOneTimeStep();
   * 
   * ProductionController.currTimeStep++;
   * modelManager.processOneTimeStep();
   * 
   * // Process third step (doorFactory completes door)
   * ProductionController.currTimeStep++;
   * modelManager.processOneTimeStep();
   * });
   * 
   * assertTrue(output.contains("[order complete] Order"));
   * 
   * assertEquals(0, modelManager.getUserRequestQueueSize());
   * }
   */

}
