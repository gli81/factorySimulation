package edu.duke.ece651.group6.factorySimulation.Model;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class Factory extends Building {
    private final Type type;
    private final Map<String, List<String>> srcs;
    

    public Factory(Type type, Map<String, List<String>> srcs) {
        this.type = type;
        this.srcs = new HashMap<>(srcs);
    }

    public void request(String recipeName, String building) {
        
    }
}
