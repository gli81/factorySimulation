package edu.duke.ece651.group6.factorySimulation.DataModel;

import edu.duke.ece651.group6.factorySimulation.ProductionController;
import java.util.ArrayList;
import java.util.Map;

abstract class BasicBuilding implements Building {

    /*
     * the name of the building
     */
    protected final String name;

    protected ArrayList<RequestItem> requestQueue;

    protected int workingTimeStep;

    public BasicBuilding(String name) {
        this.name = name;
        this.workingTimeStep = 0;
        this.requestQueue = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public int getQueueSize() {
        return this.requestQueue.size();
    }

    public abstract boolean isRecipeSupported(Recipe recipe);

    public void addRequest(Recipe recipe, Building targetBuilding) {
        // print the ingredient assignment message if the target is not user-requested
        if (ProductionController.getVerbose() >= 1 && targetBuilding != null) {
            int orderNumber = ProductionController.getAndIncrementCurrRequestIndex();
            if (ProductionController.getVerbose() >= 3) {
                System.out
                        .println("TEST:[Order Number: " + orderNumber + "]");
            }
            System.out.println(
                    "[ingredient assignment]: " + recipe.getName() + " assigned to " + this.name + " to deliver to "
                            + targetBuilding.getName());
        }
    }

    /**
     * select the recipe to work on
     * print the recipe selection message
     * 
     * @return the index of the recipe to work on,
     *         -1 if all the recipes are not ready
     */
    private int recipeSelection() {
        if (ProductionController.getVerbose() >= 2)
            System.out.println("[recipe selection]: " + this.name + " has fifo on cycle "
                    + ProductionController.getCurrTimeStep());

        int index = 0;
        for (RequestItem request : this.requestQueue) {
            if (request.status == RequestItem.Status.READY) {
                if (ProductionController.getVerbose() >= 2)
                    System.out.println("    " + index + ": " + request.recipe.getName() + " is ready");
            } else if (request.status == RequestItem.Status.WAITING) {

                // get the all the missing ingredients
                StringBuilder waitingOn = new StringBuilder("{");

                // traverse the ingredients and get the missing number
                for (Map.Entry<Recipe, Integer> missingIngredient : request.missingIngredients.entrySet()) {
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
                    System.out.println("    " + index + ": " + request.recipe.getName()
                            + " is not ready, waiting on " + waitingOn);
            }
            index++;
        }

        if (this.requestQueue.get(0).status == RequestItem.Status.READY) {
            if (ProductionController.getVerbose() >= 2)
                System.out.println("    Selecting " + 0);
            return 0;
        }

        return -1;
    }

    public void doWork() {
        // if the request queue is empty, do nothing
        if (this.requestQueue.isEmpty()) {
            return;
        }

        // if the first request is not working, select the recipe to work on
        if (this.requestQueue.get(0).status != RequestItem.Status.WORKING) {
            if (this.recipeSelection() == -1) {
                // if all the recipes are not ready, do nothing
                return;
            } else {
                // if there is a recipe to work on, set the status to working
                this.requestQueue.get(0).status = RequestItem.Status.WORKING;
            }
        }

        // get the first request in the queue
        RequestItem request = this.requestQueue.get(0);
        if (request.status == RequestItem.Status.WORKING) {
            this.workingTimeStep++;

            // check if the work is done
            int latency = request.recipe.getLatency();
            if (this.workingTimeStep == latency) {
                request.status = RequestItem.Status.DONE;
                this.workingTimeStep = 0;
            }
        }

    }

    /**
     * deliver the first request in the queue
     * 
     * @return the recipe delivered to user,
     *         null if it is not the user request or the request is not done
     */
    public Recipe doDelivery() {
        // if the request queue is empty or the first request is not done, do nothing
        if (this.requestQueue.isEmpty() ||
                this.requestQueue.get(0).status != RequestItem.Status.DONE) {
            return null;
        }

        // get the target building and the ingredient
        Building targetBuilding = this.requestQueue.get(0).targetBuilding;
        Recipe ingredient = this.requestQueue.get(0).recipe;

        // remove the first request from the queue since it is done
        this.requestQueue.remove(0);

        // if the target is the user, it means the request is done
        if (targetBuilding == null) {
            return ingredient;
        }

        // the target building must be a factory
        if (!(targetBuilding instanceof Factory)) {
            throw new IllegalArgumentException("Target building must be a factory");
        }

        // add the ingredient to the factory
        Factory factory = (Factory) targetBuilding;
        if (ProductionController.getVerbose() >= 1) {
            System.out.println("[ingredient delivered]: " + ingredient.getName() + " to " + targetBuilding.getName()
                    + " from " + this.getName() + " on cycle " + ProductionController.getCurrTimeStep());
        }
        factory.addStorage(ingredient);
        return null;
    }

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();
}
