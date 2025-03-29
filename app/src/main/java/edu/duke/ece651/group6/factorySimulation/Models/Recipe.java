package edu.duke.ece651.group6.factorySimulation.Models;

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
}
