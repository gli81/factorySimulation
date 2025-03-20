package edu.duke.ece651.group6.factorySimulation.DataModel;

public class ProductionController {
    /**
     * Verbosity level of the output—what details are printed about what is
     * going on in the simulation
     * 
     * 0: Only completion of user-requested produced are printed
     * 1: report assignment of ingredients to source factories and when an
     * ingredient is delivered (and which recipes are ready)
     * 2: report when (and how) a building select a new recipe to work on, and
     * how to select a source for ingredients
     */
    public static int verbose = 0;

    public static int currTimeStep = 0;

    public static int setVerbose(int verbose) {
        int oldVerbose = ProductionController.verbose;
        if (verbose >= 0 && verbose < 3) {
            ProductionController.verbose = verbose;
        }
        return oldVerbose;
    }

    public static void addTimeStep(int timeStep) {
        ProductionController.currTimeStep += timeStep;
    }

    public static void resetTimeStep() {
        ProductionController.currTimeStep = 0;
    }

    public static void processOneTimeStep() {
        ProductionController.currTimeStep++;
    }

}
