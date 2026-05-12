package seng201.team0.models;

import seng201.team0.exception.GuildFullException;

import java.util.ArrayList;

/**
 * guildhall represents the players guild and holds there selected and reserved adventurers.
 * As well as their selected and inventory held items.
 */
public class Guildhall {

    /** Maximum number of adventurers in the main party. */
    public static final int MAIN_PARTY_CAPACITY = 5;

    /** Maximum number of adventurers in the reserves. */
    public static final int RESERVES_CAPACITY = 5;

    private ArrayList<Adventurer> selectedAdventurers = new ArrayList<>();
    private final  ArrayList<Adventurer> reservedAdventurers = new ArrayList<>();

    private ArrayList<Item> selectedItems = new ArrayList<>();
    private final ArrayList<Item> inventoryItems = new ArrayList<>();


    public Guildhall(ArrayList<Adventurer> selectedAdventurers,ArrayList<Item> selectedItems ) {
        this.selectedAdventurers = new ArrayList<>();
        this.selectedItems = new ArrayList<>();
    }

    /**
     * Constructs an empty Guildhall.
     */
    public Guildhall() {
    }

    /**
     * adds an adventurer to the main party.
     * @param adventurer to add to the main party.
     * @throws GuildFullException if 5 adventurers are already selected.
     */
    public void makeMainPartyAdventurer(Adventurer adventurer) throws GuildFullException {
        if (selectedAdventurers.size() >= MAIN_PARTY_CAPACITY) {
            throw new GuildFullException("Main party is full (max "
                    + MAIN_PARTY_CAPACITY + ").");
        }
        selectedAdventurers.add(adventurer);
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

    public void retire(Adventurer adventurer) {
        if (getTotalAdventurers() <= 1) {
            throw new IllegalStateException("Cannot retire the last adventurer.");
        }
        selectedAdventurers.remove(adventurer);
        reservedAdventurers.remove(adventurer);
    }


    /**
     * adds an adventurer to reserves.
     * @param adventurer to add to reserves.
     */
    public void makeReservedAdventurer(Adventurer adventurer) throws GuildFullException {
        if (reservedAdventurers.size() >= RESERVES_CAPACITY) {
            throw new GuildFullException("Reserves are full (max "
                    + RESERVES_CAPACITY + ").");
        }
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
        if (!reservedAdventurers.contains(adventurer)) {
            throw new IllegalArgumentException("Adventurer is not in reserves.");
        }
        if (selectedAdventurers.size() >= MAIN_PARTY_CAPACITY) {
            throw new GuildFullException("Main party is full (max "
                    + MAIN_PARTY_CAPACITY + ").");
        }
        removeReservedAdventurer(adventurer);
        makeMainPartyAdventurer(adventurer);
    }


    /**
     * removes adventurer from main party and adds them to reserves.
     * @param adventurer to move from main party to reserves.
     * @throws GuildFullException if main party empty.
     */
    public void moveMainPartyToReserve(Adventurer adventurer) throws GuildFullException {
        if (!selectedAdventurers.contains(adventurer)) {
            throw new IllegalArgumentException("Adventurer is not in main party.");
        }
        if (selectedAdventurers.size() <= 1) {
            throw new IllegalStateException("Main party must have at least one adventurer.");
        }
        if (reservedAdventurers.size() >= RESERVES_CAPACITY) {
            throw new GuildFullException("Reserves are full (max "
                    + RESERVES_CAPACITY + ").");
        }
        selectedAdventurers.remove(adventurer);
        reservedAdventurers.add(adventurer);
        adventurer.resetExpeditionsInARow();
    }


    /** @return total number of adventurers across main party and reserves */
    public int getTotalAdventurers() {
        return selectedAdventurers.size() + reservedAdventurers.size();
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
//            throw new InsufficientGoldException("Insufficient Gold");
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