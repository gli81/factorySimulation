package edu.duke.ece651.group6.factorySimulation.DataModel;

import java.util.Map;
import java.util.HashMap;

public class Recipe {
    private String outputItem;
    private Map<Object, Integer> ingredients;
    private int latency;

    public Recipe(String outputItem, Map<Object, Integer> ingredients, int latency) {
        this.outputItem = outputItem;
        this.ingredients = ingredients;
        this.latency = latency;
    }

    public String getOutputItem() {
        return this.outputItem;
    }

    public Iterable<Map.Entry<Object, Integer>> getIngredientsIterable() {
        return new HashMap<>(this.ingredients).entrySet();
    }

    public int getLatency() {
        return this.latency;
    }

}
