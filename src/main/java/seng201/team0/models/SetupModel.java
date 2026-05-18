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

    public Difficulty getDifficulty() { return difficulty; }
    public void setDifficulty(Difficulty difficulty) { this.difficulty = difficulty; }

    public int getExpeditionCount() { return expeditionCount; }
    public void setExpeditionCount(int expeditionCount) { this.expeditionCount = expeditionCount; }

    public List<Adventurer> getDefaultAdventurers() { return defaultAdventurers; }
    public Adventurer getDefaultAdventurer(int index) { return defaultAdventurers.get(index); }

    public Adventurer[] getSelectedAdventurers() { return selectedAdventurers; }
    public void setSelectedAdventurer(int slot, Adventurer adventurer) {
        selectedAdventurers[slot] = adventurer;
    }
}