package seng201.team0.models;

/**
 *
 */
public class Archer extends Adventurer {

    public Archer() {
        super("Archer", 75, 85, 45, 8);
    }

    @Override
    public String getType() {
        return "Archer";
    }


    @Override
    public int getBuyPrice() {
        return getHiringCost();
    }

    @Override
    public String getDescription() {
        return "Archer has reasonable perception and stamina";
    }
}
