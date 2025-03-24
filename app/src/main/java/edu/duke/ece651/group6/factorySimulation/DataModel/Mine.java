package edu.duke.ece651.group6.factorySimulation.DataModel;

public class Mine extends BasicBuilding {

    private final Recipe outputItem;

    public Mine(String name, Recipe outputItem) {
        super(name);
        this.outputItem = outputItem;
    }

    public Recipe getOutputItem() {
        return this.outputItem;
    }

    public boolean isRecipeSupported(Recipe recipe) {
        return this.outputItem.equals(recipe);
    }

    public void addRequest(Recipe recipe, Building targetBuilding) {
        // print the request message
        super.addRequest(recipe, targetBuilding);
        int status = RequestItem.Status.READY;
        this.requestQueue.add(new RequestItem(recipe, status, targetBuilding));
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
