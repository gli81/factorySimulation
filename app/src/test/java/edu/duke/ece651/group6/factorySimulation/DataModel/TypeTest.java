package edu.duke.ece651.group6.factorySimulation.DataModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class TypeTest {
  @Test
  public void test() {
    Recipe wood = new Recipe("wood", 1);
    Recipe metal = new Recipe("metal", 2);
    ArrayList<Recipe> recipes = new ArrayList<>();
    recipes.add(wood);
    recipes.add(metal);
    Type doorType = new Type("doorType", recipes);
    assertEquals("doorType", doorType.getName());
  }

  @Test
  public void test_getRecipesIterr() {
    Recipe wood = new Recipe("wood", 1);
    Recipe metal = new Recipe("metal", 2);
    ArrayList<Recipe> r = new ArrayList<>();
    r.add(wood);
    r.add(metal);

    Type doorType = new Type("doorType", r);
    int count = 0;
    for (Recipe rec : doorType.getRecipesIterable()) {
      count++;
      assertTrue(rec.equals(wood) || rec.equals(metal));
    }
    assertEquals(2, count);
    ArrayList<Recipe> retRecipes = new ArrayList<>();
    doorType.getRecipesIterable().forEach(recipe -> retRecipes.add(recipe));
    assertEquals(2, retRecipes.size());
    retRecipes.add(new Recipe("glass", 3));
    count = 0;
    for (Recipe recp : doorType.getRecipesIterable()) {
      count++;
    }
    assertEquals(2, count);
  }

  @Test
  public void test_isRecipeSupported() {
    Recipe wood = new Recipe("wood", 1);
    Recipe metal = new Recipe("metal", 2);
    Recipe glass = new Recipe("glass", 3);
    ArrayList<Recipe> r = new ArrayList<>();
    r.add(wood);
    r.add(metal);
    Type doorType = new Type("doorType", r);
    assertTrue(doorType.isRecipeSupported(wood));
    assertTrue(doorType.isRecipeSupported(metal));
    assertFalse(doorType.isRecipeSupported(glass));
  }

  @Test
  public void test_toStr() {
    Recipe wood = new Recipe("wood", 1);
    Recipe metal = new Recipe("metal", 2);
    ArrayList<Recipe> r = new ArrayList<>();
    r.add(wood);
    r.add(metal);
    Type doorType = new Type("doorType", r);
    String e = "Type: { doorType, { wood, metal } }\n";
    assertEquals(e, doorType.toString());
    Type emptyType = new Type("emptyType", new ArrayList<>());
    e = "Type: { emptyType, { } }\n";
    assertEquals(e, emptyType.toString());
  }

  @Test
  public void test_equalsashCode() {
    Recipe w = new Recipe("wood", 1);
    Recipe m = new Recipe("metal", 2);
    Recipe glass = new Recipe("glass", 3);
    ArrayList<Recipe> recipes1 = new ArrayList<>();
    recipes1.add(w);
    recipes1.add(m);

    ArrayList<Recipe> recipes2 = new ArrayList<>();
    recipes2.add(w);
    recipes2.add(m);

    ArrayList<Recipe> recipes3 = new ArrayList<>();
    recipes3.add(w);
    recipes3.add(glass);
    Type doorType1 = new Type("doorType", recipes1);
    Type doorType2 = new Type("doorType", recipes2);
    Type doorType3 = new Type("doorType", recipes3);
    Type wType = new Type("windowType", recipes1);
    assertTrue(doorType1.equals(doorType1));
    assertFalse(doorType1.equals(null));
    assertFalse(doorType1.equals(new Object()));
    assertTrue(doorType1.equals(doorType3));
    assertFalse(doorType1.equals(wType));
    assertTrue(doorType1.equals(doorType2));
    assertEquals(doorType1.hashCode(), doorType2.hashCode());
    assertEquals(doorType1.hashCode(), doorType3.hashCode());
    assertNotEquals(doorType1.hashCode(), wType.hashCode());
  }

  @Test
  public void test_getIterritem() {
    Recipe wood = new Recipe("wood", 1);
    ArrayList<Recipe> sRec = new ArrayList<>();
    sRec.add(wood);
    Type sType = new Type("singleType", sRec);
    int count = 0;
    for (Recipe r : sType.getRecipesIterable()) {
      count++;
    }
    assertEquals(1, count);
  }

  @Test
  public void test_emptyRecipes() {
    ArrayList<Recipe> emptyRec = new ArrayList<>();
    Type emptyType = new Type("emptyType", emptyRec);
    assertEquals("emptyType", emptyType.getName());
    assertEquals(0, ((ArrayList<Recipe>) emptyType.getRecipesIterable()).size());
    assertEquals("Type: { emptyType, { } }\n", emptyType.toString());
  }
}
