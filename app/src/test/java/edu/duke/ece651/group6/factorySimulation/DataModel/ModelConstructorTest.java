package edu.duke.ece651.group6.factorySimulation.DataModel;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

public class ModelConstructorTest {
  //Helper to check 
  private String getResourceFilePath(String resourcePath) {
    URL url = getClass().getClassLoader().getResource(resourcePath);
    if (url == null) {
      fail("Could not find resource: " + resourcePath);
    }
    return url.getPath();
  }
  
    @Test
    public void testConstructor() throws IOException {
        ModelConstructor constructor = new ModelConstructor();
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
}
