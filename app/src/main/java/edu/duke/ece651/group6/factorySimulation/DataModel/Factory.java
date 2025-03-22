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

    private Map<Recipe, Integer> storage;
    private int remainingTimeStep;

    public Factory(String name, Type type) {
        super(name);
        this.type = type;
        this.storage = new HashMap<>();
        this.remainingTimeStep = 0;
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

    // private boolean isIngredientReady(Recipe ingredient, int quantity) {
    // if (this.storage.containsKey(ingredient)) {
    // return this.storage.get(ingredient) >= quantity;
    // }
    // return false;
    // }

    public void addRequest(Recipe recipe, Building targetBuilding) {
        // print the ingredient assignment message if the target is not user-requested
        if (ProductionController.getVerbose() >= 1 && targetBuilding != null) {
            System.out.println("[ingredient assignment]: " + recipe.getName() + " assigned to " + this.name
                    + " to deliver to " + targetBuilding.getName());
        }

        this.requestQueue.add(new RequestItem(recipe, 0, targetBuilding));

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
