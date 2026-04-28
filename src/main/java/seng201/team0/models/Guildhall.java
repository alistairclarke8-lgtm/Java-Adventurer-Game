package seng201.team0.models;

import seng201.team0.exception.GuildFullException;

import java.util.ArrayList;

/**
 * guildhall represents the players guild and holds there selected and reserved adventurers.
 * As well as their selected and inventory held items.
 */
public class Guildhall {
    private ArrayList<Adventurer> selectedAdventurers = new ArrayList<>();
    private final  ArrayList<Adventurer> reservedAdventurers = new ArrayList<>();

    private ArrayList<Item> selectedItems = new ArrayList<>();
    private final ArrayList<Item> inventoryItems = new ArrayList<>();


    public Guildhall(ArrayList<Adventurer> selectedAdventurers,ArrayList<Item> selectedItems ) {
        this.selectedAdventurers = new ArrayList<>();
        this.selectedItems = new ArrayList<>();
    }

    /**
     * adds an adventurer to the main party.
     * @param adventurer to add to the main party.
     * @throws Exception if 5 adventurers are already selected.
     */
    public void makeMainPartyAdventurer(Adventurer adventurer) throws Exception {
        if (selectedAdventurers.size() <5) {
            selectedAdventurers.add(adventurer);
        }
        else {
            throw new GuildFullException("Maximum adventurers selected");
        }
    }

    /**
     *removes adventurer from main party.
     * @param adventurer to remove from main party.
     * @throws Exception if selected adventurers is empty.
     */
    public void removeMainPartyAdventurer(Adventurer adventurer) throws Exception {
        if (!selectedAdventurers.isEmpty()) {
            selectedAdventurers.remove(adventurer);
        }
        else {
            throw new Exception("main party empty");
        }
    }

    /**
     * adds an adventurer to reserves.
     * @param adventurer to add to reserves.
     */
    public void makeReservedAdventurer(Adventurer adventurer){

        reservedAdventurers.add(adventurer);
    }

    /**
     * removes adventurer from the reserve party.
     * @param adventurer to remove from the reserve party.
     * @throws Exception if the reserve party is empty.
     */
    public void removeReservedAdventurer(Adventurer adventurer) throws Exception {
        if (!reservedAdventurers.isEmpty()) {
            reservedAdventurers.remove(adventurer);
        } else {
            throw new Exception("Reserved adventurers empty");
        }
    }

    /**
     * removes adventurer from reserves and adds them to main party.
     * @param adventurer to move from reserve to main party.
     * @throws Exception if reserve party is empty and if 5 adventurers are already selected.
     */
    public void moveReserveToMainParty(Adventurer adventurer) throws Exception {
        removeReservedAdventurer(adventurer);
        makeMainPartyAdventurer(adventurer);
    }

    /**
     * removes adventurer from main party and adds them to reserves.
     * @param adventurer to move from main party to reserves.
     * @throws Exception if main party empty.
     */
    public void moveMainPartyToReserve(Adventurer adventurer) throws Exception {
        removeMainPartyAdventurer(adventurer);
        makeReservedAdventurer(adventurer);
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