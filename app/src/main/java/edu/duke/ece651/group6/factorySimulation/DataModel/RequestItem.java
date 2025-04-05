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

    private final Recipe recipe;
    /*
     * 0: waiting for resource
     * 1: ready to be processed
     * 2: working on it
     * 3: done
     */
    private int status;
    private final Building targetBuilding;

    /**
     * if done, it is time left to deliver the product
     */
    private int deliveryTime;

    /**
     * if working, it is the time left to complete the request
     */
    private int workingTime;

    /**
     * the missing ingredients list
     * key: the ingredient
     * value: the quantity of the ingredient
     */
    private final Map<Recipe, Integer> missingIngredients;

    public RequestItem(Recipe recipe, int status, Building targetBuilding, int deliveryTime) {
        this.recipe = recipe;
        this.status = status;
        this.targetBuilding = targetBuilding;
        this.deliveryTime = deliveryTime;
        this.workingTime = recipe.getLatency();
        this.missingIngredients = new LinkedHashMap<>();
        for (Map.Entry<Recipe, Integer> ingredient : recipe.getIngredientsIterable()) {
            this.missingIngredients.put(ingredient.getKey(), ingredient.getValue());
        }
    }

    public Recipe getRecipe() {
        return this.recipe;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Building getTargetBuilding() {
        return this.targetBuilding;
    }

    public void setWorkingTime(int workingTime) {
        this.workingTime = workingTime;
    }

    public Map<Recipe, Integer> getMissingIngredients() {
        return this.missingIngredients;
    }

    public boolean isWaiting() {
        return this.status == Status.WAITING;
    }

    public boolean isReady() {
        return this.status == Status.READY;
    }

    public boolean isWorking() {
        return this.status == Status.WORKING;
    }

    public boolean isDone() {
        return this.status == Status.DONE;
    }

    public boolean isReadyToDeliver() {
        return this.status == Status.WORKING && this.workingTime == 0;
    }

    public boolean isDelivered() {
        return this.status == Status.DONE && this.deliveryTime == 0;
    }

    /**
     * if the request is working, decrease the working time
     * if the request is done, decrease the delivery time
     * if the request is waiting/ready, do nothing
     */
    public void doWork() {
        if (this.status == Status.WORKING) {
            this.workingTime--;
        } else if (this.status == Status.DONE) {
            this.deliveryTime--;
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
