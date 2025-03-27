package edu.duke.ece651.group6.factorySimulation;

import edu.duke.ece651.group6.factorySimulation.DataModel.*;
import java.io.IOException;

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
    private static int verbose = 0;
    private static int currTimeStep = 0;
    private static int currRequestIndex = 0;
    private TextView view;
    private ModelManager modelManager;
    private ModelConstructor modelConstructor;

    public ProductionController() {
        this.view = new TextView();
        this.modelManager = new ModelManager();
        this.modelConstructor = new ModelConstructor(modelManager);
    }

    public void constructFromFile(String filePath) throws IOException {
        modelConstructor.constructFromJsonFile(filePath);
    }

    public static int getVerbose() {
        return ProductionController.verbose;
    }

    public static int getCurrTimeStep() {
        return ProductionController.currTimeStep;
    }

    public static int setVerbose(int verbose) {
        int oldVerbose = ProductionController.verbose;
        if (verbose >= 0) {
            ProductionController.verbose = verbose;
        }
        return oldVerbose;
    }

    public static int getAndIncrementCurrRequestIndex() {
        int requestIndex = ProductionController.currRequestIndex;
        ProductionController.currRequestIndex++;
        return requestIndex;
    }

    public static void resetCurrRequestIndex() {
        ProductionController.currRequestIndex = 0;
    }

    public static void resetTimeStep() {
        ProductionController.currTimeStep = 0;
    }

    public void incrementTimeStep() {
        ProductionController.currTimeStep++;
        modelManager.processOneTimeStep();
    }

    public void addTimeStep(int timeStep) {
        for (int i = 0; i < timeStep; i++) {
            this.incrementTimeStep();
        }
    }

    public void addRequest(String recipeName, String sourceBuildingName) {
        modelManager.addUserRequest(recipeName, sourceBuildingName);
    }

    public void displayOutput() throws IOException {
        this.view.displayOutput(
            this.view.processCommand(this.view.promptUser(currTimeStep))
        );
    }
}
