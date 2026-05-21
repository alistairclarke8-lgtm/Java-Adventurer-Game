package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.exception.SetupValidationException;
import seng201.team0.models.Adventurer;
import seng201.team0.models.Difficulty;
import seng201.team0.models.SetupModel;
import seng201.team0.services.SetupService;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link SetupService}.
 */
class SetupServiceTest {

    private SetupModel setupModel;
    private SetupService setupService;
    private Adventurer[] selected;

    @BeforeEach
    void setUp() {
        setupModel = new SetupModel();
        setupService = new SetupService(setupModel);
        selected = new Adventurer[3];
    }

    private void fillValidSetup() {
        setupModel.setGuildName("Testers");
        setupModel.setDifficulty(Difficulty.NORMAL);
        setupModel.setExpeditionCount(5);
        for (int i = 0; i < 3; i++) {
            setupService.selectAdventurer(selected, i, i);
        }
    }

    @Test
    void completeSetup_passesWithValidInput() {
        fillValidSetup();
        assertDoesNotThrow(() -> setupService.completeSetup(selected));
    }

    @Test
    void completeSetup_failsWithEmptyGuildName() {
        setupModel.setGuildName("");
        setupModel.setDifficulty(Difficulty.NORMAL);
        setupModel.setExpeditionCount(5);
        for (int i = 0; i < 3; i++) setupService.selectAdventurer(selected, i, i);
        SetupValidationException ex = assertThrows(SetupValidationException.class,
                () -> setupService.completeSetup(selected));
        assertTrue(ex.getMessage().toLowerCase().contains("guild name"));
    }

    @Test
    void completeSetup_failsWithShortGuildName() {
        setupModel.setGuildName("ab");
        setupModel.setDifficulty(Difficulty.NORMAL);
        setupModel.setExpeditionCount(5);
        for (int i = 0; i < 3; i++) setupService.selectAdventurer(selected, i, i);
        assertThrows(SetupValidationException.class, () -> setupService.completeSetup(selected));
    }

    @Test
    void completeSetup_failsWithLongGuildName() {
        setupModel.setGuildName("ThisNameIsWayTooLong");
        setupModel.setDifficulty(Difficulty.NORMAL);
        setupModel.setExpeditionCount(5);
        for (int i = 0; i < 3; i++) setupService.selectAdventurer(selected, i, i);
        assertThrows(SetupValidationException.class, () -> setupService.completeSetup(selected));
    }

    @Test
    void completeSetup_failsWithSpecialCharsInName() {
        setupModel.setGuildName("Bad@Name!");
        setupModel.setDifficulty(Difficulty.NORMAL);
        setupModel.setExpeditionCount(5);
        for (int i = 0; i < 3; i++) setupService.selectAdventurer(selected, i, i);
        assertThrows(SetupValidationException.class, () -> setupService.completeSetup(selected));
    }

    @Test
    void completeSetup_failsWithNoDifficulty() {
        setupModel.setGuildName("Testers");
        setupModel.setExpeditionCount(5);
        for (int i = 0; i < 3; i++) setupService.selectAdventurer(selected, i, i);
        assertThrows(SetupValidationException.class, () -> setupService.completeSetup(selected));
    }

    @Test
    void completeSetup_failsWithFewerThanThreeAdventurers() {
        setupModel.setGuildName("Testers");
        setupModel.setDifficulty(Difficulty.NORMAL);
        setupModel.setExpeditionCount(5);
        setupService.selectAdventurer(selected, 0, 0);
        SetupValidationException ex = assertThrows(SetupValidationException.class,
                () -> setupService.completeSetup(selected));
        assertTrue(ex.getMessage().contains("3 starting adventurers"));
    }

    @Test
    void selectAdventurer_rejectsDuplicates() {
        boolean firstOk = setupService.selectAdventurer(selected, 0, 0);
        boolean secondOk = setupService.selectAdventurer(selected, 0, 1);
        assertTrue(firstOk);
        assertFalse(secondOk);
        // slot 1 should still be null
        assertNull(selected[1]);
    }

    @Test
    void selectAdventurer_acceptsDifferentChoices() {
        assertTrue(setupService.selectAdventurer(selected, 0, 0));
        assertTrue(setupService.selectAdventurer(selected, 1, 1));
        assertNotSame(selected[0], selected[1]);
    }
}
