package seng201.team0.models;

public class UpgradeItem extends Item {
    private final String stat;
    private final int amount;

    public UpgradeItem(String name, int cost, String description, String stat, int amount, boolean consumedOnUse) {
        super(name, cost, description);
        this.stat = stat;
        this.amount = amount;
    }
    @Override
    public String getType() {
        return "Upgrade";
    }
    @Override
    public void applyTo(Adventurer adventurer) {
        if (stat.equals("stamina")) {
            adventurer.increaseStamina(amount);
        }
        else if (stat.equals("perception")) {
            adventurer.increasePerception(amount);
        }
    }

    @Override
    public void removeFrom(Adventurer adventurer) {

    }

    public boolean isConsumedOnUse() {
        return true;
    }

    public String getStat() { return stat;}
    public int getAmount() { return amount;}



}
