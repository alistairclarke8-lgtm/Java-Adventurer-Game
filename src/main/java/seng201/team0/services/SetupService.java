package seng201.team0.services;

import seng201.team0.GameEnvironment;
import seng201.team0.exception.SetupValidationException;
import seng201.team0.models.Adventurer;
import seng201.team0.models.Difficulty;

import java.util.Arrays;

/**
 * Handles game setup logic and validation.
 */
public class SetupService {

    private final GameEnvironment gameEnvironment;

    /**
     * Creates a setup service.
     * @param gameEnvironment game environment instance
     */
    public SetupService(GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
    }

    /**
     * Sets the game difficulty.
     * @param difficulty selected difficulty
     */
    public void setDifficulty(Difficulty difficulty) {
        gameEnvironment.setDifficulty(difficulty);
    }

    /**
     * Sets expedition count.
     * @param count number of expeditions
     */
    public void setExpeditionCount(int count) {
        gameEnvironment.setNumExpeditions(count);
    }

    /**
     * Sets the guild name.
     * @param name guild name
     */
    public void setGuildName(String name) {
        gameEnvironment.setGuildName(name);
    }

    /**
     * Selects an adventurer if not already chosen.
     * @param selectedAdventurers current selected adventurers
     * @param availableIndex index from available list
     * @param selectedSlot slot to place adventurer
     * @return true if selected successfully
     */
    public boolean selectAdventurer(Adventurer[] selectedAdventurers, int availableIndex, int selectedSlot) {
        Adventurer adventurer = gameEnvironment.getDefaultAdventurers().get(availableIndex);
        boolean alreadySelected = Arrays.stream(selectedAdventurers).anyMatch(a -> a == adventurer);

        if (alreadySelected) return false;

        selectedAdventurers[selectedSlot] = adventurer;
        return true;
    }

    /**
     * Gets a default adventurer.
     * @param index adventurer index
     * @return adventurer at index
     */
    public Adventurer getDefaultAdventurer(int index) {
        return gameEnvironment.getDefaultAdventurers().get(index);
    }

    /**
     * Validates setup and starts the game.
     * @param selectedAdventurers chosen adventurers
     * @throws SetupValidationException if setup is invalid
     */
    public void completeSetup(Adventurer[] selectedAdventurers) throws SetupValidationException {
        StringBuilder errors = new StringBuilder();

        String guildName = gameEnvironment.getGuildName();

        if (guildName == null || guildName.isBlank()) {
            errors.append("- Guild name is required.\n");
        } else if (guildName.length() < 3 || guildName.length() > 15) {
            errors.append("- Guild name must be between 3 and 15 characters.\n");
        } else if (!guildName.matches("[a-zA-Z0-9 ]+")) {
            errors.append("- Guild name must not include special characters.\n");
        }

        if (gameEnvironment.getDifficulty() == null) {
            errors.append("- A difficulty must be selected.\n");
        }

        if (gameEnvironment.getNumExpeditions() <= 0) {
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

        gameEnvironment.onSetupComplete(selectedAdventurers);
    }
}