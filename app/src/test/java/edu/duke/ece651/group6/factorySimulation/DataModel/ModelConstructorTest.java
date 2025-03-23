package edu.duke.ece651.group6.factorySimulation.DataModel;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;

public class ModelConstructorTest {

    private ModelManager modelManager;
    private ModelConstructor constructor;

    @BeforeEach
    public void setUp() throws IOException {
        modelManager = new ModelManager();
        constructor = new ModelConstructor(modelManager);
    }

    @Test
    public void testConstructor1() throws IOException {
        constructor.constructFromJsonFile("src/resources/inputs/doors1.json");

        assertEquals(constructor.toString(),
                "Types:\n" +
                        "Type: { door, { door } }\n" +
                        "Type: { handle, { handle } }\n" +
                        "Type: { hinge, { hinge } }\n\n" +
                        "Recipes:\n" +
                        "Recipe: { door, 12, { wood: 1, handle: 1, hinge: 3 } }\n" +
                        "Recipe: { handle, 5, { metal: 1 } }\n" +
                        "Recipe: { hinge, 1, { metal: 1 } }\n" +
                        "Recipe: { wood, 1, { } }\n" +
                        "Recipe: { metal, 1, { } }\n\n" +
                        "Buildings:\n" +
                        "Factory: { D, door, { W, Hi, Ha } }\n" +
                        "Factory: { Ha, handle, { M } }\n" +
                        "Factory: { Hi, hinge, { M } }\n" +
                        "Mine: { W, wood }\n" +
                        "Mine: { M, metal }\n");
    }

    @Test
    public void testConstructor2() throws IOException {
        constructor.constructFromJsonFile("src/resources/inputs/doors2.json");
        assertEquals(constructor.toString(),
                "Types:\n" +
                        "Type: { door, { door } }\n" +
                        "Type: { Hw, { handle, hinge } }\n\n" +
                        "Recipes:\n" +
                        "Recipe: { door, 12, { wood: 1, handle: 1, hinge: 3 } }\n" +
                        "Recipe: { handle, 5, { metal: 1 } }\n" +
                        "Recipe: { hinge, 1, { metal: 1 } }\n" +
                        "Recipe: { wood, 1, { } }\n" +
                        "Recipe: { metal, 1, { } }\n\n" +
                        "Buildings:\n" +
                        "Factory: { D, door, { Hw1, Hw2, W } }\n" +
                        "Factory: { Hw1, Hw, { M1, M2 } }\n" +
                        "Factory: { Hw2, Hw, { M1, M2 } }\n" +
                        "Mine: { W, wood }\n" +
                        "Mine: { M1, metal }\n" +
                        "Mine: { M2, metal }\n");
    }

}
