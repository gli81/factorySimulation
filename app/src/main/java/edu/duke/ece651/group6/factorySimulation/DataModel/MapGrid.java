package edu.duke.ece651.group6.factorySimulation.DataModel;

import java.util.Map;
import java.util.HashSet;
import java.util.Set;

public class MapGrid {
    private MapObject[][] grid;
    private int width;
    private int height;

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
    }

    public MapGrid(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new MapObject[height][width];
        this.xSet = new HashSet<>();
        this.ySet = new HashSet<>();
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
        return true;
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

    /**
     * Connect two buildings
     * 
     * @param sourceBuilding The source building
     * @param targetBuilding The target building
     * @return true if the buildings are connected, false otherwise
     */
    // TODO: Robin
    public Boolean connectBuildings(Building sourceBuilding, Building targetBuilding) {
        // get the coordinates of the buildings
        int x1 = sourceBuilding.getX();
        int y1 = sourceBuilding.getY();
        int x2 = targetBuilding.getX();
        int y2 = targetBuilding.getY();
        //
        return true;
    }

    /**
     * Get the shortest path between two buildings using BFS
     * 
     * @param sourceBuilding The source building
     * @param targetBuilding The target building
     * @return The shortest path between the two buildings
     */
    public int shortestPath(Building sourceBuilding, Building targetBuilding) {
        if (targetBuilding == null) {
            return 0;
        }
        if (sourceBuilding == targetBuilding) {
            return 0;
        }
        if (sourceBuilding.getY() == targetBuilding.getY()
                && Math.abs(sourceBuilding.getX() - targetBuilding.getX()) == 1) {
            return 0;
        }
        if (sourceBuilding.getX() == targetBuilding.getX()
                && Math.abs(sourceBuilding.getY() - targetBuilding.getY()) == 1) {
            return 0;
        }

        // keep this function return 0 if u r not done
        return 0;
        // TODO: Robin
    }

}