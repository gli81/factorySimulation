package edu.duke.ece651.group6.factorySimulation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class FactoryTest {
  @Test
  public void test_constructorGetters() {
    List<String> sources = new ArrayList<>();
    sources.add("iron");
    sources.add("coal");
    
    Factory f = new Factory("M2", "metal", sources);
    
    assertEquals("M2", f.getName());
    assertEquals("metal", f.getType());
    assertTrue(f.isFactory());
    
    List<String> sourceCheck = new ArrayList<>();
    for (String source : f.getSources()) {
      sourceCheck.add(source);
    }
    
    assertEquals(2, sourceCheck.size());
    assertTrue(sourceCheck.contains("iron"));
    assertTrue(sourceCheck.contains("coal"));
  }

  @Test
  public void test_nullSources() {
    Factory f = new Factory("C", "chemical", null);
    int count = 0;
    for (String source : f.getSources()) {
      count++;
    }
    assertEquals(0, count);
  }

  @Test
  public void test_invalidName() {
    List<String> s = new ArrayList<>();
    assertThrows(IllegalArgumentException.class, 
                 () -> new Factory(null, "metal", s));
    assertThrows(IllegalArgumentException.class, 
                () -> new Factory("", "metal", s));
  }

  @Test
  public void test_invalidType() {
    List<String> s = new ArrayList<>();
    assertThrows(IllegalArgumentException.class, 
                () -> new Factory("F", null, s));
    assertThrows(IllegalArgumentException.class, 
                () -> new Factory("F2", "", s));
  }

  @Test
  public void test_toString() {
    List<String> sources = new ArrayList<>();
    sources.add("iron");
    
    Factory factory = new Factory("M", "metal", sources);
    
    String toString = factory.toString();
    assertTrue(toString.contains("Factory: M"));
    assertTrue(toString.contains("Type: metal"));
    assertTrue(toString.contains("Sources: [iron]"));
  }
}
