package edu.duke.ece651.group6.factorySimulation.DataModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class MapGridTest {

  @Test
  public void test_simpleRoadConnection() {
    MapGrid testGrid = new MapGrid(10, 10);

    Factory f1 = new Factory("F1", new Type("TestType", new ArrayList<>()), 1, 7);
    Factory f2 = new Factory("F2", new Type("TestType", new ArrayList<>()), 5, 7);

    // System.out.println("Adding F1 at (" + f1.getX() + "," + f1.getY() + ")");
    // System.out.println("Adding F2 at (" + f2.getX() + "," + f2.getY() + ")");

    testGrid.addMapObject(f1, f1.getX(), f1.getY());
    testGrid.addMapObject(f2, f2.getX(), f2.getY());
    Road r1 = new Road(2, 7);
    Road r2 = new Road(3, 7);
    Road r3 = new Road(4, 7);

    // System.out.println("Adding roads at: (2,7), (3,7), (4,7)");

    testGrid.addMapObject(r1, r1.getX(), r1.getY());
    testGrid.addMapObject(r2, r2.getX(), r2.getY());
    testGrid.addMapObject(r3, r3.getX(), r3.getY());

    // System.out.println("Grid contents:");
    for (int y = 0; y < testGrid.getHeight(); y++) {
      for (int x = 0; x < testGrid.getWidth(); x++) {
        MapObject obj = testGrid.getMapObject(x, y);
        if (obj != null) {
          // System.out.println(" At (" + x + "," + y + "): " +
          // obj.getClass().getSimpleName());
        }
      }
    }

    int path = testGrid.shortestPath(f1, f2);
    assertEquals(3, path, "Path should exist from F1 to F2");
  }

  @Test
  public void test_simpleConnectBuildings() {
    MapGrid testGrid = new MapGrid(10, 10);

    Factory f1 = new Factory("F1", new Type("TestType", new ArrayList<>()), 1, 7);
    Factory f2 = new Factory("F2", new Type("TestType", new ArrayList<>()), 5, 7);

    // System.out.println("Adding F1 at (" + f1.getX() + "," + f1.getY() + ")");
    // System.out.println("Adding F2 at (" + f2.getX() + "," + f2.getY() + ")");

    testGrid.addMapObject(f1, f1.getX(), f1.getY());
    testGrid.addMapObject(f2, f2.getX(), f2.getY());

    boolean connected = testGrid.connectBuildings(f1, f2);

    assertTrue(connected, "Buildings should be connected successfully");
    assertTrue(f1.getConnectedBuildings().contains(f2), "F1 should have F2 in its connected buildings");

    List<Road> roads = testGrid.getRoads();
    assertFalse(roads.isEmpty(), "Roads should have been created");

    boolean hasDirectionSet = false;
    for (Road road : roads) {
      for (int dir = 0; dir < 4; dir++) {
        if (road.isDirectionAvailable(dir)) {
          hasDirectionSet = true;
          // System.out.println("Road at (" + road.getX() + "," + road.getY() + ") has
          // direction " + dir + " set");
          break;
        }
      }
      if (hasDirectionSet)
        break;
    }
    assertTrue(hasDirectionSet, "At least one road should have a direction set");

    int path = testGrid.shortestPath(f1, f2);
    assertEquals(3, path, "Path should exist from F1 to F2");
    // System.out.println("Grid after connecting buildings:");
    for (int y = 0; y < testGrid.getHeight(); y++) {
      for (int x = 0; x < testGrid.getWidth(); x++) {
        MapObject obj = testGrid.getMapObject(x, y);
        if (obj != null) {
          String type = obj.getClass().getSimpleName();
          if (obj instanceof Road) {
            Road road = (Road) obj;
            String dirs = "";
            for (int dir = 0; dir < 4; dir++) {
              if (road.isDirectionAvailable(dir)) {
                dirs += dir + ",";
              }
            }
            if (!dirs.isEmpty()) {
              dirs = dirs.substring(0, dirs.length() - 1);
            }
            type += "[" + dirs + "]";
          }
          // System.out.println(" At (" + x + "," + y + "): " + type);
        }
      }
    }
  }

  ////////// Base level Placement tests
  @Test
  public void test_defaultPlacement() {
    MapGrid testGrid = new MapGrid(20, 20); // empty grid
    Factory factory = new Factory("DefaultFactory", new Type("TestType", new ArrayList<>())); // no coordinates building

    // Choose coordinates for the building
    Map.Entry<Integer, Integer> coordinates = testGrid.chooseAvailableCoordinate();

    // Set the coordinates and add the building to the grid
    factory.setCoordinates(coordinates.getKey(), coordinates.getValue());
    testGrid.addMapObject(factory, factory.getX(), factory.getY());
    assertEquals(0, factory.getX(), "Building should be placed at x=0");
    assertEquals(0, factory.getY(), "Building should be placed at y=0");
  }

  @Test
  public void test_autoPlacementRules() {
    MapGrid testGrid = new MapGrid(50, 50);

    Factory f1 = new Factory("F1", new Type("TestType", new ArrayList<>()), 10, 10);
    Factory f2 = new Factory("F2", new Type("TestType", new ArrayList<>()), 25, 20);
    Factory f3 = new Factory("F3", new Type("TestType", new ArrayList<>()), 15, 30);

    testGrid.addMapObject(f1, f1.getX(), f1.getY());
    testGrid.addMapObject(f2, f2.getX(), f2.getY());
    testGrid.addMapObject(f3, f3.getX(), f3.getY());

    Factory autoPlacedFactory = new Factory("AutoFactory", new Type("TestType", new ArrayList<>())); // no coords

    Map.Entry<Integer, Integer> coordinates = testGrid.chooseAvailableCoordinate();

    autoPlacedFactory.setCoordinates(coordinates.getKey(), coordinates.getValue());
    testGrid.addMapObject(autoPlacedFactory, autoPlacedFactory.getX(), autoPlacedFactory.getY());

    boolean isFarEnough = true;
    if (Math.abs(autoPlacedFactory.getX() - f1.getX()) < 5 && Math.abs(autoPlacedFactory.getY() - f1.getY()) < 5) {
      isFarEnough = false;
    }
    if (Math.abs(autoPlacedFactory.getX() - f2.getX()) < 5 && Math.abs(autoPlacedFactory.getY() - f2.getY()) < 5) {
      isFarEnough = false;
    }
    if (Math.abs(autoPlacedFactory.getX() - f3.getX()) < 5 && Math.abs(autoPlacedFactory.getY() - f3.getY()) < 5) {
      isFarEnough = false;
    }
    assertTrue(isFarEnough, "Auto-placed building should be at least 5 units away from other buildings");

    boolean isCloseEnough = false;
    if (Math.abs(autoPlacedFactory.getX() - f1.getX()) <= 10 && Math.abs(autoPlacedFactory.getY() - f1.getY()) <= 10) {
      isCloseEnough = true;
    }
    if (Math.abs(autoPlacedFactory.getX() - f2.getX()) <= 10 && Math.abs(autoPlacedFactory.getY() - f2.getY()) <= 10) {
      isCloseEnough = true;
    }
    if (Math.abs(autoPlacedFactory.getX() - f3.getX()) <= 10 && Math.abs(autoPlacedFactory.getY() - f3.getY()) <= 10) {
      isCloseEnough = true;
    }
    assertTrue(isCloseEnough, "Auto-placed building should be within 10 units of at least one building");
  }

  @Test
  public void test_occupationPrevention() {
    MapGrid testGrid = new MapGrid(10, 10);

    // Create two buildings with the same coordinates
    Factory f1 = new Factory("F1", new Type("TestType", new ArrayList<>()), 5, 5);
    Factory f2 = new Factory("F2", new Type("TestType", new ArrayList<>()), 5, 5);

    // Add the first building
    boolean firstPlacement = testGrid.addMapObject(f1, f1.getX(), f1.getY());

    // Try to add the second building at the same location
    boolean secondPlacement = testGrid.addMapObject(f2, f2.getX(), f2.getY());

    assertTrue(firstPlacement, "First building should be placed successfully");
    assertFalse(secondPlacement, "Second building should not be placed at the same location"); // should fail

    MapObject objectAtLocation = testGrid.getMapObject(5, 5);
    assertEquals(f1, objectAtLocation, "The first building should remain at the location");
  }

  ///////////// Path finding and Obstacles stuff
  @Test
  public void test_pathAroundObstacles() {
    // Create a smaller 5x5 grid for better control
    MapGrid testGrid = new MapGrid(5, 5);

    // Place factories at opposite corners
    Factory f1 = new Factory("F1", new Type("TestType", new ArrayList<>()), 1, 1);
    Factory f2 = new Factory("F2", new Type("TestType", new ArrayList<>()), 4, 4);

    // Add the factories to the grid
    testGrid.addMapObject(f1, f1.getX(), f1.getY());
    testGrid.addMapObject(f2, f2.getX(), f2.getY());

    // Block the entire 2nd column with obstacles
    Factory obstacle1 = new Factory("Obstacle1", new Type("TestType", new ArrayList<>()), 2, 1);
    Factory obstacle2 = new Factory("Obstacle2", new Type("TestType", new ArrayList<>()), 2, 2);
    Factory obstacle3 = new Factory("Obstacle3", new Type("TestType", new ArrayList<>()), 2, 3);

    testGrid.addMapObject(obstacle1, obstacle1.getX(), obstacle1.getY());
    testGrid.addMapObject(obstacle2, obstacle2.getX(), obstacle2.getY());
    testGrid.addMapObject(obstacle3, obstacle3.getX(), obstacle3.getY());

    // Print the grid before connecting (for debugging)
    // System.out.println("Grid before connecting:");
    // System.out.println(testGrid.toString());

    // Connect the buildings - this should create a path going around the obstacles
    boolean connected = testGrid.connectBuildings(f1, f2);

    // Verify connection was successful
    assertTrue(connected, "Buildings should be connected even with obstacles");

    // Print the grid after connecting (for debugging)
    // System.out.println("Grid after connecting:");
    // System.out.println(testGrid.toString());

    // Verify a path exists with the expected latency
    int pathLatency = testGrid.shortestPath(f1, f2);
    // System.out.println(pathLatency);

    // We expect the latency to be 5 time steps (number of road segments)
    assertEquals(5, pathLatency, "Path latency should be 5 time steps");

    // Verify path goes around obstacles by checking road placement
    // Expected path: up and right
    assertTrue(testGrid.getMapObject(1, 2) instanceof Road, "Road should be at (1,2)");
    assertTrue(testGrid.getMapObject(1, 3) instanceof Road, "Road should be at (1,3)");
    assertTrue(testGrid.getMapObject(1, 4) instanceof Road, "Road should be at (1,4)");
    assertTrue(testGrid.getMapObject(2, 4) instanceof Road, "Road should be at (2,4)");
    assertTrue(testGrid.getMapObject(3, 4) instanceof Road, "Road should be at (3,4)");

    // Verify no roads were placed on obstacles
    assertFalse(testGrid.getMapObject(2, 1) instanceof Road, "Road should not be at obstacle (2,1)");
    assertFalse(testGrid.getMapObject(2, 2) instanceof Road, "Road should not be at obstacle (2,2)");
    assertFalse(testGrid.getMapObject(2, 3) instanceof Road, "Road should not be at obstacle (2,3)");
    assertFalse(testGrid.getMapObject(2, 5) instanceof Road, "Road should not be at obstacle (2,5)");
    assertFalse(testGrid.getMapObject(3, 3) instanceof Road, "Road should not be at obstacle (3,3)");
    assertFalse(testGrid.getMapObject(3, 5) instanceof Road, "Road should not be at obstacle (3,5)");

    // Count the total road tiles created
    int roadCount = 0;
    for (int y = 0; y < testGrid.getHeight(); y++) {
      for (int x = 0; x < testGrid.getWidth(); x++) {
        if (testGrid.getMapObject(x, y) instanceof Road) {
          roadCount++;
        }
      }
    }

    // The optimal path should have exactly 5 road tiles
    assertEquals(5, roadCount, "Should have exactly 5 road tiles");
  }

  @Test
  public void test_pathReuse() {
    MapGrid testGrid = new MapGrid(5, 5);

    Factory f1 = new Factory("F1", new Type("TestType", new ArrayList<>()), 0, 2);
    Factory f2 = new Factory("F2", new Type("TestType", new ArrayList<>()), 4, 2);
    Factory f3 = new Factory("F3", new Type("TestType", new ArrayList<>()), 2, 0);

    // Add factories to the grid
    testGrid.addMapObject(f1, f1.getX(), f1.getY());
    testGrid.addMapObject(f2, f2.getX(), f2.getY());
    testGrid.addMapObject(f3, f3.getX(), f3.getY());

    // Print the grid before any connections
    // System.out.println("Grid before any connections:");
    // System.out.println(testGrid.toString());

    // First, connect F1 to F2 horizontally
    boolean connectedF1F2 = testGrid.connectBuildings(f1, f2);
    assertTrue(connectedF1F2, "F1 should connect to F2");

    // Print grid after first connection
    // System.out.println("Grid after F1-F2 connection:");
    // System.out.println(testGrid.toString());

    // Count roads after first connection
    int roadCountAfterFirstConnection = 0;
    for (int y = 0; y < testGrid.getHeight(); y++) {
      for (int x = 0; x < testGrid.getWidth(); x++) {
        if (testGrid.getMapObject(x, y) instanceof Road) {
          roadCountAfterFirstConnection++;
        }
      }
    }

    // The path from F1 to F2 should use 3 road tiles (at positions 1,2 - 2,2 - 3,2)
    assertEquals(3, roadCountAfterFirstConnection, "F1-F2 connection should create exactly 3 road tiles");

    // Verify the first connection created the expected road tiles
    assertTrue(testGrid.getMapObject(1, 2) instanceof Road, "Road should be at (1,2)");
    assertTrue(testGrid.getMapObject(2, 2) instanceof Road, "Road should be at (2,2)");
    assertTrue(testGrid.getMapObject(3, 2) instanceof Road, "Road should be at (3,2)");

    // Store the coordinate set of existing roads for later comparison
    Set<String> existingRoadCoordinates = new HashSet<>();
    for (int y = 0; y < testGrid.getHeight(); y++) {
      for (int x = 0; x < testGrid.getWidth(); x++) {
        if (testGrid.getMapObject(x, y) instanceof Road) {
          existingRoadCoordinates.add(x + "," + y);
        }
      }
    }

    // Now connect F3 to F2, which should reuse part of the existing path
    boolean connectedF3F2 = testGrid.connectBuildings(f3, f2);
    assertTrue(connectedF3F2, "F3 should connect to F2");

    // Print grid after second connection
    // System.out.println("Grid after F3-F2 connection:");
    // System.out.println(testGrid.toString());

    // Count roads after second connection
    int roadCountAfterSecondConnection = 0;
    for (int y = 0; y < testGrid.getHeight(); y++) {
      for (int x = 0; x < testGrid.getWidth(); x++) {
        if (testGrid.getMapObject(x, y) instanceof Road) {
          roadCountAfterSecondConnection++;
        }
      }
    }

    // Calculate the minimum new roads needed for F3 to F2
    // Direct path would need: (2,1) and (2,2) (already exists), (3,2) (already
    // exists)
    // So we should only add 1 new road at (2,1)
    int expectedNewRoads = 1;
    assertEquals(roadCountAfterFirstConnection + expectedNewRoads, roadCountAfterSecondConnection,
        "F3-F2 connection should reuse existing roads");

    // Verify the new road was created
    assertTrue(testGrid.getMapObject(2, 1) instanceof Road, "New road should be at (2,1)");

    // Verify the existing roads were reused (still exist)
    for (String coord : existingRoadCoordinates) {
      String[] parts = coord.split(",");
      int x = Integer.parseInt(parts[0]);
      int y = Integer.parseInt(parts[1]);
      assertTrue(testGrid.getMapObject(x, y) instanceof Road,
          "Existing road at (" + x + "," + y + ") should be reused");
    }

    // Check the latency matches our expectations
    int pathLength = testGrid.shortestPath(f3, f2);
    // Should be 3: F3 -> (2,1) -> (2,2) -> (3,2) -> F2
    assertEquals(3, pathLength, "Path latency from F3 to F2 should be 3 time steps");
  }

  @Test
  public void test_roadDirectionCrossJunction() {
    MapGrid testGrid = new MapGrid(5, 5);
    Factory f1 = new Factory("F1", new Type("TestType", new ArrayList<>()), 0, 2);
    Factory f2 = new Factory("F2", new Type("TestType", new ArrayList<>()), 4, 2);
    Factory f3 = new Factory("F3", new Type("TestType", new ArrayList<>()), 2, 0);
    Factory f4 = new Factory("F4", new Type("TestType", new ArrayList<>()), 2, 4);

    testGrid.addMapObject(f1, f1.getX(), f1.getY());
    testGrid.addMapObject(f2, f2.getX(), f2.getY());
    testGrid.addMapObject(f3, f3.getX(), f3.getY());
    testGrid.addMapObject(f4, f4.getX(), f4.getY());

    // System.out.println("Grid before any connections:");
    // System.out.println(testGrid.toString());

    // First, connect F1 to F2 horizontally
    boolean connectedF1F2 = testGrid.connectBuildings(f1, f2);
    assertTrue(connectedF1F2, "F1 should connect to F2");

    // Print grid after first connection
    // System.out.println("Grid after F1-F2 connection:");
    // System.out.println(testGrid.toString());

    // Verify horizontal roads exist
    assertTrue(testGrid.getMapObject(1, 2) instanceof Road, "Horizontal road should exist at (1,2)");
    assertTrue(testGrid.getMapObject(2, 2) instanceof Road, "Horizontal road should exist at (2,2)");
    assertTrue(testGrid.getMapObject(3, 2) instanceof Road, "Horizontal road should exist at (3,2)");

    // Check that roads have only horizontal directions set (directions: 0=up,
    // 1=down, 2=left, 3=right)
    Road horizontalRoad1 = (Road) testGrid.getMapObject(1, 2);
    Road horizontalRoad2 = (Road) testGrid.getMapObject(2, 2);
    Road horizontalRoad3 = (Road) testGrid.getMapObject(3, 2);

    // Check that horizontal roads only have horizontal directions (left/right)
    boolean onlyHorizontalDirections = (horizontalRoad2.isDirectionAvailable(2)
        || horizontalRoad2.isDirectionAvailable(3)) &&
        (!horizontalRoad2.isDirectionAvailable(0) && !horizontalRoad2.isDirectionAvailable(1));

    assertTrue(onlyHorizontalDirections,
        "Center road should only have horizontal directions initially");

    // Now connect F3 to F4 vertically
    boolean connectedF3F4 = testGrid.connectBuildings(f3, f4);
    assertTrue(connectedF3F4, "F3 should connect to F4");

    // Print grid after second connection
    //  System.out.println("Grid after F3-F4 connection:");
    // System.out.println(testGrid.toString());

    // Verify vertical roads exist
    assertTrue(testGrid.getMapObject(2, 1) instanceof Road, "Vertical road should exist at (2,1)");
    assertTrue(testGrid.getMapObject(2, 3) instanceof Road, "Vertical road should exist at (2,3)");

    // The road at (2,2) should now be converted to a junction with all four
    // directions
    Road junctionRoad = (Road) testGrid.getMapObject(2, 2);

    // Count how many directions are set on the junction road
    int directionCount = 0;
    for (int dir = 0; dir < 4; dir++) {
      if (junctionRoad.isDirectionAvailable(dir)) {
        directionCount++;
        // System.out.println("Direction " + dir + " is available at junction");
      }
    }

    // Junction should have at least 3 directions (ideally all 4)
    assertTrue(directionCount == 2,
        "Junction road should have 2 directions available after vertical connection");

    // Check specific directions that should be available
    assertTrue(junctionRoad.isDirectionAvailable(0) || junctionRoad.isDirectionAvailable(1),
        "Junction should have at least one vertical direction");
    assertTrue(junctionRoad.isDirectionAvailable(2) || junctionRoad.isDirectionAvailable(3),
        "Junction should have at least one horizontal direction");

    // Check the latency for all paths
    int pathLengthF1F2 = testGrid.shortestPath(f1, f2);
    int pathLengthF3F4 = testGrid.shortestPath(f3, f4);

    assertEquals(3, pathLengthF1F2, "Path latency from F1 to F2 should be 3 time steps");
    assertEquals(3, pathLengthF3F4, "Path latency from F3 to F4 should be 3 time steps");

    // Now connect F1 to F3, which should modify the road at (2,2) to include a new
    // direction
    boolean connectedF1F3 = testGrid.connectBuildings(f1, f3);
    assertTrue(connectedF1F3, "F1 should connect to F3");

    // Print grid after third connection
    // System.out.println("Grid after F1-F3 connection:");
    // System.out.println(testGrid.toString());

    // The road at (2,2) should now have 3 directions: right (3), up (0), and down
    // (1)
    Road updatedJunctionRoad = (Road) testGrid.getMapObject(2, 2);

    // Print all available directions at the junction for debugging
    // System.out.println("Directions available at junction after F1-F3 connection:");
    for (int dir = 0; dir < 4; dir++) {
      if (updatedJunctionRoad.isDirectionAvailable(dir)) {
        //        System.out.println("Direction " + dir + " is available at junction");
      }
    }

    // Verify the junction now has all 3 expected directions
    assertTrue(updatedJunctionRoad.isDirectionAvailable(0),
        "Junction should have up (0) direction available");
    assertTrue(updatedJunctionRoad.isDirectionAvailable(1),
        "Junction should have down (1) direction available");
    assertTrue(updatedJunctionRoad.isDirectionAvailable(3),
        "Junction should have right (3) direction available");

    // Count directions to make sure we have exactly 3
    int updatedDirectionCount = 0;
    for (int dir = 0; dir < 4; dir++) {
      if (updatedJunctionRoad.isDirectionAvailable(dir)) {
        updatedDirectionCount++;
      }
    }
    assertEquals(3, updatedDirectionCount,
        "Junction should have exactly 3 directions after F1-F3 connection");

    // Verify path lengths
    int pathLengthF1F3 = testGrid.shortestPath(f1, f3);

    assertEquals(3, pathLengthF1F2, "Path latency from F1 to F2 should be 3 time steps");
    assertEquals(3, pathLengthF3F4, "Path latency from F3 to F4 should be 3 time steps");

    // TODO for test change -- comprehensive suite test
    assertEquals(3, pathLengthF1F3, "Path latency from F1 to F3 should be 3 time steps");
  }

  @Test
  public void test_impossibleConnection() {
    MapGrid testGrid = new MapGrid(5, 5);

    Factory f1 = new Factory("F1", new Type("TestType", new ArrayList<>()), 0, 2);
    Factory f2 = new Factory("F2", new Type("TestType", new ArrayList<>()), 4, 2);

    // Add factories to the grid
    testGrid.addMapObject(f1, f1.getX(), f1.getY());
    testGrid.addMapObject(f2, f2.getX(), f2.getY());

    // Create a complete wall of obstacles in the middle column (2)
    for (int y = 0; y < testGrid.getHeight(); y++) {
      Factory obstacle = new Factory("Obstacle" + y, new Type("TestType", new ArrayList<>()), 2, y);
      testGrid.addMapObject(obstacle, obstacle.getX(), obstacle.getY());
    }

    // Print the grid before attempting connection
    //    System.out.println("Grid with obstacles:");
    // System.out.println(testGrid.toString());

    // Attempt to connect F1 to F2 - should fail because there's no possible path
    boolean connected = testGrid.connectBuildings(f1, f2);

    // Verify connection was NOT successful
    assertFalse(connected, "Buildings should NOT be connectable when fully blocked by obstacles");

    // Verify that no road was created
    boolean anyRoadsFound = false;
    for (int y = 0; y < testGrid.getHeight(); y++) {
      for (int x = 0; x < testGrid.getWidth(); x++) {
        if (testGrid.getMapObject(x, y) instanceof Road) {
          anyRoadsFound = true;
          System.out.println("Unexpected road found at (" + x + ", " + y + ")");
          break;
        }
      }
      if (anyRoadsFound)
        break;
    }

    assertFalse(anyRoadsFound, "No roads should be created when connection is impossible");

    // Also verify that shortestPath returns -1 for an impossible path
    int pathLength = testGrid.shortestPath(f1, f2);
    assertEquals(-1, pathLength, "Shortest path should return -1 for an impossible path");
  }

  @Test
public void test_buildingAdjacencyRelationships() {
    MapGrid testGrid = new MapGrid(5, 5);
    
    // Create factories in the configuration from the screenshot
    Factory f1 = new Factory("F1", new Type("TestType", new ArrayList<>()), 1, 2);
    Factory f2 = new Factory("F2", new Type("TestType", new ArrayList<>()), 2, 2);
    Factory f3 = new Factory("F3", new Type("TestType", new ArrayList<>()), 2, 1);
    
    // Add factories to the grid
    testGrid.addMapObject(f1, f1.getX(), f1.getY());
    testGrid.addMapObject(f2, f2.getX(), f2.getY());
    testGrid.addMapObject(f3, f3.getX(), f3.getY());
    
    // Test 1: Check adjacency between F1 and F2 (horizontally adjacent)
    int pathLengthF1F2 = testGrid.shortestPath(f1, f2);
    assertEquals(0, pathLengthF1F2, "Horizontally adjacent buildings (F1-F2) should have 0 latency");
    
    // Test 2: Check adjacency between F2 and F3 (vertically adjacent)
    int pathLengthF2F3 = testGrid.shortestPath(f2, f3);
    assertEquals(0, pathLengthF2F3, "Vertically adjacent buildings (F2-F3) should have 0 latency");
    
    // Test 3: Check diagonal relationship between F1 and F3
    int pathLengthF1F3 = testGrid.shortestPath(f1, f3);
    // Diagonal buildings are not considered adjacent
    assertNotEquals(0, pathLengthF1F3, "Diagonally positioned buildings (F1-F3) should not have 0 latency");
    
    // Test 4: Connect F1 and F3 and verify the path
    boolean connected = testGrid.connectBuildings(f1, f3);
    assertTrue(connected, "F1 should be able to connect to F3");
    
    // Calculate the new path length
    int newPathLengthF1F3 = testGrid.shortestPath(f1, f3);
    
    // Based on the implementation, we expect a single road at (1,1) 
    // that connects diagonally to both buildings
    assertEquals(1, newPathLengthF1F3, "Path length for diagonal buildings should be 1 after road connection");
    
    // Verify that exactly 1 road segment was created
    int roadCount = 0;
    for (int y = 0; y < testGrid.getHeight(); y++) {
        for (int x = 0; x < testGrid.getWidth(); x++) {
            if (testGrid.getMapObject(x, y) instanceof Road) {
                roadCount++;
            }
        }
    }
    assertEquals(1, roadCount, "Connection between diagonal buildings should create exactly 1 road segment");
    
    // Test 5: Verify that the road is at (1,1) with both down and right directions enabled
    Road road = (Road) testGrid.getMapObject(1, 1);
    assertNotNull(road, "Road should be placed at (1,1)");
    
    assertFalse(road.isDirectionAvailable(0), "Road should have down direction (0) disabled");
    assertFalse(road.isDirectionAvailable(1), "Road should have down direction (1) disabled");
    assertFalse(road.isDirectionAvailable(2), "Road should have down direction (2) disabled");
    assertTrue(road.isDirectionAvailable(3), "Road should have right direction (3) enabled");
    
}
}
