package edu.duke.ece651.group6.factorySimulation.DataModel;

public interface Building {

    public void processOneTimeStep();

    public void addRequest();

    public void removeRequest();

    public void passResource();
}