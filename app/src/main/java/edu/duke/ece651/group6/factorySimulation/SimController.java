package edu.duke.ece651.group6.factorySimulation;

import edu.duke.ece651.group6.factorySimulation.RuleChecker.RuleChecker;

// simpleton pattern
public class SimController {
    private SimController instance = null;
    private final RuleChecker checker;
    private final InputParser parser;
    private int verbose;
    private int timestep;


    private SimController(RuleChecker checker, InputParser parser) {
        this.checker = checker;
        this.parser = parser;
        this.verbose = 0;
        this.timestep = 0;
    }


    public SimController getController(RuleChecker checker, InputParser parser) {
        if (null == this.instance) {
            this.instance = new SimController(checker, parser);
        }
        return this.instance;
    }
}
