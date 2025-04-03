package edu.duke.ece651.group6.factorySimulation;

import java.util.Map;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import edu.duke.ece651.group6.factorySimulation.RuleChecker.*;

public class RuleCheckerFactory {
    public RuleCheckerFactory() {

    }

    public RuleChecker getRuleChecker() {
        return new HasFieldsAndTypeRuleChecker(
        new HasFieldsAndTypeRuleChecker(
        new HasFieldsAndTypeRuleChecker(
        new HasFieldsAndTypeRuleChecker(
        new MineOrTypeRuleChecker(
        new NoApostropheRuleChecker(
        new NoApostropheRuleChecker(
        new NoApostropheRuleChecker(
        new DuplicateValueRuleChecker(
        new DuplicateValueRuleChecker(
        new DuplicateValueRuleChecker(
        new RecipeIngredientsExistRuleChecker(
        new TypesRecipesExistRuleChecker(
        new BuildingsTypesExistRuleChecker(
            null
        )
        )
        ),
            // check buildings' name has no duplicate
            new String[]{"buildings"}, "name"
        ),
            // check types' name has no duplicate
            new String[]{"types"}, "name"
        ),
            // check recipes' output has no duplicate
            new String[]{"recipes"}, "output"
        ),    
        // check buildings' name has no apostrophe
            new String[]{"buildings"}, "name", true
        ),
            // check types' name has no apostrophe
            new String[] {"types"}, "name", true
        ),
            // check recipes' output has no apostrophe
            new String[]{"recipes"}, "output", true    
        ),
            new String[]{"buildings"}, true
        ),
            // check buildings has name and sources
            new String[]{"buildings"},
            Map.of(
                "name", JsonNodeType.STRING,
                "sources", JsonNodeType.ARRAY
            ),
            true
        ),
            // check types has name and recipes
            new String[]{"types"},
            Map.of(
                "name", JsonNodeType.STRING,
                "recipes", JsonNodeType.ARRAY
            ),
            true
        ),
            // check recipes has output, ingredients, and latency
            new String[]{"recipes"},
            Map.of(
                "output", JsonNodeType.STRING,
                "ingredients", JsonNodeType.OBJECT,
                "latency", JsonNodeType.NUMBER
            ),
            true
        ),
            // check root has recipes, types, and buildings
            Map.of(
                "recipes", JsonNodeType.ARRAY,
                "types", JsonNodeType.ARRAY,
                "buildings", JsonNodeType.ARRAY
            )
        );
    }
}
