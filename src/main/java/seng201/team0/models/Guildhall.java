package seng201.team0.models;

import seng201.team0.exception.GuildFullException;

import java.util.ArrayList;

/**
 * guildhall represents the players guild and holds there selected and reserved adventurers.
 * As well as their selected and inventory held items.
 */
public class Guildhall {
    private ArrayList<Adventurer> selectedAdventurers = new ArrayList<>();
    private ArrayList<Adventurer> reservedAdventurers = new ArrayList<>();

    private ArrayList<Item> selectedItems = new ArrayList<>();
    private ArrayList<Item> inventoryItems = new ArrayList<>();


    public Guildhall(ArrayList<Adventurer> selectedAdventurers,ArrayList<Item> selectedItems ) {
        this.selectedAdventurers = new ArrayList<>();
        this.selectedItems = new ArrayList<>();
    }

    /**
     * adds an adventurer to the selected adventurers
     * @param adventurer to add to the main party
     * @throws Exception if 5 adventurers are already selected.
     */
    public void makeSelectedAdventurer(Adventurer adventurer) throws Exception {
        if (selectedAdventurers.size() <5) {
            selectedAdventurers.add(adventurer);
        }
        else {
            throw new GuildFullException("Maximum adventurers selected");
        }
    }

    /**
     *removes adventurer from main party
     * @param adventurer to remove from main party
     * @throws Exception if selected adventurers is empty
     */
    public void removeSelectedAdventurer(Adventurer adventurer) throws Exception {
        if (!selectedAdventurers.isEmpty()) {
            selectedAdventurers.remove(adventurer);
        }
        else {
            throw new Exception("selected adventurers empty");
        }
    }

    /**
     * adds an adventurer to reserves
     * @param adventurer to add to reserves
     */
    public void makeReservedAdventurer(Adventurer adventurer){
        reservedAdventurers.add(adventurer);
    }

    /**
     * kicks adventurer from the reserve party
     * @param adventurer to remove from the reserve party
     * @throws Exception if the reserve party is empty
     */
    public void kickReservedAdventurer(Adventurer adventurer) throws Exception {
        if (!reservedAdventurers.isEmpty()) {
            reservedAdventurers.remove(adventurer);
        } else {
            throw new Exception("Reserved adventurers empty");
        }
    }

    public void moveReserveToMainParty() {

    }

    public ArrayList<Adventurer> getReservedAdventurers() {return reservedAdventurers;}

    public ArrayList<Adventurer> getSelectedAdventurers() {return selectedAdventurers;}

    public ArrayList<Item> getSelectedItems() {return selectedItems;}

    public ArrayList<Item> getInventoryItems() {return inventoryItems;}

}



// SHOULD BE in MARKETSERVICE


//    public void buyItem(Item item) {
//        if (playersGoldAmount - item.getCost() >= 0) {
//            playersGoldAmount -= item.getCost();
//            inventoryItems.add(item);
//        } else {
//            throw new IllegalArgumentException("Insufficient Gold");
//        }
//    }
//
//    public void selectItem(Item item) throws Exception {
//        if (inventoryItems.contains(item)) {
//            inventoryItems.remove(item);
//            selectedItems.add(item);
//        }
//        else {
//            throw new Exception("item not in Inventory");
//        }
//    }