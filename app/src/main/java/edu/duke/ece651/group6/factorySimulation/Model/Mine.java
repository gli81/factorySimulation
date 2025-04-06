package edu.duke.ece651.group6.factorySimulation.Model;

import java.util.List;

public class Mine extends Building {
    public Mine(String name, Recipe mine) {
        super(name);
        this.recipes.add(mine);
    }

    public Mine(String name, Coordinate coord, Recipe mine) {
        super(name, coord);
        this.recipes.add(mine);
    }


    @Override
    public void setBuildingWithBuilding(List<Building> bLst) {}
}
