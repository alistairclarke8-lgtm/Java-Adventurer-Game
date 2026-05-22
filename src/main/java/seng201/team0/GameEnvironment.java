package seng201.team0;

import seng201.team0.exception.InsufficientGoldException;
import seng201.team0.gui.ScreenNavigator;
import seng201.team0.models.*;
import seng201.team0.services.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Central state holder for an in-progress game. Owns the guildhall,
 * market, current expedition, gold, score, and difficulty, and brokers
 * navigation between screens via the supplied {@link ScreenNavigator}.
 * Services delegate state mutations back to this class so the game state
 * remains consistent across the codebase.
 */
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
    private final RandomEventService randomEventService;
    private Expedition selectedExpedition;


    private final ScreenNavigator navigator;


    /**
     * Constructs the game environment and launches the setup screen immediately
     * @param navigator the screen navigator used to switch between screens
     */
    public GameEnvironment(ScreenNavigator navigator) {
        this.navigator = navigator;
        this.marketService = new MarketService(this);
        this.recruitmentService = new RecruitmentService(this);
        this.randomEventService = new RandomEventService(this);
        if (navigator != null) navigator.launchSetupScreen(this);
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
        marketService.refreshStock();
        recruitmentService.refreshPool();
        navigateToMenu();
    }

    /** Navigates to the menu screen. */
    public void navigateToMenu() {
        navigator.launchMenuScreenController(this);
    }

    /** Navigates to the recruitment screen. */
    public void navigateToRecruitment() {
        if (navigator == null) return;
        navigator.launchRecruitmentScreenController(this);
    }

    /** Navigates to the market screen. */
    public void navigateToMarket() {
        navigator.launchMarketScreenController(this);
    }

    /** Navigates to the in-progress expedition screen. */
    public void navigateToExpedition() {navigator.launchExpeditionInProgressScreen(this);}

    /** Navigates to the guild hall screen. */
    public void navigateToGuildHall() {
        navigator.launchGuildHallScreenController(this);
    }

    /** Navigates to the expedition selection screen. */
    public void navigateToExpeditionSelect() {
        navigator.launchExpeditionSelectScreenController(this);
    }

    /**
     * Wraps up an expedition: updates each adventurer's consecutive
     * expeditions counter, rolls any post-round random events,
     * refreshes the market stock and recruitment pool, then navigates
     * to the post-expedition summary screen.
     */
    public void navigateToPostExpedition() {
        updateExpeditionStreaks();
        List<String> messages = randomEventService.runRandomEvents();
        expeditionService.setRandomEventMessages(messages);
        marketService.refreshStock();
        recruitmentService.refreshPool();
        navigator.launchPostExpeditionScreen(this);
    }

    /**
     * Increments the consecutive expedition counter for every main-party
     * adventurer, and resets the counter for every reserve adventurer.
     */
    private void updateExpeditionStreaks() {
        for (Adventurer adventurer : new ArrayList<>(guildhall.getMainPartyAdventurers())) {
            adventurer.incrementExpeditionsInARow();
        }
        for (Adventurer adventurer : new ArrayList<>(guildhall.getReservedAdventurers())) {
            adventurer.resetExpeditionsInARow();
        }
    }

    /**
     * Called by the post-expedition screen's Continue button. Advances the
     * expedition counter and either ends the game (win or loss) or returns
     * the player to the menu for the next round.
     */
    public void continueAfterExpedition() {
        incrementExpedition();
        if (currentExpedition > numExpeditions) {
            won = true;
            endGame();
            return;
        }
        if (isPlayerLost()) {
            won = false;
            endGame();
            return;
        }
        navigateToMenu();
    }

    /** Routes to the end-game summary screen. */
    public void endGame() {
        navigator.launchEndGameScreen(this);
    }

    /**
     * The player loses when they have no adventurers in the guild, and they
     * cannot afford to hire the cheapest available recruit.
     * @return true if the loss condition is met
     */
    public boolean isPlayerLost() {
        boolean noAdventurers = guildHallService.getTotalAdventurers() == 0;
        if (!noAdventurers) return false;
        int cheapest = recruitmentService.getCheapestHireCost();
        return cheapest < 0 || gold < cheapest;
    }

    /** Increments the current expedition counter by one. */
    public void incrementExpedition() { currentExpedition++; }

    /** @return number of expeditions the player chose at setup */
    public int getNumExpeditions() {return numExpeditions;}

    /** @return current difficulty */
    public Difficulty getDifficulty() {return difficulty;}

    /** @return the player's guild name */
    public String getGuildName() {return guildName;}

    /** @return the player's current gold */
    public int getGold() {return gold;}

    /** @return the 1-indexed current expedition number */
    public int getCurrentExpedition() {return currentExpedition;}

    /** @return remaining expeditions including the current one */
    public int getRemainingExpeditions() {return numExpeditions-currentExpedition;}

    /** @return the guildhall service */
    public GuildHallService getGuildHallService() { return guildHallService; }

    /** @return the guildhall */
    public Guildhall getGuildhall() { return guildhall; }

    /**
     * Stores the expedition the player picked at the selection screen.
     * @param expedition the chosen expedition
     */
    public void setSelectedExpedition(Expedition expedition) { this.selectedExpedition = expedition; }

    /** @return the expedition the player picked at the selection screen */
    public Expedition getSelectedExpedition() { return selectedExpedition; }

    /** @return the market */
    public Market getMarket() { return market; }

    /** @return the market service */
    public MarketService getMarketService() { return marketService; }



    /**
     * Used to adjust gold when gold should not decrease below 0.
     * @param amount of gold.
     */
    public void adjustGold(int amount) {
        gold = Math.max(0, gold + amount);
    }

    /**
     * Increases the player's gold amount.
     * @param amount the amount the gold will increase by, must be {@code >= 0}
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
        gold = Math.max(0, gold - amount);
    }

    /** @return the expedition service */
    public ExpeditionService getExpeditionService() { return expeditionService; }

    /** @return the recruitment service */
    public RecruitmentService getRecruitmentService() { return recruitmentService; }

    /** @return the random-event service */
    public RandomEventService getRandomEventService() { return randomEventService; }

    /** @return the player's current score */
    public int getScore() {
        return score;
    }

    /**
     * Overwrites the player's score. Called by {@link ExpeditionService}
     * to accrue points after each expedition.
     * @param score the new score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /** @return true if the player completed all expeditions */
    public boolean isWon() {
        return won;
    }

    /** @return number of expeditions the player has completed */
    public int getExpeditionsCompleted() {
        return Math.min(currentExpedition - 1, numExpeditions);
    }
}