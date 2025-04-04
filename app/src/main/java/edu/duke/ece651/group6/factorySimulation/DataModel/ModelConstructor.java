package edu.duke.ece651.group6.factorySimulation.DataModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ModelConstructor {
    private ModelManager modelManager;
    private MapGrid mapGrid;

    public ModelConstructor(ModelManager modelManager, MapGrid mapGrid) {
        this.modelManager = modelManager;
        this.mapGrid = mapGrid;
    }

    /**
     * Construct the Model from the json file
     * 
     * @param filePath
     * @throws IOException
     * @throws JsonParseException
     */
    public void constructFromJsonFile(String filePath) throws IOException, JsonParseException {
        String jsonContent = readJsonFile(filePath);
        JsonObject jsonObject = parseJsonToObject(jsonContent);

        // must be called in this order
        constructRecipes(jsonObject);
        constructTypes(jsonObject);
        constructBuildings(jsonObject);
        constructMapGrid();
        // connectAllBuildings();
    }

    /**
     * Connect all source buildings to their target buildings
     */
    private void connectAllBuildings() {
        for (Building building : modelManager.getAllBuildingsIterable()) {
            if (building instanceof Factory) {
                Factory factory = (Factory) building;
                for (Building source : factory.getSourcesIterable()) {
                    if (!mapGrid.connectBuildings(source, factory)) {
                        throw new InvalidInputException("Failed to connect building " + source.getName() + " to "
                                + factory.getName());
                    }
                }
            } else if (building instanceof Storage) {
                Storage storage = (Storage) building;
                for (Building source : storage.getSourcesIterable()) {
                    if (!mapGrid.connectBuildings(source, storage)) {
                        throw new InvalidInputException("Failed to connect building " + source.getName() + " to "
                                + storage.getName());
                    }
                }
            }
        }
    }

    /**
     * Construct the MapGrid from the model
     */
    private void constructMapGrid() {
        for (Building building : modelManager.getAllBuildingsIterable()) {
            // add all buildings with specified coordinates to the map grid
            if (building.getX() != -1 && building.getY() != -1) {

                if (!mapGrid.addMapObject(building, building.getX(), building.getY())) {
                    throw new InvalidInputException("Failed to add building " + building.getName()
                            + " to map grid at coordinates (" + building.getX() + ", " + building.getY() + ")");
                }
            }

        }

        for (Building building : modelManager.getAllBuildingsIterable()) {
            // add all buildings with no specified coordinates to the map grid
            if (building.getX() == -1 && building.getY() == -1) {
                // choose an available coordinate
                Map.Entry<Integer, Integer> availableCoordinate = mapGrid.chooseAvailableCoordinate();
                if (availableCoordinate == null) {
                    throw new InvalidInputException("Failed to find available coordinates for building "
                            + building.getName());
                }
                // set the building's coordinates
                building.setCoordinates(availableCoordinate.getKey(), availableCoordinate.getValue());
                mapGrid.addMapObject(building, building.getX(), building.getY());
            }

        }

    }

    @Override
    public String toString() {
        return modelManager.toString();
    }

    /**
     * Read the json file and return the content as a string
     * 
     * @param filePath
     * @return
     * @throws IOException
     */
    private String readJsonFile(String filePath) throws IOException {
        return Files.readString(Path.of(filePath));
    }

    /**
     * Parse the json string to a JsonObject
     * 
     * @param jsonString
     * @return
     */
    private JsonObject parseJsonToObject(String jsonString) throws JsonParseException {
        try {
            Gson gson = new Gson();
            return gson.fromJson(jsonString, JsonObject.class);
        } catch (Exception e) {
            throw new JsonParseException("Failed to parse JSON string: " + e.getMessage());
        }
    }

    /**
     * Construct the Recipe objects
     * 
     * @param jsonObject
     */
    private void constructRecipes(JsonObject jsonObject) {
        JsonArray recipesArray = jsonObject.getAsJsonArray("recipes");
        if (recipesArray == null) {
            throw new InvalidInputException("Missing recipes array in JSON");
        }

        ArrayList<Map<String, Integer>> ingredientsList = new ArrayList<>();
        for (JsonElement recipeElement : recipesArray) {
            JsonObject recipeObject = recipeElement.getAsJsonObject();

            if (!recipeObject.has("output")) {
                throw new InvalidInputException("Missing output of recipe in JSON");
            }
            String output = recipeObject.get("output").getAsString();

            // check 1: check if recipe name contains apostrophes
            validateName(output, "Recipe");

            // check 11: validate recipe name is unique
            if (modelManager.getRecipe(output) != null) {
                throw new InvalidInputException("Recipe name '" + output + "' is not unique");
            }

            JsonElement latencyElement = recipeObject.get("latency");
            if (latencyElement == null) {
                throw new InvalidInputException("Missing latency of recipe " + output + " in JSON");
            }
            int latency = latencyElement.getAsInt();

            // check 10: validate that latency is at least 1
            if (latency < 1) {
                throw new InvalidInputException(
                        "Recipe latency must be at least 1, got " + latency + " for recipe '" + output + "'");
            }

            // get the ingredients
            JsonObject ingredientsObject = recipeObject.getAsJsonObject("ingredients");
            if (ingredientsObject == null) {
                throw new InvalidInputException("Missing ingredients of recipe " + output + " in JSON");
            }
            Map<String, Integer> ingredients = new LinkedHashMap<>();
            for (String ingredientName : ingredientsObject.keySet()) {
                ingredients.put(ingredientName, ingredientsObject.get(ingredientName).getAsInt());
            }

            ingredientsList.add(ingredients);
            Recipe recipe = new Recipe(output, latency);
            modelManager.addRecipe(recipe);
        }

        // check 6: ingredidents specified for all receipes must be defined in the
        // recipes section. Then add the ingredient objects to the recipe object
        Iterator<Recipe> recipeIterator = modelManager.getAllRecipesIterable().iterator();
        Iterator<Map<String, Integer>> ingredientsIterator = ingredientsList.iterator();

        while (recipeIterator.hasNext() && ingredientsIterator.hasNext()) {
            Recipe recipe = recipeIterator.next();
            Map<String, Integer> ingredients = ingredientsIterator.next();
            for (String ingredientName : ingredients.keySet()) {
                Recipe ingredient = modelManager.getRecipe(ingredientName);
                if (ingredient == null) {
                    throw new InvalidInputException(
                            "Ingredient of recipe " + recipe.getName() + " not found: " + ingredientName);
                }
                recipe.addIngredient(ingredient, ingredients.get(ingredientName));
            }
        }

    }

    /**
     * Construct the Type objects
     * 
     * @param jsonObject
     */
    private void constructTypes(JsonObject jsonObject) {
        JsonArray typesArray = jsonObject.getAsJsonArray("types");
        if (typesArray == null) {
            throw new InvalidInputException("Missing types array in JSON");
        }

        // iterate over the types array
        for (JsonElement typeElement : typesArray) {
            JsonObject typeObject = typeElement.getAsJsonObject();

            // get the type name

            if (!typeObject.has("name")) {
                throw new InvalidInputException("Missing name of type in JSON");
            }
            String typeName = typeObject.get("name").getAsString();

            // check 1: check if type name contains apostrophes
            validateName(typeName, "Type");

            // check 12: validate type name is unique
            if (modelManager.getType(typeName) != null) {
                throw new InvalidInputException("Type name '" + typeName + "' is not unique");
            }

            // get the recipes
            ArrayList<Recipe> recipes = new ArrayList<>();
            JsonArray recipesArray = typeObject.getAsJsonArray("recipes");
            if (recipesArray == null) {
                throw new InvalidInputException("Missing recipes array of type " + typeName + " in JSON");
            }

            // iterate over the recipes array
            for (JsonElement recipeName : recipesArray) {
                // check 5: recipes specified in the types must be defined in the recipes
                // section
                Recipe recipe = modelManager.getRecipe(recipeName.getAsString());
                if (recipe == null) {
                    throw new InvalidInputException("Recipe of type " + typeName + " not found: "
                            + recipeName.getAsString());
                }
                // check 7: all recipes used in the types section must be recipes appropriate
                // for factories
                // (each recipe must have at least one input ingredient)
                boolean hasIngredients = false;
                for (Map.Entry<Recipe, Integer> ingredient : recipe.getIngredientsIterable()) {
                    hasIngredients = true;
                    break;
                }
                if (!hasIngredients) {
                    throw new InvalidInputException("Recipe '" + recipe.getName() +
                            "' used in type '" + typeName + "' must have at least one ingredient for factories");
                }

                recipes.add(recipe);
            }

            // create and add the new type
            Type type = new Type(typeName, recipes);
            modelManager.addType(type);
        }
    }

    /**
     * Construct the Building objects
     * 
     * @param jsonObject
     */
    private void constructBuildings(JsonObject jsonObject) {
        JsonArray buildingsArray = jsonObject.getAsJsonArray("buildings");
        if (buildingsArray == null) {
            throw new InvalidInputException("Missing buildings array in JSON");
        }

        // temporary list to store sources stringof each building
        ArrayList<ArrayList<String>> sourcesList = new ArrayList<>();

        // iterate over the buildings array
        for (JsonElement buildingElement : buildingsArray) {
            JsonObject buildingObject = buildingElement.getAsJsonObject();

            // get the building name
            if (!buildingObject.has("name")) {
                throw new InvalidInputException("Missing name of building in JSON");
            }
            String name = buildingObject.get("name").getAsString();

            // check 1: check if building name contains apostrophes
            validateName(name, "Building");

            // check 13: validate building name is unique
            if (modelManager.getBuilding(name) != null) {
                throw new InvalidInputException("Building name '" + name + "' is not unique");
            }

            // get the source buildings
            ArrayList<String> sources = new ArrayList<>();
            JsonArray sourcesArray = buildingObject.getAsJsonArray("sources");
            if (sourcesArray != null) {
                for (JsonElement source : sourcesArray) {
                    sources.add(source.getAsString());
                }
            }
            // add the sources to the temporary sources list
            sourcesList.add(sources);

            // get the coordinates
            int x, y;
            if (buildingObject.has("x") && buildingObject.has("y")) {
                x = buildingObject.get("x").getAsInt();
                y = buildingObject.get("y").getAsInt();
                if (x < 0 || y < 0) {
                    throw new InvalidInputException("Building " + name + " has negative coordinates");
                }
            } else if (!buildingObject.has("x") && !buildingObject.has("y")) {
                x = -1;
                y = -1;
            } else {
                throw new InvalidInputException("Building " + name + " must have both or neither x and y coordinates");
            }

            // determine the building type
            boolean isMine = buildingObject.has("mine");
            boolean isFactory = buildingObject.has("type");
            boolean isStorage = buildingObject.has("stores");

            if (isMine && !isFactory && !isStorage) {
                // mine type

                // check 4: either the sources field must not be present, or it must be empty
                if (sources.size() > 0) {
                    throw new InvalidInputException("Sources must be empty for mine type " + name);
                }

                String mineType = buildingObject.get("mine").getAsString();
                Recipe mineItem = modelManager.getRecipe(mineType);
                if (mineItem == null) {
                    throw new InvalidInputException("Mine item of building " + name + " not found: " + mineType);
                }

                // check 8: mine recipes must have no ingredients
                boolean hasIngredients = false;
                for (Map.Entry<Recipe, Integer> ingredient : mineItem.getIngredientsIterable()) {
                    hasIngredients = true;
                    break;
                }
                if (hasIngredients) {
                    throw new InvalidInputException("Mine recipe '" + mineItem.getName() +
                            "' for building '" + name + "' must have no ingredients");
                }

                Mine mine = new Mine(name, mineItem, x, y);
                modelManager.addBuilding(mine);
            } else if (isFactory && !isMine && !isStorage) {
                // factory type
                String type = buildingObject.get("type").getAsString();

                // check 2: type of each building must be defined in the types section
                Type buildingType = modelManager.getType(type);
                if (buildingType == null) {
                    throw new InvalidInputException("Type of building " + name + " not found: " + type);
                }
                Factory factory = new Factory(name, buildingType, x, y);
                modelManager.addBuilding(factory);

            } else if (isStorage && !isMine && !isFactory) {

                // capacity
                int capacity = buildingObject.get("capacity").getAsInt();
                if (capacity <= 0) {
                    throw new InvalidInputException("Capacity of building " + name + " must be positive");
                }

                // priority
                double priority = buildingObject.get("priority").getAsDouble();
                if (priority <= 0) {
                    throw new InvalidInputException("Priority of building " + name + " must be positive");
                }

                Storage storage = new Storage(name, capacity, priority, x, y);
                modelManager.addBuilding(storage);

            } else {
                throw new InvalidInputException("Building " + name + " must be either a factory, mine, or storage");
            }
        }

        // check 3: buildings named in the sources of a building must be defined in the
        // buildings section. Then add the building objects
        // to the factory/storage object
        Iterator<Building> buildingIterator = modelManager.getAllBuildingsIterable().iterator();
        Iterator<ArrayList<String>> sourcesIterator = sourcesList.iterator();

        while (buildingIterator.hasNext() && sourcesIterator.hasNext()) {
            Building building = buildingIterator.next();
            ArrayList<String> sources = sourcesIterator.next();

            if (building instanceof Factory) {
                Factory factory = (Factory) building;
                for (String sourceName : sources) {
                    Building sourceBuilding = modelManager.getBuilding(sourceName);
                    if (sourceBuilding == null) {
                        throw new InvalidInputException(
                                "Source building " + sourceName + " not found: " + sourceName);
                    }
                    factory.addSource(sourceBuilding);
                }
                // check 9: For each factory, all ingredients it might need must be available
                checkFactoryIngredientsAvailability(factory);
            } else if (building instanceof Storage) {
                Storage storage = (Storage) building;
                for (String sourceName : sources) {
                    Building sourceBuilding = modelManager.getBuilding(sourceName);
                    if (sourceBuilding == null) {
                        throw new InvalidInputException(
                                "Source building " + sourceName + " not found: " + sourceName);
                    }
                    storage.addSource(sourceBuilding);
                }
            }
        }
    }

    /**
     * Check if all ingredients needed by a factory are available from at least one
     * of its source buildings
     * 
     * @param factory the factory to check
     * @throws InvalidInputException if an ingredient is not available
     */
    private void checkFactoryIngredientsAvailability(Factory factory) {
        Type factoryType = factory.getType();

        // Collect all unique ingredients needed by this factory's recipes
        Map<String, Boolean> requiredIngredients = new LinkedHashMap<>();

        for (Recipe recipe : factoryType.getRecipesIterable()) {
            for (Map.Entry<Recipe, Integer> ingredient : recipe.getIngredientsIterable()) {
                requiredIngredients.put(ingredient.getKey().getName(), false);
            }
        }

        // No ingredients required? Nothing to check
        if (requiredIngredients.isEmpty()) {
            return;
        }

        // Check if each required ingredient can be provided by at least one source
        for (Building source : factory.getSourcesIterable()) {
            for (String ingredientName : requiredIngredients.keySet()) {
                Recipe ingredientRecipe = modelManager.getRecipe(ingredientName);
                if (source.isRecipeSupported(ingredientRecipe)) {
                    requiredIngredients.put(ingredientName, true);
                }
            }
        }

        // Check if any ingredient is still not available
        for (Map.Entry<String, Boolean> entry : requiredIngredients.entrySet()) {
            if (!entry.getValue()) {
                throw new InvalidInputException("Factory '" + factory.getName() +
                        "' cannot get ingredient '" + entry.getKey() +
                        "' from any of its sources");
            }
        }
    }

    /**
     * Validate a name for both recipes and types
     * 
     * @param name       the name to validate
     * @param entityType the type of entity (e.g., "Recipe", "Type")
     * @throws InvalidInputException if the name is invalid
     */
    private void validateName(String name, String Category) {
        // check 1: check if name contains apostrophes
        if (name.contains("'")) {
            throw new InvalidInputException(
                    Category + " name '" + name + "' contains an apostrophe which is not allowed");
        }
    }

}
