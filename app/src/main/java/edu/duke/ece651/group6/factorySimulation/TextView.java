package edu.duke.ece651.group6.factorySimulation;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class TextView {
    private final Scanner sc;

    
    public TextView() {
        this(System.in);
    }

    public TextView(InputStream in) {
        this.sc = new Scanner(in);
    }

    
    public String promptUser(PrintStream out) {
        out.print("0> ");
        return sc.nextLine();
    }

    public void displayOutput(PrintStream out, String output) {
        out.println(output);
    }
}
