package edu.duke.ece651.group6.factorySimulation.DataModel;

import edu.duke.ece651.group6.factorySimulation.ProductionController;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public abstract class Building extends MapObject {

    /*
     * the name of the building
     */
    private final String name;

    private ArrayList<RequestItem> requestQueue;

    private boolean isWorking;

    private ArrayList<Building> sources;

    private ArrayList<Building> connectedBuildings;

    public Building(String name, int x, int y) {
        super(x, y);
        this.name = name;
        this.requestQueue = new ArrayList<>();
        this.isWorking = false;
        this.sources = new ArrayList<>();
        this.connectedBuildings = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<RequestItem> getRequestQueue() {
        return this.requestQueue;
    }

    /**
     * Get the size of the request queue
     * does not count the done/delivering requests
     * 
     * @return the size of the request queue
     */
    public int getQueueSize() {
        int size = 0;
        for (RequestItem request : this.requestQueue) {
            if (!request.isDone()) {
                size++;
            }
        }
        return size;
    }

    public void addSource(Building source) {
        this.sources.add(source);
    }

    public Iterable<Building> getSourcesIterable() {
        return new ArrayList<Building>(this.sources);
    }

    public boolean isWorking() {
        return this.isWorking;
    }

    public void setWorkingStatus(boolean isWorking) {
        this.isWorking = isWorking;
    }

    public abstract boolean isRecipeSupported(Recipe recipe);

    public void addConnectedBuildings(Building building) {
        this.connectedBuildings.add(building);
    }

    public List<Building> getConnectedBuildings() {
        return new ArrayList<Building>(this.connectedBuildings);
    }

    public void addRequest(RequestItem requestItem) {
        this.requestQueue.add(requestItem);
    }

    /**
     * Select the source building that can produce the recipe
     * 
     * @param sourceRecipe the recipe that the factory lacks
     * @return the source building that can produce the recipe
     */
    public Building sourceSelect(Recipe sourceRecipe) {

        ArrayList<Building> availableSources = new ArrayList<>();

        // find all available sources for the recipe
        for (Building sourceBuilding : this.sources) {
            if (sourceBuilding.isRecipeSupported(sourceRecipe)) {
                availableSources.add(sourceBuilding);
            }
        }

        // find the source building with the least request queue size
        int minRequestQueueSize = Integer.MAX_VALUE;
        Building selectedSource = null;
        for (Building sourceBuilding : availableSources) {
            if (ProductionController.getVerbose() >= 2) {
                System.out.println("    " + sourceBuilding.getName() + ":" + sourceBuilding.getQueueSize());
            }
            if (sourceBuilding.getQueueSize() < minRequestQueueSize) {
                minRequestQueueSize = sourceBuilding.getQueueSize();
                selectedSource = sourceBuilding;
            }
        }

        if (ProductionController.getVerbose() >= 2 && selectedSource != null) {
            System.out.println("    Selecting " + selectedSource.getName());
        }
        return selectedSource;
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
        int printIndex = 0;
        int indexInQueue = 0;
        for (RequestItem request : this.requestQueue) {
            if (request.isReady()) {
                if (ProductionController.getVerbose() >= 2)
                    System.out.println("    " + printIndex + ": " + request.getRecipe().getName() + " is ready");
                if (firstReadyIndex == -1) {
                    firstReadyIndex = indexInQueue;
                }
                printIndex++;

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
                    System.out.println("    " + printIndex + ": " + request.getRecipe().getName()
                            + " is not ready, waiting on " + waitingOn);
                printIndex++;

            }
            indexInQueue++;
        }

        // if there is a ready request, select it
        if (firstReadyIndex != -1) {
            if (ProductionController.getVerbose() >= 2)
                System.out.println("    Selecting " + firstReadyIndex);
            return firstReadyIndex;
        }

        return -1;
    }

    /**
     * Do the work for the building
     * 
     * if the request queue is empty or all the requests are on delivery, do nothing
     * if the building is not working, select a recipe to work on
     * do delivery/working/waiting for all the requests
     */
    public void doWork() {
        // if the request queue is empty, do nothing
        if (this.requestQueue.isEmpty()) {
            return;
        }

        boolean isAllOnDelivery = true;
        for (RequestItem request : this.requestQueue) {
            if (!request.isDone()) {
                isAllOnDelivery = false;
            }
        }

        // if the building is not working, select the recipe to work on
        if (!this.isWorking() && !isAllOnDelivery) {
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
            if (request.isReadyToDeliver()) {
                request.setStatus(RequestItem.Status.DONE);
                this.setWorkingStatus(false);
            }
        }
    }

    /**
     * deliver the first request in the queue
     * 
     * if the request is delivered to the user, return the recipe
     * if the request is delivered to a building, add the recipe to the storage
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

                // if the target is the user, it means the request is done
                if (targetBuilding == null) {
                    return ingredient;
                }

                // print the delivery message
                if (ProductionController.getVerbose() >= 1) {
                    System.out.println("[ingredient delivered]: " + ingredient.getName() + " to "
                            + targetBuilding.getName()
                            + " from " + this.getName() + " on cycle " + ProductionController.getCurrTimeStep());
                }

                if (targetBuilding instanceof Factory) {
                    ((Factory) targetBuilding).addStorage(ingredient);
                } else if (targetBuilding instanceof Storage) {
                    ((Storage) targetBuilding).increaseStock();
                } else {
                    throw new IllegalArgumentException("Target building must be a factory or a storage");
                }
                return null;
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
