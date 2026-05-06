package seng201.team0;

import seng201.team0.gui.ScreenNavigator;
import seng201.team0.models.Adventurer;
import seng201.team0.models.Archer;
import seng201.team0.models.Swordsman;
import seng201.team0.models.Wizard;

import java.util.ArrayList;
import java.util.List;

public class GameEnvironment {
    private final List<Adventurer> defaultAdventurers = new ArrayList<>();
    private List<Adventurer> selectedAdventurers = new ArrayList<>();
    private int numExpeditions;
    private String difficulty;
    private String guildName;
    private final ScreenNavigator navigator;





    public GameEnvironment(ScreenNavigator navigator) {
        this.navigator = navigator;
        defaultAdventurers.addAll(List.of(new Archer(), new Swordsman(), new Wizard(), new Wizard(), new Swordsman()));
        navigator.launchSetupScreen(this);

    }

    public void onSetupComplete(List<Adventurer> selectedAdventurers, int numExpeditions,
                                String difficulty, String guildName) {
        this.selectedAdventurers = selectedAdventurers;
        this.numExpeditions = numExpeditions;
        this.difficulty = difficulty;
        this.guildName = guildName;
//        navigator.launchMainScreen.this;


    }

    public List<Adventurer> getDefualtAdventurers() {return defaultAdventurers;}
    public List<Adventurer> getSelectedAdventurers() {return selectedAdventurers;}
    public int getNumExpeditions() {return numExpeditions;}
    public String getDifficulty() {return difficulty;}
    public String getGuildName() {return guildName;}
    public void setGuildName(String name) {this.guildName = name;}





    }
