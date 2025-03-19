package edu.duke.ece651.group6.factorySimulation.DataModel;

import java.util.ArrayList;

/*
 * Manager of all buildings in the factory
 */
public class BuildingsManager {

    private ArrayList<Building> buildings;
    private int currTimeStep;
    // private OrderQueue orderQueue;

    public BuildingsManager() {
        this.buildings = new ArrayList<>();
        // this.currTimeStep = 0;
    }

    public void addBuilding(Building building) {
        this.buildings.add(building);
    }

    public void removeBuilding(Building building) {
        this.buildings.remove(building);
    }

    public Iterable<Building> getBuildingsIterable() {
        return new ArrayList<Building>(this.buildings);
    }

    // public void addOrder(Order order) {

    // }

    // public void removeOrder(Order order) {

    // }

    // public Building sourceSelect(Building building, String itemName) {

    // }

}