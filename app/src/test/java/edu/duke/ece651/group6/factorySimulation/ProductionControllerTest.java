package edu.duke.ece651.group6.factorySimulation;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
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
    public void testDoors1AddTimeStep1() {
        setupForDoors1();

        this.productionController.addRequest("door", "D");

        String output = captureSystemOut(() -> this.productionController.addTimeStep(50));

        String expectedOutput = "[recipe selection]: D has fifo on cycle 1\n" +
                "    0: door is not ready, waiting on {wood, handle, 3x hinge}\n" +
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
                "    0: door is not ready, waiting on {handle, hinge}\n" +
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

    @Test
    public void testDoors1AddTimeStep2() {
        setupForDoors1();

        this.productionController.addRequest("door", "D");
        this.productionController.addRequest("door", "D");

        String output = captureSystemOut(() -> this.productionController.addTimeStep(50));

        // String expectedOutput = "haha";

        // assertEquals(expectedOutput, output);
    }

    private void setupForDoors2() {
        ProductionController.setVerbose(2);
        ProductionController.resetTimeStep();
        ProductionController.resetCurrRequestIndex();

        this.productionController = new ProductionController();
        assertDoesNotThrow(() -> productionController.constructFromFile("src/resources/inputs/doors2.json"));
    }

    @Test
    public void testDoors2AddTimeStep1() {
        setupForDoors2();

        this.productionController.addRequest("door", "D");

        String output = captureSystemOut(() -> this.productionController.addTimeStep(50));

        String expectedOutput = "[recipe selection]: D has fifo on cycle 1\n" +
                "    0: door is not ready, waiting on {wood, handle, 3x hinge}\n" +
                "[recipe selection]: Hw1 has fifo on cycle 1\n" +
                "    0: handle is not ready, waiting on {metal}\n" +
                "    1: hinge is not ready, waiting on {metal}\n" +
                "[recipe selection]: Hw2 has fifo on cycle 1\n" +
                "    0: hinge is not ready, waiting on {metal}\n" +
                "    1: hinge is not ready, waiting on {metal}\n" +
                "[recipe selection]: W has fifo on cycle 1\n" +
                "    0: wood is ready\n" +
                "    Selecting 0\n" +
                "[recipe selection]: M1 has fifo on cycle 1\n" +
                "    0: metal is ready\n" +
                "    1: metal is ready\n" +
                "    Selecting 0\n" +
                "[recipe selection]: M2 has fifo on cycle 1\n" +
                "    0: metal is ready\n" +
                "    1: metal is ready\n" +
                "    Selecting 0\n" +
                "[ingredient delivered]: wood to D from W on cycle 1\n" +
                "[ingredient delivered]: metal to Hw1 from M1 on cycle 1\n" +
                "    0: handle is ready\n" +
                "[ingredient delivered]: metal to Hw2 from M2 on cycle 1\n" +
                "    0: hinge is ready\n" +
                "[recipe selection]: D has fifo on cycle 2\n" +
                "    0: door is not ready, waiting on {handle, 3x hinge}\n" +
                "[recipe selection]: Hw1 has fifo on cycle 2\n" +
                "    0: handle is ready\n" +
                "    1: hinge is not ready, waiting on {metal}\n" +
                "    Selecting 0\n" +
                "[recipe selection]: Hw2 has fifo on cycle 2\n" +
                "    0: hinge is ready\n" +
                "    1: hinge is not ready, waiting on {metal}\n" +
                "    Selecting 0\n" +
                "[recipe selection]: M1 has fifo on cycle 2\n" +
                "    0: metal is ready\n" +
                "    Selecting 0\n" +
                "[recipe selection]: M2 has fifo on cycle 2\n" +
                "    0: metal is ready\n" +
                "    Selecting 0\n" +
                "[ingredient delivered]: hinge to D from Hw2 on cycle 2\n" +
                "[ingredient delivered]: metal to Hw1 from M1 on cycle 2\n" +
                "    0: hinge is ready\n" +
                "[ingredient delivered]: metal to Hw2 from M2 on cycle 2\n" +
                "    0: hinge is ready\n" +
                "[recipe selection]: D has fifo on cycle 3\n" +
                "    0: door is not ready, waiting on {handle, 2x hinge}\n" +
                "[recipe selection]: Hw2 has fifo on cycle 3\n" +
                "    0: hinge is ready\n" +
                "    Selecting 0\n" +
                "[ingredient delivered]: hinge to D from Hw2 on cycle 3\n" +
                "[recipe selection]: D has fifo on cycle 4\n" +
                "    0: door is not ready, waiting on {handle, hinge}\n" +
                "[recipe selection]: D has fifo on cycle 5\n" +
                "    0: door is not ready, waiting on {handle, hinge}\n" +
                "[recipe selection]: D has fifo on cycle 6\n" +
                "    0: door is not ready, waiting on {handle, hinge}\n" +
                "[ingredient delivered]: handle to D from Hw1 on cycle 6\n" +
                "[recipe selection]: D has fifo on cycle 7\n" +
                "    0: door is not ready, waiting on {hinge}\n" +
                "[recipe selection]: Hw1 has fifo on cycle 7\n" +
                "    0: hinge is ready\n" +
                "    Selecting 0\n" +
                "[ingredient delivered]: hinge to D from Hw1 on cycle 7\n" +
                "    0: door is ready\n" +
                "[recipe selection]: D has fifo on cycle 8\n" +
                "    0: door is ready\n" +
                "    Selecting 0\n" +
                "[order complete] Order 0 completed (door) at time 19\n";

        assertEquals(expectedOutput, output);
    }

    private void setupForStorageWithCoordinates() {
        ProductionController.setVerbose(2);
        ProductionController.resetTimeStep();
        ProductionController.resetCurrRequestIndex();

        this.productionController = new ProductionController();
        assertDoesNotThrow(
                () -> productionController.constructFromFile(
                        "src/resources/inputs/storage_with_coordinates.json"));
    }

    @Test
    public void testStorageWithCoordinates() {
        setupForStorageWithCoordinates();

        this.productionController.addRequest("door", "D");

        String output = captureSystemOut(() -> this.productionController.finishAllRequests());

        String expectedOutput = "[recipe selection]: D has fifo on cycle 1\n" +
                "    0: door is not ready, waiting on {wood, handle, 3x hinge}\n" +
                "[recipe selection]: Ha has fifo on cycle 1\n" +
                "    0: handle is not ready, waiting on {metal}\n" +
                "[recipe selection]: Hi has fifo on cycle 1\n" +
                "    0: hinge is not ready, waiting on {metal}\n" +
                "    1: hinge is not ready, waiting on {metal}\n" +
                "    2: hinge is not ready, waiting on {metal}\n" +
                "[recipe selection]: W has fifo on cycle 1\n" +
                "    0: wood is ready\n" +
                "    Selecting 0\n" +
                "[ingredient delivered]: wood to D from W on cycle 1\n" +
                "[source selection]: Metal Storage has request for metal on 2\n" +
                "[Metal Storage:metal:0] For recipe metal\n" +
                "    M:0\n" +
                "    Selecting M\n" +
                "[ingredient assignment]: metal assigned to M to deliver to Metal Storage\n" +
                "[recipe selection]: D has fifo on cycle 2\n" +
                "    0: door is not ready, waiting on {handle, 3x hinge}\n" +
                "[recipe selection]: Ha has fifo on cycle 2\n" +
                "    0: handle is not ready, waiting on {metal}\n" +
                "[recipe selection]: Hi has fifo on cycle 2\n" +
                "    0: hinge is not ready, waiting on {metal}\n" +
                "    1: hinge is not ready, waiting on {metal}\n" +
                "    2: hinge is not ready, waiting on {metal}\n" +
                "[recipe selection]: M has fifo on cycle 2\n" +
                "    0: metal is ready\n" +
                "    Selecting 0\n" +
                "[ingredient delivered]: metal to Metal Storage from M on cycle 2\n" +
                "[recipe selection]: D has fifo on cycle 3\n" +
                "    0: door is not ready, waiting on {handle, 3x hinge}\n" +
                "[recipe selection]: Ha has fifo on cycle 3\n" +
                "    0: handle is not ready, waiting on {metal}\n" +
                "[recipe selection]: Hi has fifo on cycle 3\n" +
                "    0: hinge is not ready, waiting on {metal}\n" +
                "    1: hinge is not ready, waiting on {metal}\n" +
                "    2: hinge is not ready, waiting on {metal}\n" +
                "[ingredient delivered]: metal to Ha from Metal Storage on cycle 3\n" +
                "    0: handle is ready\n" +
                "[source selection]: Metal Storage has request for metal on 4\n" +
                "[Metal Storage:metal:0] For recipe metal\n" +
                "    M:0\n" +
                "    Selecting M\n" +
                "[ingredient assignment]: metal assigned to M to deliver to Metal Storage\n" +
                "[recipe selection]: D has fifo on cycle 4\n" +
                "    0: door is not ready, waiting on {handle, 3x hinge}\n" +
                "[recipe selection]: Ha has fifo on cycle 4\n" +
                "    0: handle is ready\n" +
                "    Selecting 0\n" +
                "[recipe selection]: Hi has fifo on cycle 4\n" +
                "    0: hinge is not ready, waiting on {metal}\n" +
                "    1: hinge is not ready, waiting on {metal}\n" +
                "    2: hinge is not ready, waiting on {metal}\n" +
                "[recipe selection]: M has fifo on cycle 4\n" +
                "    0: metal is ready\n" +
                "    Selecting 0\n" +
                "[ingredient delivered]: metal to Metal Storage from M on cycle 4\n" +
                "[recipe selection]: D has fifo on cycle 5\n" +
                "    0: door is not ready, waiting on {handle, 3x hinge}\n" +
                "[recipe selection]: Hi has fifo on cycle 5\n" +
                "    0: hinge is not ready, waiting on {metal}\n" +
                "    1: hinge is not ready, waiting on {metal}\n" +
                "    2: hinge is not ready, waiting on {metal}\n" +
                "[ingredient delivered]: metal to Hi from Metal Storage on cycle 5\n" +
                "    0: hinge is ready\n" +
                "[source selection]: Metal Storage has request for metal on 6\n" +
                "[Metal Storage:metal:0] For recipe metal\n" +
                "    M:0\n" +
                "    Selecting M\n" +
                "[ingredient assignment]: metal assigned to M to deliver to Metal Storage\n" +
                "[recipe selection]: D has fifo on cycle 6\n" +
                "    0: door is not ready, waiting on {handle, 3x hinge}\n" +
                "[recipe selection]: Hi has fifo on cycle 6\n" +
                "    0: hinge is ready\n" +
                "    1: hinge is not ready, waiting on {metal}\n" +
                "    2: hinge is not ready, waiting on {metal}\n" +
                "    Selecting 0\n" +
                "[recipe selection]: M has fifo on cycle 6\n" +
                "    0: metal is ready\n" +
                "    Selecting 0\n" +
                "[ingredient delivered]: hinge to D from Hi on cycle 6\n" +
                "[ingredient delivered]: metal to Metal Storage from M on cycle 6\n" +
                "[recipe selection]: D has fifo on cycle 7\n" +
                "    0: door is not ready, waiting on {handle, 2x hinge}\n" +
                "[recipe selection]: Hi has fifo on cycle 7\n" +
                "    0: hinge is not ready, waiting on {metal}\n" +
                "    1: hinge is not ready, waiting on {metal}\n" +
                "[ingredient delivered]: metal to Hi from Metal Storage on cycle 7\n" +
                "    0: hinge is ready\n" +
                "[source selection]: Metal Storage has request for metal on 8\n" +
                "[Metal Storage:metal:0] For recipe metal\n" +
                "    M:0\n" +
                "    Selecting M\n" +
                "[ingredient assignment]: metal assigned to M to deliver to Metal Storage\n" +
                "[recipe selection]: D has fifo on cycle 8\n" +
                "    0: door is not ready, waiting on {handle, 2x hinge}\n" +
                "[recipe selection]: Hi has fifo on cycle 8\n" +
                "    0: hinge is ready\n" +
                "    1: hinge is not ready, waiting on {metal}\n" +
                "    Selecting 0\n" +
                "[recipe selection]: M has fifo on cycle 8\n" +
                "    0: metal is ready\n" +
                "    Selecting 0\n" +
                "[ingredient delivered]: handle to D from Ha on cycle 8\n" +
                "[ingredient delivered]: hinge to D from Hi on cycle 8\n" +
                "[ingredient delivered]: metal to Metal Storage from M on cycle 8\n" +
                "[recipe selection]: D has fifo on cycle 9\n" +
                "    0: door is not ready, waiting on {hinge}\n" +
                "[recipe selection]: Hi has fifo on cycle 9\n" +
                "    0: hinge is not ready, waiting on {metal}\n" +
                "[ingredient delivered]: metal to Hi from Metal Storage on cycle 9\n" +
                "    0: hinge is ready\n" +
                "[source selection]: Metal Storage has request for metal on 10\n" +
                "[Metal Storage:metal:0] For recipe metal\n" +
                "    M:0\n" +
                "    Selecting M\n" +
                "[ingredient assignment]: metal assigned to M to deliver to Metal Storage\n" +
                "[recipe selection]: D has fifo on cycle 10\n" +
                "    0: door is not ready, waiting on {hinge}\n" +
                "[recipe selection]: Hi has fifo on cycle 10\n" +
                "    0: hinge is ready\n" +
                "    Selecting 0\n" +
                "[recipe selection]: M has fifo on cycle 10\n" +
                "    0: metal is ready\n" +
                "    Selecting 0\n" +
                "[ingredient delivered]: hinge to D from Hi on cycle 10\n" +
                "    0: door is ready\n" +
                "[ingredient delivered]: metal to Metal Storage from M on cycle 10\n" +
                "[recipe selection]: D has fifo on cycle 11\n" +
                "    0: door is ready\n" +
                "    Selecting 0\n" +
                "[source selection]: Metal Storage has request for metal on 12\n" +
                "[Metal Storage:metal:0] For recipe metal\n" +
                "    M:0\n" +
                "    Selecting M\n" +
                "[ingredient assignment]: metal assigned to M to deliver to Metal Storage\n" +
                "[recipe selection]: M has fifo on cycle 12\n" +
                "    0: metal is ready\n" +
                "    Selecting 0\n" +
                "[ingredient delivered]: metal to Metal Storage from M on cycle 12\n" +
                "[source selection]: Metal Storage has request for metal on 15\n" +
                "[Metal Storage:metal:0] For recipe metal\n" +
                "    M:0\n" +
                "    Selecting M\n" +
                "[ingredient assignment]: metal assigned to M to deliver to Metal Storage\n" +
                "[recipe selection]: M has fifo on cycle 15\n" +
                "    0: metal is ready\n" +
                "    Selecting 0\n" +
                "[ingredient delivered]: metal to Metal Storage from M on cycle 15\n" +
                "[source selection]: Metal Storage has request for metal on 18\n" +
                "[Metal Storage:metal:0] For recipe metal\n" +
                "    M:0\n" +
                "    Selecting M\n" +
                "[ingredient assignment]: metal assigned to M to deliver to Metal Storage\n" +
                "[recipe selection]: M has fifo on cycle 18\n" +
                "    0: metal is ready\n" +
                "    Selecting 0\n" +
                "[ingredient delivered]: metal to Metal Storage from M on cycle 18\n" +
                "[source selection]: Metal Storage has request for metal on 21\n" +
                "[Metal Storage:metal:0] For recipe metal\n" +
                "    M:0\n" +
                "    Selecting M\n" +
                "[ingredient assignment]: metal assigned to M to deliver to Metal Storage\n" +
                "[recipe selection]: M has fifo on cycle 21\n" +
                "    0: metal is ready\n" +
                "    Selecting 0\n" +
                "[ingredient delivered]: metal to Metal Storage from M on cycle 21\n" +
                "[order complete] Order 0 completed (door) at time 22\n";

        assertEquals(expectedOutput, output);
    }

    @Test
    public void test_setVerbose_negative_value() {
        // First set verbose to a known value
        ProductionController.setVerbose(2);
        assertEquals(2, ProductionController.getVerbose());

        // Now try setting it to a negative value
        int oldValue = ProductionController.setVerbose(-1);

        // The old value should be 2
        assertEquals(2, oldValue);

        // The verbose level should still be 2 (unchanged)
        assertEquals(2, ProductionController.getVerbose());
    }

    @Test
    void testCleanCommand() {
        this.productionController = new ProductionController();
        String expeceted = "request a from b";
        String c1 = "request a from b";
        assertEquals(expeceted, productionController.cleanCommand(c1));
        String c2 = "   request a from b";
        assertEquals(expeceted, productionController.cleanCommand(c2));
        String c3 = "request  a  from b";
        assertEquals(expeceted, productionController.cleanCommand(c3));
        String c4 = "request a from b    ";
        assertEquals(expeceted, productionController.cleanCommand(c4));
    }

    @Test
    void testIsNonnegativeDigit() {
        this.productionController = new ProductionController();
        assertTrue(productionController.isNonnegativeDigit("123"));
        assertFalse(productionController.isNonnegativeDigit("-123"));
        assertFalse(productionController.isNonnegativeDigit(""));
        assertFalse(productionController.isNonnegativeDigit(null));
    }

    @Test
    void testProcessCommand() throws EndOfProductionException {
        ProductionController pc = new ProductionController();
        assertEquals(
                "Invalid command - Usage: request 'ITEM' from 'BUILDING'",
                pc.processCommand("request 'a' from 'b"));
        assertEquals(
                "Invalid command - Usage: request 'ITEM' from 'BUILDING'",
                pc.processCommand("request 'a'from 'b'"));
        assertEquals(
                "Invalid command - Usage: request 'ITEM' from 'BUILDING'",
                pc.processCommand("request 'a'from 'b'"));
        assertEquals(
                "Invalid command - Usage: request 'ITEM' from 'BUILDING'",
                pc.processCommand("request 'a from 'b'"));
        assertEquals(
                "Invalid command - Usage: request 'ITEM' from 'BUILDING'",
                pc.processCommand("request a' from 'b'"));
        assertEquals(
                "Invalid command - Usage: request 'ITEM' from 'BUILDING'",
                pc.processCommand("request 'a' from b'"));
        assertEquals(
                "Invalid command - Usage: request 'ITEM' from 'BUILDING'",
                pc.processCommand("request 'a' from 'b'c"));
        assertNull(pc.processCommand(""));
        assertNull(pc.processCommand("      "));
        assertEquals(
                "Invalid command - Invalid verbose level",
                pc.processCommand("verbose 3"));
        assertEquals(
                "Invalid command - Usage: verbose <int:verbose-level>",
                pc.processCommand("verbose -1"));
        assertEquals(
                "Invalid command - Usage: finish",
                pc.processCommand("finish lol"));
        assertThrows(
                EndOfProductionException.class,
                () -> pc.processCommand("finish"));
        assertEquals(
                "Invalid command - Usage: step <int:step>",
                pc.processCommand("step 2 2"));
        assertEquals(
                "Invalid command - Usage: step <int:step>",
                pc.processCommand("step -2"));
        assertEquals(
                "Invalid command - Please try again",
                pc.processCommand("hhihi"));
        assertEquals(
                "Invalid request - Recipe not found: door",
                pc.processCommand("request 'door' from 'F'"));
    }
}
