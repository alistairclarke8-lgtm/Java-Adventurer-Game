package seng201.team0.services;

import seng201.team0.GameEnvironment;
import seng201.team0.exception.InsufficientGoldException;
import seng201.team0.models.Item;

/**
 * Service class for market logic
 * Buying items, selling items back and refreshing the per round stock
 */
public class MarketService {

    /** The minimum number of items shown per round. */
    public static final int MIN_STOCK = 3;

    /** The maximum number of items shown per round. */
    public static final int MAX_STOCK = 6;

    /**
     * The shared game environment, used to access and mutate gold, market
     * stock, player inventory, and difficulty settings.
     */
    private final GameEnvironment gameEnvironment;

    /**
     * Constructs a {@code MarketService} bound to the given game environment.
     *
     * @param gameEnvironment the active GameEnvironment, must not be null
     */
    public MarketService(GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
    }

    public void buy(Item item) throws InsufficientGoldException {
        if (!gameEnvironment.getMarket().getAvailableItems().contains(item)) {
            throw new IllegalArgumentException("Item is not in the market.");
        }
        int price = getBuyPrice(item);
        gameEnvironment.spendGold(price);
        gameEnvironment.getMarket().removeItem(item);
        gameEnvironment.getGuildhall().getInventoryItems().add(item);
    }

    /**
     * Sells an item back into the market from the player's inventory
     * @param item the item being sold
     */
    public void sell(Item item) {
        if (!gameEnvironment.getGuildhall().getInventoryItems().contains(item)) {
            throw new IllegalArgumentException("Item is not in inventory.");
        }
        int price = getSellPrice(item);
        gameEnvironment.getGuildhall().getInventoryItems().remove(item);
        gameEnvironment.gainGold(price);
    }

    public int getBuyPrice(Item item) {
        double multiplier = gameEnvironment.getDifficulty().getCostMultiplier();
        return (int) (item.getCost() * multiplier);
    }

    public int getSellPrice(Item item) {
        double multiplier = gameEnvironment.getDifficulty().getCostMultiplier();
        return (int) item.getSellPrice(multiplier);
    }
}
