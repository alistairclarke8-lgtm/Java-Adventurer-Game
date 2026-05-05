package seng201.team0.models;

/**
 *  Subclass of item for dealing with item which are applied to the Adventurer
 *  These items change an Adventurer's stats while they are equipped
 */
public class EquipableItem  extends Item {

    /**
     * Initializing the item and its attributes
     */
    private final String stat;
    private final int amount;

    public EquipableItem(String name, int cost, String description, String stat, int amount) {
        super(name, cost, description);
        this.stat = stat;
        this.amount = amount;
    }

    /**
     * Getter for the type of item we are dealing with.
     * @return "Equipable" as this is the item type
     */
    @Override
    public String getType() {
        return "Equipable";
    }

    /**
     * Getter for checking if the item is a consumable
     * @return False because the item with not be consumed on use
     */
    @Override
    public boolean isConsumedOnUse() {
        return false;
    }

    /**
     * Modifying the Adventurers's stats based on the item's type
     * It will change either
     * @param adventurer modifying the Adventurers's stats
     */
    @Override
    public void applyTo(Adventurer adventurer) {
        if (stat.equals("stamina")) {
            adventurer.increaseStamina(amount);
        }
        if (stat.equals("perception")) {
            adventurer.increasePerception(amount);
        }
    }

    /**
     * Removing an equipable item from an Adventurer
     * This method modifies the perception or stamina back to its original state
     * @param adventurer this is the Adventurer being modified
     */
    @Override
    public void removeFrom(Adventurer adventurer) {
        if ("stamina".equals(stat)) adventurer.decreaseStamina(amount);
        if ("perception".equals(stat)) adventurer.decreasePerception(amount);
    }



}
