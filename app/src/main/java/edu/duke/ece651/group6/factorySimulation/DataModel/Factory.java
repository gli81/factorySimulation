package edu.duke.ece651.group6.factorySimulation.DataModel;

import edu.duke.ece651.group6.factorySimulation.ProductionController;
import java.util.ArrayList;
import java.util.Map;

public class Factory extends Building {

    private final Type type;

    public Factory(String name, Type type) {
        super(name, -1, -1);
        this.type = type;
    }

    public Factory(String name, Type type, int x, int y) {
        super(name, x, y);
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }

    public boolean isRecipeSupported(Recipe recipe) {
        return this.type.isRecipeSupported(recipe);
    }

    /*
     * add the recipe to the storage
     * and then check is any recipe ready,
     * if so, delete the resource needed from the storage
     */
    public void addStorage(Recipe ingredientDelivered) {

        boolean isUpdated = false;
        int readyCount = 0;
        // traverse the request queue and update the missing ingredients
        for (RequestItem requestItem : this.getRequestQueue()) {
            // for every ready request, print the message
            if (requestItem.isReady()) {
                if (ProductionController.getVerbose() > 0) {
                    System.out.println("    " + readyCount + ": " + requestItem.getRecipe().getName() + " is ready");
                }
                readyCount++;
            }

            // for the waiting request, check the missing list
            if (requestItem.isWaiting() && !isUpdated) {
                for (Map.Entry<Recipe, Integer> missingIngredient : requestItem.getMissingIngredients().entrySet()) {
                    // if the ingredient fits the missing list, update
                    if (missingIngredient.getKey().equals(ingredientDelivered)) {
                        if (missingIngredient.getValue() == 1) {
                            // if the ingredient is the last one, remove
                            requestItem.getMissingIngredients().remove(missingIngredient.getKey());
                            if (requestItem.getMissingIngredients().isEmpty()) {
                                requestItem.setStatus(RequestItem.Status.READY);
                                if (ProductionController.getVerbose() > 0) {
                                    System.out.println(
                                            "    " + readyCount + ": " + requestItem.getRecipe().getName()
                                                    + " is ready");
                                }
                                readyCount++;
                            }
                        } else {
                            // if not, decrease the quantity
                            requestItem.getMissingIngredients().put(missingIngredient.getKey(),
                                    missingIngredient.getValue() - 1);
                        }
                        isUpdated = true;
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
        boolean isNameEqual = this.getName().equals(factory.getName());
        boolean isTypeEqual = this.getType().equals(factory.getType());

        return isNameEqual && isTypeEqual;
    }

    @Override
    public int hashCode() {
        return 31 * this.getType().hashCode() + this.getName().hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sourcesString = new StringBuilder();
        this.getSourcesIterable().forEach(source -> {
            sourcesString.append(source.getName()).append(", ");
        });
        // remove the last comma
        if (sourcesString.length() > 0) {
            sourcesString.deleteCharAt(sourcesString.length() - 2);
        }
        return "Factory: { " + this.getName() + ", " + this.getType().getName() + ", { " + sourcesString.toString()
                + "} }\n";
    }
}
