package seng201.team0.models;

public class EquipableItem  extends Item {

    private final String stat;
    private final int amount;

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

    @Override
    public void applyTo(Adventurer adventurer) {
        if (stat.equals("stamina")) {
            adventurer.increaseStamina(amount);
        }
        if (stat.equals("perception")) {
            adventurer.increasePerception(adventurer.getPerception());
        }
    }

    @Override
    public void removeFrom(Adventurer adventurer) {
        if ("stamina".equals(stat)) adventurer.decreaseStamina(amount);
        if ("perception".equals(stat)) adventurer.decreasePerception(amount);
    }



}
