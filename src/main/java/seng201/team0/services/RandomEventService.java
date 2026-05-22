package seng201.team0.services;

import seng201.team0.GameEnvironment;
import seng201.team0.models.Adventurer;
import seng201.team0.models.Skill;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Runs per-round random events after each expedition. Rolls each main-party
 * adventurer for a stat change, a skill gain, and fatigue-based retirement.
 * Event frequency is gated by the current difficulty's random-event chance.
 */
public class RandomEventService {

    /** Catalogue of skills random events can grant. */
    private static final List<Skill> SKILL_POOL = List.of(
            new Skill("Lucky Find", "Some adventurers just have an eye for treasure.", 0, 3),
            new Skill("Second Wind", "Hardened from many expeditions.", 5, 0),
            new Skill("Tracker", "Reads the land like a book.", 2, 4),
            new Skill("Iron Constitution", "Shrugs off the toughest marches.", 7, 0),
            new Skill("Keen Senses", "Spots danger and treasure alike.", 0, 6)
    );

    private final GameEnvironment gameEnvironment;
    private final Random random;

    /**
     * Constructs a RandomEventService with a default Random
     * @param gameEnvironment the active game environment, must not be null
     */
    public RandomEventService(GameEnvironment gameEnvironment) {
        this(gameEnvironment, new Random());
    }

    /**
     * Constructs a RandomEventService with an explicit Random.
     * Used by unit tests to obtain deterministic outcomes.
     * @param gameEnvironment the active game environment
     * @param random the random source
     */
    public RandomEventService(GameEnvironment gameEnvironment, Random random) {
        this.gameEnvironment = gameEnvironment;
        this.random = random;
    }

    /**
     * Rolls random events for each main-party adventurer after an expedition.
     * Each adventurer is independently checked for a stat change, a skill
     * gain, and fatigue-based retirement. Returns human-readable messages
     * for display on the post-expedition screen.
     * @return unmodifiable list of messages describing events that occurred
     */
    public List<String> runRandomEvents() {
        List<String> messages = new ArrayList<>();
        double chance = gameEnvironment.getDifficulty().getRandomEventChance();

        // Snapshot the party so we can safely retire from it while iterating.
        List<Adventurer> party = new ArrayList<>(gameEnvironment.getGuildhall().getMainPartyAdventurers());

        for (Adventurer adventurer : party) {
            // Skip if already retired during this call.
            if (!gameEnvironment.getGuildhall().getMainPartyAdventurers().contains(adventurer)) {
                continue;
            }

            // stat change
            if (random.nextDouble() < chance) {
                messages.add(rollStatChange(adventurer));
            }

            // skill learning
            if (random.nextDouble() < chance) {
                messages.add(rollSkillLearned(adventurer));
            }

            // fatigue-based retirement. Chance increases with consecutive
            // expeditions and is also gated by difficulty.
            int streak = adventurer.getExpeditionsInARow();
            double retireChance = chance * (streak / 5.0);
            if (streak >= 2 && random.nextDouble() < retireChance
                    && gameEnvironment.getGuildHallService().getTotalAdventurers() > 1) {
                gameEnvironment.getGuildhall().getMainPartyAdventurers().remove(adventurer);
                messages.add(adventurer.getName() + " has retired from the guild after "
                        + streak + " expeditions in a row.");
            }
        }
        return Collections.unmodifiableList(messages);
    }

    /**
     * Rolls a random stat change (stamina or perception, positive or negative)
     * and applies it to the adventurer.
     * @param adventurer the adventurer being affected
     * @return a message describing what happened
     */
    private String rollStatChange(Adventurer adventurer) {
        boolean stamina = random.nextBoolean();
        boolean positive = random.nextBoolean();
        int amount = 3 + random.nextInt(5); // 3..7
        if (stamina) {
            if (positive) {
                adventurer.increaseMaxStamina(amount);
                return adventurer.getName() + " feels stronger! Max stamina +" + amount + ".";
            } else {
                adventurer.decreaseMaxStamina(amount);
                return adventurer.getName() + " is feeling worn. Max stamina -" + amount + ".";
            }
        } else {
            if (positive) {
                adventurer.increasePerception(amount);
                return adventurer.getName() + " has sharpened their senses. Perception +" + amount + ".";
            } else {
                adventurer.decreasePerception(amount);
                return adventurer.getName() + " has grown distracted. Perception -" + amount + ".";
            }
        }
    }

    /**
     * Picks a random skill from the pool and teaches it to the given adventurer.
     * @param adventurer the recipient of the skill
     * @return a message describing what happened
     */
    private String rollSkillLearned(Adventurer adventurer) {
        Skill skill = SKILL_POOL.get(random.nextInt(SKILL_POOL.size()));
        adventurer.learnSkill(skill);
        return adventurer.getName() + " learned the " + skill.getName() + " skill!";
    }
}
