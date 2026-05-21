package seng201.team0.services;

import seng201.team0.GameEnvironment;
import seng201.team0.exception.InsufficientGoldException;
import seng201.team0.factories.ItemFactory;
import seng201.team0.models.Item;
import seng201.team0.models.Market;

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
     * Constructs a MarketService bound to the given game environment.
     * @param gameEnvironment the active GameEnvironment, must not be null
     */
    public MarketService(GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
    }

    /**
     * Purchases an item from the market and adds it to the player's inventory.
     *
     * The buy price is calculated via getBuyPrice(Item), which applies
     * the current difficulty cost multiplier. The item is then removed from the
     * market's available stock and placed into the guildhall inventory.
     *
     * @param item the item in question being purchased
     * @throws InsufficientGoldException if the player does not have enough gold to purchase the item
     * @throws IllegalArgumentException if the requested item is not available in the market
     */
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
        gameEnvironment.adjustGold(price);
    }

    /**
     * Returns the price the player must pay to buy the given item.
     * Calculated as item.getCost() * difficultyMultiplier, truncated
     * to an integer. A higher difficulty multiplier makes items more expensive.
     *
     * @param item the item whose buy price is being calculated
     * @return the gold cost to purchase the item under the current difficulty
     */
    public int getBuyPrice(Item item) {
        double multiplier = gameEnvironment.getDifficulty().getCostMultiplier();
        return (int) (item.getCost() * multiplier);
    }

    /**
     * Returns the gold the player receives when selling the given item.
     * Delegates to getSellPrice(double), passing in the current
     * difficulty cost multiplier. The exact sell-price formula is defined on the
     * item itself (typically a fraction of its base cost).
     *
     * @param item the item whose sell price is being calculated
     * @return the gold awarded for selling item under the current difficulty
     */
    public int getSellPrice(Item item) {
        double multiplier = gameEnvironment.getDifficulty().getCostMultiplier();
        return (int) item.getSellPrice(multiplier);
    }

    /**
     * Refreshed the available items in the market to purchase
     * It does this by calling the getRandom(5) method where it is
     * looking for 5 random items from the ItemFactory and replace
     * the current items with these new ones
     */
    public void refreshStock() {
        gameEnvironment.getMarket().setItems(ItemFactory.getRandom(5));
    }
}
