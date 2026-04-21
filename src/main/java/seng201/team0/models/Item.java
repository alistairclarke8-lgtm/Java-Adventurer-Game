package seng201.team0.models;

public abstract class Item {

    private final String name;
    private final int cost;
    private final String description;

    public Item(String name, int cost, String description) {
        this.name = name;
        this.cost = cost;
        this.description = description;
    }

    public abstract String getType();

    public abstract void applyTo(Adventurer adventurer);

    public abstract boolean isConsumedOnUse();

//sell price will be decided based off difficulty selected.
    public double getSellPrice(double difficultyMultiplier) {
        return (cost * difficultyMultiplier);
    }

    public String getName() {return name;}

    public int getCost() {return cost;}

    public String getDescription() {return description;}
}
