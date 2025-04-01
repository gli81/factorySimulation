package edu.duke.ece651.group6.factorySimulation.Model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public abstract class Building {
    private final String name;
    private final Coordinate coord;
    private final List<Recipe> recipes;
    private final Queue<Order> orders;


    public Building() {
        this.name = "";
        this.coord = new Coordinate();
        this.recipes = new ArrayList<>();
        // ArrayDeque prohibit null, alternative: LinkedList
        this.orders = new ArrayDeque<>();
    }
}
