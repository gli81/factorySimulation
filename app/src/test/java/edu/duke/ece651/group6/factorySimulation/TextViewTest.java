package edu.duke.ece651.group6.factorySimulation;

import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import org.junit.jupiter.api.Test;

public class TextViewTest {

    @Test
    void testPromptUser() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true);
        TextView v1 = new TextView(
                new BufferedReader(new StringReader("ss")), out);
        v1.promptUser();
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
    void testProcessCommand() {
        String command1 = "request a from b";
        String command2 = "request a  from b";
        TextView v = new TextView();
        v.processCommand(command1);
        v.processCommand(command2);
    }
}
