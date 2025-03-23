package edu.duke.ece651.group6.factorySimulation.DataModel;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import edu.duke.ece651.group6.factorySimulation.ProductionController;

public class ModelManagerTest {

    private ModelManager modelManager;
    private ModelConstructor constructor;

    private String captureSystemOut(Runnable runnable) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        System.setOut(printStream);

        try {
            runnable.run();

            return outputStream.toString();
        } finally {
            System.setOut(originalOut);
        }
    }

    @BeforeEach
    public void setUp() throws IOException {
        ProductionController.setVerbose(2);
        ProductionController.resetTimeStep();
        ProductionController.resetCurrRequestIndex();

        modelManager = new ModelManager();
        constructor = new ModelConstructor(modelManager);
        constructor.constructFromJsonFile("src/resources/inputs/doors1.json");
    }

    @Test
    public void testAddUserRequest() {

        Runnable runnable = () -> modelManager.addUserRequest("door", "D");
        String output = captureSystemOut(runnable);

        // verify the output content
        assertEquals(1, modelManager.getUserRequestQueueSize());

        String expectedOutput = "[source selection]: D has request for door on 0\n" +
                "[D:door:0] For ingredient wood\n" +
                "    W:0\n" +
                "    Selecting W\n" +
                "[ingredient assignment]: wood assigned to W to deliver to D\n" +
                "[D:door:1] For ingredient handle\n" +
                "    Ha:0\n" +
                "    Selecting Ha\n" +
                "[ingredient assignment]: handle assigned to Ha to deliver to D\n" +
                "[source selection]: Ha has request for handle on 0\n" +
                "[Ha:handle:0] For ingredient metal\n" +
                "    M:0\n" +
                "    Selecting M\n" +
                "[ingredient assignment]: metal assigned to M to deliver to Ha\n" +
                "[D:door:2] For ingredient hinge\n" +
                "    Hi:0\n" +
                "    Selecting Hi\n" +
                "[ingredient assignment]: hinge assigned to Hi to deliver to D\n" +
                "[source selection]: Hi has request for hinge on 0\n" +
                "[Hi:hinge:0] For ingredient metal\n" +
                "    M:1\n" +
                "    Selecting M\n" +
                "[ingredient assignment]: metal assigned to M to deliver to Hi\n" +
                "[D:door:3] For ingredient hinge\n" +
                "    Hi:1\n" +
                "    Selecting Hi\n" +
                "[ingredient assignment]: hinge assigned to Hi to deliver to D\n" +
                "[source selection]: Hi has request for hinge on 0\n" +
                "[Hi:hinge:0] For ingredient metal\n" +
                "    M:2\n" +
                "    Selecting M\n" +
                "[ingredient assignment]: metal assigned to M to deliver to Hi\n" +
                "[D:door:4] For ingredient hinge\n" +
                "    Hi:2\n" +
                "    Selecting Hi\n" +
                "[ingredient assignment]: hinge assigned to Hi to deliver to D\n" +
                "[source selection]: Hi has request for hinge on 0\n" +
                "[Hi:hinge:0] For ingredient metal\n" +
                "    M:3\n" +
                "    Selecting M\n" +
                "[ingredient assignment]: metal assigned to M to deliver to Hi\n";
        assertEquals(expectedOutput, output);

    }

}
