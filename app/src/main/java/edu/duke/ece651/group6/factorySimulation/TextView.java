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
     * processes the command input by user
     * request 'ITEM' from 'BUILDING'
     *  step N
     *  verbose N
     *  finish
     * multiple spaces count as one, no space is invalid => ignore silently
     * 
     * @param command is the command that the user input
     * @return the output displayed to user
     */
    public String processCommand(String command) {
        String cleaned_cmd = cleanCommand(command);
        String[] cleaned_words = cleaned_cmd.split(" ");
        if (cleaned_words.length == 0) {
            // if null silently ignore
            return null;
        }
        switch (cleaned_words[0]) {
            case "request":
                // check four '
                /*
                 * if nothing after last ', then 4 parts (space previously removed)
                 * can't tell between
                 *      request 'a' from 'b'
                 * and
                 *      request 'a' from 'b
                 * append a letter to make sure 5 parts
                 */
                String tmp = cleaned_cmd + "a";
                String[] split_by_quote = tmp.split("'");
                if ( // 4 ' so 5 parts
                    !(
                        // make sure the last element is just what is appended
                        split_by_quote.length == 5 && split_by_quote[4].equals("a") 
                    ) || !split_by_quote[2].equals(" from ")
                ) {
                    return "Invalid command - Usage: request 'ITEM' from 'BUILDING'";
                }
                /*
                 * what if request '   a' from 'b'
                 */
                // make sure item in recipe, building in factory
                // need interface
                return cleaned_cmd;
                // break;
            case "verbose":
                // check only two words,second word isdigit
                if (cleaned_words.length != 2 || !isNonnegativeDigit(cleaned_words[1])) {
                    return "Invalid command - Usage: verbose <int:verbose-level>";
                }
                // check within range
                // what is the range?
                return String.join(" ", cleaned_words); // echo the cmd for now
            case "finish":
                // check only one word
                return cleaned_words.length == 1 ?
                    "finish" : // echo the cmd for now
                    "Invalid command - Usage: finish";
            case "step":
                // check only two words, second word isdigit
                if (cleaned_words.length != 2 || !isNonnegativeDigit(cleaned_words[1])) {
                    return "Invalid command - Usage: step <int:verbose-level>";
                }
                return "step";
            default:
                return "Invalid command - Please try again";
        }
    }

    public void displayOutput(String output) {
        // if null silently ignore
        if (null != output) this.out.println(output);
    }

    protected String cleanCommand(String cmd) {
        // split by space, check first word
        String[] words = cmd.split(" ");
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
        return String.join(" ", cleaned_words);
    }

    protected boolean isNonnegativeDigit(String str) {
        int len = str.length();
        if (null == str || len == 0) return false;
        for (int i = 0; i < len; ++i) {
            if (!Character.isDigit(str.charAt(i))) return false;
        }
        return true;
    }
}
