package edu.duke.ece651.group6.factorySimulation;

import edu.duke.ece651.group6.factorySimulation.DataModel.*;
import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
    private MapGrid mapGrid;
    private ModelConstructor modelConstructor;

    public ProductionController() {
        this.view = new TextView();
        this.mapGrid = new MapGrid();
        this.modelManager = new ModelManager(mapGrid);
        this.modelConstructor = new ModelConstructor(modelManager, mapGrid);
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

    public void finishAllRequests() {
        while (modelManager.getUserRequestQueueSize() > 0) {
            this.incrementTimeStep();
        }
    }

    public void addRequest(String recipeName, String sourceBuildingName) {
        modelManager.addUserRequest(recipeName, sourceBuildingName);
    }

    private String capsystem_Output(Runnable runnable) {
        PrintStream out = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        try {
            runnable.run();
            return outputStream.toString();
        } finally {
            System.setOut(out);
        }
    }

    /**
     * 1. get size of grid
     * 
     * @return the x y coordinates of the grid
     */
    public Map.Entry<Integer, Integer> getGridSize() {
        int x = mapGrid.getWidth();
        int y = mapGrid.getHeight();
        return Map.entry(x, y);
    }

    /**
     * 2. get information about building at start
     * 
     * @return a list of buildings
     *         get the coordinate of the building:
     *         building.getX()
     *         building.getY()
     */
    public List<Building> getBuildingList() {
        List<Building> buildings = new ArrayList<>();
        for (Building building : modelManager.getAllBuildingsIterable()) {
            buildings.add(building);
        }
        return buildings;
    }

    /**
     * 3. step for frontend
     * 
     * @return the output displayed to user
     */
    public String stepWithLog() {
        try {
            String output = capsystem_Output(() -> incrementTimeStep());
            return output;
        } catch (Exception e) {
            return "Simulation error at time-step " + currTimeStep + ":\n" + e.getMessage();
        }
    }

    /**
     * 4. finish all requests for frontend
     */
    public String finishWithLog() {
        try {
            String output = capsystem_Output(() -> finishAllRequests());
            return output;
        } catch (Exception e) {
            return "Simulation error at time-step " + currTimeStep + ":\n" + e.getMessage();
        }
    }

    /**
     * 5. get all recipes supported by a building
     * 
     * @param buildingName the name of the building
     * @return a list of recipes
     */
    public List<Recipe> getAllRecipesSupported(String buildingName) {
        Building building = modelManager.getBuilding(buildingName);
        List<Recipe> recipes = new ArrayList<>();
        for (Recipe recipe : modelManager.getAllRecipesIterable()) {
            if (building.isRecipeSupported(recipe)) {
                recipes.add(recipe);
            }
        }
        return recipes;
    }

    /**
     * 6. add request for frontend
     * 
     * @param recipeName         the name of the recipe
     * @param sourceBuildingName the name of the source building
     * @return the output displayed to user
     */
    public String addRequestWithLog(String recipeName, String sourceBuildingName) {
        modelManager.addUserRequest(recipeName, sourceBuildingName);
        return "Request added at time-step " + currTimeStep;
    }

    /**
     * 7. get list of destinations the building can be connected to
     * 
     * @param buildingName the name of the building
     * @return a list of destinations
     */
    public List<Building> getConnectedBuildings(String buildingName) {
        Building building = modelManager.getBuilding(buildingName);
        return building.getConnectedBuildings();
    }

    /**
     * 8. connect two buildings
     * 
     * @param sourceBuildingName the name of the source building
     * @param targetBuildingName the name of the target building
     * @return true if the buildings are connected, false otherwise
     */
    public boolean connectBuildings(String sourceBuildingName, String targetBuildingName) {
        Building sourceBuilding = modelManager.getBuilding(sourceBuildingName);
        Building targetBuilding = modelManager.getBuilding(targetBuildingName);
        return mapGrid.connectBuildings(sourceBuilding, targetBuilding);
    }

    /**
     * 9. get all the roads
     * 
     * @return a list of roads
     */
    public List<Road> getAllRoads() {
        return mapGrid.getRoads();
    }

    public void displayOutput() throws IOException, EndOfProductionException {
        this.view.displayOutput(
                this.processCommand(this.view.promptUser(currTimeStep)));
    }

    public String promptUser() throws IOException {
        return this.view.promptUser(currTimeStep);
    }

    protected String cleanCommand(String cmd) {
        // split by space, check first word
        String[] words = cmd.split(" ");
        // multiple spaces would result in empty element, remove
        int word_ct = 0;
        for (String word : words) {
            if (!word.equals(""))
                word_ct += 1;
        }
        String[] cleaned_words = new String[word_ct];
        int ct = 0;
        for (String word : words) {
            if (!word.equals(""))
                cleaned_words[ct++] = word;
        }
        return String.join(" ", cleaned_words);
    }

    protected boolean isNonnegativeDigit(String str) {

        if (null == str) {
            return false;
        }
        int len = str.length();
        if (len == 0) {
            return false;
        }
        for (int i = 0; i < len; ++i) {
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }
        return true;
    }

    /**
     * processes the command input by user
     * request 'ITEM' from 'BUILDING'
     * step N
     * verbose N
     * finish
     * multiple spaces count as one
     * no word or only space is invalid => ignore silently
     * 
     * @param command is the command that the user input
     * @return the output displayed to user
     */
    protected String processCommand(String command) throws EndOfProductionException {
        String cleaned_cmd = cleanCommand(command);
        String[] cleaned_words = cleaned_cmd.split(" ");
        if (cleaned_words.length == 1 && cleaned_words[0].equals("")) {
            // if null silently ignore
            return null;
        }
        switch (cleaned_words[0]) {
            case "request":
                // check four '
                /*
                 * if nothing after last ', then 4 parts (space previously removed)
                 * can't tell between
                 * request 'a' from 'b'
                 * and
                 * request 'a' from 'b
                 * append a letter to make sure 5 parts
                 */
                String tmp = cleaned_cmd + "a";
                String[] split_by_quote = tmp.split("'");
                if ( // 4 ' so 5 parts
                !(
                // make sure the last element is just what is appended
                split_by_quote.length == 5 && split_by_quote[4].equals("a")) ||
                        !split_by_quote[2].equals(" from ")) {
                    return "Invalid command - Usage: request 'ITEM' from 'BUILDING'";
                }
                /*
                 * what if request ' a' from 'b'
                 */
                // make sure item in recipe, building in factory
                // make sure factory can make recipe
                try {
                    this.modelManager.addUserRequest(split_by_quote[1], split_by_quote[3]);
                    return null;
                } catch (InvalidInputException e) {
                    return "Invalid request - " + e.getMessage();
                }
                // break;
            case "verbose":
                // check only two words,second word isdigit
                if (cleaned_words.length != 2 || !isNonnegativeDigit(cleaned_words[1])) {
                    return "Invalid command - Usage: verbose <int:verbose-level>";
                }
                /*
                 * assume range is 0 - 2
                 */
                int level = Integer.valueOf(cleaned_words[1]);
                if (level < 0 || level > 2) {
                    return "Invalid command - Invalid verbose level";
                }
                setVerbose(level);
                // System.out.println(getVerbose());
                return null; // output from Evan's part
            case "finish":
                // check only one word
                if (cleaned_words.length == 1) {
                    finishAllRequests();
                    String rslt = "Simulation completed at time-step " + currTimeStep;
                    this.view.displayOutput(rslt);
                    throw new EndOfProductionException();
                } else {
                    return "Invalid command - Usage: finish";
                }
            case "step":
                // check only two words, second word isdigit
                if (cleaned_words.length != 2 || !isNonnegativeDigit(cleaned_words[1])) {
                    return "Invalid command - Usage: step <int:step>";
                }
                addTimeStep(Integer.valueOf(cleaned_words[1]));
                return null;
            default:
                return "Invalid command - Please try again";
        }
    }
}
