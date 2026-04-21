package seng201.team0.models;

import java.util.Objects;

public abstract class Item {

    private final String name;
    private final int cost;
    private final String description;

    public Item(String name, int cost, String description) {
        this.name = Objects.requireNonNull(name, "name");
        this.cost = cost;
        this.description = Objects.requireNonNull(description, "description");
    }

    public abstract String getType();

    public abstract void applyTo(Adventurer adventurer);

//sell price will be decided based off difficulty selected.
    public double getSellPrice(double difficultyMultiplier) {
        return (cost * difficultyMultiplier);
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }
}
