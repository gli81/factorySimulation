package edu.duke.ece651.group6.factorySimulation.Model;

import java.util.ArrayList;
import java.util.List;

public class Factory extends Building {
    private final Type type;
    private final List<String> srcStr;
    private final List<Building> srcs;
    

    public Factory(String name, Type type, List<String> srcStr) {
        super(name);
        this.type = type;
        this.type.getRecipes().forEachRemaining(this.recipes::add);
        this.srcStr = new ArrayList<String>(srcStr);
        this.srcs = new ArrayList<Building>();
    }

    public Factory(
        String name, Coordinate coord, Type type,
        List<String> srcStr
    ) {
        super(name, coord);
        this.type = type;
        this.type.getRecipes().forEachRemaining(this.recipes::add);
        this.srcStr = new ArrayList<String>(srcStr);
        this.srcs = new ArrayList<Building>();
    }


    public void request(String recipeName, String building) {
        
    }

    @Override
    public void setBuildingWithBuilding(List<Building> bLst) {
        for (String src : srcStr) {
            // rule checker ensures src can be found
            Building building = Building.getBuildingFromListByName(bLst, src);
            this.srcs.add(building);
        }
    }
}
