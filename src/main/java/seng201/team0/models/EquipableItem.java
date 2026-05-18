package seng201.team0.models;

/**
 *  Subclass of item for dealing with item which are applied to the Adventurer
 *  These items change an Adventurer's stats while they are equipped
 */
public class EquipableItem extends Item {

    private final String stat;
    private final int amount;

    /**
     * Constructs a new EquipableItem.
     * @param name display name
     * @param cost gold cost
     * @param description short blurb
     * @param stat the stat this item buffs: "stamina" or "perception"
     * @param amount the size of the buff
     */
    public EquipableItem(String name, int cost, String description, String stat, int amount) {
        super(name, cost, description);
        this.stat = stat;
        this.amount = amount;
    }

    @Override
    public String getType() {
        return "Equipable";
    }

    @Override
    public boolean isConsumedOnUse() {
        return false;
    }

    /**
     * Applies an increase to the specified stat of the given adventurer.
     * @param adventurer the adventurer whose stat will be increased
     */
    @Override
    public void applyTo(Adventurer adventurer) {
        if (stat.equals("stamina")) {
            adventurer.increaseMaxStamina(amount);
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

    public String getStat() { return stat; }
    public int getAmount() { return amount; }
}