package seng201.team0.models;

/**
 * Subclass of Item for one-time consumable upgrades. Using one
 * permanently increases a single stat on the chosen adventurer, then the
 * item is removed from the inventory.
 */
public class UpgradeItem extends Item {

    private final String stat;
    private final int amount;

    /**
     * Constructs a new UpgradeItem.
     * @param name display name
     * @param cost gold cost in the market
     * @param description short blurb
     * @param stat the stat to upgrade: "stamina" or "perception"
     * @param amount the size of the permanent buff
     */
    public UpgradeItem(String name, int cost, String description, String stat, int amount) {
        super(name, cost, description);
        this.stat = stat;
        this.amount = amount;
    }

    /**
     * Returning the type of item
     * @return "Upgrade"
     */
    @Override
    public String getType() {
        return "Upgrade";
    }

    /**
     * Permanently raises the chosen stat on the given adventurer.
     * @param adventurer the adventurer to upgrade
     */
    @Override
    public void applyTo(Adventurer adventurer) {
        if (stat.equals("stamina")) {
            adventurer.increaseMaxStamina(amount);
        } else if (stat.equals("perception")) {
            adventurer.increasePerception(amount);
        }
    }

    /**
     * Upgrades are consumed and have no removal.
     * @param adventurer the adventurer
     */
    @Override
    public void removeFrom(Adventurer adventurer) {

    }

    /**
     * @return true. Upgrades are consumed on use
     */
    public boolean isConsumedOnUse() {
        return true;
    }

    /** @return the stat name buffed by this upgrade */
    public String getStat() { return stat;}

    /** @return the size of the permanent buff */
    public int getAmount() { return amount;}



}
