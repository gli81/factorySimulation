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

    /**
     * 
     * @param command is the command that the user input
     * @return the output displayed to user
     */
    public String processCommand(String command) {
        /*
         * request ’ITEM’ from ’BUILDING’
         * step N
         * finish
         * verbose N
         * multiple space counts as 1, no space is invalid
         */
        // split by space, check first word
        String[] words = command.split(" ");
        // multiple spaces would result in empty element, remove
        int word_ct = 0;
        for (String word : words) {
            if (!word.equals("")) word_ct += 1;
        }
        // System.out.println(word_ct);
        String[] cleaned_words = new String[word_ct];
        int ct = 0;
        for (String word : words) {
            if (!word.equals("")) cleaned_words[ct++] = word;
        }
        // for (String word : cleaned_words) {
        //     System.out.print(word);
        //     System.out.print(", ");
        // }
        // System.out.println();
        if (cleaned_words.length == 4 && cleaned_words[0].equals("request")) {
            return "request";
        } else if (cleaned_words.length == 2 && cleaned_words[0].equals("step")) {
            return "step";
        } else if (cleaned_words.length == 2 && cleaned_words[0].equals("verbose")) {
            return "verbose";
        } else if (cleaned_words.length == 1 && cleaned_words[0].equals("finish")) {
            return "finish";
        } else {
            return "Invalid";
        }
    }

    public void displayOutput(String output) {
        this.out.println(output);
    }
}
