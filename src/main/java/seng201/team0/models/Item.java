package seng201.team0.models;

public class Item {

    private final String name;
    private final int cost;
    private final String description;
    private final String statAffected;
    private final int affectAmount;

    public Item(String name, int cost, String description, String statAffected, int affectAmount) {
        this.name = name;
        this.cost = cost;
        this.description = description;
        this.statAffected = statAffected;
        this.affectAmount = affectAmount;

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

    public String getStatAffected() {
        return statAffected;
    }

    public int getAffectAmount() {
        return affectAmount;
    }
}
