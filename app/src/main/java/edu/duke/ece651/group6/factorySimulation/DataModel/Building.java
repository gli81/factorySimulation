package edu.duke.ece651.group6.factorySimulation.DataModel;

public interface Building {

    public String getName();

    public int getQueueSize();

    public boolean isRecipeSupported(Recipe recipe);

    public void addRequest(Recipe recipe, Building targetBuilding);

    public void removeRequest(Recipe recipe, Building targetBuilding);

    public void processOneTimeStep();

    public void passResource();

    @Override
    public String toString();

    @Override
    public boolean equals(Object obj);

    @Override
    public int hashCode();
}