package edu.duke.ece651.group6.factorySimulation.Model;

import java.util.Map;

public class Recipe {
    private final String output;
    private final Map<Recipe, Integer> ingredients;
    private final int latency;


    public Recipe(String output, Map<Recipe, Integer> ingre, int latency) {
        this.output = output;
        this.ingredients = ingre;
        this.latency = latency;
    }

    @Override
    public boolean equals(Object obj) {
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        Recipe o = (Recipe)obj;
        return this.output.equals(o.output);
    }
}
