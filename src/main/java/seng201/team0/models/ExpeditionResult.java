package seng201.team0.models;

import java.util.List;

/**
 * snapshot of what happened during a completed expedition.
 */
public class ExpeditionResult {

    private final String expeditionName;
    private final int eventsCompleted;
    private final int goldEarned;
    private final int payDeducted;
    private final List<Item> itemsFound;
    private final List<String> randomEventMessages;
    private final boolean wasRetreated;

    /**
     * Constructs an ExpeditionResult.
     * @param expeditionName     name of the expedition that was run
     * @param eventsCompleted    number of events resolved before the expedition ended
     * @param goldEarned         gross gold collected during the expedition
     * @param payDeducted        total adventurer pay deducted (after modifier)
     * @param itemsFound         items added to the guild inventory
     * @param randomEventMessages human-readable messages describing any random events
     * @param wasRetreated       true if the player retreated early
     */
    public ExpeditionResult(String expeditionName, int eventsCompleted,
                            int goldEarned, int payDeducted,
                            List<Item> itemsFound,
                            List<String> randomEventMessages,
                            boolean wasRetreated) {
        this.expeditionName = expeditionName;
        this.eventsCompleted = eventsCompleted;
        this.goldEarned = goldEarned;
        this.payDeducted = payDeducted;
        this.itemsFound = List.copyOf(itemsFound);
        this.randomEventMessages = List.copyOf(randomEventMessages);
        this.wasRetreated = wasRetreated;
    }

    /** @return expedition name */
    public String getExpeditionName() { return expeditionName; }

    /** @return number of events completed */
    public int getEventsCompleted() { return eventsCompleted; }

    /** @return gross gold earned during the expedition */
    public int getGoldEarned() { return goldEarned; }

    /** @return total pay deducted from the player */
    public int getPayDeducted() { return payDeducted; }

    /** @return net gold (earned minus pay) */
    public int getNetGold() { return goldEarned - payDeducted; }

    /** @return items found and added to storage */
    public List<Item> getItemsFound() { return itemsFound; }

    /** @return messages describing random events that occurred after the expedition */
    public List<String> getRandomEventMessages() { return randomEventMessages; }

    /** @return true if the player retreated before all events completed */
    public boolean wasRetreated() { return wasRetreated; }
}