package edu.duke.ece651.group6.factorySimulation.DataModel;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Factory extends BasicBuilding {

    private Type type;
    /*
     * the sources list of the building
     */
    private ArrayList<Building> sources;

    private Map<Recipe, Integer> currStorage;
    private int remainingTimeStep;

    public Factory(String name, Type type) {
        super(name);
        this.type = type;
        this.currStorage = new HashMap<>();
        this.remainingTimeStep = 0;
        this.sources = new ArrayList<>();
    }

    public void addSource(Building source) {
        this.sources.add(source);
    }

    public Iterable<Building> getSourcesIterable() {
        return new ArrayList<Building>(this.sources);
    }

    @Override
    public String toString() {
        StringBuilder sourcesString = new StringBuilder();
        this.sources.forEach(source -> {
            sourcesString.append(source.getName()).append(", ");
        });
        // remove the last comma
        if (sourcesString.length() > 0) {
            sourcesString.deleteCharAt(sourcesString.length() - 2);
        }
        return "Factory: { " + this.name + ", " + this.type.getName() + ", { " + sourcesString.toString() + "} }\n";
    }

    @Override
    public void addRequest() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addRequest'");
    }

    @Override
    public void removeRequest() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeRequest'");
    }

    @Override
    public void passResource() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'passResource'");
    }

    @Override
    public void processOneTimeStep() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'processOneTimeStep'");
    }

}
