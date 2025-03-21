package edu.duke.ece651.group6.factorySimulation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MineTest {
  @Test
  public void test_constructorGetters() {
    Mine m = new Mine("M1", "iron"); 
    assertEquals("M1", m.getName());
    assertEquals("iron", m.getMineType());
    assertFalse(m.isFactory());
  }

  @Test
  public void test_invalidName() {
    assertThrows(IllegalArgumentException.class, 
                () -> new Mine(null, "iron"));
    assertThrows(IllegalArgumentException.class, 
                () -> new Mine("", "iron"));
  }

  @Test
  public void test_invalidMineType() {
    assertThrows(IllegalArgumentException.class, 
                () -> new Mine("M1", null));
    assertThrows(IllegalArgumentException.class, 
                () -> new Mine("M1", ""));
  }

  @Test
  public void test_toString() {
    Mine mine = new Mine("M1", "iron");
    String str = mine.toString();
    assertTrue(str.contains("Mine: M1"));
    assertTrue(str.contains("Mines: iron"));
  }

}
