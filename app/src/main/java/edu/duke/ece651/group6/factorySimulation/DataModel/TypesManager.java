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

    public Type getType(String name) {
        for (Type type : this.types) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder typesString = new StringBuilder();
        this.types.forEach(type -> {
            typesString.append(type.toString());
        });
        return typesString.toString();
    }
}
