package seng201.team0;

import seng201.team0.factories.AdventurerFactory;
import seng201.team0.gui.ScreenNavigator;
import seng201.team0.models.*;
import seng201.team0.services.GuildHallService;
import seng201.team0.services.MarketService;

import java.util.ArrayList;
import java.util.List;

public class GameEnvironment {
    private final List<Adventurer> defaultAdventurers = new ArrayList<>();
    private int numExpeditions;
    private Difficulty difficulty;
    private String guildName;
    private final ScreenNavigator navigator;
    private int gold;
    private int currentExpedition = 1;
    private int expeditionsRemaining;
    private MarketService marketService;

    private final Guildhall guildhall = new Guildhall();
    private final GuildHallService guildHallService = new GuildHallService(guildhall);


    public GameEnvironment(ScreenNavigator navigator) {
        this.navigator = navigator;
        defaultAdventurers.addAll(AdventurerFactory.getRandom(5));
        navigator.launchSetupScreen(this);

    }

    public void onSetupComplete(Adventurer[] selectedAdventurers) {
        gold = difficulty.getStartingGold();
        guildHallService.addStartingParty(selectedAdventurers);
        navigateToMenu();
    }
    public void navigateToMenu() {
        navigator.launchMenuScreenController(this);
    }
    public void navigateToRecruitment() {
//        navigator.launchMenuScreenController(this);
    }
    public void navigateToMarket() {
//        navigator.launchMenuScreenController(this);
    }
    public void navigateToExpedition() {
//        navigator.launchMenuScreenController(this);
    }
    public void navigateToGuildHall() {
        navigator.launchGuildHallScreenController(this);
    }

    public List<Adventurer> getDefaultAdventurers() {return defaultAdventurers;}
    public int getNumExpeditions() {return numExpeditions;}
    public Difficulty getDifficulty() {return difficulty;}
    public void setDifficulty(Difficulty difficulty) {this.difficulty = difficulty;}
    public String getGuildName() {return guildName;}
    public void setGuildName(String name) {this.guildName = name;}
    public void setNumExpeditions(int num) {this.numExpeditions = num;}
    public int getGold() {return gold;}
    public int getCurrentExpedition() {return currentExpedition;}
    public int getRemainingExpeditions() {return numExpeditions-currentExpedition;}
    private final Market market = new Market();
    public GuildHallService getGuildHallService() { return guildHallService; }
    public Guildhall getGuildhall() { return guildhall; }


    /** @return the market */
    public Market getMarket() { return market; }
    public MarketService getMarketService() { return marketService; }

}
