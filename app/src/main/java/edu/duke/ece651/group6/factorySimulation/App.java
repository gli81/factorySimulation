package edu.duke.ece651.group6.factorySimulation;

import java.io.File;
import java.io.IOException;
import com.google.gson.JsonParseException;

public class App {
    private final ProductionController ctrl;


    public App() {
        this.ctrl = new ProductionController();
    }

    
    public static void main(String[] args) throws IOException {
        App app = new App();
        try {
            // check number of args
            if (args.length != 1) {
                throw new Exception("Invalid Config File - Usage: app <json-file>");
            }
            app.readJson(args[0]);
        } catch (JsonParseException jpe) {
            System.err.println(
                "Invalid Config File - Invalid format"
            );
            System.exit(0); // exit?
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(0); // exit?
        }
        while (true) {
            app.ctrl.displayOutput();
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
