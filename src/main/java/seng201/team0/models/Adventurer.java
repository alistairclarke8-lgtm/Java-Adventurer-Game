package seng201.team0.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for holding information and operating on Adventurers
 */

public class Adventurer implements Purchasable {

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
    private int expeditionsInARow = 0;
    private final String type;


    /**
     * Constructor for an Adventurer
     * @param name Adventurer's name
     * @param stamina The initial stamina of an Adventurer. Also doubles as the initial maxStamina
     * @param perception The initial perception of an Adventurer
     * @param hiringCost amount needed to buy an Adventurer from the market
     * @param pay the amount an Adventurer must be paid per Expedition
     */
    public Adventurer(String name, int stamina, int perception, int hiringCost, int pay, String type) {
        this.name = name;
        this.stamina = stamina;
        this.perception = perception;
        this.hiringCost = hiringCost;
        this.pay = pay;
        this.maxStamina = stamina;
        this.type = type;
    }

    /**
     * A series of getters for subclasses
     * @return (one of an Adventurer's) name, hiring cost, or description
     */
    @Override public String getName() { return name; }
    @Override public int getBuyPrice() { return hiringCost; }
    @Override public String getDescription() { return getType() + " — Pay: " + pay + "g/exp"; }


    /**
     * Method for increasing an Adventurer's stamina
     * @param amount the amount the Adventurer's stamina is going to increase
     */
    public void increaseStamina(int amount) {
        if (amount < 0) throw new IllegalArgumentException("amount must be >= 0");
        this.stamina = Math.min(stamina + amount, maxStamina); /*stamina cannot increase past maxStamina*/
    }
    /**
     * Method for decreasing an Adventurer's stamina
     * @param amount the amount the Adventurer's stamina is going to decrease
     *               Its stamina cannot decrease below zero
     */
    public void decreaseStamina(int amount) {
        if (amount < 0) throw new IllegalArgumentException("amount must be >= 0");
        this.stamina = Math.max(0, stamina - amount);
    }
    /**
     * Method for increasing an Adventurer's perception
     * @param amount the amount the Adventurer's perception is going to increase
     */
    public void increasePerception(int amount) {
        if (amount < 0) throw new IllegalArgumentException("amount must be >= 0");
        this.perception = this.perception + amount; /*no max to perception for now*/
    }
    /**
     * Method for decreasing an Adventurer's perception
     * @param amount the amount the Adventurer's perception is going to decrease
     *               Its stamina cannot decrease below zero
     */
    public void decreasePerception(int amount) {
        if (amount < 0) throw new IllegalArgumentException("amount must be >= 0");
        this.perception = Math.max(this.perception - amount, 0);
    }


    public void increaseMaxStamina(int staminaBonus) {
        if (staminaBonus < 0) throw new IllegalArgumentException("amount must be >= 0");
        this.maxStamina += staminaBonus;
        this.stamina += staminaBonus;
    }

    /**
     * Restores the adventurer's stamina back to their current max.
     */
    public void fullyRest() {
        this.stamina = this.maxStamina;
    }


    /**
     * Method for equipping and item for and Adventurer
     * Removes any current equipped item first
     * @param item the item being equipped
     */
    public void setEquippedItem(Item item) {
        if (equippedItem != null) equippedItem.removeFrom(this);
        equippedItem = item;
        if (item != null) item.applyTo(this);
    }


    /**
     * Method for teaching an adventurer a skill
     * The modifying their attributes based on what the skill provides
     * @param skill The skill in question being applied to the Adventurer
     */
    public void learnSkill(Skill skill) {
        skills.add(skill);
        if (skill.getStaminaBonus() > 0) {
            increaseMaxStamina(skill.getStaminaBonus());
        }
        if (skill.getPerceptionBonus() != 0) {
            increasePerception(skill.getPerceptionBonus());
        }
    }

    /**
     * Increments the consecutive expedition counter (used for fatigue-based
     * random retirement chance).
     */
    public void incrementExpeditionsInARow() { this.expeditionsInARow++; }

    /**
     * Resets the consecutive expedition counter (when the adventurer is benched).
     */
    public void resetExpeditionsInARow() { this.expeditionsInARow = 0; }

    /** @return number of consecutive expeditions this adventurer has been on */
    public int getExpeditionsInARow() { return expeditionsInARow; }


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

    public String getType() {return type;}
}
