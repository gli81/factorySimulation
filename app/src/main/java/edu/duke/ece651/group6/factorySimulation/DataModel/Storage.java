package edu.duke.ece651.group6.factorySimulation.DataModel;

import java.util.ArrayList;

import edu.duke.ece651.group6.factorySimulation.ProductionController;

public class Storage extends Building {
    private Recipe storedRecipe;
    private int capacity;
    private double priority;
    /*
     * the sources list of the building
     */
    private ArrayList<Building> sources;

    /*
     * the count of the current inventory
     */
    private int stock;
    /*
     * the count of the outstanding requests
     */
    private int outstanding;

    public Storage(String name, Recipe storedRecipe, int capacity, double priority) {
        super(name, -1, -1);
        this.storedRecipe = storedRecipe;
        this.capacity = capacity;
        this.priority = priority;
        this.sources = new ArrayList<>();
        this.stock = 0;
    }

    public Storage(String name, Recipe storedRecipe, int capacity, double priority, int x, int y) {
        super(name, x, y);
        this.storedRecipe = storedRecipe;
        this.capacity = capacity;
        this.priority = priority;
        this.sources = new ArrayList<>();
    }

    public Recipe getStoredRecipe() {
        return this.storedRecipe;
    }

    public void addSource(Building source) {
        this.sources.add(source);
    }

    public Iterable<Building> getSourcesIterable() {
        return new ArrayList<Building>(this.sources);
    }

    public int getStock() {
        return this.stock;
    }

    /**
     * Add stock to the storage
     */
    public void increaseStock() {
        this.outstanding--;
        if (this.getQueueSize() == 0) {
            this.stock++;
        } else {
            // find the first waiting request and set it to ready
            for (RequestItem requestItem : this.getRequestQueue()) {
                if (requestItem.getStatus() == RequestItem.Status.WAITING) {
                    requestItem.setStatus(RequestItem.Status.READY);
                    break;
                }
            }
        }
    }

    public void decreaseStock() {
        this.stock--;
    }

    /**
     * Check if the storage should make a request
     * 
     * @return the source building to make the request
     *         null if no need to make a request
     */
    public Building shouldMakeRequest() {
        int r = capacity - stock - outstanding + this.getQueueSize();
        if (r <= 0) {
            return null;
        } else {
            int f = (int) Math.ceil((double) (capacity * capacity) / r / priority);
            boolean shouldMakeRequest = ProductionController.getCurrTimeStep() % f == 0;
            if (shouldMakeRequest) {
                outstanding++;
                return this.sourceSelect(this.storedRecipe);
            } else {
                return null;
            }
        }
    }

    /**
     * Get the size of the request queue
     * does not count the done/delivering requests
     * 
     * @return the size of the request queue
     */
    @Override
    public int getQueueSize() {
        return super.getQueueSize() - this.stock;
    }

    /**
     * Deliver the items received from the last cycle
     */
    @Override
    public void doWork() {
        for (RequestItem requestItem : this.getRequestQueue()) {
            if (requestItem.getStatus() == RequestItem.Status.READY) {
                requestItem.setStatus(RequestItem.Status.DONE);
            }
        }
    }

    @Override
    public boolean isRecipeSupported(Recipe recipe) {
        return this.storedRecipe.equals(recipe);
    }

    @Override
    public int hashCode() {
        return 31 * this.storedRecipe.hashCode() + this.capacity + (int) this.priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Storage storage = (Storage) o;

        boolean isRecipeEqual = this.storedRecipe.equals(storage.storedRecipe);
        boolean isCapacityEqual = this.capacity == storage.capacity;
        boolean isPriorityEqual = this.priority == storage.priority;

        return isRecipeEqual && isCapacityEqual && isPriorityEqual;
    }

    @Override
    public String toString() {
        StringBuilder sourcesString = new StringBuilder();
        this.sources.forEach(source -> {
            sourcesString.append(source.getName()).append(", ");
        });
        // remove the last comma
        if (sourcesString.length() > 0) {
            sourcesString.deleteCharAt(sourcesString.length() - 2);
        }
        return "Storage: { " + this.getName() + ", " + this.storedRecipe.getName() + ": " + this.capacity + ", "
                + this.priority + ", { " + sourcesString.toString() + "} }\n";
    }
}
