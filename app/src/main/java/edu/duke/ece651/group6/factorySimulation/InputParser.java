package edu.duke.ece651.group6.factorySimulation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.Reader;
import edu.duke.ece651.group6.factorySimulation.Exception.InvalidJsonFileException;
import edu.duke.ece651.group6.factorySimulation.Model.Building;
import edu.duke.ece651.group6.factorySimulation.Model.Recipe;
import edu.duke.ece651.group6.factorySimulation.Model.Type;
import edu.duke.ece651.group6.factorySimulation.RuleChecker.*;

/**
 * This handles parsing JSON input to the simulation
 */
public class InputParser {
    private final ObjectMapper mapper = new ObjectMapper();
    private final RuleCheckerFactory f = new RuleCheckerFactory();


    public InputParser() {}


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
     * 
     * @param rootNode root of a JSON parsed node
     * @return list of Recipes
     */
    public List<Recipe> parseRecipes(JsonNode recipeRoot) {
        // every rule checked
        List<Recipe> recipes = new ArrayList<>();
        for (JsonNode node : recipeRoot) {
            String output = node.get("output").asText();
            int lat = node.path("latency").asInt();
            Map<String, Integer> ingredient = new HashMap<>();
            JsonNode ingredientsNode = node.get("ingredients");
            Iterator<Map.Entry<String, JsonNode>> items = ingredientsNode.fields();
            while (items.hasNext()) {
            Map.Entry<String, JsonNode> entry = items.next();
            ingredient.put(entry.getKey(), entry.getValue().asInt());
            }
            Recipe recipe = new Recipe(output, ingredient, lat);
            recipes.add(recipe);
        }
        // convert Map<String, Integer> to Map<Recipe, Integer>
        for (Recipe r : recipes) {
            r.setIngredientWithRecipe(recipes);
        }
        return recipes;
    }

    protected List<Building> parseBuildings(JsonNode root) {
        return null;
    }

    protected List<Type> parseTypes(JsonNode root) {
        return null;
    }

    public void parseJsonFile(
        Reader reader, List<Recipe> recipeLst,
        List<Type> typeLst, List<Building> bldgLst
    ) throws Exception {
        // readFile
        JsonNode root = this.mapper.readTree(reader);
        // root checker
        RuleChecker rootChecker = this.f.getRuleChecker();
        String rslt = rootChecker.checkJson(root);
        if (null != rslt) {
            // rslt indicating which node is missing
            throw new InvalidJsonFileException(
                "Invalid json file - " + rslt
            );
        }
        // parse Recipe
        JsonNode recipeRoot = root.get("recipes");
        recipeLst.addAll(parseRecipes(recipeRoot));
        // check Type
        // parse Type
        RuleChecker typeChecker = this.f.getTypeChecker(recipeLst);
        JsonNode typeRoot = root.get("types");
        typeChecker.checkJson(typeRoot);
        typeLst.addAll(parseTypes(typeRoot));
        // parse Building
        // check Building
        RuleChecker bldgChecker = this.f.getBuildingChecker(recipeLst, typeLst);
        JsonNode bldgRoot = root.get("buildings");
        bldgChecker.checkJson(bldgRoot);
        bldgLst.addAll(parseBuildings(bldgRoot));
    }
}
