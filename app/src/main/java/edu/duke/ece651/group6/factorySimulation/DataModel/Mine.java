package edu.duke.ece651.group6.factorySimulation.DataModel;

import edu.duke.ece651.group6.factorySimulation.ProductionController;
import java.util.ArrayList;

public class Mine extends BasicBuilding {

    private final Recipe outputItem;
    private boolean isMining;

    public Mine(String name, Recipe outputItem) {
        super(name);
        this.outputItem = outputItem;
        this.isMining = false;
    }

    public Recipe getOutputItem() {
        return this.outputItem;
    }

    public boolean isRecipeSupported(Recipe recipe) {
        return this.outputItem.equals(recipe);
    }

    public void addRequest(Recipe recipe, Building targetBuilding) {
        // print the ingredient assignment message if the target is not user-requested
        if (ProductionController.getVerbose() >= 1 && targetBuilding != null) {
            System.out.println("[ingredient assignment]: " + recipe.getName() + " assigned to " + this.name
                    + " to deliver to " + targetBuilding.getName());
        }

        // if the request queue is empty, set the status to working
        // otherwise, set the status to ready, since no ingredient is needed
        int status = this.requestQueue.isEmpty() ? 2 : 1;
        this.requestQueue.add(new RequestItem(recipe, status, targetBuilding));
    }

    public void removeRequest(Recipe recipe, Building targetBuilding) {
    }

    public void passResource() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'passResource'");
    }

    public void processOneTimeStep() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'processOneTimeStep'");
    }

    /*
     * two mines are equal if they have the same name and output item
     * since name is unique for each mine
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Mine mine = (Mine) obj;
        boolean isNameEqual = this.name.equals(mine.name);
        boolean isOutputItemEqual = this.outputItem.equals(mine.outputItem);
        return isNameEqual && isOutputItemEqual;
    }

    @Override
    public int hashCode() {
        return 31 * name.hashCode() + outputItem.hashCode();
    }

    @Override
    public String toString() {
        return "Mine: { " + this.name + ", " + this.outputItem.getName() + " }\n";
    }
}
