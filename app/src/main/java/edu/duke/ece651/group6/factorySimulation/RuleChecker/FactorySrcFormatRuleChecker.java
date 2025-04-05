/*
 * Change in requirement, no longer needed
 */

// package edu.duke.ece651.group6.factorySimulation.RuleChecker;

// import java.util.Arrays;
// import com.fasterxml.jackson.databind.JsonNode;

// public class FactorySrcFormatRuleChecker extends RuleChecker {

//     public FactorySrcFormatRuleChecker(RuleChecker next) {
//         super(next);
//     }

//     @Override
//     protected String checkRule(JsonNode bldgNode) {
//         // assume bldgNode is ARRAY
//         for (JsonNode bldg : bldgNode) {
//             if (bldg.has("type")) {
//                 // assume type means sources field is ARRAY and non empty
//                 // (MineOrTypeAndSourcesRuleChecker)
//                 JsonNode src = bldg.get("sources");
//                 for (JsonNode s : src) {
//                     String srcStr = s.asText();
//                     System.out.println(srcStr);
//                     String[] srcArr = srcStr.split(" ");
//                     System.out.println(Arrays.toString(srcArr));
//                     if (srcArr.length != 3) {
//                         return "sources field is not the expected format";
//                     }
//                     // last element is int
//                     try {
//                         Integer.parseInt(srcArr[2]);
//                     } catch (NumberFormatException e) {
//                         return "sources field is not the expected format";
//                     }
//                 }
//             }
//         }
//         return null;
//     }
    
// }
