package edu.duke.ece651.group6.factorySimulation.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Map;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class RecipeTest {
    @Test
    void testEquals() {
        Recipe r1 = new Recipe("door", Map.of(), 1);
        Recipe r2 = new Recipe("door", Map.of(), 1);
        assertTrue(r1.equals(r2));
    }

    @Disabled
    @Test
    void testToString() {
        Recipe r = new Recipe("door", Map.of("hinge", 2), 1);
        assertEquals(
            "[\n" + "\toutput: door\n" + "\tingredients: [\n\t\thinge, 2\n\t]\n" +
                "\tlatency: 1\n]",
            r.toString()
        );

        Recipe r2 = new Recipe("wood", Map.of(), 1);
        assertEquals(
            "[\n" + "\toutput: wood\n" + "\tingredients: []\n" +
                "\tlatency: 1\n]",
            r2.toString()
        );
    }

    @Test
    void testToStringWithRecipeIngredients() {
        Recipe hinge = new Recipe("hinge", Map.of(), Map.of(), 1);
        Recipe door = new Recipe(
            "door", Map.of(hinge, 1), Map.of("hinge", 1), 15
        );
        assertEquals(
            "[\n" + "\toutput: door\n" + "\tingredients: [\n\t\thinge, 1\n\t]\n" +
                "\tlatency: 15\n]",
            door.toString()
        );
    }
}
