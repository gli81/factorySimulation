package edu.duke.ece651.group6.factorySimulation.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Recipe {
    private final String output;
    private Map<Recipe, Integer> ingredients;
    private final int latency;
    private final Map<String, Integer> stringIngres;


    public Recipe(
        String output, Map<Recipe, Integer> ingre,
        Map<String, Integer> strIngres, int latency
    ) {
        this.output = output;
        this.ingredients = ingre;
        this.stringIngres = strIngres;
        this.latency = latency;
    }

    public Recipe(String output, Map<String, Integer> strIngres, int latency) {
        this(output, new HashMap<Recipe, Integer>(), strIngres, latency);
    }

    // public Recipe(String output, Map<Recipe, Integer> ingres, int latency) {
    //     this(output, ingres, new HashMap<String, Integer>(), latency);
    // } 


    @Override
    public boolean equals(Object obj) {
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        Recipe o = (Recipe)obj;
        return this.output.equals(o.output);
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder("[\n\t");
        ans.append("output: ").append(this.output).append("\n\t");
        ans.append("ingredients: [");
        if (this.stringIngres.size() > 0) {
            ans.append("\n");
        }
        for (Entry<Recipe, Integer> entry : this.ingredients.entrySet()) {
            ans.append("\t\t").append(entry.getKey().output).append(", ");
            ans.append(entry.getValue()).append("\n");
        }
        if (this.stringIngres.size() > 0) {
            ans.append("\t");
        }
        ans.append("]\n");
        ans.append("\tlatency: ").append(this.latency).append("\n]");
        return ans.toString();
    }

    public void setIngredientWithRecipe(List<Recipe> recipeLst) {
        // System.out.println(recipeLst.size());
        // for (Recipe r : recipeLst) {
        //     System.out.print(r.getOutput() + ", ");
        // }
        // for each key in ingredients
        for (String ingredientName : this.stringIngres.keySet()) {
            // get Recipe from list of Recipes
            // put in Map<Recipe, Integer>
            // System.out.println(ingredientName);
            Recipe r = getRecipeFromListByOutput(recipeLst, ingredientName);
            // System.out.println(r);
            this.ingredients.put(
                r, this.stringIngres.get(ingredientName)
            );
        }
    }

    public String getOutput() {return this.output;}

    /**
     * get Recipe instance from a list of Recipe instances by output name
     * should not return null in a real simulation, because input is verified to
     * have all possible name
     * 
     * @param lst a list of Recipe instance to be searched from
     * @param name is the output name being searched
     * @return the Recipe instance which has the specified output,
     * null if no Recipe instance matches
     */
    public static Recipe getRecipeFromListByOutput(List<Recipe> lst, String name) {
        for (Recipe r : lst) {
            // System.out.println(r.ingredients.toString());
            if (r.output.equals(name)) {
                return r;
            }
        }
        // System.out.println(name + " not found");
        return null;
    }

    public boolean hasIngredients() {
        return this.ingredients.size() > 0;
    }
}
