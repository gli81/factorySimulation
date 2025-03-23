package edu.duke.ece651.group6.factorySimulation.DataModel;

import edu.duke.ece651.group6.factorySimulation.ProductionController;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Factory extends BasicBuilding {

    private final Type type;
    /*
     * the sources list of the building
     */
    private ArrayList<Building> sources;

    public Factory(String name, Type type) {
        super(name);
        this.type = type;
        this.sources = new ArrayList<>();
    }

    public Type getType() {
        return this.type;
    }

    public void addSource(Building source) {
        this.sources.add(source);
    }

    public Iterable<Building> getSourcesIterable() {
        return new ArrayList<Building>(this.sources);
    }

    public boolean isRecipeSupported(Recipe recipe) {
        return this.type.isRecipeSupported(recipe);
    }

    /**
     * Select the source building that can produce the recipe
     * 
     * @param sourceRecipe the recipe that the factory lacks
     * @return the source building that can produce the recipe
     */
    private Building sourceSelect(Recipe sourceRecipe) {

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

    public void addRequest(Recipe recipe, Building targetBuilding) {
        // print the request message
        super.addRequest(recipe, targetBuilding);

        this.requestQueue.add(new RequestItem(recipe, RequestItem.Status.WAITING, targetBuilding));

        // for each ingredient, add a request to the source building
        int ingredientCount = 0;
        // if the recipe has ingredients, print the source selection message
        if (recipe.getIngredientsIterable().iterator().hasNext()) {
            // print the source selection message
            if (ProductionController.getVerbose() >= 2) {
                System.out.println("[source selection]: " + this.name + " has request for " + recipe.getName()
                        + " on " + ProductionController.getCurrTimeStep());
            }
        }
        for (Map.Entry<Recipe, Integer> ingredient : recipe.getIngredientsIterable()) {
            for (int i = 0; i < ingredient.getValue(); i++) {
                // print the ingredient selection message
                if (ProductionController.getVerbose() >= 2) {
                    System.out.println(
                            "[" + this.name + ":" + recipe.getName() + ":" + ingredientCount + "] For ingredient "
                                    + ingredient.getKey().getName());
                }
                Building sourceBuilding = sourceSelect(ingredient.getKey());
                sourceBuilding.addRequest(ingredient.getKey(), this);
                ingredientCount++;
            }
        }
    }

    /*
     * add the recipe to the storage
     * and then check is any recipe ready,
     * if so, delete the resource needed from the storage
     */
    public void addStorage(Recipe ingredientDelivered) {

        boolean isDone = false;
        int readyCount = 0;
        // traverse the request queue and update the missing ingredients
        for (RequestItem requestItem : this.requestQueue) {
            // for every ready request, print the message
            if (requestItem.status == RequestItem.Status.READY) {
                System.out.println("    " + readyCount + ": " + requestItem.recipe.getName() + " is ready");
                readyCount++;
            }

            // for the waiting request, check the missing list
            if (requestItem.status == RequestItem.Status.WAITING && !isDone) {
                for (Map.Entry<Recipe, Integer> missingIngredient : requestItem.missingIngredients.entrySet()) {
                    // if the ingredient fits the missing list, update
                    if (missingIngredient.getKey().equals(ingredientDelivered)) {
                        if (missingIngredient.getValue() == 1) {
                            // if the ingredient is the last one, remove
                            requestItem.missingIngredients.remove(missingIngredient.getKey());
                            if (requestItem.missingIngredients.isEmpty()) {
                                requestItem.status = RequestItem.Status.READY;
                                System.out.println(
                                        "    " + readyCount + ": " + requestItem.recipe.getName() + " is ready");
                                readyCount++;
                            }
                        } else {
                            // if not, decrease the quantity
                            requestItem.missingIngredients.put(missingIngredient.getKey(),
                                    missingIngredient.getValue() - 1);
                        }
                        isDone = true;
                        break;
                    }
                }
            }
        }

    }

    /*
     * two factories are equal if they have the same name and type
     * since name is unique for each factory
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Factory factory = (Factory) o;
        boolean isNameEqual = this.name.equals(factory.name);
        boolean isTypeEqual = this.type.equals(factory.type);

        return isNameEqual && isTypeEqual;
    }

    @Override
    public int hashCode() {
        return 31 * type.hashCode() + name.hashCode();
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
        return "Factory: { " + this.name + ", " + this.type.getName() + ", { " + sourcesString.toString() + "} }\n";
    }
}
