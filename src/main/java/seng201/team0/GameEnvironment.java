package seng201.team0;

import seng201.team0.exception.InsufficientGoldException;
import seng201.team0.gui.ScreenNavigator;
import seng201.team0.models.*;
import seng201.team0.services.ExpeditionService;
import seng201.team0.services.GuildHallService;
import seng201.team0.services.MarketService;
import seng201.team0.services.RecruitmentService;

public class GameEnvironment {
    private int numExpeditions;
    private Difficulty difficulty;
    private String guildName;
    private int gold;
    private int currentExpedition = 1;
    private int score = 0;
    private boolean won = false;

    private final Guildhall guildhall = new Guildhall();
    private final GuildHallService guildHallService = new GuildHallService(guildhall);
    private final Market market = new Market();
    private final MarketService marketService;
    private final RecruitmentService recruitmentService;
    private final ExpeditionService expeditionService = new ExpeditionService(this);

    private final ScreenNavigator navigator;


    /**
     * Constructs the game environment and launches the setup screen immediately
     * @param navigator the screen navigator used to switch between screens
     */
    public GameEnvironment(ScreenNavigator navigator) {
        this.navigator = navigator;
        this.marketService = new MarketService(this);
        this.recruitmentService = new RecruitmentService(this);
        navigator.launchSetupScreen(this);
    }

    /**
     * Called by the SetupScreen once the player has gone through the
     * necessary motions to begin the game.
     * Initializes the starting gold, part, stock, recruitment pool,
     * and expeditions, then navigates to the menu.
     * @param setupModel the validated setup model
     */
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
    /** Navigates to the recruitment screen. */
    public void navigateToRecruitment() {
        if (navigator == null) return;
        navigator.launchRecruitmentScreenController(this);
    }
    public void navigateToMarket() {
        navigator.launchMarketScreenController(this);
    }
    public void navigateToExpedition() {
//        navigator.launchMenuScreenController(this);
    }
    public void navigateToGuildHall() {
        navigator.launchGuildHallScreenController(this);
    }
    public void navigateToExpeditionSelect() {
        navigator.launchExpeditionSelectScreenController(this);
    }

    public void incrementExpedition() { currentExpedition++; }
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


    /**
     * Increases the player's gold amount
     * @param amount the amount the gold will increase by
     *               must be a non-negative value
     */
    public void gainGold(int amount) {
        if (amount < 0) throw new IllegalArgumentException("amount must be >= 0");
        gold += amount;
    }

    /**
     * Decreases the player's gold amount
     * @param amount the amount the gold will decrease by
     *               must be a non-negative value
     *               must be not greater than the amount of gold the player has
     */
    public void spendGold(int amount) throws InsufficientGoldException {
        if (amount < 0) throw new IllegalArgumentException("amount must be >= 0");
        if (amount > gold) throw new InsufficientGoldException(amount, gold);
        gold -= amount;
    }
    public ExpeditionService getExpeditionService() { return expeditionService; }

    /** @return the recruitment service */
    public RecruitmentService getRecruitmentService() { return recruitmentService; }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isWon() {
        return won;
    }

    public int getExpeditionsCompleted() {
        return currentExpedition;
    }
}