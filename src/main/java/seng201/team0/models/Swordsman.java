package seng201.team0.models;

public class Swordsman extends Adventurer {

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
