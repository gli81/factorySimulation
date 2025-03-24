package edu.duke.ece651.group6.factorySimulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class TextView {
    private final BufferedReader input;
    private final PrintStream out;

    
    public TextView() {
        this(new BufferedReader(new InputStreamReader(System.in)), System.out);
    }

    public TextView(BufferedReader in, PrintStream out) {
        this.input = in;
        this.out = out;
    }

    
    public String promptUser() throws IOException{
        this.out.print("0> ");
        return this.input.readLine();
    }

    public void displayOutput(String output) {
        this.out.println(output);
    }
}
