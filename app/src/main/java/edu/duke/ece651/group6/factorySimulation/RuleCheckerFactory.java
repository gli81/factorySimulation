package edu.duke.ece651.group6.factorySimulation;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import edu.duke.ece651.group6.factorySimulation.Model.Recipe;
import edu.duke.ece651.group6.factorySimulation.RuleChecker.*;

public class RuleCheckerFactory {
    public RuleCheckerFactory() {

    }

    /**
     * checks root node and recipes node
     * @return
     */
    public RuleChecker getRuleChecker() {
        return new HasFieldsAndTypeRuleChecker(
        new HasFieldsAndTypeRuleChecker(
        new NoApostropheRuleChecker(
        new DuplicateValueRuleChecker(
        new RecipeIngredientsExistRuleChecker(
            null
        ),
            // check recipes' output has no duplicate
            new String[]{"recipes"}, "output"
        ),
            // check recipes' output has no apostrophe
            new String[]{"recipes"}, "output", true    
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

    /**
     * check types node
     * 
     * @return
     */
    public RuleChecker getTypeChecker(List<Recipe> recipeList) {
        return new HasFieldsAndTypeRuleChecker(
        new DuplicateValueRuleChecker(
        new NoApostropheRuleChecker(
        new TypesRecipesExistRuleChecker(
        null,
            recipeList
        ),
            "name"
        ),
            "name"
        ),
            new String[]{},
            Map.of("name", JsonNodeType.STRING, "recipes", JsonNodeType.ARRAY),
            true
        );
    }
    
    /**
     * check buildings node
     * 
     * @return
     */
    public RuleChecker getBuildingChecker() {
        return new HasFieldsAndTypeRuleChecker(
            null, new String[]{}, Map.of(), false
        );
    }
}
