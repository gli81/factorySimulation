package edu.duke.ece651.group6.factorySimulation.DataModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.duke.ece651.group6.factorySimulation.ProductionController;

public class FactoryTest {
  private Type doorType;
  private Recipe wood;
  private Recipe metal;
  private Recipe door;
  private Factory doorFactory;
  private Mine woodMine;
  private Mine metalMine;

  @BeforeEach
  public void factry() {
    wood = new Recipe("wood", 1);
    metal = new Recipe("metal", 1);
    door = new Recipe("door", 5);
    door.addIngredient(wood, 2);
    door.addIngredient(metal, 3);

    ArrayList<Recipe> recip = new ArrayList<>();
    recip.add(door);
    doorType = new Type("doorType", recip);

    doorFactory = new Factory("doorFactory", doorType);
    woodMine = new Mine("woodMine", wood);
    metalMine = new Mine("metalMine", metal);

    doorFactory.addSource(woodMine);
    doorFactory.addSource(metalMine);
  }

  @Test
  public void test_constructorAndType() {
    assertEquals(doorType, doorFactory.getType());
    assertEquals("doorFactory", doorFactory.getName());
  }

  @Test
  public void test_SourcesStuff() {
    int count = 0;
    for (Building s : doorFactory.getSourcesIterable()) {
      count++;
      assertTrue(s.equals(woodMine) || s.equals(metalMine));
    }
    assertEquals(2, count);

    Mine glassM = new Mine("glassM", new Recipe("glass", 1));
    doorFactory.addSource(glassM);

    count = 0; // reset
    for (Building s : doorFactory.getSourcesIterable()) {
      count++;
    }
    assertEquals(3, count);

    ArrayList<Building> sList = new ArrayList<>();
    doorFactory.getSourcesIterable().forEach(source -> sList.add(source));
    assertEquals(3, sList.size());
  }

  @Test
  public void test_RecipModelSupport() {
    assertTrue(doorFactory.isRecipeSupported(door));
    assertFalse(doorFactory.isRecipeSupported(wood));
    assertFalse(doorFactory.isRecipeSupported(metal));
  }

  @Test
  public void test_addRequest() {
    Factory receiverF = new Factory("receiverFact", doorType);
    doorFactory.addRequest(door, receiverF);
    assertEquals(1, doorFactory.getQueueSize());
    assertEquals(2, woodMine.getQueueSize()); // 2 wood?
    assertEquals(3, metalMine.getQueueSize());
  }

  @Test
  public void test_Storage() {
    Factory receiverF = new Factory("receiverFact", doorType);
    doorFactory.addRequest(door, receiverF);
    doorFactory.addStorage(wood);
    doorFactory.addStorage(wood);
    doorFactory.addStorage(metal);
    doorFactory.addStorage(metal);
    doorFactory.addStorage(metal);
    assertEquals(1, doorFactory.getQueueSize());
  }

  @Test
  public void test_sourceDiff_queueSize() {
    Factory testF = new Factory("testFactory", doorType);
    testF.addSource(woodMine);
    testF.addSource(metalMine);

    Factory dummyF = new Factory("dummyFactory", doorType);
    woodMine.addRequest(wood, dummyF);
    Factory receiverFactory = new Factory("receiverF", doorType);
    testF.addRequest(door, receiverFactory);

    assertEquals(3, woodMine.getQueueSize());
    assertEquals(3, metalMine.getQueueSize()); // 3 new reqs
  }

  @Test
  public void test_sourceVerbose() {
    ProductionController.setVerbose(2);
    Factory testF = new Factory("testFactry", doorType);
    testF.addSource(woodMine);
    testF.addSource(metalMine);
    Factory r = new Factory("receiverFactory", doorType);
    testF.addRequest(door, r);

    ProductionController.setVerbose(0);
    assertEquals(1, testF.getQueueSize());
  }

