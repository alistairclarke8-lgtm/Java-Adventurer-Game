package seng201.team0.services;

import seng201.team0.GameEnvironment;
import seng201.team0.exception.ExpeditionOverException;
import seng201.team0.factories.ExpeditionPool;
import seng201.team0.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public ExpeditionService(GameEnvironment gameEnvironment, Random random) {
        this.gameEnvironment = gameEnvironment;
        this.random = random;
    }

    public ExpeditionService(GameEnvironment gameEnvironment) {
        this(gameEnvironment, new Random());
    }

    public void refreshExpeditions() {
        availableExpeditions = ExpeditionPool.getRandom(3);
    }

    public List<Expedition> getAvailableExpeditions() {
        return availableExpeditions;
    }

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

    public Event getCurrentEvent() {
        if (expeditionOver) return null;
        List<Event> events = currentExpedition.getEvents();
        if (currentEventIndex >= events.size()) return null;
        return events.get(currentEventIndex);
    }

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
    private void finaliseExpedition() {
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

        log.add("Gold earned: " + goldEarned + "g");
        log.add("Pay deducted: " + payDeducted + "g");
        log.add("Net gain: " + netGain + "g");
    }

    private void applyStaminaToParty(int delta) {
        for (Adventurer adventurer : gameEnvironment.getGuildhall().getMainPartyAdventurers()) {
            adventurer.adjustStamina(delta);
        }
    }

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
    public void retreat() {
        retreated = true;
        expeditionOver = false;
        finaliseExpedition();
    }
    public boolean wasRetreated() {
        return retreated;
    }
    }
