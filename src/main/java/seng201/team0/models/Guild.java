package seng201.team0.models;

import java.util.ArrayList;

public class Guild {
    ArrayList<Adventurer> selectedAdventurers = new ArrayList<>();
    ArrayList<Adventurer> reservedAdventurers = new ArrayList<>();

    ArrayList<Item> selectedItems = new ArrayList<>();
    ArrayList<Item> inventoryItems = new ArrayList<>();

    int playersGoldAmount;
    int currentExpedition;


    public Guild(int playersGoldAmount, ArrayList<Adventurer> allAdventurers, ArrayList<Item> allItems) {
        this.playersGoldAmount = playersGoldAmount;
    }

    public void buyItem(Item item) {
        if (playersGoldAmount - item.getCost() >= 0) {
            playersGoldAmount = -item.getCost();
            inventoryItems.add(item);
        } else {
            throw new IllegalArgumentException("Insufficient Gold");
        }
    }
}
