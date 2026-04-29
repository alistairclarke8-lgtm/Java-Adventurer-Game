package seng201.team0.models;

import java.util.ArrayList;

/**
 * represents the market where players can buy and sell items.
 */
public class Market {
    private final ArrayList<Item> availableItems = new ArrayList<>();

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
     * @param item to remove from available items.
     */
    public void removeItem(Item item) {
        availableItems.remove(item);
    }

    public ArrayList<Item> getAvailableItems() {
        return availableItems;
    }
}

