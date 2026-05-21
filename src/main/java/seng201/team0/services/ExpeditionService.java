package seng201.team0.services;

import seng201.team0.GameEnvironment;
import seng201.team0.exception.ExpeditionOverException;
import seng201.team0.exception.InsufficientGoldException;
import seng201.team0.factories.ExpeditionPool;
import seng201.team0.factories.ItemFactory;
import seng201.team0.models.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Drives all expedition logic. Event resolution, gold tracking, pay deduction, and party stamina.
 */
public class ExpeditionService {

    /** Base probability a single event yields an item. Modulated by perception. */
    private static final double baseItemDropChance = 0.15;

    /** Perception above this value contributes diminishing returns to drop chance. */
    private static final int perceptionReference = 100;

    private final GameEnvironment gameEnvironment;
    private final Random random;

    private List<Expedition> availableExpeditions = new ArrayList<>();

    private Expedition currentExpedition;
    private int currentEventIndex = 0;
    private int goldEarned = 0;
    private int payDeducted = 0;
    private int netGain = 0;
    private int eventsCompleted = 0;
    private boolean expeditionOver = false;
    private boolean retreated = false;
    private final List<String> log = new ArrayList<>();
    private final List<Item> itemsFound = new ArrayList<>();
    private List<String> randomEventMessages = new ArrayList<>();

    /**
     * Constructs an ExpeditionService with an explicit Random instance.
     * Used in tests to obtain deterministic outcomes.
     * @param gameEnvironment the active game environment
     * @param random the random source used by this service
     */
    public ExpeditionService(GameEnvironment gameEnvironment, Random random) {
        this.gameEnvironment = gameEnvironment;
        this.random = random;
    }
    /**
     * Constructs an ExpeditionService with a default {@link Random} source.
     * @param gameEnvironment the active game environment
     */
    public ExpeditionService(GameEnvironment gameEnvironment) {
        this(gameEnvironment, new Random());
    }

    /**
     * Repopulates the list of three expeditions offered to the player at the
     * start of each round by drawing them at random from the pool.
     */
    public void refreshExpeditions() {
        availableExpeditions = ExpeditionPool.getRandom(3);
    }

    /** @return the three expeditions currently offered to the player */
    public List<Expedition> getAvailableExpeditions() {
        return availableExpeditions;
    }

    /**
     * Resets all per-expedition state and queues the supplied expedition for
     * play. Called from the expedition-select screen when the player departs.
     * @param expedition the expedition the player has chosen
     */
    public void startExpedition(Expedition expedition) {
        this.currentExpedition = expedition;
        this.currentEventIndex = 0;
        this.goldEarned = 0;
        this.payDeducted = 0;
        this.netGain = 0;
        this.eventsCompleted = 0;
        this.expeditionOver = false;
        this.retreated = false;
        this.log.clear();
        this.itemsFound.clear();
        this.randomEventMessages = new ArrayList<>();
        log.add("Your party sets out on the " + expedition.getName() + ".");
    }

    /** @return the current event, or null if the expedition is over */
    public Event getCurrentEvent() {
        if (expeditionOver) return null;
        List<Event> events = currentExpedition.getEvents();
        if (currentEventIndex >= events.size()) return null;
        return events.get(currentEventIndex);
    }

    /**
     * Resolves the player's choice for the current event. The outcome is
     * rolled from the option's weighted outcomes, gold and stamina deltas
     * are applied (gold is scaled by the current difficulty's reward
     * multiplier), a perception-weighted item drop is rolled, and the
     * expedition is advanced (or finalized if it ended).
     *
     * @param choiceIndex index of the option the player picked
     * @throws ExpeditionOverException if the expedition has already ended
     */
    public void completeChoice(int choiceIndex) throws ExpeditionOverException, InsufficientGoldException {
        if (expeditionOver) throw new ExpeditionOverException();

        Event event = getCurrentEvent();
        EventOption option = event.options().get(choiceIndex);
        EventOutcome outcome = option.rollOutcome(random);
        log.add("-" + outcome.getDescription());

        double rewardMultiplier = gameEnvironment.getDifficulty().getRewardMultiplier();

        if (outcome.getGoldChange() > 0) {
            int scaled = (int) (outcome.getGoldChange() * rewardMultiplier);
            goldEarned += scaled;
            log.add("  (+" + scaled + " gold)");
        }
        else if (outcome.getGoldChange() < 0) {
            goldEarned = Math.max(0, goldEarned + outcome.getGoldChange());
            log.add("  (" + outcome.getGoldChange() + " gold)");
        }
        if (outcome.getStaminaChange() != 0) {
            applyStaminaToParty(outcome.getStaminaChange());
            if (outcome.getStaminaChange() > 0) {
                log.add("  (Party stamina +" + outcome.getStaminaChange() + ")");
            } else {
                log.add("  (Party stamina " + outcome.getStaminaChange() + ")");
            }
        }

        rollItemDrop();

        eventsCompleted++;
        currentEventIndex++;

        if (outcome.endsExpedition()) {
            log.add("The expedition has come to an early end.");
            expeditionOver = true;
            finaliseExpedition();
            return;
        }

        if (isPartyExhausted()) {
            log.add("The party is exhausted. We have to head back to the guild.");
            expeditionOver = true;
            finaliseExpedition();
            return;
        }

        if (currentEventIndex >= currentExpedition.getEvents().size()) {
            log.add("The party has completed the " + currentExpedition.getName() + "!");
            expeditionOver = true;
            finaliseExpedition();
        }
    }

