package edu.duke.ece651.group6.factorySimulation;

import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import org.junit.jupiter.api.Test;

public class TextViewTest {
    TextView v = new TextView();


    @Test
    void testPromptUser() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true);
        TextView v1 = new TextView(
                new BufferedReader(new StringReader("ss")), out);
        v1.promptUser(0);
        assertEquals("0> ", bytes.toString());
    }

    @Test
    void testDisplayOutput() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true);
        TextView v1 = new TextView(
                new BufferedReader(new StringReader("ss")), out);
        v1.displayOutput("ss");
        assertEquals("ss\n", bytes.toString());
    }

    @Test
    public void testDefaultConstructor() {
        TextView view = new TextView();
        assertNotNull(view);
        
        assertDoesNotThrow(() -> {
            view.displayOutput("test message");
        });
    }

    // @Test
    // void testProcessCommand() {
    //     String c3 = "";
    //     String c4 = "   ";
    //     v.processCommand(c3);
    //     v.processCommand(c4);
    //     String c5 = "finish";
    //     v.processCommand(c5);
    // }
}