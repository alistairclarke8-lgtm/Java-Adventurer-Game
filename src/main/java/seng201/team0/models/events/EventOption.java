package seng201.team0.models.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * One choice presented to the player at an event. The option holds a
 * weighted list of possible {@link EventOutcome}s; when picked, exactly one
 * outcome is rolled using a weighted random selection.
 */
public record EventOption(String label, List<EventOutcome> outcomes) {

    /**
     * Constructs a new option.
     * @param label the text shown on the button for this choice
     * @param outcomes one or more possible outcomes
     */
    public EventOption(String label, List<EventOutcome> outcomes) {
        if (outcomes == null || outcomes.isEmpty()) {
            throw new IllegalArgumentException("must supply at least one outcome");
        }
        this.label = label;
        this.outcomes = new ArrayList<>(outcomes);
    }

    /**
     * Rolls a random outcome weighted by each outcome's weight.
     * @param random the random source to use
     * @return the chosen outcome
     */
    public EventOutcome rollOutcome(Random random) {
        int totalWeight = 0;
        for (EventOutcome o : outcomes) totalWeight += o.getWeight();
        int roll = random.nextInt(totalWeight);
        int cumulative = 0;
        for (EventOutcome o : outcomes) {
            cumulative += o.getWeight();
            if (roll < cumulative) return o;
        }
        // unreachable
        return outcomes.getLast();
    }

    /**
     * @return the button label for this option
     */
    @Override
    public String label() {
        return label;
    }

    /**
     * @return an unmodifiable view of the possible outcomes
     */
    @Override
    public List<EventOutcome> outcomes() {
        return Collections.unmodifiableList(outcomes);
    }
}
