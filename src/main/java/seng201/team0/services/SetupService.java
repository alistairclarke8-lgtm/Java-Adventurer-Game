package seng201.team0.services;

import seng201.team0.exception.SetupValidationException;
import seng201.team0.models.Adventurer;
import seng201.team0.models.SetupModel;

import java.util.Arrays;

/**
 * Handles game setup logic and validation.
 */
public class SetupService {

    private final SetupModel setupModel;

    /**
     * Creates a setup service.
     * @param setupModel setup model instance
     */
    public SetupService(SetupModel setupModel) {
        this.setupModel = setupModel;
    }

    /**
     * Selects an adventurer if not already chosen.
     * @param selectedAdventurers current selected adventurers
     * @param availableIndex index from available list
     * @param selectedSlot slot to place adventurer
     * @return true if selected successfully
     */
    public boolean selectAdventurer(Adventurer[] selectedAdventurers, int availableIndex, int selectedSlot) {
        Adventurer adventurer = setupModel.getDefaultAdventurer(availableIndex);
        boolean alreadySelected = Arrays.stream(selectedAdventurers).anyMatch(a -> a == adventurer);

        if (alreadySelected) return false;

        setupModel.setSelectedAdventurer(selectedSlot, adventurer);
        selectedAdventurers[selectedSlot] = adventurer;
        return true;
    }

    /**
     * Gets a default adventurer.
     * @param index adventurer index
     * @return adventurer at index
     */
    public Adventurer getDefaultAdventurer(int index) {
        return setupModel.getDefaultAdventurer(index);
    }

    /**
     * Validates setup and starts the game.
     * @param selectedAdventurers chosen adventurers
     * @throws SetupValidationException if setup is invalid
     */
    public void completeSetup(Adventurer[] selectedAdventurers) throws SetupValidationException {
        StringBuilder errors = new StringBuilder();

        String guildName = setupModel.getGuildName();

        if (guildName == null || guildName.isBlank()) {
            errors.append("- Guild name is required.\n");
        } else if (guildName.length() < 3 || guildName.length() > 15) {
            errors.append("- Guild name must be between 3 and 15 characters.\n");
        } else if (!guildName.matches("[a-zA-Z0-9 ]+")) {
            errors.append("- Guild name must not include special characters.\n");
        }

        if (setupModel.getDifficulty() == null) {
            errors.append("- A difficulty must be selected.\n");
        }

        if (setupModel.getExpeditionCount() <= 0) {
            errors.append("- An expedition count must be selected.\n");
        }

        int chosenCount = 0;
        for (Adventurer adventurer : selectedAdventurers) {
            if (adventurer != null) {
                chosenCount++;
            }
        }

        if (chosenCount < 3) {
            errors.append("- You must select 3 starting adventurers.\n");
        }

        if (!errors.isEmpty()) {
            throw new SetupValidationException(errors.toString().trim());
        }
    }
}