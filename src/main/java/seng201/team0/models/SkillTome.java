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

    @Override
    public String getType() {
        return "Skill Tome";
    }

    @Override
    public void applyTo(Adventurer adventurer) {adventurer.learnSkill(skill);}

    @Override
    public void removeFrom(Adventurer adventurer) { }

    @Override
    public boolean isConsumedOnUse() { return true; }

    public Skill getSkill(){
        return skill;
    }
}
