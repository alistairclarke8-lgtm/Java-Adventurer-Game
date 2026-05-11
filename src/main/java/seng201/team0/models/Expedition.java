package seng201.team0.models;

import seng201.team0.models.events.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a single expedition destination the player can choose to embark
 * on. Each expedition has a name, a short description, an event pool to draw
 * from, a maximum number of events for a full expedition, a pay multiplier
 * applied to adventurer wages, and a reward multiplier on gold gains.
 *
 * <p>Use {@code ExpeditionService} to actually run an expedition; this class
 * is just the data describing one.
 */
public class Expedition {

    private final String name;
    private final String description;
    private final int maxEvents;
    private final double payMultiplier;
    private final double rewardMultiplier;
    private final List<Event> eventPool;

    /**
     * Constructs a new expedition definition.
     * @param name display name shown when picking a destination
     * @param description short blurb about what makes this expedition different
     * @param maxEvents the maximum number of events for a full run
     * @param payMultiplier multiplier applied to adventurer pay (e.g. 1.5 = harder)
     * @param rewardMultiplier multiplier applied to gold rewards
     * @param eventPool the events that can occur here
     */
    public Expedition(String name, String description, int maxEvents,
                      double payMultiplier, double rewardMultiplier,
                      List<Event> eventPool) {
        this.name = name;
        this.description = description;
        this.maxEvents = maxEvents;
        this.payMultiplier = payMultiplier;
        this.rewardMultiplier = rewardMultiplier;
        this.eventPool = new ArrayList<>(eventPool);
    }

    /** @return the display name */
    public String getName() { return name; }

    /** @return the short description shown in the expedition picker */
    public String getDescription() { return description; }

    /** @return the maximum number of events a full run can present */
    public int getMaxEvents() { return maxEvents; }

    /** @return multiplier applied to adventurer pay on this expedition */
    public double getPayMultiplier() { return payMultiplier; }

    /** @return multiplier applied to gold rewards on this expedition */
    public double getRewardMultiplier() { return rewardMultiplier; }

    /** @return an unmodifiable view of the event pool */
    public List<Event> getEventPool() {
        return Collections.unmodifiableList(eventPool);
    }
}
