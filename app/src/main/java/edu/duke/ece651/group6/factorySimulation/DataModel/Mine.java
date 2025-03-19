package edu.duke.ece651.group6.factorySimulation.DataModel;

import java.util.ArrayList;

public class Mine extends BasicBuilding {

    private String mineItem;
    private boolean isMining;

    public Mine(String name, String utility, ArrayList<Building> sources, String mineItem) {
        super(name, utility, sources);
        this.mineItem = mineItem;
        this.isMining = false;
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
