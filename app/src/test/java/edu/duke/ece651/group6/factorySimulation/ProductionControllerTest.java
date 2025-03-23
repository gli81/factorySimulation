package edu.duke.ece651.group6.factorySimulation;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ProductionControllerTest {

    private ProductionController productionController;

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

    private void setupForDoors1() {
        ProductionController.setVerbose(2);
        ProductionController.resetTimeStep();
        ProductionController.resetCurrRequestIndex();

        this.productionController = new ProductionController();
        assertDoesNotThrow(() -> productionController.constructFromFile("src/resources/inputs/doors1.json"));
    }

    @Test
    public void testDoors1AddRequest() {
        setupForDoors1();
        String output = captureSystemOut(() -> this.productionController.addRequest("door", "D"));

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

    @Test
    public void testDoors1AddTimeStep() {
        setupForDoors1();
        this.productionController.addRequest("door", "D");
        String output = captureSystemOut(() -> this.productionController.addTimeStep(50));

        String expectedOutput = "[recipe selection]: D has fifo on cycle 1\n" +
                "    0: door is not ready, waiting on {wood, 1x handle, 3x hinge}\n" +
                "[recipe selection]: Ha has fifo on cycle 1\n" +
                "    0: handle is not ready, waiting on {metal}\n" +
                "[recipe selection]: Hi has fifo on cycle 1\n" +
                "    0: hinge is not ready, waiting on {metal}\n" +
                "    1: hinge is not ready, waiting on {metal}\n" +
                "    2: hinge is not ready, waiting on {metal}\n" +
                "[recipe selection]: W has fifo on cycle 1\n" +
                "    0: wood is ready\n" +
                "    Selecting 0\n" +
                "[recipe selection]: M has fifo on cycle 1\n" +
                "    0: metal is ready\n" +
                "    1: metal is ready\n" +
                "    2: metal is ready\n" +
                "    3: metal is ready\n" +
                "    Selecting 0\n" +
                "[ingredient delivered]: wood to D from W on cycle 1\n" +
                "[ingredient delivered]: metal to Ha from M on cycle 1\n" +
                "    0: handle is ready\n" +
                "[recipe selection]: D has fifo on cycle 2\n" +
                "    0: door is not ready, waiting on {handle, 3x hinge}\n" +
                "[recipe selection]: Ha has fifo on cycle 2\n" +
                "    0: handle is ready\n" +
                "    Selecting 0\n" +
                "[recipe selection]: Hi has fifo on cycle 2\n" +
                "    0: hinge is not ready, waiting on {metal}\n" +
                "    1: hinge is not ready, waiting on {metal}\n" +
                "    2: hinge is not ready, waiting on {metal}\n" +
                "[recipe selection]: M has fifo on cycle 2\n" +
                "    0: metal is ready\n" +
                "    1: metal is ready\n" +
                "    2: metal is ready\n" +
                "    Selecting 0\n" +
                "[ingredient delivered]: metal to Hi from M on cycle 2\n" +
                "    0: hinge is ready\n" +
                "[recipe selection]: D has fifo on cycle 3\n" +
                "    0: door is not ready, waiting on {handle, 3x hinge}\n" +
                "[recipe selection]: Hi has fifo on cycle 3\n" +
                "    0: hinge is ready\n" +
                "    1: hinge is not ready, waiting on {metal}\n" +
                "    2: hinge is not ready, waiting on {metal}\n" +
                "    Selecting 0\n" +
                "[recipe selection]: M has fifo on cycle 3\n" +
                "    0: metal is ready\n" +
                "    1: metal is ready\n" +
                "    Selecting 0\n" +
                "[ingredient delivered]: hinge to D from Hi on cycle 3\n" +
                "[ingredient delivered]: metal to Hi from M on cycle 3\n" +
                "    0: hinge is ready\n" +
                "[recipe selection]: D has fifo on cycle 4\n" +
                "    0: door is not ready, waiting on {handle, 2x hinge}\n" +
                "[recipe selection]: Hi has fifo on cycle 4\n" +
                "    0: hinge is ready\n" +
                "    1: hinge is not ready, waiting on {metal}\n" +
                "    Selecting 0\n" +
                "[recipe selection]: M has fifo on cycle 4\n" +
                "    0: metal is ready\n" +
                "    Selecting 0\n" +
                "[ingredient delivered]: hinge to D from Hi on cycle 4\n" +
                "[ingredient delivered]: metal to Hi from M on cycle 4\n" +
                "    0: hinge is ready\n" +
                "[recipe selection]: D has fifo on cycle 5\n" +
                "    0: door is not ready, waiting on {handle, 1x hinge}\n" +
                "[recipe selection]: Hi has fifo on cycle 5\n" +
                "    0: hinge is ready\n" +
                "    Selecting 0\n" +
                "[ingredient delivered]: hinge to D from Hi on cycle 5\n" +
                "[recipe selection]: D has fifo on cycle 6\n" +
                "    0: door is not ready, waiting on {handle}\n" +
                "[ingredient delivered]: handle to D from Ha on cycle 6\n" +
                "    0: door is ready\n" +
                "[recipe selection]: D has fifo on cycle 7\n" +
                "    0: door is ready\n" +
                "    Selecting 0\n" +
                "[order complete] Order 0 completed (door) at time 18\n";

        assertEquals(expectedOutput, output);
    }

}