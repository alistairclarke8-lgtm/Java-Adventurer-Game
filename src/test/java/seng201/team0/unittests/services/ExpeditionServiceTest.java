package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.GameEnvironment;
import seng201.team0.exception.ExpeditionOverException;
import seng201.team0.exception.InsufficientGoldException;
import seng201.team0.unittests.gui.TestNavigator;
import seng201.team0.models.Adventurer;
import seng201.team0.models.Difficulty;
import seng201.team0.models.Event;
import seng201.team0.models.EventOption;
import seng201.team0.models.EventOutcome;
import seng201.team0.models.Expedition;
import seng201.team0.models.ExpeditionResult;
import seng201.team0.models.SetupModel;
import seng201.team0.services.ExpeditionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ExpeditionService} using a deterministic
 * synthetic expedition and seeded {@link Random}.
 */
class ExpeditionServiceTest {

    private GameEnvironment environment;
    private ExpeditionService service;
    private Expedition expedition;

    /** Builds a 5-event expedition with single deterministic outcomes. */
    private Expedition buildDeterministicExpedition(int goldPerEvent, int staminaPerEvent) {
        List<Event> events = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            events.add(new Event("ev " + i,
                    List.of(new EventOption("only",
                            List.of(new EventOutcome("ok " + i, 1, goldPerEvent, staminaPerEvent, false))))));
        }
        return new Expedition("Test Exp", "desc", 1.0, -1, events);
    }

    @BeforeEach
    void setUp() {
        TestNavigator navigator = new TestNavigator();
        environment = new GameEnvironment(navigator);

        SetupModel setup = new SetupModel();
        setup.setGuildName("Testers");
        setup.setDifficulty(Difficulty.NORMAL);
        setup.setExpeditionCount(5);
        for (int i = 0; i < 3; i++) {
            setup.setSelectedAdventurer(i, setup.getDefaultAdventurer(i));
        }
        environment.onSetupComplete(setup);

        // Replace the service with a seeded one for deterministic tests
        service = new ExpeditionService(environment, new Random(7));
        expedition = buildDeterministicExpedition(10, -5);
        environment.setSelectedExpedition(expedition);
    }

    @Test
    void startExpedition_resetsState() {
        service.startExpedition(expedition);
        assertEquals(0, service.getEventsCompleted());
        assertEquals(0, service.getGoldEarned());
        assertFalse(service.isExpeditionOver());
        assertSame(expedition.getEvents().getFirst(), service.getCurrentEvent());
    }

    @Test
    void completeChoice_advancesEventAndUpdatesGold() throws ExpeditionOverException, InsufficientGoldException {
        service.startExpedition(expedition);
        service.completeChoice(0);
        assertEquals(1, service.getEventsCompleted());
        assertEquals(10, service.getGoldEarned());
    }

    @Test
    void completeChoice_endsAfterAllEvents() throws ExpeditionOverException, InsufficientGoldException {
        service.startExpedition(expedition);
        for (int i = 0; i < 5; i++) service.completeChoice(0);
        assertTrue(service.isExpeditionOver());
        assertNull(service.getCurrentEvent());
    }

    @Test
    void completeChoice_throwsAfterEnd() throws ExpeditionOverException, InsufficientGoldException {
        service.startExpedition(expedition);
        for (int i = 0; i < 5; i++) service.completeChoice(0);
        assertThrows(ExpeditionOverException.class, () -> service.completeChoice(0));
    }

    @Test
    void completeChoice_outcomeFlagEndsExpedition() throws ExpeditionOverException, InsufficientGoldException {
        List<Event> events = new ArrayList<>();
        events.add(new Event("p", List.of(new EventOption("opt",
                List.of(new EventOutcome("over", 1, 0, 0, true))))));
        for (int i = 1; i < 5; i++) {
            events.add(new Event("p" + i, List.of(new EventOption("opt",
                    List.of(new EventOutcome("ok", 1, 0, 0, false))))));
        }
        Expedition fatalExp = new Expedition("X", "y", 1.0, -1, events);
        environment.setSelectedExpedition(fatalExp);
        service.startExpedition(fatalExp);
        service.completeChoice(0);
        assertTrue(service.isExpeditionOver());
    }

    @Test
    void refreshExpeditions_givesThreeChoices() {
        service.refreshExpeditions();
        assertEquals(3, service.getAvailableExpeditions().size());
    }

    @Test
    void partyExhaustion_endsExpedition() throws ExpeditionOverException, InsufficientGoldException {
        // Build an expedition with a stamina cutoff of 100, so any drain ends it
        List<Event> events = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            events.add(new Event("p" + i, List.of(new EventOption("opt",
                    List.of(new EventOutcome("ok", 1, 0, -10, false))))));
        }
        Expedition tough = new Expedition("Tough", "x", 1.0, 1000, events);
        environment.setSelectedExpedition(tough);
        service.startExpedition(tough);
        service.completeChoice(0);
        assertTrue(service.isExpeditionOver());
    }
}
