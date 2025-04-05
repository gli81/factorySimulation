package edu.duke.ece651.group6.factorySimulation.DataModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class RecipeTest {

  @Test
  public void test_Constructor() {
    Recipe r = new Recipe("wood", 3);
    assertEquals("wood", r.getName());
    assertEquals(3, r.getLatency());
  }

  @Test
  public void test_addIngred_Iterable() {
    Recipe main = new Recipe("table", 5);
    Recipe wood = new Recipe("wood", 1);
    Recipe m = new Recipe("metal", 2);
    main.addIngredient(wood, 4);
    main.addIngredient(m, 2);
    boolean foundWood = false;
    boolean foundMetal = false;
    int count = 0;
    int woodQy = 0;
    int metalQy = 0;
    for (Map.Entry<Recipe, Integer> ingredient : main.getIngredientsIterable()) {
      count++;
      Recipe key = ingredient.getKey();
      if (key.equals(wood)) {
        foundWood = true;
        woodQy = ingredient.getValue();
      }

      if (key.equals(m)) {
        foundMetal = true;
        metalQy = ingredient.getValue();
      }
    }

    assertEquals(2, count);
    assertTrue(foundWood);
    assertTrue(foundMetal);
    assertEquals(4, woodQy);
    assertEquals(2, metalQy);
    boolean foundIngred = false;
    Recipe glass = new Recipe("glass", 3);
    for (Map.Entry<Recipe, Integer> entry : main.getIngredientsIterable()) {
      foundIngred = true;
      break;
    }
    assertTrue(foundIngred);
    count = 0;
    for (Map.Entry<Recipe, Integer> i : main.getIngredientsIterable()) {
      count++;
    }
    assertEquals(2, count);
  }

  @Test
  public void test_toStr() {
    Recipe emptyRecip = new Recipe("empty", 1);
    String e = "Recipe: { empty, 1, { } }\n";
    assertEquals(e, emptyRecip.toString());

    Recipe complexRec = new Recipe("table", 5);
    Recipe wood = new Recipe("wood", 1);
    Recipe metal = new Recipe("metal", 2);

    complexRec.addIngredient(wood, 4);
    complexRec.addIngredient(metal, 2);

    String r = complexRec.toString();
    assertTrue(r.contains("table"));
    assertTrue(r.contains("5"));
    assertTrue(r.contains("wood: 4"));
    assertTrue(r.contains("metal: 2"));
  }

  @Test
  public void test_equalsHashCode() {
    Recipe wood1 = new Recipe("wood", 3);
    Recipe wood2 = new Recipe("wood", 3);
    Recipe wood3 = new Recipe("wood", 4);
    Recipe m = new Recipe("metal", 3);
    assertTrue(wood1.equals(wood1));
    assertFalse(wood1.equals(null));
    assertFalse(wood1.equals(new Object()));
    assertTrue(wood1.equals(wood2));
    assertFalse(wood1.equals(wood3));
    assertFalse(wood1.equals(m));
    assertEquals(wood1.hashCode(), wood2.hashCode());
    assertNotEquals(wood1.hashCode(), wood3.hashCode());
    assertNotEquals(wood1.hashCode(), m.hashCode());
  }

  @Test
  public void test_equalsName() {
    Recipe null1 = new Recipe(null, 3);
    Recipe null2 = new Recipe(null, 3);
    Recipe null3 = new Recipe(null, 4);
    Recipe woodRecipe = new Recipe("wood", 3);
    assertTrue(null1.equals(null1));
    assertTrue(null1.equals(null2));
    assertFalse(null1.equals(null3));
    assertFalse(null1.equals(woodRecipe));
    assertFalse(woodRecipe.equals(null1));

    int hashCode = null1.hashCode();
    assertEquals(null2.hashCode(), hashCode);
    assertNotEquals(null3.hashCode(), hashCode);
  }

  @Test
  public void test_toStrempty_ingred() {
    Recipe recipe = new Recipe("empty", 1);
    String r = recipe.toString();
    assertEquals("Recipe: { empty, 1, { } }\n", r);
  }

  @Test
  public void test_toStrsingle_ingred() {
    Recipe recipe = new Recipe("simple", 2);
    Recipe i = new Recipe("wood", 1);
    recipe.addIngredient(i, 3);

    String r = recipe.toString();
    assertEquals("Recipe: { simple, 2, { wood: 3 } }\n", r);
  }

  @Test
  public void test_toStrmulIngred() {
    Recipe r = new Recipe("complex", 5);
    Recipe w = new Recipe("wood", 1);
    Recipe m = new Recipe("metal", 2);
    Recipe glass = new Recipe("glass", 3);

    r.addIngredient(w, 4);
    r.addIngredient(m, 2);
    r.addIngredient(glass, 1);
    String res = r.toString();
    assertTrue(res.contains("complex"));
    assertTrue(res.contains("5"));
    assertTrue(res.contains("wood: 4"));
    assertTrue(res.contains("metal: 2"));
    assertTrue(res.contains("glass: 1"));
  }

  @Test
  public void test_overwriteIngred() {
    Recipe r = new Recipe("chair", 4);
    Recipe w = new Recipe("wood", 1);
    r.addIngredient(w, 2);
    Map<Recipe, Integer> i = new HashMap<>();
    r.getIngredientsIterable().forEach(entry -> i.put(entry.getKey(), entry.getValue()));
    assertEquals(2, i.get(w));
    r.addIngredient(w, 5);
    i.clear();
    r.getIngredientsIterable().forEach(entry -> i.put(entry.getKey(), entry.getValue()));
    assertEquals(5, i.get(w));
  }

}
