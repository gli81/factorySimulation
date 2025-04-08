package edu.duke.ece651.group6.factorySimulation.DataModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class MapGrid {
    private MapObject[][] grid;
    private int width;
    private int height;
    private List<Road> roads;

    // the set of occupied coordinates,
    // used to choose available coordinates
    private Set<Integer> xSet;
    private Set<Integer> ySet;

    public MapGrid() {
        this.width = 100;
        this.height = 100;
        this.grid = new MapObject[height][width];
        this.xSet = new HashSet<>();
        this.ySet = new HashSet<>();
        this.roads = new ArrayList<>();
    }

    public MapGrid(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new MapObject[height][width];
        this.xSet = new HashSet<>();
        this.ySet = new HashSet<>();
        this.roads = new ArrayList<>();
    }

    /**
     * Get the width of the grid
     * 
     * @return The width of the grid
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height of the grid
     * 
     * @return The height of the grid
     */
    public int getHeight() {
        return height;
    }

    /**
     * Check if a number is safe to use as an available coordinate
     * 
     * @param num The number to check
     * @param set The set of occupied coordinates
     * @return true if the number is safe, false otherwise
     */
    private boolean isSafe(int num, Set<Integer> set) {
        // check if the num is at least 5 away from any num in the set
        boolean isSafe = true;
        for (int occupied : set) {
            if (Math.abs(num - occupied) < 5) {
                isSafe = false;
                break;
            }
        }
        if (!isSafe) {
            return false;
        }

        // check if the num is at most 10 away from at least one num in the set
        isSafe = false;
        for (int occupied : set) {
            if (Math.abs(num - occupied) <= 10) {
                isSafe = true;
                break;
            }
        }

        return isSafe;
    }

    /**
     * Choose an available coordinate
     * 
     * @return The chosen coordinate, or null if no available coordinates
     */
    public Map.Entry<Integer, Integer> chooseAvailableCoordinate() {
        if (xSet.isEmpty() && ySet.isEmpty()) {
            return Map.entry(0, 0);
        }

        int foundX = -1;
        int foundY = -1;
        // try to find an x coordinate that is safe
        for (int x = 0; x < width; x++) {
            if (isSafe(x, xSet)) {
                foundX = x;
                break;
            }
        }
        if (foundX == -1) {
            return null;
        }
        // try to find a y coordinate that is safe
        for (int y = 0; y < height; y++) {
            if (isSafe(y, ySet)) {
                foundY = y;
                break;
            }
        }
        if (foundY == -1) {
            return null;
        }
        return Map.entry(foundX, foundY);
    }

    /**
     * Add a map object to the specified position
     * 
     * @param mapObject The map object to add
     * @param x         The x coordinate
     * @param y         The y coordinate
     * @return true if the object was added successfully, false otherwise
     */
    public boolean addMapObject(MapObject mapObject, int x, int y) {
        // check if the position is out of bounds
        if (!isValidPosition(x, y)) {
            return false;
        }
        // check if the position is already occupied
        if (getMapObject(x, y) != null) {
            return false;
        }
        // add the map object to the grid
        grid[y][x] = mapObject;
        xSet.add(x);
        ySet.add(y);

        if (mapObject instanceof Road) {
            roads.add((Road) mapObject);
        }
        return true;
    }

    /**
     * Add a road to the grid
     * 
     * @param road The road to add
     * @return true if the road was added successfully, false otherwise
     */
    public List<Road> getRoads() {
        return new ArrayList<>(roads);
    }

    /**
     * Remove a map object from the specified position
     * 
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The removed object, or null if no object was at that position
     */
    public MapObject removeMapObject(int x, int y) {
        if (!isValidPosition(x, y)) {
            return null;
        }
        MapObject mapObject = grid[y][x];
        grid[y][x] = null;
        return mapObject;
    }

    /**
     * Get the map object at the specified position
     * 
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The map object at the position, or null if there is no object
     */
    public MapObject getMapObject(int x, int y) {
        if (!isValidPosition(x, y)) {
            return null;
        }
        return grid[y][x];
    }

    /**
     * Check if a position is within the grid
     * 
     * @param x The x coordinate
     * @param y The y coordinate
     * @return true if the position is within the grid, false otherwise
     */
    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    /**
     * Check if a position is occupied
     * 
     * @param x The x coordinate
     * @param y The y coordinate
     * @return true if the position is occupied, false otherwise
     */
    public boolean isOccupied(int x, int y) {
        if (!isValidPosition(x, y)) {
            return false;
        }
        return grid[y][x] != null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("MapGrid:\n");
        sb.append(" ");
        for (int x = 0; x < width; x++) {
            sb.append(x % 10);
            sb.append("|");

        }
        sb.append("\n");
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x == 0) {
                    sb.append(y % 10);
                }
                MapObject mapObject = grid[y][x];
                if (mapObject == null) {
                    sb.append(" ");
                } else if (mapObject instanceof Road) {
                    sb.append("+");
                } else if (mapObject instanceof Mine) {
                    sb.append("M");
                } else if (mapObject instanceof Factory) {
                    sb.append("F");
                } else if (mapObject instanceof Storage) {
                    sb.append("S");
                }
                sb.append("|");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

  public int shortestPath(Building sourceBuilding, Building targetBuilding) {
    // Handle special cases
    if (targetBuilding == null) {
      return 0;
    }
    if (sourceBuilding == targetBuilding) {
      return 0;
    }
    // If buildings are adjacent, no road needed
    if (sourceBuilding.getY() == targetBuilding.getY()
        && Math.abs(sourceBuilding.getX() - targetBuilding.getX()) == 1) {
      return 0;
    }
    if (sourceBuilding.getX() == targetBuilding.getX()
        && Math.abs(sourceBuilding.getY() - targetBuilding.getY()) == 1) {
      return 0;
    }

    // Initialize the queue for BFS and visited array
    Queue<int[]> queue = new LinkedList<>();
    boolean[][] visited = new boolean[height][width];

    // Start BFS from the source building
    queue.add(new int[] { sourceBuilding.getX(), sourceBuilding.getY(), 0 });
    visited[sourceBuilding.getY()][sourceBuilding.getX()] = true;

    // System.out.println("Starting BFS from: (" + sourceBuilding.getX() + "," +
    // sourceBuilding.getY() + ")");
    // System.out.println("Target building at: (" + targetBuilding.getX() + "," +
    // targetBuilding.getY() + ")");

    // BFS to find the shortest path
    while (!queue.isEmpty()) {
      int[] current = queue.poll();
      int x = current[0];
      int y = current[1];
      int distance = current[2];

      // System.out.println("Visiting: (" + x + "," + y + ") at distance " +
      // distance);

      // Try all four directions
      int[][] directions = { { 0, -1 }, { 0, 1 }, { -1, 0 }, { 1, 0 } }; // up, down, left, right

      for (int d = 0; d < directions.length; d++) {
        int[] dir = directions[d];
        int newX = x + dir[0];
        int newY = y + dir[1];

        // Skip if out of bounds
        if (newX < 0 || newX >= width || newY < 0 || newY >= height) {
          continue;
        }

        // Skip if already visited
        if (visited[newY][newX]) {
          continue;
        }

        // Check what's at this position
        MapObject obj = getMapObject(newX, newY);
        // System.out.println(" Checking: (" + newX + "," + newY + ") - " +
        // (obj == null ? "empty" : obj.getClass().getSimpleName()));

        // Check if we reached the target building
        if (obj == targetBuilding) {
          // System.out.println("Found target at distance: " + (distance + 1));
          return distance;
        }

        // Check if this is a road
        if (obj instanceof Road) {
          // System.out.println(" Adding road to queue");
          visited[newY][newX] = true;
          queue.add(new int[] { newX, newY, distance + 1 });
        }
      }
    }

    // System.out.println("No path found");
    // If no path is found, return -1
    return -1;
  }

  /**
   * Connect two buildings by creating a path of roads between them
   * 
   * @param sourceBuilding The source building
   * @param targetBuilding The target building
   * @return true if the buildings are connected, false otherwise
   */
  public Boolean connectBuildings(Building sourceBuilding, Building targetBuilding) {
    // Check if buildings are already connected
    if (sourceBuilding.getConnectedBuildings().contains(targetBuilding)) {
      return true;
    }

    // Add the connection to the building's list
    sourceBuilding.addConnectedBuildings(targetBuilding);

    // Find the optimal path
    List<int[]> path = findOptimalPath(sourceBuilding, targetBuilding);

    // If no path was found
    if (path == null) {
      return false;
    }

    // Build roads along the path
    buildRoadsAlongPath(path, sourceBuilding, targetBuilding);

    return true;
  }

  /**
   * Finds the optimal path between source and target buildings
   * that minimizes (total distance + new roads added)
   * 
   * @param sourceBuilding The source building
   * @param targetBuilding The target building
   * @return List of coordinates for the path, or null if no path found
   */
  private List<int[]> findOptimalPath(Building sourceBuilding, Building targetBuilding) {
    int startX = sourceBuilding.getX();
    int startY = sourceBuilding.getY();
    int endX = targetBuilding.getX();
    int endY = targetBuilding.getY();

    // Initialize path info grid and priority queue
    int[][][][] pathInfo = initializePathInfo();
    pathInfo[startY][startX][0][0] = 0; // totalCost
    pathInfo[startY][startX][1][0] = 0; // newRoadsAdded

    PriorityQueue<int[]> pq = createPriorityQueue();
    pq.add(new int[] { startX, startY, 0, 0 });

    // Run the path finding algorithm
    boolean pathFound = runDijkstraAlgorithm(pq, pathInfo, targetBuilding);

    if (!pathFound) {
      return null;
    }

    // Reconstruct and return the path
    return reconstructPath(pathInfo, startX, startY, endX, endY);
  }

  /**
   * Initializes the pathInfo grid
   */
  private int[][][][] initializePathInfo() {
    int[][][][] pathInfo = new int[height][width][4][1];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        pathInfo[y][x][0][0] = Integer.MAX_VALUE; // totalCost
        pathInfo[y][x][1][0] = Integer.MAX_VALUE; // newRoadsAdded
        pathInfo[y][x][2][0] = -1; // previousX
        pathInfo[y][x][3][0] = -1; // previousY
      }
    }
    return pathInfo;
  }

  /**
   * Creates priority queue for path finding
   */
  private PriorityQueue<int[]> createPriorityQueue() {
    return new PriorityQueue<>((a, b) -> {
      // Compare by totalCost first
      if (a[2] != b[2])
        return Integer.compare(a[2], b[2]);
      // If totalCost is the same, compare by newRoadsAdded
      return Integer.compare(a[3], b[3]);
    });
  }

  /**
   * Runs Dijkstra's algorithm to find the optimal path
   */
  private boolean runDijkstraAlgorithm(PriorityQueue<int[]> pq, int[][][][] pathInfo,
      Building targetBuilding) {
    // Directions: up, down, left, right
    int[][] directions = { { 0, -1 }, { 0, 1 }, { -1, 0 }, { 1, 0 } };
    // Direction indices that correspond to the Road class
    int[] dirIndices = { 0, 1, 2, 3 }; // 0: up, 1: down, 2: left, 3: right

    int endX = targetBuilding.getX();
    int endY = targetBuilding.getY();

    while (!pq.isEmpty()) {
      int[] current = pq.poll();
      int x = current[0];
      int y = current[1];
      int currentTotalCost = current[2];
      int currentNewRoads = current[3];

      // If we've reached the target
      if (x == endX && y == endY) {
        return true;
      }

      // Skip if we've found a better path already
      if (currentTotalCost > pathInfo[y][x][0][0])
        continue;

      // Check all four directions
      for (int d = 0; d < directions.length; d++) {
        exploreDirection(x, y, d, directions, dirIndices, currentTotalCost,
            currentNewRoads, pathInfo, pq, targetBuilding);
      }
    }

    return false;
  }

  /**
   * Explores a single direction from the current position
   */
  private void exploreDirection(int x, int y, int d, int[][] directions, int[] dirIndices,
      int currentTotalCost, int currentNewRoads,
      int[][][][] pathInfo, PriorityQueue<int[]> pq,
      Building targetBuilding) {
    int newX = x + directions[d][0];
    int newY = y + directions[d][1];

    // Skip if out of bounds
    if (!isValidPosition(newX, newY))
      return;

    // Skip if it's a building (other than the target)
    MapObject obj = getMapObject(newX, newY);
    if (obj instanceof Building && !(obj == targetBuilding))
      return;

    // Calculate costs for this move
    int newDistance = currentTotalCost + 1; // One step further
    int newRoadsAdded = currentNewRoads;

    // Check if this is a new road or reusing existing road
    boolean needNewRoad = true;

    if (obj instanceof Road) {
      Road road = (Road) obj;
      // Check if the road already allows movement in this direction
      if (road.isDirectionAvailable(dirIndices[d])) {
        // We can reuse this road
        needNewRoad = false;
      }
    }

    if (needNewRoad) {
      newRoadsAdded++;
    }

    // Calculate total cost
    int newTotalCost = newDistance + newRoadsAdded;

    // Check if this is a better path
    if (newTotalCost < pathInfo[newY][newX][0][0] ||
        (newTotalCost == pathInfo[newY][newX][0][0] && newRoadsAdded < pathInfo[newY][newX][1][0])) {

      pathInfo[newY][newX][0][0] = newTotalCost;
      pathInfo[newY][newX][1][0] = newRoadsAdded;
      pathInfo[newY][newX][2][0] = x;
      pathInfo[newY][newX][3][0] = y;

      pq.add(new int[] { newX, newY, newTotalCost, newRoadsAdded });
    }
  }

  /**
   * Reconstructs the path from the pathInfo grid
   */
  private List<int[]> reconstructPath(int[][][][] pathInfo, int startX, int startY,
      int endX, int endY) {
    List<int[]> path = new ArrayList<>();
    int currX = endX;
    int currY = endY;

    while (currX != startX || currY != startY) {
      path.add(new int[] { currX, currY });
      int prevX = pathInfo[currY][currX][2][0];
      int prevY = pathInfo[currY][currX][3][0];
      currX = prevX;
      currY = prevY;
    }

    // Reverse the path to go from source to target
    Collections.reverse(path);
    return path;
  }

  /**
   * Builds or updates roads along the found path
   */
  private void buildRoadsAlongPath(List<int[]> path, Building sourceBuilding,
      Building targetBuilding) {
    for (int i = 0; i < path.size(); i++) {
      int pathX = path.get(i)[0];
      int pathY = path.get(i)[1];

      // Skip the target building position
      if (pathX == targetBuilding.getX() && pathY == targetBuilding.getY()) {
        continue;
      }

      // Determine the direction for this road segment
      int dirIndex = -1;

      if (i < path.size() - 1) {
        dirIndex = determineDirection(pathX, pathY, path.get(i + 1)[0], path.get(i + 1)[1]);
      }

      // Create or update road
      createOrUpdateRoad(pathX, pathY, dirIndex);
    }
  }

  /**
   * Determines the direction index based on current and next positions
   */
  private int determineDirection(int currX, int currY, int nextX, int nextY) {
    if (nextX == currX && nextY == currY - 1)
      return 0; // up
    else if (nextX == currX && nextY == currY + 1)
      return 1; // down
    else if (nextX == currX - 1 && nextY == currY)
      return 2; // left
    else if (nextX == currX + 1 && nextY == currY)
      return 3; // right
    else
      return -1; // invalid direction
  }

  /**
   * Creates a new road or updates an existing one
   */
  private void createOrUpdateRoad(int x, int y, int dirIndex) {
    MapObject obj = getMapObject(x, y);

    // Create a new road or update an existing one
    if (obj == null) {
      // Create new road
      Road road = new Road(x, y);
      if (dirIndex != -1) {
        road.setDirectionAvailable(dirIndex, true);
      }
      addMapObject(road, x, y);
    } else if (obj instanceof Road) {
      // Update existing road
      Road road = (Road) obj;
      if (dirIndex != -1 && !road.isDirectionAvailable(dirIndex)) {
        road.setDirectionAvailable(dirIndex, true);
      }
    }
  }
}
