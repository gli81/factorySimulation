package edu.duke.ece651.group6.factorySimulation.DataModel;

import java.util.ArrayList;

public class Mine extends BasicBuilding {

    private Recipe outputItem;
    private boolean isMining;

    public Mine(String name, Recipe outputItem) {
        super(name);
        this.outputItem = outputItem;
        this.isMining = false;
    }

    @Override
    public String toString() {
        return "Mine: { " + this.name + ", " + this.outputItem.getOutputItem() + " }\n";
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
