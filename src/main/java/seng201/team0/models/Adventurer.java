package seng201.team0.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for holding information and operating on Adventurers
 */

public abstract class Adventurer implements Purchasable {

    /**
     * A series of setters for initializing an Adventurer
     **/
    private final String name;
    private int stamina;
    private int maxStamina;
    private int perception;
    private final int hiringCost;
    private final int pay;
    private Item equippedItem;
    private final List<Skill> skills = new ArrayList<>();


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


    private void increaseMaxStamina(int staminaBonus) {
        if (staminaBonus < 0) throw new IllegalArgumentException("amount must be >= 0");
        this.maxStamina += staminaBonus;
        this.stamina += staminaBonus;
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


    public void learnSkill(Skill skill) {
        skills.add(skill);
        if (skill.getStaminaBonus() > 0) {
            increaseMaxStamina(skill.getStaminaBonus());
        }
        if (skill.getPerceptionBonus() != 0) {
            increasePerception(skill.getPerceptionBonus());
        }
    }

    @Override
    public String toString() {return name;}


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
    public Item getEquippedItem() { return equippedItem; }
    public List<Skill> getSkills() {return Collections.unmodifiableList(skills); }

}
