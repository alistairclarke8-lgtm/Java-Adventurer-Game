package seng201.team0.models;

/**
 * One possible result of an EventOption, including the narrative
 * text shown to the player and the impact. i.e. gold gained/lost,
 * stamina cost, and whether this outcome ends the expedition early.
 */
public class EventOutcome {

    private final String description;
    private final int weight;
    private final int goldChange;
    private final int staminaChange;
    private final boolean endsExpedition;

    /**
     * Constructs a new outcome.
     * @param description the narrative shown to the player when this outcome rolls
     * @param weight the weight used in the weighted random roll
     *               must be > 0. The probability of this outcome is
     *               weight / totalWeight for the parent EventOption.
     * @param goldChange gold awarded or taken
     * @param staminaChange stamina drained from each main-party adventurer
     * @param endsExpedition true if this outcome immediately ends the expedition
     */
    public EventOutcome(String description, int weight, int goldChange,
                        int staminaChange, boolean endsExpedition) {
        if (weight <= 0) {
            throw new IllegalArgumentException("weight must be > 0");
        }
        this.description = description;
        this.weight = weight;
        this.goldChange = goldChange;
        this.staminaChange = staminaChange;
        this.endsExpedition = endsExpedition;
    }

    /** @return the narrative description */
    public String getDescription() { return description; }

    /** @return the weight used in the weighted random roll */
    public int getWeight() { return weight; }

    /** @return gold change applied to the player's treasury */
    public int getGoldChange() { return goldChange; }

    /** @return stamina drained from each main-party adventurer */
    public int getStaminaChange() { return staminaChange; }

    /** @return true if this outcome ends the expedition early */
    public boolean endsExpedition() { return endsExpedition; }
}
