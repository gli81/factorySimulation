package edu.duke.ece651.group6.factorySimulation.DataModel;

public interface Building {

    public String getName();

    public void processOneTimeStep();

    public void addRequest();

    public void removeRequest();

    public void passResource();

    @Override
    public String toString();
}