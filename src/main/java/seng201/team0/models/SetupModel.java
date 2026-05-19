package seng201.team0.models;

import seng201.team0.factories.AdventurerFactory;
import java.util.List;

public class SetupModel {
    private String guildName;
    private Difficulty difficulty;
    private int expeditionCount;
    private final List<Adventurer> defaultAdventurers;
    private final Adventurer[] selectedAdventurers = new Adventurer[3];

    public SetupModel() {
        this.defaultAdventurers = AdventurerFactory.getRandom(5);
    }

    public String getGuildName() { return guildName; }
    public void setGuildName(String guildName) { this.guildName = guildName; }

    /**
     * @return the difficulty chosen by the player
     */
    public Difficulty getDifficulty() { return difficulty; }
    /**
     * Sets the difficulty chosen by the player.
     * @param difficulty the difficulty
     */
    public void setDifficulty(Difficulty difficulty) { this.difficulty = difficulty; }

    /**
     * @return the number of expeditions the game will run for
     */
    public int getExpeditionCount() { return expeditionCount; }
    /**
     * Sets the number of expeditions the game will run for.
     * @param expeditionCount the expedition count
     */
    public void setExpeditionCount(int expeditionCount) { this.expeditionCount = expeditionCount; }

    /**
     * Returns the pool of randomly generated adventurers the player can
     * choose their starting party from. The pool is created once at
     * construction and does not change.
     * @return the default adventurer pool
     */
    public List<Adventurer> getDefaultAdventurers() { return defaultAdventurers; }
    /**
     * Returns the default adventurer at the given index in the pool.
     * @param index the index into the default adventurer pool
     * @return the adventurer at that index
     */
    public Adventurer getDefaultAdventurer(int index) { return defaultAdventurers.get(index); }

    /**
     * Returns the player's three starting-party slots. Unfilled slots
     * contain null.
     * @return the selected adventurers array (length 3)
     */
    public Adventurer[] getSelectedAdventurers() { return selectedAdventurers; }
    public void setSelectedAdventurer(int slot, Adventurer adventurer) {
        selectedAdventurers[slot] = adventurer;
    }
}