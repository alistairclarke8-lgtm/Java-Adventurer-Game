package seng201.team0.models;

/**
 * Swordsman adventurer. High stamina, modest perception
 */
public class Swordsman extends Adventurer {

    /**
     * Constructs a new Swordsman with the standard swordsman stat block.
     */
    public Swordsman() {
        super("Swordsman", 90, 50, 35, 10);
    }

    @Override
    public String getType() {
        return "Swordsman";
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
