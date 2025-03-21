package edu.duke.ece651.group6.factorySimulation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class NameApostropheCheckerTest {
  @Test
  public void test_validName() {
    Map<String, Integer> ingred = new HashMap<>();
    Recipe validRecip = new BasicRecipe("wood", ingred, 1);
 
    NameApostropheChecker checker = new NameApostropheChecker(null);
    assertNull(checker.checkMyRule(validRecip, null));
    assertNull(checker.checkRecipe(validRecip, null));
  }

  @Test
  public void test_invalidName() {
    Map<String, Integer> ingred = new HashMap<>();
    Recipe invalidRecip = new BasicRecipe("wood's", ingred, 1);
    RecipeRuleChecker checker = new NameApostropheChecker(null);
  
    String result = checker.checkMyRule(invalidRecip, null);
    assertNotNull(result);
    assertEquals(result, "Recipe name 'wood's' contains an apostrophe which is invalid");
  }

}
