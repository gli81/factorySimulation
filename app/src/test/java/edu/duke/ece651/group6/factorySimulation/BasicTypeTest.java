package edu.duke.ece651.group6.factorySimulation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class BasicTypeTest {
  @Test
  public void test_constructorGetters() {
    List<String> r = new ArrayList<>();
    r.add("door");
    r.add("window");
    r.add("chair");
    
    BasicType type = new BasicType("furniture", r);
    assertEquals("furniture", type.getName());

    List<String> actualRecipes = new ArrayList<>();
    for (String recipe : type.getRecipes()) {
      actualRecipes.add(recipe);
    }
    
    assertEquals(3, actualRecipes.size());
    assertTrue(actualRecipes.contains("door"));
    assertTrue(actualRecipes.contains("window"));
    assertTrue(actualRecipes.contains("chair"));
  }

  @Test
  public void testInvalidName() {
    List<String> r = new ArrayList<>();
    r.add("bolt");
    r.add("nail");
    assertThrows(IllegalArgumentException.class, () -> new BasicType(null, r));
    assertThrows(IllegalArgumentException.class, () -> new BasicType("", r));
  }

  @Test
  public void test_nullRecipes() {
    BasicType type = new BasicType("metal", null); 
    int count = 0;
    for (String r : type.getRecipes()) {
      count++;
    }
    assertEquals(0, count);
  }

  @Test
  public void testString() {
    List<String> r = new ArrayList<>();
    r.add("door");
    r.add("window");
    
    BasicType type = new BasicType("furniture", r);
    String toString = type.toString();
    assertTrue(toString.contains("Type: furniture"));
    assertTrue(toString.contains("Recipes: [door, window]"));
  }
}
