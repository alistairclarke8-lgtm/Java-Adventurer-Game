package seng201.team0.models;

/**
 * Wizard adventurer. Lower stamina, but the highest perception of any class.
 */
public class Wizard extends Adventurer {

    /**
     * Constructs a new Adventurer (Wizard) with wizard attributes
     */
    public Wizard() {
        super("Wizard", 50, 100, 35, 10);
    }

    @Override
    public String getType() {
        return "Wizard";
    }


    @Override
    public int getBuyPrice() {
        return getHiringCost();
    }

    @Override
    public String getDescription() {
        return "";
    }
}
