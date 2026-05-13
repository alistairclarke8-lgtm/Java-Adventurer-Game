package seng201.team0.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Guildhall represents the players guild and holds their main party and reserved adventurers,
 * as well as their equipped and inventory held items.
 */
public class Guildhall {

    /** Maximum number of adventurers in the main party. */
    public static final int MAIN_PARTY_CAPACITY = 5;

    /** Maximum number of adventurers in the reserves. */
    public static final int RESERVES_CAPACITY = 5;

    private ArrayList<Adventurer> mainPartyAdventurers = new ArrayList<>();
    private final ArrayList<Adventurer> reservedAdventurers = new ArrayList<>();
    private ArrayList<Item> selectedItems = new ArrayList<>();
    private final ArrayList<Item> inventoryItems = new ArrayList<>();


    /**
     * Constructs a Guildhall with existing adventurers and items.
     * @param mainPartyAdventurers the starting main party adventurers
     * @param selectedItems the starting selected items
     */
    public Guildhall(ArrayList<Adventurer> mainPartyAdventurers, ArrayList<Item> selectedItems) {
        this.mainPartyAdventurers = mainPartyAdventurers;
        this.selectedItems = selectedItems;
    }

    /**
     * Constructs an empty Guildhall.
     */
    public Guildhall() {
//        for testing guildservices
        reservedAdventurers.addAll(List.of(new Archer(), new Swordsman(), new Wizard(), new Wizard(), new Swordsman()));

    }

    /** @return the main party adventurers */
    public ArrayList<Adventurer> getMainPartyAdventurers() { return mainPartyAdventurers; }

    /** @return the reserved adventurers */
    public ArrayList<Adventurer> getReservedAdventurers() { return reservedAdventurers; }

    /** @return the selected items */
    public ArrayList<Item> getSelectedItems() { return selectedItems; }

    /** @return the inventory items */
    public ArrayList<Item> getInventoryItems() { return inventoryItems; }
}