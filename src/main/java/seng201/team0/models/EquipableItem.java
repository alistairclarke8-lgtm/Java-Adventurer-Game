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
    public void removeItem(Adventurer adventurer) {
        if (stat.equals("stamina")) {
            adventurer.decreaseStamina(amount);
        }
        if (stat.equals("perception")) {
            adventurer.decreasePerception(adventurer.getPerception());
        }


    }
}
