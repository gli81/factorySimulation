package edu.duke.ece651.group6.factorySimulation.Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Type {
    private final String name;
    private final List<Recipe> recipes;

    
    public Type(String name, List<Recipe> recipies) {
        this.name = name;
        this.recipes = new ArrayList<>(recipies);
    }

    public Iterator<Recipe> getRecipes() {
        return recipes.iterator();
    }

    public static Type getTypeByName(List<Type> tList, String name) {
        for (Type t : tList) {
            if (t.name.equals(name)) {
                return t;
            }
        }
        return null;
    }
}
