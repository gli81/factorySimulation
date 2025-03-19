package edu.duke.ece651.group6.factorySimulation.DataModel;

import java.util.ArrayList;

abstract class BasicBuilding implements Building {

    /*
     * the name of the building
     */
    private String name;

    /*
     * whether to be a "factory" or a "mine"
     */
    private String utility;

    /*
     * the sources list of the building
     */
    private ArrayList<Building> sources;

    // private RecipeQueue recipeQueue;
    private int buildingTimeStep;
    private boolean itemIsReady;
    private int lastTimeStep;
    private int produceTimeStep;
    // private Recipe currProduction;

    public BasicBuilding(String name, String utility, ArrayList<Building> sources) {
        this.name = name;
        this.utility = utility;
        this.sources = sources;

        this.buildingTimeStep = 0;
        this.itemIsReady = false;
        this.lastTimeStep = 0;
        this.produceTimeStep = 0;
    }

    public void setSources(ArrayList<Building> sources) {
        this.sources = sources;
    }

    public String getName() {
        return this.name;
    }

    public String getUtility() {
        return this.utility;
    }

    public Iterable<Building> getSourcesIterable() {
        return new ArrayList<Building>(this.sources);
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
