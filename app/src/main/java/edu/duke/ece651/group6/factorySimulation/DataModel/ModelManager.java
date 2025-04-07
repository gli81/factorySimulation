package edu.duke.ece651.group6.factorySimulation.DataModel;

import java.util.ArrayList;
import java.util.Map;
import edu.duke.ece651.group6.factorySimulation.ProductionController;

public class ModelManager {
    private ArrayList<Type> types;
    private ArrayList<Recipe> recipes;
    private ArrayList<Building> buildings;
    private MapGrid mapGrid;

    /**
     * order of buildings to be processed
     * pair of recipe and building
     * 
     * first: index of the order in the user request queue
     * second: recipe and which building is responsible for the recipe
     */
    private ArrayList<Map.Entry<Integer, Map.Entry<Recipe, Building>>> userRequestQueue;

    public ModelManager(MapGrid mapGrid) {
        this.types = new ArrayList<>();
        this.recipes = new ArrayList<>();
        this.buildings = new ArrayList<>();
        this.userRequestQueue = new ArrayList<>();
        this.mapGrid = mapGrid;
    }

    public MapGrid getMapGrid() {
        return this.mapGrid;
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

        // start the recursive task distribution
        // target building is null because it is delivered to the user
        DFSRecursiveTaskDistribution(sourceBuilding, recipe, null);
    }

    /**
     * Recursively distribute the task to the source building using DFS
     * 
     * @param sourceBuilding the source building
     * @param recipe         the recipe
     * @param targetBuilding the target building
     */
    private void DFSRecursiveTaskDistribution(Building sourceBuilding, Recipe recipe, Building targetBuilding) {
        // print the ingredient assignment message if the target is not user-requested
        if (ProductionController.getVerbose() >= 1 && targetBuilding != null) {
            int orderNumber = ProductionController.getAndIncrementCurrRequestIndex();
            if (ProductionController.getVerbose() >= 3) {
                System.out
                        .println("TEST:[Order Number: " + orderNumber + "]");
            }
            System.out.println(
                    "[ingredient assignment]: " + recipe.getName() + " assigned to " + sourceBuilding.getName()
                            + " to deliver to "
                            + targetBuilding.getName());
        }

        if (sourceBuilding.getClass() == Mine.class) {
            // if the source building is a mine
            // add a request to the source building
            int deliveryTime = mapGrid.shortestPath(sourceBuilding, targetBuilding);
            sourceBuilding.addRequest(new RequestItem(recipe, RequestItem.Status.READY, targetBuilding, deliveryTime));

        } else if (sourceBuilding.getClass() == Storage.class) {
            // if the source building is a storage
            Storage storage = (Storage) sourceBuilding;
            int deliveryTime = mapGrid.shortestPath(storage, targetBuilding);

            if (storage.getStock() > 0) {
                // if the storage has stock, start the delivery
                storage.decreaseStock();
                storage
                        .addRequest(new RequestItem(recipe, RequestItem.Status.DONE, targetBuilding, deliveryTime));
            } else {
                // if the storage has no stock, add a request
                storage.addRequest(new RequestItem(recipe, RequestItem.Status.WAITING, targetBuilding, deliveryTime));
            }
        } else if (sourceBuilding.getClass() == Factory.class) {
            Factory sourceFactory = (Factory) sourceBuilding;
            // if the source building is a factory
            int deliveryTime = mapGrid.shortestPath(sourceBuilding, targetBuilding);
            sourceFactory.addRequest(new RequestItem(recipe, RequestItem.Status.WAITING, targetBuilding, deliveryTime));

            // for each ingredient, add a request to the source building
            int ingredientCount = 0;
            // if the recipe has ingredients, print the source selection message
            if (recipe.getIngredientsIterable().iterator().hasNext()) {
                // print the source selection message
                if (ProductionController.getVerbose() >= 2) {
                    System.out
                            .println("[source selection]: " + sourceFactory.getName() + " has request for "
                                    + recipe.getName()
                                    + " on " + ProductionController.getCurrTimeStep());
                }
            }
            for (Map.Entry<Recipe, Integer> ingredient : recipe.getIngredientsIterable()) {
                for (int i = 0; i < ingredient.getValue(); i++) {
                    // print the ingredient selection message
                    if (ProductionController.getVerbose() >= 2) {
                        System.out.println(
                                "[" + sourceFactory.getName() + ":" + recipe.getName() + ":" + ingredientCount
                                        + "] For ingredient "
                                        + ingredient.getKey().getName());
                    }
                    Building selectedSource = sourceFactory.sourceSelect(ingredient.getKey());
                    DFSRecursiveTaskDistribution(selectedSource, ingredient.getKey(), sourceFactory);
                    ingredientCount++;
                }
            }
        }
    }

    /**
     * Process one time step
     */
    public void processOneTimeStep() {
        for (Building building : this.buildings) {
            building.doWork();
            // if the building is a storage, try to make a request
            if (building instanceof Storage) {
                Storage storage = (Storage) building;
                Building sourceBuilding = storage.shouldMakeRequest();
                if (sourceBuilding != null) {
                    DFSRecursiveTaskDistribution(sourceBuilding, storage.getStoredRecipe(), storage);
                }
            }
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
