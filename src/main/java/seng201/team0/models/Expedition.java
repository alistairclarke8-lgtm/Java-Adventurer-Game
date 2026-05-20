package seng201.team0.models;

import java.util.List;

/**
 * Represents one expedition the player can choose to go on.
 * An expedition has a name, description, pay modifier, a stamina cutoff
 * threshold that ends the expedition early, and an ordered list of
 * {@link Event}s that play out in sequence.
 */
public class Expedition {

    private final String name;
    private final String description;
    private final double payMultiplier;
    private final int staminaCutoffThreshold;
    private final List<Event> events;

    /**
     * Constructs an Expedition.
     * @param name display name shown to the player
     * @param description short description of the expedition
     * @param payMultiplier multiplier applied to each adventurer's pay
     * @param staminaCutoffThreshold if every party member's stamina drops at or below this value the expedition ends early.
     * @param events ordered list of events. Must have at least 5
     */
    public Expedition(String name, String description, double payMultiplier, int staminaCutoffThreshold,List<Event> events) {
        if (events == null || events.size() < 5) {
            throw new IllegalArgumentException("An expedition must have at least 5 events.");
        }
        this.name = name;
        this.description = description;
        this.payMultiplier = payMultiplier;
        this.staminaCutoffThreshold = staminaCutoffThreshold;
        this.events = List.copyOf(events);
    }

    /** @return expedition display name */
    public String getName() { return name; }

    /** @return short description shown on the selection card */
    public String getDescription() { return description; }

    /** @return pay multiplier for this expedition */
    public double getPayModifier() { return payMultiplier; }

    /** @return stamina level at or below which the expedition ends early */
    public int getStaminaCutoffThreshold() { return staminaCutoffThreshold; }

    /** @return unmodifiable ordered list of events */
    public List<Event> getEvents() { return events; }
}