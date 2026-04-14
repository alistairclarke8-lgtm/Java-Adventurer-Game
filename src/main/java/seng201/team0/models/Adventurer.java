package seng201.team0.models;

public abstract class Adventurer {

    private final String name;
    private int stamina;
    private final int maxStamina;
    private int perception;
    private final int hiringCost;
    private final int pay;

    public Adventurer(String name, int stamina, int perception, int hiringCost, int pay) {
        this.name = name;
        this.stamina = stamina;
        this.perception = perception;
        this.hiringCost = hiringCost;
        this.pay = pay;
        this.maxStamina = stamina;
    }

    public abstract String getType();

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

    public void increaseStamina(int amount) {
        this.stamina = Math.min(stamina + amount, maxStamina); /*stamina cannot increase past maxStamina*/

    }

    public void decreaseStamina(int amount) {
        this.stamina = Math.max(0, stamina - amount);
    }

}