  @Test
  public void test_equalsHashCode() {
    Factory sameDoorF = new Factory("doorFactory", doorType);
    ArrayList<Recipe> otherRecip = new ArrayList<>();
    otherRecip.add(new Recipe("chair", 3));
    Type chairType = new Type("chairType", otherRecip);
    Factory diffType = new Factory("doorFactory", chairType);

    Factory diffName = new Factory("chairFactory", doorType);
    assertTrue(doorFactory.equals(doorFactory));
    assertFalse(doorFactory.equals(null));
    assertFalse(doorFactory.equals(new Object()));
    assertTrue(doorFactory.equals(sameDoorF));
    assertFalse(doorFactory.equals(diffType));
    assertFalse(doorFactory.equals(diffName));

    // hash
    assertEquals(doorFactory.hashCode(), sameDoorF.hashCode());
    assertNotEquals(doorFactory.hashCode(), diffType.hashCode());
    assertNotEquals(doorFactory.hashCode(), diffName.hashCode());
  }

  @Test
  public void test_toString() {
    String e = "Factory: { doorFactory, doorType, { woodMine, metalMine } }\n";
    assertEquals(e, doorFactory.toString());

    Factory emptySourcesFact = new Factory("emptyFactory", doorType);
    e = "Factory: { emptyFactory, doorType, { } }\n";
    assertEquals(e, emptySourcesFact.toString());
  }

  @Test
  public void testStorage_part() {
    Factory receiverF = new Factory("receiverFactory", doorType);
    doorFactory.addRequest(door, receiverF);
    doorFactory.addStorage(wood);
    doorFactory.addStorage(metal);
    assertEquals(1, doorFactory.getQueueSize());
    doorFactory.addStorage(wood);
    doorFactory.addStorage(metal);
    doorFactory.addStorage(metal);
    assertEquals(1, doorFactory.getQueueSize());
  }

  @Test
  public void test_Storagerequests() {
    Recipe t = new Recipe("table", 8);
    t.addIngredient(wood, 4);
    t.addIngredient(metal, 1);
    ArrayList<Recipe> updatedRecip = new ArrayList<>();
    updatedRecip.add(door);
    updatedRecip.add(t);
    Type type = new Type("doorType", updatedRecip);

    Factory multiFactory = new Factory("multiFactory", type);
    multiFactory.addSource(woodMine);
    multiFactory.addSource(metalMine);

    Factory receiverF = new Factory("receiverFactory", type);
    multiFactory.addRequest(door, receiverF);
    multiFactory.addRequest(t, receiverF);
    assertEquals(2, multiFactory.getQueueSize());
    multiFactory.addStorage(wood);
    multiFactory.addStorage(wood);
    multiFactory.addStorage(metal);
    multiFactory.addStorage(metal);
    multiFactory.addStorage(metal);

    assertEquals(2, multiFactory.getQueueSize());
    multiFactory.addStorage(wood);
    multiFactory.addStorage(wood);
    multiFactory.addStorage(wood);
    multiFactory.addStorage(wood);
    multiFactory.addStorage(metal);
    assertEquals(2, multiFactory.getQueueSize());
  }

  @Test
  public void test_verbose3() {
    PrintStream ogOut = System.out;
    try {
      ByteArrayOutputStream outcontent = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outcontent));
      ProductionController.setVerbose(3);
      doorFactory.addRequest(door, woodMine);
      String output = outcontent.toString();
      assertTrue(output.contains("TEST:[Order Number:"));

      ProductionController.setVerbose(0);
    } finally {
      System.setOut(ogOut);
    }
  }

  @Test
  public void test_workingFlow() {
    doorFactory.addRequest(door, null);
    doorFactory.addStorage(wood);
    doorFactory.addStorage(wood);
    doorFactory.addStorage(metal);
    doorFactory.addStorage(metal);
    doorFactory.addStorage(metal);
    doorFactory.doWork();
    for (int i = 0; i < 4; i++) {
      doorFactory.doWork();
    }

    Recipe deliv = doorFactory.doDelivery();
    assertEquals(door, deliv);
  }

  @Test
  public void test_verbos2_recip() {
    PrintStream out = System.out;
    try {
      ByteArrayOutputStream outText = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outText));
      ProductionController.setVerbose(2);
      doorFactory.addRequest(door, null);
      doorFactory.addStorage(wood);
      doorFactory.addStorage(wood);
      doorFactory.addStorage(metal);
      doorFactory.addStorage(metal);
      doorFactory.addStorage(metal);
      doorFactory.doWork();

      String ret = outText.toString();
      assertTrue(ret.contains("[recipe selection]:"));
      assertTrue(ret.contains("is ready"));
      assertTrue(ret.contains("Selecting 0"));

      ProductionController.setVerbose(0);
    } finally {
      System.setOut(out);
    }
  }

}
