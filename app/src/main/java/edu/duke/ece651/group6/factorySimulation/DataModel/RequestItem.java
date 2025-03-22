package edu.duke.ece651.group6.factorySimulation.DataModel;

class RequestItem {
    private final Recipe recipe;
    /*
     * 0: waiting
     * 1: ready
     * 2: working
     */
    private final int status;
    private final Building targetBuilding;

    public RequestItem(Recipe recipe, int status, Building targetBuilding) {
        this.recipe = recipe;
        this.status = status;
        this.targetBuilding = targetBuilding;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public int getStatus() {
        return status;
    }

    public Building getTargetBuilding() {
        return targetBuilding;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RequestItem that = (RequestItem) o;

        boolean isStatusEqual = this.status == that.status;
        boolean isRecipeEqual = this.recipe.equals(that.recipe);
        boolean isTargetBuildingEqual = this.targetBuilding.equals(that.targetBuilding);

        return isStatusEqual && isRecipeEqual && isTargetBuildingEqual;
    }

    @Override
    public int hashCode() {
        return 31 * recipe.hashCode() + status + targetBuilding.hashCode();
    }

    @Override
    public String toString() {
        return "Request[recipe=" + recipe +
                ", status=" + status +
                ", targetBuilding=" + targetBuilding + "]";
    }
}
