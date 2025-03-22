package edu.duke.ece651.group6.factorySimulation.DataModel;

import java.util.ArrayList;

abstract class BasicBuilding implements Building {

    /*
     * the name of the building
     */
    protected final String name;

    protected ArrayList<RequestItem> requestQueue;

    protected int buildingTimeStep;
    protected boolean itemIsReady;
    protected int lastTimeStep;
    protected int produceTimeStep;
    protected Recipe currProduction;

    public BasicBuilding(String name) {
        this.name = name;

        this.buildingTimeStep = 0;
        this.itemIsReady = false;
        this.lastTimeStep = 0;
        this.produceTimeStep = 0;
        this.requestQueue = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public int getQueueSize() {
        return this.requestQueue.size();
    }

    public abstract boolean isRecipeSupported(Recipe recipe);

    public abstract void addRequest(Recipe recipe, Building targetBuilding);

    public abstract void removeRequest(Recipe recipe, Building targetBuilding);

    public abstract void processOneTimeStep();

    public abstract void passResource();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();
}
