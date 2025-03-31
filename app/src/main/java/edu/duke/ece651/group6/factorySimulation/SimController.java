package edu.duke.ece651.group6.factorySimulation;

import edu.duke.ece651.group6.factorySimulation.RuleChecker.RuleChecker;

// singleton pattern
public class SimController {
    private static SimController instance = null;
    // private final RuleChecker checker;
    private final InputParser parser;
    private int verbose;
    private int timestep;
    private final TextView text;


    private SimController(InputParser parser) {
        // this.checker = checker;
        this.parser = parser;
        this.verbose = 0;
        this.timestep = 0;
        this.text = new TextView();
    }


    public static SimController getController(
        RuleChecker checker, InputParser parser
    ) {
        if (null == instance) {
            instance = new SimController(parser);
        }
        return instance;
    }

    // public void loadJsonFile() {
    //     parser.parseJsonFile(

    //     );
    // }
}
