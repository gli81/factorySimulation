package edu.duke.ece651.group6.factorySimulation.DataModel;

import java.util.ArrayList;

abstract class BasicBuilding implements Building {

    /*
     * the name of the building
     */
    protected String name;

    // private RecipeQueue recipeQueue;
    private int buildingTimeStep;
    private boolean itemIsReady;
    private int lastTimeStep;
    private int produceTimeStep;
    // private Recipe currProduction;

    public BasicBuilding(String name) {
        this.name = name;

        this.buildingTimeStep = 0;
        this.itemIsReady = false;
        this.lastTimeStep = 0;
        this.produceTimeStep = 0;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public abstract void addRequest();

    @Override
    public abstract void removeRequest();

    @Override
    public abstract void processOneTimeStep();

    @Override
    public abstract void passResource();

}
