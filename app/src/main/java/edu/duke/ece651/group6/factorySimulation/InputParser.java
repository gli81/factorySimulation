package edu.duke.ece651.group6.factorySimulation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.Reader;

import edu.duke.ece651.group6.factorySimulation.Models.Building;
import edu.duke.ece651.group6.factorySimulation.Models.Recipe;
import edu.duke.ece651.group6.factorySimulation.Models.Type;
import edu.duke.ece651.group6.factorySimulation.RuleChecker.*;

/**
 * This handles parsing JSON input to the simulation
 */
public class InputParser {
    private final ObjectMapper mapper;
    private final RuleChecker checker;
    private final RuleChecker recipeChecker;
    private final RuleChecker typeChecker;
    private final RuleChecker buildingChecker;


    public InputParser() {
        this.mapper = new ObjectMapper();
        this.checker = new HasFieldsRuleChecker(null, null);
        this.recipeChecker = new HasFieldsRuleChecker(null, null);
        this.typeChecker = new HasFieldsRuleChecker(null, null);
        this.buildingChecker = new HasFieldsRuleChecker(null, null);
    }

    public InputParser(
        RuleChecker checker,
        RuleChecker recipeChecker,
        RuleChecker typeChecker,
        RuleChecker buildingChecker
    ) {
        this.mapper = new ObjectMapper();
        this.checker = checker;
        this.recipeChecker = recipeChecker;
        this.typeChecker = typeChecker;
        this.buildingChecker = buildingChecker;
    }


    // /**
    //  * Parse JSON file into the JsonNode
    //  * 
    //  * @param reader path to JSON input file
    //  * @return a parsed Json node
    //  */
    // public JsonNode parseFile(Reader reader) throws IOException {
    //     return mapper.readTree(reader);
    // }

    /**
     * Extract recipes from a JSON file
     * TODO: only recipes for now, later add types and buildings
     * 
     * @param rootNode root of a JSON parsed node
     * @return list of Recipes
     */
    public List<Recipe> parseRecipes(JsonNode recipeRoot) {
        List<Recipe> recipes = new ArrayList<>();
        JsonNode recipNode = recipeRoot.path("recipes");
        if ((recipNode.isMissingNode()) || (!recipNode.isArray())) {
            return recipes;
        }
        for (JsonNode node : recipNode) {
            String output = node.path("output").asText();
            int lat = node.path("latency").asInt();
            Map<String, Integer> ingredient = new HashMap<>();
            JsonNode ingredientsNode = node.path("ingredients");
            if ((!ingredientsNode.isMissingNode()) && (ingredientsNode.isObject())) {
                Iterator<Map.Entry<String, JsonNode>> items = ingredientsNode.fields();
                while (items.hasNext()) {
                Map.Entry<String, JsonNode> entry = items.next();
                ingredient.put(entry.getKey(), entry.getValue().asInt());
                }
            }
            // Recipe recipe = new BasicRecipe(output, ingredient, lat);
            // recipes.add(recipe);
        }
        return recipes;
    }

    public List<Building> parseBuildings(JsonNode root) {
        return null;
    }

    public List<Type> parseTypes(JsonNode root) {
        return null;
    }

    public Map<String, List<Object>> parseJsonFile(
        Reader reader
    ) throws Exception {
        // readFile
        JsonNode root = this.mapper.readTree(reader);
        // root checker
        String rslt = checker.checkJson(root);
        if (null != rslt) {
            // rslt indicating which node is missing
            throw new Exception(rslt);
        }
        // parse Recipe
        JsonNode recipeRoot = root.get("recipes");
        this.recipeChecker.checkJson(recipeRoot);
        parseRecipes(recipeRoot);
        // parse Type
        JsonNode typeRoot = root.get("types");
        this.typeChecker.checkJson(typeRoot);
        parseTypes(typeRoot);
        // parse Building
        JsonNode bldgRoot = root.get("buildings");
        this.buildingChecker.checkJson(bldgRoot);
        parseBuildings(bldgRoot);
        return null;
    }
}
