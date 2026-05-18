package seng201.team0.services;

import seng201.team0.GameEnvironment;
import seng201.team0.exception.InsufficientGoldException;
import seng201.team0.models.Item;

/**
 * Service class for market logic
 * Buying items, selling items back and refreshing the per round stock
 *
 */
public class MarketService {

    private final GameEnvironment gameEnvironment;

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
