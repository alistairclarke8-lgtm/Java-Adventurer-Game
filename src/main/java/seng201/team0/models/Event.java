package seng201.team0.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A single event encountered during an expedition. An event has a
 * prompt and one or more options the player can pick between.
 */
public record Event(String prompt, List<EventOption> options) {

    /**
     * Constructs a new event.
     *
     * @param prompt  the narrative text shown to the player
     * @param options one or more options the player can pick
     */
    public Event(String prompt, List<EventOption> options) {
        if (options == null || options.isEmpty()) {
            throw new IllegalArgumentException("must supply at least one option");
        }
        this.prompt = prompt;
        this.options = new ArrayList<>(options);
    }

    /**
     * @return the narrative prompt for this event
     */
    @Override
    public String prompt() {
        return prompt;
    }

    /**
     * @return an unmodifiable view of the player's choices
     */
    @Override
    public List<EventOption> options() {
        return Collections.unmodifiableList(options);
    }
}
