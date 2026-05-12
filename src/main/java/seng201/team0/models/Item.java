package seng201.team0.models;

import java.util.Objects;

/**
 * Abstract class for anything the player can gold in their Guild inventory
 * Each item has a name, cost, description, and methods to apply
 * increases / decreases to attributes
 */
public abstract class Item implements Purchasable {

    private final String name;
    private final int cost;
    private final String description;

    /**
     * Constructs an Item with the given core attributes.
     * @param name display name; must not be null
     * @param cost gold cost in the market
     * @param description short player-facing blurb; must not be null
     */
    public Item(String name, int cost, String description) {
        this.name = Objects.requireNonNull(name, "name");
        this.cost = cost;
        this.description = Objects.requireNonNull(description, "description");
    }

    /**
     * @return the category of this item (e.g. "Equipable", "Upgrade",
     * "Skill Tome"); used for shop display.
     */
    public abstract String getType();

    /**
     * Applies this item's effect to the given adventurer (e.g. equip, or
     * permanently raise a stat).
     * @param adventurer the recipient of the effect
     */
    public abstract void applyTo(Adventurer adventurer);

    /**
     * Removes this item's effect from the given adventurer (no-op for items
     * that aren't equipable).
     * @param adventurer the adventurer to un-apply the effect from
     */
    public abstract void removeFrom(Adventurer adventurer);

    /**
     * @return true if using this item consumes it (e.g. an upgrade tome);
     * false if it can be re-equipped.
     */
    public abstract boolean isConsumedOnUse();

    /**
     * The sell price of this item, scaled by the difficulty's cost multiplier.
     * Items always sell at half their buy price before scaling.
     * @param difficultyMultiplier the cost multiplier
     * @return the gold the player receives when selling this item
     */
    public double getSellPrice(double difficultyMultiplier) {
        return (int) (cost * 0.5 * difficultyMultiplier);
    }

    /** @return the Item's name */
    @Override
    public String getName() { return name; }

    /** @return the Item's cost */
    public int getCost() {return cost;}

    /** @return the Item's description */
    @Override
    public String getDescription() {return description;}

    /** @return the buy price */
    @Override
    public int getBuyPrice() { return cost; }

    @Override
    public String toString() { return name + " (" + getType() + ", " + cost + "g)"; }
}
