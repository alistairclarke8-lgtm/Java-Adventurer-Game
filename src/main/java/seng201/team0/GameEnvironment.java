package seng201.team0;

import seng201.team0.gui.ScreenNavigator;
import seng201.team0.models.*;
import seng201.team0.services.GuildHallService;
import seng201.team0.services.MarketService;

public class GameEnvironment {
    private int numExpeditions;
    private Difficulty difficulty;
    private String guildName;
    private final ScreenNavigator navigator;
    private int gold;
    private final int currentExpedition = 1;
    private MarketService marketService;

    private final Guildhall guildhall = new Guildhall();
    private final GuildHallService guildHallService = new GuildHallService(guildhall);
    private final Market market = new Market();

    public GameEnvironment(ScreenNavigator navigator) {
        this.navigator = navigator;
        navigator.launchSetupScreen(this);
    }

    public void onSetupComplete(SetupModel setupModel) {
        this.guildName = setupModel.getGuildName();
        this.numExpeditions = setupModel.getExpeditionCount();
        this.difficulty = setupModel.getDifficulty();
        this.gold = difficulty.getStartingGold();
        guildHallService.addStartingParty(setupModel.getSelectedAdventurers());
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

    public int getNumExpeditions() {return numExpeditions;}
    public Difficulty getDifficulty() {return difficulty;}
    public String getGuildName() {return guildName;}
    public int getGold() {return gold;}
    public int getCurrentExpedition() {return currentExpedition;}
    public int getRemainingExpeditions() {return numExpeditions-currentExpedition;}
    public GuildHallService getGuildHallService() { return guildHallService; }
    public Guildhall getGuildhall() { return guildhall; }

    /** @return the market */
    public Market getMarket() { return market; }
    public MarketService getMarketService() { return marketService; }
}