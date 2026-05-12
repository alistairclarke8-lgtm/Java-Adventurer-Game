package seng201.team0.models;

import seng201.team0.exception.GuildFullException;

import java.util.ArrayList;

/**
 * guildhall represents the players guild and holds their selected and reserved adventurers,
 * As well as their selected and inventory held items.
 */
public class Guildhall {

    /** Maximum number of adventurers in the main party. */
    public static final int MAIN_PARTY_CAPACITY = 5;

    /** Maximum number of adventurers in the reserves. */
    public static final int RESERVES_CAPACITY = 5;

    private ArrayList<Adventurer> selectedAdventurers = new ArrayList<>();
    private final ArrayList<Adventurer> reservedAdventurers = new ArrayList<>();
    private ArrayList<Item> selectedItems = new ArrayList<>();
    private final ArrayList<Item> inventoryItems = new ArrayList<>();

    /**
     * Constructs a Guildhall with existing adventurers and items.
     * @param selectedAdventurers the starting main party adventurers
     * @param selectedItems the starting selected items
     */
    public Guildhall(ArrayList<Adventurer> selectedAdventurers, ArrayList<Item> selectedItems) {
        this.selectedAdventurers = selectedAdventurers;
        this.selectedItems = selectedItems;
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
    public ArrayList<Adventurer> getSelectedAdventurers() { return selectedAdventurers; }

    public ArrayList<Adventurer> getReservedAdventurers() { return reservedAdventurers; }

    public ArrayList<Item> getSelectedItems() { return selectedItems; }

    public ArrayList<Item> getInventoryItems() { return inventoryItems; }
}