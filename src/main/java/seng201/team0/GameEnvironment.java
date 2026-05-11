package seng201.team0;

import seng201.team0.gui.ScreenNavigator;
import seng201.team0.models.*;

import java.util.ArrayList;
import java.util.List;

public class GameEnvironment {
    private final List<Adventurer> defaultAdventurers = new ArrayList<>();
    private List<Adventurer> selectedAdventurers = new ArrayList<>();
    private int numExpeditions;
    private Difficulty difficulty;
    private String guildName;
    private final ScreenNavigator navigator;





    public GameEnvironment(ScreenNavigator navigator) {
        this.navigator = navigator;
        defaultAdventurers.addAll(List.of(new Archer(), new Swordsman(), new Wizard(), new Wizard(), new Swordsman()));
        navigator.launchSetupScreen(this);

    }

    public void onSetupComplete() {
        navigator.launchMenuScreenController(this);


    }

    public List<Adventurer> getDefaultAdventurers() {return defaultAdventurers;}
    public List<Adventurer> getSelectedAdventurers() {return selectedAdventurers;}
    public void setSelectedAdventurers(Adventurer adventurer) {this.selectedAdventurers.add(adventurer);}
    public int getNumExpeditions() {return numExpeditions;}
    public Difficulty getDifficulty() {return difficulty;}
    public void setDifficulty(Difficulty difficulty) {this.difficulty = difficulty;}
    public String getGuildName() {return guildName;}
    public void setGuildName(String name) {this.guildName = name;}
    public int setNumExpeditions(int num) {return this.numExpeditions = num;}





    }
