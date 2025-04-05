// package edu.duke.ece651.group6.factorySimulation.RuleChecker;

// import static org.junit.jupiter.api.Assertions.*;
// import java.io.IOException;
// import java.io.InputStream;
// import org.junit.jupiter.api.Test;
// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import edu.duke.ece651.group6.factorySimulation.InputParser;

// public class FactorySrcFormatRuleCheckerTest {
//     private InputStream j = this.getClass().getResourceAsStream(
//         "/inputs/doors1.json"
//     );
//     private final InputParser parser = new InputParser();
//     private final ObjectMapper mapper = new ObjectMapper();


//     @Test
//     void testCheckRule() throws IOException{
//         JsonNode node = mapper.readTree(j);
//         FactorySrcFormatRuleChecker checker =
//             new FactorySrcFormatRuleChecker(null);
//         assertNull(
//             checker.checkRule(node.get("buildings"))
//         );
//     }
// }
