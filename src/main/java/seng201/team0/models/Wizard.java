package seng201.team0.models;

public class Wizard extends Adventurer {

    public Wizard() {
        super("Wizard", 50, 100, 35, 10);
    }

    @Override
    public String getType() {

        return "Wizard";
    }


}
