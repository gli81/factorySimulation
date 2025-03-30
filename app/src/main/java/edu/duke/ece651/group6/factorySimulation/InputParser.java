package edu.duke.ece651.group6.factorySimulation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.Reader;

/**
 * This handles parsing JSON input to the simulation
 */
public class InputParser {
  private final ObjectMapper mapper;
  
  public InputParser() {
    this.mapper = new ObjectMapper();
  }
  
  /**
   * Parse JSON file into the JsonNode
   * @param reader path to JSON input file
   * @return a parsed Json node
   */

  public JsonNode parseFile(Reader reader) throws IOException {
    return mapper.readTree(reader);
  }
  
  /**
   * Extract recipes from a JSON file TODO: only recipes for now, later add types and buildings 
   * @param rootNode root of a JSON parsed node
   * @return list of Recipes 
   */
  public List<Recipe> recipesInputParser(JsonNode root) {
    List<Recipe> recipes = new ArrayList<>();
    JsonNode recipNode = root.path("recipes");
    
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
      
      Recipe recipe = new BasicRecipe(output, ingredient, lat);
      recipes.add(recipe);
    }
    
    return recipes;
  }
}
