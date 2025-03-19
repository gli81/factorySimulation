package edu.duke.ece651.group6.factorySimulation.DataModel;

import java.io.IOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ModelConstructor {

    private TypesManager typesManager;
    private RecipesManager recipesManager;
    private BuildingsManager buildingsManager;

    public ModelConstructor() {
        this.typesManager = new TypesManager();
        this.recipesManager = new RecipesManager();
        this.buildingsManager = new BuildingsManager();
    }

    /**
     * Read the json file and return the content as a string
     * 
     * @param filePath
     * @return
     * @throws IOException
     */
    private String readJsonFile(String filePath) throws IOException {
        return "jsonContent";
    }

    /**
     * Parse the json string to a JsonObject
     * 
     * @param jsonString
     * @return
     */
    private JsonObject parseJsonToObject(String jsonString) throws JsonParseException {
        return new JsonObject();
    }

    /**
     * Construct the Recipes object
     * 
     * @param jsonObject
     */
    private void constructRecipes(JsonObject jsonObject) {

    }

    /**
     * Construct the Types object
     * 
     * @param jsonObject
     */
    private void constructTypes(JsonObject jsonObject) {

    }

    /**
     * Construct the Buildings object
     * 
     * @param jsonObject
     */
    private void constructBuildings(JsonObject jsonObject) {

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

        constructTypes(jsonObject);
        constructRecipes(jsonObject);
        constructBuildings(jsonObject);
    }

}