    /**
     * Rolls a perception-weighted chance that the party finds an item during
     * an event. The base chance is bumped by the average party perception
     * and scaled by the difficulty's reward multiplier. Found items are
     * added to the guild's inventory and to {@link #itemsFound} for the
     * post-expedition summary.
     */
    private void rollItemDrop() {
        List<Adventurer> party = gameEnvironment.getGuildhall().getMainPartyAdventurers();
        if (party.isEmpty()) return;

        int totalPerception = 0;
        for (Adventurer adventurer : party) {
            totalPerception += adventurer.getPerception();
        }
        double avgPerception = (double) totalPerception / party.size();
        double dropChance = baseItemDropChance
                + (avgPerception / perceptionReference) * baseItemDropChance;
        dropChance *= gameEnvironment.getDifficulty().getRewardMultiplier();
        if (dropChance > 0.6) dropChance = 0.6;

        if (random.nextDouble() < dropChance) {
            List<Item> candidates = ItemFactory.getRandom(1);
            if (!candidates.isEmpty()) {
                Item item = candidates.getFirst();
                itemsFound.add(item);
                gameEnvironment.getGuildhall().getInventoryItems().add(item);
                log.add("  (Found: " + item.getName() + ")");
            }
        }
    }

    /**
     * Settles the expedition: each main-party adventurer is paid (modulated
     * by the expedition's pay modifier), the net gold delta is banked to or
     * deducted from the player's treasury, and a difficulty-weighted score
     * proportional to gold earned is added to the running total.
     */
    private void finaliseExpedition() throws InsufficientGoldException {
        double payModifier = currentExpedition.getPayModifier();
        for (Adventurer adventurer : gameEnvironment.getGuildhall().getMainPartyAdventurers()) {
            payDeducted += (int) (adventurer.getPay() * payModifier);
        }

        netGain = goldEarned - payDeducted;

        if (netGain >= 0) {
            gameEnvironment.gainGold(netGain);
        } else {
            gameEnvironment.spendGold(Math.min(-netGain, gameEnvironment.getGold()));
        }

        int scoreDelta = (int) (goldEarned * gameEnvironment.getDifficulty().getScoreMultiplier());
        gameEnvironment.setScore(gameEnvironment.getScore() + scoreDelta);

        log.add("Gold earned: " + goldEarned + "g");
        log.add("Pay deducted: " + payDeducted + "g");
        log.add("Net gain: " + netGain + "g");
        log.add("Score gained: " + scoreDelta);
    }

    /** Applies a stamina change to every main party adventurer. */
    private void applyStaminaToParty(int delta) {
        for (Adventurer adventurer : gameEnvironment.getGuildhall().getMainPartyAdventurers()) {
            adventurer.adjustStamina(delta);
        }
    }

    /** @return true if every adventurer is at or below the expedition's stamina cutoff */
    private boolean isPartyExhausted() {
        int threshold = currentExpedition.getStaminaCutoffThreshold();
        for (Adventurer adventurer : gameEnvironment.getGuildhall().getMainPartyAdventurers()) {
            if (adventurer.getStamina() > threshold) return false;
        }
        return true;
    }

    /** @return gold earned during this expedition (before pay) */
    public int getGoldEarned() { return goldEarned; }
    /** @return total pay deducted from the player after this expedition */
    public int getPayDeducted() { return payDeducted; }
    /** @return net gold (earned minus pay) */
    public int getNetGain() { return netGain; }
    /** @return number of events resolved before the expedition ended */
    public int getEventsCompleted() { return eventsCompleted; }
    /** @return ordered log lines from the current expedition */
    public List<String> getLog() { return log; }
    /** @return items found during this expedition */
    public List<Item> getItemsFound() { return itemsFound; }
    /** @return true if the current expedition has ended */
    public boolean isExpeditionOver() { return expeditionOver; }
    /** @return random-event messages set after this expedition concluded */
    public List<String> getRandomEventMessages() {
        return Collections.unmodifiableList(randomEventMessages);
    }
    /**
     * Records the random-event messages produced by {@link RandomEventService}
     * for the post-expedition screen to display.
     * @param messages messages describing any random events that occurred
     */
    public void setRandomEventMessages(List<String> messages) {
        this.randomEventMessages = new ArrayList<>(messages);
    }

    /** Marks the expedition as a retreat and finalises pay. */
    public void retreat() throws InsufficientGoldException {
        retreated = true;
        expeditionOver = true;
        finaliseExpedition();
    }

    /** @return true if the player retreated before all events completed */
    public boolean wasRetreated() { return retreated; }

    /** @return the expedition currently being run, or null if none */
    public Expedition getCurrentExpedition() {
        return currentExpedition;
    }
}