package seng201.team0.models;

/**
 * Class for holding information and operating on Adventurers
 */

public abstract class Adventurer implements Purchasable {

    /**
     * A series of setters for initialising an Adventurer
     **/
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

    /**
     * A series of getters for subclasses
     * @return (one of an Adventurer's) name, hiring cost, or description
     */
    @Override public String getName() { return name; }
    @Override public int getBuyPrice() { return hiringCost; }
    @Override public String getDescription() { return getType() + " — Pay: " + pay + "g/exp"; }


    public abstract String getType();

    /**
     * Method for increasing an Adventurer's stamina
     * @param amount the amount the Adventurer's stamina is going to increase
     */
    public void increaseStamina(int amount) {
        this.stamina = Math.min(stamina + amount, maxStamina); /*stamina cannot increase past maxStamina*/
    }
    /**
     * Method for decreasing an Adventurer's stamina
     * @param amount the amount the Adventurer's stamina is going to decrease
     *               Its stamina cannot decrease below zero
     */
    public void decreaseStamina(int amount) {
        this.stamina = Math.max(0, stamina - amount);
    }
    /**
     * Method for increasing an Adventurer's perception
     * @param amount the amount the Adventurer's perception is going to increase
     */
    public void increasePerception(int amount) {
        this.perception = this.perception + amount; /*no max to perception for now*/
    }
    /**
     * Method for decreasing an Adventurer's perception
     * @param amount the amount the Adventurer's perception is going to decrease
     *               Its stamina cannot decrease below zero
     */
    public void decreasePerception(int amount) {
        this.perception = Math.max(this.perception - amount, 0);
    }


    /**
     * Method for equipping and item for and Adventurer
     * Removes any current equipped item first
     * @param item the item being equipped
     */
    public void equip(Item item) {
        if (equippedItem != null) equippedItem.removeFrom(this);
        equippedItem = item;
        if (item != null) item.applyTo(this);
    }


    /**
     * A series of getters to retrieve information about an Adventurer
     * @return (one of and Adventurer's) MaxStamina, CurrentStamina
     *          Perception, HiringCost, Pay per expedition
     */
    public int getMaxStamina() {return maxStamina;}
    public int getStamina() {return stamina;}
    public int getPerception() {return perception;}
    public int getHiringCost() {return hiringCost;}
    public int getPay() {return pay;}

}
