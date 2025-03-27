package edu.duke.ece651.group6.factorySimulation.DataModel;

import java.util.ArrayList;
import java.util.Map;
import edu.duke.ece651.group6.factorySimulation.ProductionController;

public class ModelManager {
    private ArrayList<Type> types;
    private ArrayList<Recipe> recipes;
    private ArrayList<Building> buildings;

    /**
     * order of buildings to be processed
     * pair of recipe and building
     * 
     * first: index of the order in the user request queue
     * second: recipe and which building is responsible for the recipe
     */
    private ArrayList<Map.Entry<Integer, Map.Entry<Recipe, Building>>> userRequestQueue;

    public ModelManager() {
        this.types = new ArrayList<>();
        this.recipes = new ArrayList<>();
        this.buildings = new ArrayList<>();
        this.userRequestQueue = new ArrayList<>();
    }

    public void addType(Type type) {
        this.types.add(type);
    }

    public Type getType(String name) {
        for (Type type : this.types) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }

    public Iterable<Type> getAllTypesIterable() {
        return new ArrayList<>(this.types);
    }

    public void addRecipe(Recipe recipe) {
        this.recipes.add(recipe);
    }

    public Recipe getRecipe(String name) {
        for (Recipe recipe : this.recipes) {
            if (recipe.getName().equals(name)) {
                return recipe;
            }
        }
        return null;
    }

    public void addBuilding(Building building) {
        this.buildings.add(building);
    }

    public Building getBuilding(String name) {
        for (Building building : this.buildings) {
            if (building.getName().equals(name)) {
                return building;
            }
        }
        return null;
    }

    public Iterable<Building> getAllBuildingsIterable() {
        return new ArrayList<Building>(this.buildings);
    }

    public Iterable<Recipe> getAllRecipesIterable() {
        return new ArrayList<>(this.recipes);
    }

    public int getUserRequestQueueSize() {
        return this.userRequestQueue.size();
    }

    /**
     * Add a request to the buildings manager
     * validate the recipe and target building string inputs
     * 
     * @param recipeName         the name of the recipe
     * @param sourceBuildingName from which building the recipe is requested
     */
    public void addUserRequest(String recipeName, String sourceBuildingName) {
        Recipe recipe = getRecipe(recipeName);
        if (recipe == null) {
            throw new InvalidInputException("Recipe not found: " + recipeName);
        }
        Building sourceBuilding = getBuilding(sourceBuildingName);
        if (sourceBuilding == null) {
            throw new InvalidInputException("Building not found: " + sourceBuildingName);
        }
        // validate the recipe and target building
        if (!sourceBuilding.isRecipeSupported(recipe)) {
            throw new InvalidInputException(
                    "Building " + sourceBuildingName + " does not support recipe: " + recipeName);
        }

        // add the request to the user request queue
        this.userRequestQueue.add(Map.entry(ProductionController.getAndIncrementCurrRequestIndex(),
                Map.entry(recipe, sourceBuilding)));

        // the target building is null because target is the user
        sourceBuilding.addRequest(recipe, null);
    }

    public void processOneTimeStep() {
        for (Building building : this.buildings) {
            building.doWork();
        }

        for (Building building : this.buildings) {
            Recipe recipe = building.doDelivery();

            // if the returned recipe is not null,
            // it means the recipe is delivered to the user
            if (recipe != null) {
                // remove the request from the user request queue
                int index = -1;
                for (int i = 0; i < userRequestQueue.size(); i++) {
                    if (userRequestQueue.get(i).getValue().equals(Map.entry(recipe, building))) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {
                    int orderNumber = userRequestQueue.get(index).getKey();
                    this.userRequestQueue.remove(index);
                    System.out.println(
                            "[order complete] Order " + orderNumber + " completed ("
                                    + recipe.getName() + ") at time "
                                    + ProductionController.getCurrTimeStep());
                }
            }
        }

    }

    @Override
    public String toString() {

        StringBuilder typesString = new StringBuilder();
        this.types.forEach(type -> {
            typesString.append(type.toString());
        });

        StringBuilder recipesString = new StringBuilder();
        this.recipes.forEach(recipe -> {
            recipesString.append(recipe.toString());
        });

        StringBuilder buildingsString = new StringBuilder();
        this.buildings.forEach(building -> {
            buildingsString.append(building.toString());
        });

        return "Types:\n" + typesString.toString() + "\n" +
                "Recipes:\n" + recipesString.toString() + "\n" +
                "Buildings:\n" + buildingsString.toString();
    }
}
