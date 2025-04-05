package edu.duke.ece651.group6.factorySimulation.DataModel;

import edu.duke.ece651.group6.factorySimulation.ProductionController;
import java.util.ArrayList;
import java.util.Map;

abstract class Building extends MapObject {

    /*
     * the name of the building
     */
    private final String name;

    private ArrayList<RequestItem> requestQueue;

    private boolean isWorking;

    public Building(String name, int x, int y) {
        super(x, y);
        this.name = name;
        this.requestQueue = new ArrayList<>();
        this.isWorking = false;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<RequestItem> getRequestQueue() {
        return this.requestQueue;
    }

    public int getQueueSize() {
        return this.requestQueue.size();
    }

    public boolean isWorking() {
        return this.isWorking;
    }

    public void setWorkingStatus(boolean isWorking) {
        this.isWorking = isWorking;
    }

    public abstract boolean isRecipeSupported(Recipe recipe);

    public void addRequest(RequestItem requestItem) {
        this.requestQueue.add(requestItem);
    }

    /**
     * select a request in the queue to work on
     * print the recipe selection message
     * 
     * @return the index of the request to work on,
     *         -1 if all the requests are not ready
     */
    private int recipeSelection() {
        if (ProductionController.getVerbose() >= 2)
            System.out.println("[recipe selection]: " + this.name + " has fifo on cycle "
                    + ProductionController.getCurrTimeStep());

        int firstReadyIndex = -1;

        // traverse the request queue to print the recipe selection message
        int index = 0;
        for (RequestItem request : this.requestQueue) {
            if (request.isReady()) {
                if (ProductionController.getVerbose() >= 2)
                    System.out.println("    " + index + ": " + request.getRecipe().getName() + " is ready");
                if (firstReadyIndex == -1) {
                    firstReadyIndex = index;
                }
            } else if (request.isWaiting()) {
                // get the all the missing ingredients
                StringBuilder waitingOn = new StringBuilder("{");

                // traverse the ingredients and get the missing number
                for (Map.Entry<Recipe, Integer> missingIngredient : request.getMissingIngredients().entrySet()) {
                    int missingNumber = missingIngredient.getValue();
                    if (missingNumber > 1) {
                        waitingOn.append(missingNumber + "x ");
                    }
                    waitingOn.append(missingIngredient.getKey().getName() + ", ");
                }

                waitingOn.delete(waitingOn.length() - 1, waitingOn.length());
                waitingOn.delete(waitingOn.length() - 1, waitingOn.length());
                waitingOn.append("}");

                if (ProductionController.getVerbose() >= 2)
                    System.out.println("    " + index + ": " + request.getRecipe().getName()
                            + " is not ready, waiting on " + waitingOn);
            }
            index++;
        }

        // if there is a ready request, select it
        if (firstReadyIndex != -1) {
            if (ProductionController.getVerbose() >= 2)
                System.out.println("    Selecting " + firstReadyIndex);
            return firstReadyIndex;
        }

        return -1;
    }

    public void doWork() {
        // if the request queue is empty, do nothing
        if (this.requestQueue.isEmpty()) {
            return;
        }

        // if the building is not working, select the recipe to work on
        if (!this.isWorking()) {
            int index = this.recipeSelection();
            if (index == -1) {
                // if all the recipes are not ready, do nothing
                return;
            } else {
                // if there is a recipe to work on, set the status to working
                this.requestQueue.get(index).setStatus(RequestItem.Status.WORKING);
                this.setWorkingStatus(true);
            }
        }

        // do delivery/working/waiting for all the requests
        for (RequestItem request : this.requestQueue) {
            request.doWork();
            // if the request is done, set the status to done
            if (request.isDone()) {
                request.setStatus(RequestItem.Status.DONE);
                this.setWorkingStatus(false);
            }
        }
    }

    /**
     * deliver the first request in the queue
     * 
     * @return the recipe delivered to user,
     *         null if nothing to deliver to USER
     */
    public Recipe doDelivery() {
        for (RequestItem request : this.requestQueue) {
            if (request.isDelivered()) {
                // get the target building and the ingredient
                Building targetBuilding = request.getTargetBuilding();
                Recipe ingredient = request.getRecipe();

                // remove the request from the queue since it is delivered
                this.requestQueue.remove(request);

                if (targetBuilding == null) {
                    // if the target is the user, it means the request is done
                    // and nothing else to do
                    return ingredient;
                } else if (targetBuilding instanceof Factory) {
                    // if the target is a factory, add the ingredient to the factory
                    Factory factory = (Factory) targetBuilding;
                    if (ProductionController.getVerbose() >= 1) {
                        System.out.println("[ingredient delivered]: " + ingredient.getName() + " to "
                                + targetBuilding.getName()
                                + " from " + this.getName() + " on cycle " + ProductionController.getCurrTimeStep());
                    }
                    factory.addStorage(ingredient);
                    return null;
                } else if (targetBuilding instanceof Storage) {
                    // if the target is a storage, add the ingredient to the storage
                    // TODO: hahah
                    return null;
                } else {
                    // if the target is not a factory or a storage, throw an exception
                    throw new IllegalArgumentException("Target building must be a factory or a storage");
                }
            }
        }
        return null;
    }

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();
}
