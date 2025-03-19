package edu.duke.ece651.group6.factorySimulation.DataModel;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Factory extends BasicBuilding {

    private Type type;
    private Map<String, Integer> currStorage;
    private int remainingTimeStep;

    public Factory(String name, String utility, ArrayList<Building> sources, Type type) {
        super(name, utility, sources);
        this.type = type;
        this.currStorage = new HashMap<>();
        this.remainingTimeStep = 0;
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
