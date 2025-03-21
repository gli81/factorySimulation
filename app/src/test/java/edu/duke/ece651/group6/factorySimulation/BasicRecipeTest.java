package edu.duke.ece651.group6.factorySimulation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class BasicRecipeTest {
  @Test
  public void test_constructorGetters() {
    Map<String, Integer> ingredients = new HashMap<>();
    ingredients.put("wood", 2);
    ingredients.put("metal", 3);
    
    BasicRecipe recipe = new BasicRecipe("table", ingredients, 5);
    
    assertEquals("table", recipe.getOutput());
    assertEquals(5, recipe.getLatency());
    int count = 0;
    for (Map.Entry<String, Integer> entry : recipe.getAllIngredients()) {
      if (entry.getKey().equals("wood")) {
        assertEquals(2, entry.getValue());
        count++;
      } else if (entry.getKey().equals("metal")) {
        assertEquals(3, entry.getValue());
        count++;
      }
    }
    assertEquals(2, count);
  }

  @Test
  public void testInvalidOutputName() {
    Map<String, Integer> ingredients = new HashMap<>();
    ingredients.put("iron", 1);
    
    // Test with null output
    assertThrows(IllegalArgumentException.class, () -> new BasicRecipe(null, ingredients, 2));
    
    // Test with empty output
    assertThrows(IllegalArgumentException.class, () -> new BasicRecipe("", ingredients, 2));
  }

 @Test
  public void testInvalidLatency() {
    Map<String, Integer> ingredients = new HashMap<>();
    ingredients.put("iron", 1);
    
    assertThrows(IllegalArgumentException.class, () -> new BasicRecipe("sword", ingredients, 0));
    
    assertThrows(IllegalArgumentException.class, () -> new BasicRecipe("sword", ingredients, -1));
  }

  @Test
  public void testToString() {
    Map<String, Integer> ingredients = new HashMap<>();
    ingredients.put("iron", 2);
    ingredients.put("coal", 1);
    
    BasicRecipe recipe = new BasicRecipe("steel", ingredients, 6);
    
    String toString = recipe.toString();
    assertTrue(toString.contains("Output: steel"));
    assertTrue(toString.contains("Ingredients: {"));
    assertTrue(toString.contains("iron=2"));
    assertTrue(toString.contains("coal=1"));
    assertTrue(toString.contains("Latency: 6"));
  }
  
}
