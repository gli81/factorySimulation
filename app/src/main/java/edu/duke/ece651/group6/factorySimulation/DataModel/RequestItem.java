package edu.duke.ece651.group6.factorySimulation.DataModel;

import java.util.Map;
import java.util.LinkedHashMap;

class RequestItem {
    public static class Status {
        public static final int WAITING = 0;
        public static final int READY = 1;
        public static final int WORKING = 2;
        public static final int DONE = 3;
    }

    protected final Recipe recipe;
    /*
     * 0: waiting for resource
     * 1: ready to be processed
     * 2: working on it
     * 3: done
     */
    protected int status;
    protected final Building targetBuilding;

    /**
     * the missing ingredients list
     * key: the ingredient
     * value: the quantity of the ingredient
     */
    protected final Map<Recipe, Integer> missingIngredients;

    public RequestItem(Recipe recipe, int status, Building targetBuilding) {
        this.recipe = recipe;
        this.status = status;
        this.targetBuilding = targetBuilding;
        this.missingIngredients = new LinkedHashMap<>();
        for (Map.Entry<Recipe, Integer> ingredient : recipe.getIngredientsIterable()) {
            this.missingIngredients.put(ingredient.getKey(), ingredient.getValue());
        }
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
