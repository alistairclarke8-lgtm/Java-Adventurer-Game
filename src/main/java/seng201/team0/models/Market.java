package seng201.team0.models;

import java.util.ArrayList;

/**
 * represents the market where players can buy and sell items.
 */
public class Market {
    private final ArrayList<Item> availableItems = new ArrayList<>();

    /**
     * Construct an empty market
     */
    public Market(){}

    /**
     * adds an item to the shop.
     * @param item to add to available items.
     */
    public void addItem(Item item) {
        availableItems.add(item);
    }


    /**
     * removes item from shop.
     * @param item to remove from available items
     * @return True is the item was removed
     */
    public boolean removeItem(Item item) {
        return availableItems.remove(item);
    }


    /** @return an unmodifiable view of the items currently for sale */
    public ArrayList<Item> getAvailableItems() {
        return availableItems;
    }

    /**
     * Clears all available items
     */
    public void clear() {
        availableItems.clear();
    }
}

