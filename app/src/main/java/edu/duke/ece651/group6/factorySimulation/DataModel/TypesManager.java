package edu.duke.ece651.group6.factorySimulation.DataModel;

import java.util.ArrayList;

public class TypesManager {

    private ArrayList<Type> types;

    public TypesManager() {
        this.types = new ArrayList<>();
    }

    public void addType(Type type) {
        this.types.add(type);
    }

    public Iterable<Type> getAllTypesIterable() {
        return new ArrayList<>(this.types);
    }
}