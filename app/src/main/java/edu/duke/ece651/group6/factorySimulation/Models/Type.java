package edu.duke.ece651.group6.factorySimulation.Models;

import java.util.ArrayList;
import java.util.List;

public class Type {
    private final String name;
    private final List<Recipe> recipes;

    
    public Type(String name, List<Recipe> recipies) {
        this.name = name;
        this.recipes = new ArrayList<>(recipies);
    }
}
