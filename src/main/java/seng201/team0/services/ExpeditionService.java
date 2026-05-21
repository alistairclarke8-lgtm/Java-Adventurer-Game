package seng201.team0.services;

import seng201.team0.GameEnvironment;
import seng201.team0.exception.ExpeditionOverException;
import seng201.team0.factories.ExpeditionPool;
import seng201.team0.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Drives all expedition logic. Event resolution, gold tracking, pay deduction, and party stamina.
 */
public class ExpeditionService {

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

    /**
     * @param gameEnvironment the game environment
     * @param random the random instance to use
     */
    public ExpeditionService(GameEnvironment gameEnvironment, Random random) {
        this.gameEnvironment = gameEnvironment;
        this.random = random;
    }

    /** Uses a default {@link Random}. */
    public ExpeditionService(GameEnvironment gameEnvironment) {
        this(gameEnvironment, new Random());
    }

    /** Picks 3 random expeditions for this round. */
    public void refreshExpeditions() {
        availableExpeditions = ExpeditionPool.getRandom(3);
    }

    /** @return the expeditions available this round */
    public List<Expedition> getAvailableExpeditions() {
        return availableExpeditions;
    }

    /** Resets all expedition state and begins the given expedition. */
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
     * Completes the player's choice for the current event.
     * Applies gold and stamina changes, advances the event index,
     * and finalises the expedition if it ends.
     * @param choiceIndex index of the chosen option
     * @throws ExpeditionOverException if the expedition has already ended
     */
    public void completeChoice(int choiceIndex) throws ExpeditionOverException {
        if (expeditionOver) throw new ExpeditionOverException();

        Event event = getCurrentEvent();
        EventOption option = event.options().get(choiceIndex);
        EventOutcome outcome = option.rollOutcome(random);
        log.add("-" + outcome.getDescription());

        if (outcome.getGoldChange() > 0) {
            goldEarned += outcome.getGoldChange();
            log.add("  (+" + outcome.getGoldChange() + " gold)");
        }
        else if (outcome.getGoldChange() < 0) {
            goldEarned = Math.max(0,goldEarned+ outcome.getGoldChange());
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

    /** Calculates pay, applies net gold change, and logs the results. */
    private void finaliseExpedition() {
        double payModifier = currentExpedition.getPayModifier();
        for (Adventurer adventurer : gameEnvironment.getGuildhall().getMainPartyAdventurers()) {
            payDeducted += (int)(adventurer.getPay() * payModifier);
        }

        netGain = goldEarned - payDeducted;
        gameEnvironment.adjustGold(netGain);

        log.add("Gold earned: " + goldEarned + "g");
        log.add("Pay deducted: " + payDeducted + "g");
        log.add("Net gain: " + netGain + "g");
    }

    /** Ends the expedition early and finalises. */
    public void retreat() {
        retreated = true;
        expeditionOver = true;
        finaliseExpedition();
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

    public int getGoldEarned() { return goldEarned; }
    public int getPayDeducted() { return payDeducted; }
    public int getNetGain() { return netGain; }
    public int getEventsCompleted() { return eventsCompleted; }
    public List<String> getLog() { return log; }
    public List<Item> getItemsFound() { return itemsFound; }
    public boolean isExpeditionOver() { return expeditionOver; }
    public boolean wasRetreated() { return retreated; }
}