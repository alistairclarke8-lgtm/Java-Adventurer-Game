package seng201.team0.models;

import java.util.Objects;

/**
 * A consumable item that teaches an adventurer a
 */
public class SkillTome extends Item {

    private final Skill skill;

    /**
     * Constructor for SkillTome. it calls the superclass to create and instance of
     * a skill tome
     * @param name The name of the skill being applied to the adventurer
     * @param cost the cost of said skill to buy and learn
     * @param description Description of the skill in question
     * @param skill The skill in question being learned by the adventurer
     */
    public SkillTome(String name, int cost, String description, Skill skill) {
        super(name, cost, description);
        this.skill = Objects.requireNonNull(skill, "skill");
    }

    /** @return "Skill Tome" */
    @Override
    public String getType() {
        return "Skill Tome";
    }

    /**
     * Teaches the contained skill to the given adventurer.
     * @param adventurer the recipient
     */
    @Override
    public void applyTo(Adventurer adventurer) {adventurer.learnSkill(skill);}

    /**
     * Skills cannot be unlearned
     * @param adventurer the adventurer to un-apply the effect from
     */
    @Override
    public void removeFrom(Adventurer adventurer) { }

    /** @return true skillTomes are destroyed on use */
    @Override
    public boolean isConsumedOnUse() { return true; }

    /** @return the skill granted by this tome */
    public Skill getSkill(){
        return skill;
    }
}
