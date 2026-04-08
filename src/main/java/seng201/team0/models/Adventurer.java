package seng201.team0.models;

public class Adventurer {

    private final String name;
    private int stamina;
    private int perception;
    private final int hiringCost;
    private final int pay;

    public Adventurer(String name, int stamina, int perception, int hiringCost, int pay) {
        this.name = name;
        this.stamina = stamina;
        this.perception = perception;
        this.hiringCost = hiringCost;
        this.pay = pay;
    }


    public String getName() {
        return name;
    }

    public int getStamina() {
        return stamina;
    }

    public int getPerception() {
        return perception;
    }

    public int getHiringCost() {
        return hiringCost;
    }

    public int getPay() {
        return pay;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public void setPerception(int perception) {
        this.perception = perception;
    }

}
