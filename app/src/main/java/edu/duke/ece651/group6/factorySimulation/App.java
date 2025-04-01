package edu.duke.ece651.group6.factorySimulation;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.google.gson.JsonParseException;
import edu.duke.ece651.group6.factorySimulation.Exception.EndOfProductionException;
import edu.duke.ece651.group6.factorySimulation.RuleChecker.*;

public class App {
    private final ProductionController ctrl;


    public App() {
        RuleChecker rootChecker = new HasFieldsAndTypeRuleChecker( // check root has these fields
            new HasFieldsAndTypeRuleChecker( // check each recipe has these fields
                new HasFieldsAndTypeRuleChecker( // check each type has these fields
                    new HasFieldsAndTypeRuleChecker( // check each building has these fields
                        new NoApostropheRuleChecker( // check recipes' output has no apostrophe
                            new NoApostropheRuleChecker(
                                new NoApostropheRuleChecker(
                                    null,
                                    new String[]{"buildings"},
                                    "name",
                                    true    
                                ),
                                new String[] {"types"},
                                "name",
                                true
                            ),
                            new String[]{"recipes"},
                            "output",
                            true    
                        ),
                        new String[]{"buildings"},
                        Map.of(
                            "name", JsonNodeType.STRING,
                            "sources", JsonNodeType.ARRAY
                        ),
                        true
                    ),
                    new String[]{"types"},
                    Map.of(
                        "name", JsonNodeType.STRING,
                        "recipes", JsonNodeType.ARRAY
                    ),
                    true
                ),
                new String[]{"recipes"},
                Map.of(
                    "output", JsonNodeType.STRING,
                    "ingredients", JsonNodeType.OBJECT,
                    "latency", JsonNodeType.NUMBER
                ),
                true
            ),
            Map.of(
                "recipes", JsonNodeType.ARRAY,
                "types", JsonNodeType.ARRAY,
                "buildings", JsonNodeType.ARRAY
            )
        );
        // RuleChecker recipeChecker = new ListRuleChecker(
        //     new HasFieldsRuleChecker(
        //         null,
        //         new HashSet<>(Arrays.asList("output", "ingredients", "latency"))
        //     )
        // );
        // RuleChecker typeChecker = new ListRuleChecker(
        //     new HasFieldsRuleChecker(
        //         null,
        //         new HashSet<>(Arrays.asList("name", "recipes"))
        //     )
        // );
        // RuleChecker bldgChecker = new ListRuleChecker(
        //     new HasFieldsRuleChecker(
        //         null,
        //         new HashSet<>(Arrays.asList("name", "sources"))
        //     )
        // );
        SimController.getController(rootChecker, null);
        this.ctrl = new ProductionController();
    }

    
    public static void main(String[] args) throws IOException {
        App app = new App();
        try {
            // check number of args
            if (args.length != 1) {
                throw new Exception("Invalid Config File - Usage: app [-nw] <json-file>");
            }
            app.readJson(args[0]);
            while (true) {
                app.ctrl.displayOutput();
            }
        } catch (JsonParseException jpe) {
            System.out.println(
                "Invalid Config File - Invalid format"
            );
        } catch (EndOfProductionException epe) {
            // get out of loop do nothing
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void readJson(
        String configPath
    ) throws IOException, JsonParseException, Exception {
        // check file exist
        File f = new File(configPath);
        if (!f.exists()) {
            throw new Exception("Invalid Config File - File doesn't exist");
        }
        // read json
        this.ctrl.constructFromFile(configPath);
    }
}
