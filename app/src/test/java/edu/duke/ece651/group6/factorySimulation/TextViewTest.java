package edu.duke.ece651.group6.factorySimulation;

import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Text;

public class TextViewTest {
    TextView v = new TextView();


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
        String command2 = "request aa  from b";
        v.processCommand(command1);
        v.processCommand(command2);
    }

    @Test
    void testCleanCommand() {
        String expeceted = "request a from b";
        String c1 = "request a from b";
        assertEquals(expeceted, v.cleanCommand(c1));
        String c2 = "   request a from b";
        assertEquals(expeceted, v.cleanCommand(c2));
        String c3 = "request  a  from b";
        assertEquals(expeceted, v.cleanCommand(c3));
        String c4 = "request a from b    ";
        assertEquals(expeceted, v.cleanCommand(c4));
    }

    @Test
    void testIsNonnegativeDigit() {
        assertTrue(v.isNonnegativeDigit("123"));
        assertFalse(v.isNonnegativeDigit("-123"));
    }
}
