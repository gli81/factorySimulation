package edu.duke.ece651.group6.factorySimulation.DataModel;

public class Mine extends Building {

    private final Recipe outputItem;

    public Mine(String name, Recipe outputItem) {
        super(name, -1, -1);
        this.outputItem = outputItem;
    }

    public Mine(String name, Recipe outputItem, int x, int y) {
        super(name, x, y);
        this.outputItem = outputItem;
    }

    public Recipe getOutputItem() {
        return this.outputItem;
    }

    public boolean isRecipeSupported(Recipe recipe) {
        return this.outputItem.equals(recipe);
    }

    /**
     * Mines do not have sources
     * 
     * @param source the source to add
     * @throws UnsupportedOperationException if the mine has sources
     */
    @Override
    public void addSource(Building source) {
        throw new UnsupportedOperationException("Mines do not have sources");
    }

    /**
     * Mines do not have sources
     * 
     * @return null
     */
    @Override
    public Building sourceSelect(Recipe sourceRecipe) {
        throw new UnsupportedOperationException("Mines do not have sources");
    }

    /*
     * two mines are equal if they have the same name and output item
     * since name is unique for each mine
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Mine mine = (Mine) obj;
        boolean isNameEqual = this.getName().equals(mine.getName());
        boolean isOutputItemEqual = this.outputItem.equals(mine.outputItem);
        return isNameEqual && isOutputItemEqual;
    }

    @Override
    public int hashCode() {
        return 31 * this.getName().hashCode() + this.outputItem.hashCode();
    }

    @Override
    public String toString() {
        return "Mine: { " + this.getName() + ", " + this.outputItem.getName() + " }\n";
    }
}
