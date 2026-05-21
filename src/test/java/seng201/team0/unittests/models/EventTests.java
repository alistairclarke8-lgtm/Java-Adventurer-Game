package seng201.team0.unittests.models;

import org.junit.jupiter.api.Test;
import seng201.team0.models.Event;
import seng201.team0.models.EventOption;
import seng201.team0.models.EventOutcome;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Event}, {@link EventOption} and {@link EventOutcome}.
 */
class EventTest {

    @Test
    void eventOutcome_rejectsZeroWeight() {
        assertThrows(IllegalArgumentException.class,
                () -> new EventOutcome("nope", 0, 0, 0, false));
    }

    @Test
    void eventOutcome_storesAllFields() {
        EventOutcome outcome = new EventOutcome("desc", 50, 10, -5, true);
        assertEquals("desc", outcome.getDescription());
        assertEquals(50, outcome.getWeight());
        assertEquals(10, outcome.getGoldChange());
        assertEquals(-5, outcome.getStaminaChange());
        assertTrue(outcome.endsExpedition());
    }

    @Test
    void event_rejectsEmptyOptions() {
        assertThrows(IllegalArgumentException.class,
                () -> new Event("prompt", List.of()));
    }

    @Test
    void event_optionsAreUnmodifiable() {
        Event event = new Event("p",
                List.of(new EventOption("A",
                        List.of(new EventOutcome("o", 1, 0, 0, false)))));
        assertThrows(UnsupportedOperationException.class,
                () -> event.options().add(null));
    }

    @Test
    void eventOption_rejectsEmptyOutcomes() {
        assertThrows(IllegalArgumentException.class,
                () -> new EventOption("A", List.of()));
    }

    @Test
    void rollOutcome_singleOutcomeAlwaysReturnsIt() {
        EventOutcome only = new EventOutcome("solo", 1, 5, 0, false);
        EventOption option = new EventOption("A", List.of(only));
        // Use a fixed-seed Random; doesn't matter what it returns
        assertSame(only, option.rollOutcome(new Random(0)));
    }

    @Test
    void rollOutcome_respectsWeights() {
        // outcome A weight 1, outcome B weight 99 -> over many rolls B dominates
        EventOutcome a = new EventOutcome("a", 1, 0, 0, false);
        EventOutcome b = new EventOutcome("b", 99, 0, 0, false);
        EventOption option = new EventOption("opt", List.of(a, b));

        Random random = new Random(42);
        int aCount = 0;
        int bCount = 0;
        for (int i = 0; i < 10_000; i++) {
            EventOutcome rolled = option.rollOutcome(random);
            if (rolled == a) aCount++;
            else bCount++;
        }
        // B should be picked vastly more often than A
        assertTrue(bCount > aCount * 50, "Expected B to dominate but got A=" + aCount + " B=" + bCount);
    }
}
