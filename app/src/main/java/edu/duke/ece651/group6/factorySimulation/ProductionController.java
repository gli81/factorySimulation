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

    public void finishAllRequests() {
        while (modelManager.getUserRequestQueueSize() > 0) {
            this.incrementTimeStep();
        }
    }

    public void addRequest(String recipeName, String sourceBuildingName) {
        modelManager.addUserRequest(recipeName, sourceBuildingName);
    }

    public void displayOutput() throws IOException {
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
        int len = str.length();
        if (null == str || len == 0)
            return false;
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
    protected String processCommand(String command) {
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
                split_by_quote.length == 5 && split_by_quote[4].equals("a")) || !split_by_quote[2].equals(" from ")) {
                    return "Invalid command - Usage: request 'ITEM' from 'BUILDING'";
                }
                /*
                 * what if request ' a' from 'b'
                 */
                // make sure item in recipe, building in factory
                // make sure factory can make recipe
                // need interface
                return cleaned_cmd;
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
                return cleaned_words.length == 1 ? cleaned_cmd : // echo the cmd for now
                        "Invalid command - Usage: finish";
            case "step":
                // check only two words, second word isdigit
                if (cleaned_words.length != 2 || !isNonnegativeDigit(cleaned_words[1])) {
                    return "Invalid command - Usage: step <int:verbose-level>";
                }
                addTimeStep(Integer.valueOf(cleaned_words[1]));
                return null;
            default:
                return "Invalid command - Please try again";
        }
    }
}
