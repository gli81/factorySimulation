package edu.duke.ece651.group6.factorySimulation.DataModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Iterator;

public class ModelConstructor {

    private TypesManager typesManager;
    private RecipesManager recipesManager;
    private BuildingsManager buildingsManager;

    public ModelConstructor() {
        this.typesManager = new TypesManager();
        this.recipesManager = new RecipesManager();
        this.buildingsManager = new BuildingsManager();
    }

    @Override
    public String toString() {
        return "Types:\n" + typesManager.toString() + "\n" +
                "Recipes:\n" + recipesManager.toString() + "\n" +
                "Buildings:\n" + buildingsManager.toString();
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

        // temporary list to store ingredients strings of each recipe
        ArrayList<Map<String, Integer>> ingredientsList = new ArrayList<>();

        // iterate over the recipes JSON array
        for (JsonElement recipeElement : recipesArray) {
            JsonObject recipeObject = recipeElement.getAsJsonObject();

            // get the output and latency
            String output = recipeObject.get("output").getAsString();
            if (output == null) {
                throw new InvalidInputException("Missing output of recipe in JSON");
            }
            JsonElement latencyElement = recipeObject.get("latency");
            if (latencyElement == null) {
                throw new InvalidInputException("Missing latency of recipe " + output + " in JSON");
            }
            int latency = latencyElement.getAsInt();

            // get the ingredients
            JsonObject ingredientsObject = recipeObject.getAsJsonObject("ingredients");
            if (ingredientsObject == null) {
                throw new InvalidInputException("Missing ingredients of recipe " + output + " in JSON");
            }
            Map<String, Integer> ingredients = new LinkedHashMap<>();
            for (String ingredientName : ingredientsObject.keySet()) {
                ingredients.put(ingredientName, ingredientsObject.get(ingredientName).getAsInt());
            }

            // add the ingredients to the temporary ingredients list
            ingredientsList.add(ingredients);

            // create and add the new recipe
            Recipe recipe = new Recipe(output, latency);
            recipesManager.addRecipe(recipe);
        }

        // check 6: ingredidents specified for all receipes must be defined in the
        // recipes section. Then add the ingredient objects to the recipe object
        Iterator<Recipe> recipeIterator = recipesManager.getAllRecipesIterable().iterator();
        Iterator<Map<String, Integer>> ingredientsIterator = ingredientsList.iterator();

        while (recipeIterator.hasNext() && ingredientsIterator.hasNext()) {
            Recipe recipe = recipeIterator.next();
            Map<String, Integer> ingredients = ingredientsIterator.next();
            for (String ingredientName : ingredients.keySet()) {
                Recipe ingredient = recipesManager.getRecipe(ingredientName);
                if (ingredient == null) {
                    throw new InvalidInputException(
                            "Ingredient of recipe " + recipe.getOutputItem() + " not found: " + ingredientName);
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
            String typeName = typeObject.get("name").getAsString();
            if (typeName == null) {
                throw new InvalidInputException("Missing name of type in JSON");
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
                Recipe recipe = recipesManager.getRecipe(recipeName.getAsString());
                if (recipe == null) {
                    throw new InvalidInputException("Recipe of type " + typeName + " not found: "
                            + recipeName.getAsString());
                }
                recipes.add(recipe);
            }

            // create and add the new type
            Type type = new Type(typeName, recipes);
            typesManager.addType(type);
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
            String name = buildingObject.get("name").getAsString();
            if (name == null) {
                throw new InvalidInputException("Missing name of building in JSON");
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

            // determine the building type: factory or mine
            if (buildingObject.has("mine") && !buildingObject.has("type")) {
                // mine type

                // check 4: either the sources field must not be present, or it must be empty
                if (sources.size() > 0) {
                    throw new InvalidInputException("Sources must be empty for mine type " + name);
                }

                String mineType = buildingObject.get("mine").getAsString();
                Recipe mineItem = recipesManager.getRecipe(mineType);
                if (mineItem == null) {
                    throw new InvalidInputException("Mine item of building " + name + " not found: " + mineType);
                }
                Mine mine = new Mine(name, mineItem);
                buildingsManager.addBuilding(mine);
            } else if (!buildingObject.has("mine") && buildingObject.has("type")) {
                // factory type
                String type = buildingObject.get("type").getAsString();

                // check 2: type of each building must be defined in the types section
                Type buildingType = typesManager.getType(type);
                if (buildingType == null) {
                    throw new InvalidInputException("Type of building " + name + " not found: " + type);
                }
                Factory factory = new Factory(name, buildingType);
                buildingsManager.addBuilding(factory);

            } else if (buildingObject.has("mine") && buildingObject.has("type")) {
                throw new InvalidInputException("Building " + name + " cannot be both a factory and a mine");
            } else {
                throw new InvalidInputException("Building " + name + " must be either a factory or a mine");
            }
        }

        // check 3: buildings named in the sources of a building must be defined in the
        // buildings section. Then add the building objects to the factory object
        Iterator<Building> buildingIterator = buildingsManager.getAllBuildingsIterable().iterator();
        Iterator<ArrayList<String>> sourcesIterator = sourcesList.iterator();

        while (buildingIterator.hasNext() && sourcesIterator.hasNext()) {
            Building building = buildingIterator.next();
            ArrayList<String> sources = sourcesIterator.next();
            if (building instanceof Factory) {
                Factory factory = (Factory) building;
                for (String sourceName : sources) {
                    Building sourceBuilding = buildingsManager.getBuilding(sourceName);
                    if (sourceBuilding == null) {
                        throw new InvalidInputException(
                                "Source building " + sourceName + " not found: " + sourceName);
                    }
                    factory.addSource(sourceBuilding);
                }
            }
        }
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
    }

}
