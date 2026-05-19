package seng201.team0.models;

import java.util.Objects;

/**
 * An immutable named attribute that an Adventurer can learn
 * from a SkillTome. Learning a skill grants the Adventurer with
 * a stamina and or perception bonus when the tome is processed
 */
public class Skill {

    private final String name;
    private final String description;
    private final int staminaBonus;
    private final int perceptionBonus;


    /**
     * The constructor for
     * @param name The name of the skill in question
     * @param description A string which represents what the skill is
     * @param staminaBonus The increase in stamina an Adventurer gets from the skill
     * @param perceptionBonus The increase in perception an Adventurer gets from the skill
     */
    public Skill(String name, String description,
                 int staminaBonus, int perceptionBonus) {
        this.name = Objects.requireNonNull(name, "Skill name");
        this.description = Objects.requireNonNull(description, "Skill description");
        this.staminaBonus = staminaBonus;
        this.perceptionBonus = perceptionBonus;
    }

    /**
     * A series of getters for the attributes of a skill
     * @return (one of) name, staminaBonus
     *                  description, perceptionBonus
     */
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getStaminaBonus() { return staminaBonus; }
    public int getPerceptionBonus() { return perceptionBonus; }


    /**
     * Compares this skill to another object for equality. Two skills are
     * considered equal if they have the same name, description, stamina bonus,
     * and perception bonus.
     * @param o the object to compare against
     * @return true if Object o is a Skill with the same fields, false otherwise
     */
    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (!(o instanceof Skill other)) return false;
        return staminaBonus == other.getStaminaBonus()
                && perceptionBonus == other.getPerceptionBonus()
                && name.equals(other.name)
                && description.equals(other.description);

    }

    /**
     * @return a human-readable string showing what the skill is about
     */
    @Override
    public String toString() {
        return "Skill{" + name + "(+"
                + staminaBonus + "sta, +"
                + perceptionBonus + "per)}";
    }
    
}
