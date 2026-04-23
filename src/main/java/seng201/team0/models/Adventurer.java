package seng201.team0.models;

public abstract class Adventurer implements Purchasable {

    private final String name;
    private int stamina;
    private final int maxStamina;
    private int perception;
    private final int hiringCost;
    private final int pay;
    private Item equippedItem;


    public Adventurer(String name, int stamina, int perception, int hiringCost, int pay) {
        this.name = name;
        this.stamina = stamina;
        this.perception = perception;
        this.hiringCost = hiringCost;
        this.pay = pay;
        this.maxStamina = stamina;
    }

    public abstract String getType();

    public void increaseStamina(int amount) {
        this.stamina = Math.min(stamina + amount, maxStamina); /*stamina cannot increase past maxStamina*/
    }
    public void decreaseStamina(int amount) {
        this.stamina = Math.max(0, stamina - amount);
    }
    public void increasePerception(int amount) {
        this.perception = this.perception + amount; /*no max to perception for now*/
    }
    public void decreasePerception(int amount) {
        this.perception = Math.max(this.perception - amount, 0);
    }


    public void equip(Item item) {
        if (equippedItem != null) equippedItem.removeFrom(this);
        equippedItem = item;
        if (item != null) item.applyTo(this);
    }


    public String getName() {return name;}
    public int getMaxStamina() {return maxStamina;}
    public int getStamina() {return stamina;}
    public int getPerception() {return perception;}
    public int getHiringCost() {return hiringCost;}
    public int getPay() {return pay;}

}
