package edu.duke.ece651.group6.factorySimulation.DataModel;

import java.util.ArrayList;
import java.util.Map;

/*
 * Manager of all buildings in the factory
 */
public class BuildingsManager {

    private ArrayList<Building> buildings;

    /**
     * order of buildings to be processed
     * pair of recipe and building
     * 
     * first: recipe to be processed
     * second: building to process the recipe / responsible for the recipe
     */
    private ArrayList<Map.Entry<Recipe, Building>> orderQueue;

    public BuildingsManager() {
        this.buildings = new ArrayList<>();
        this.orderQueue = new ArrayList<>();
    }

    public void addBuilding(Building building) {
        this.buildings.add(building);
    }

    public void removeBuilding(Building building) {
        this.buildings.remove(building);
    }

    public Iterable<Building> getAllBuildingsIterable() {
        return new ArrayList<Building>(this.buildings);
    }

    public Building getBuilding(String name) {
        for (Building building : this.buildings) {
            if (building.getName().equals(name)) {
                return building;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder buildingsString = new StringBuilder();
        this.buildings.forEach(building -> {
            buildingsString.append(building.toString());
        });
        return buildingsString.toString();
    }

    public void addOrder(Recipe recipe, Building building) {
        this.orderQueue.add(Map.entry(recipe, building));
    }

    public void removeOrder(Recipe recipe, Building building) {
        this.orderQueue.remove(Map.entry(recipe, building));
    }

    public void processOneTimeStep() {
        // TODO: implement
    }

    // public void removeOrder(Order order) {

    // }

    // public Building sourceSelect(Building building, String itemName) {

    // }

}