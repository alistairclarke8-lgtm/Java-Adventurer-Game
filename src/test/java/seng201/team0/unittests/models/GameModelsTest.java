package seng201.team0.unittests.models;

import org.junit.jupiter.api.Test;
import seng201.team0.models.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Expedition}, {@link ExpeditionResult},
 * {@link Skill}, and {@link Difficulty}.
 */
class GameModelsTest {

    private List<Event> fiveEvents() {
        List<Event> events = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            events.add(new Event("event " + i,
                    List.of(new EventOption("only",
                            List.of(new EventOutcome("desc", 1, 0, 0, false))))));
        }
        return events;
    }

    @Test
    void expedition_rejectsFewerThanFiveEvents() {
        List<Event> tooFew = fiveEvents();
        tooFew.remove(0);
        assertThrows(IllegalArgumentException.class,
                () -> new Expedition("x", "y", 1.0, 5, tooFew));
    }

    @Test
    void expedition_rejectsNullEvents() {
        assertThrows(IllegalArgumentException.class,
                () -> new Expedition("x", "y", 1.0, 5, null));
    }

    @Test
    void expedition_storesAllFields() {
        Expedition expedition = new Expedition("name", "desc", 1.5, 8, fiveEvents());
        assertEquals("name", expedition.getName());
        assertEquals("desc", expedition.getDescription());
        assertEquals(1.5, expedition.getPayModifier());
        assertEquals(8, expedition.getStaminaCutoffThreshold());
        assertEquals(5, expedition.getEvents().size());
    }

    @Test
    void expedition_eventsListIsImmutable() {
        Expedition expedition = new Expedition("name", "desc", 1.0, 5, fiveEvents());
        assertThrows(UnsupportedOperationException.class,
                () -> expedition.getEvents().clear());
    }

    @Test
    void expeditionResult_netGoldIsEarnedMinusPay() {
        ExpeditionResult result = new ExpeditionResult("name", 5, 100, 30,
                List.of(), List.of(), false);
        assertEquals(70, result.getNetGold());
    }

    @Test
    void expeditionResult_storesAllFields() {
        ExpeditionResult result = new ExpeditionResult("name", 3, 50, 20,
                List.of(), List.of("event"), true);
        assertEquals("name", result.getExpeditionName());
        assertEquals(3, result.getEventsCompleted());
        assertEquals(50, result.getGoldEarned());
        assertEquals(20, result.getPayDeducted());
        assertTrue(result.wasRetreated());
        assertEquals(1, result.getRandomEventMessages().size());
    }

    @Test
    void skill_equalityByAllFields() {
        Skill a = new Skill("Sight", "desc", 0, 8);
        Skill b = new Skill("Sight", "desc", 0, 8);
        Skill c = new Skill("Sight", "desc", 0, 9);
        assertEquals(a, b);
        assertNotEquals(a, c);
    }

    @Test
    void skill_storesBonuses() {
        Skill skill = new Skill("name", "desc", 5, 3);
        assertEquals(5, skill.getStaminaBonus());
        assertEquals(3, skill.getPerceptionBonus());
    }

    @Test
    void difficulty_hardHasHigherCostMultiplier() {
        assertTrue(Difficulty.HARD.getCostMultiplier() > Difficulty.EASY.getCostMultiplier());
    }

    @Test
    void difficulty_easyHasHighestStartingGold() {
        assertTrue(Difficulty.EASY.getStartingGold() > Difficulty.HARD.getStartingGold());
    }
}
