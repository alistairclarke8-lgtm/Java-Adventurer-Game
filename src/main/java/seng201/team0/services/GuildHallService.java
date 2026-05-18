package seng201.team0.services;

import seng201.team0.exception.GuildFullException;
import seng201.team0.exception.LastAdventurerException;
import seng201.team0.models.Adventurer;
import seng201.team0.models.Guildhall;
import seng201.team0.models.Item;

/**
 * Service class for managing guild operations such as moving, retiring adventurers and using items.
 */
public class GuildHallService {

    private final Guildhall guildhall;

    /**
     * Constructs a GuildHallService with the given Guildhall.
     * @param guildhall the guildhall to operate on
     */
    public GuildHallService(Guildhall guildhall) {
        this.guildhall = guildhall;
    }

    /**
     * Adds the starting adventurers to the main party at the beginning of the game.
     * @param adventurers the starting adventurers selected during setup
     */
    public void addStartingParty(Adventurer[] adventurers) {
        for (Adventurer adventurer : adventurers) {
            if (adventurer != null) {
                guildhall.getMainPartyAdventurers().add(adventurer);
            }
        }
    }

    /**
     * Adds an adventurer to the main party.
     * @param adventurer to add to the main party
     * @throws GuildFullException if the main party is full
     */
    public void makeMainPartyAdventurer(Adventurer adventurer) throws GuildFullException {
        if (guildhall.getMainPartyAdventurers().size() >= Guildhall.MAIN_PARTY_CAPACITY) {
            throw new GuildFullException("Main party is full (max " + Guildhall.MAIN_PARTY_CAPACITY + ").");
        }
        guildhall.getMainPartyAdventurers().add(adventurer);
    }

    /**
     * Removes an adventurer from the main party.
     * @param adventurer to remove
     * @throws Exception if the main party is empty
     */
    public void removeMainPartyAdventurer(Adventurer adventurer) throws Exception {
        if (!guildhall.getMainPartyAdventurers().isEmpty()) {
            guildhall.getMainPartyAdventurers().remove(adventurer);
        } else {
            throw new Exception("Main party is empty.");
        }
    }

    /**
     * Adds an adventurer to reserves.
     * @param adventurer to add to reserves
     * @throws GuildFullException if reserves are full
     */
    public void makeReservedAdventurer(Adventurer adventurer) throws GuildFullException {
        if (guildhall.getReservedAdventurers().size() >= Guildhall.RESERVES_CAPACITY) {
            throw new GuildFullException("Reserves are full (max " + Guildhall.RESERVES_CAPACITY + ").");
        }
        guildhall.getReservedAdventurers().add(adventurer);
    }

    /**
     * Removes an adventurer from reserves.
     * @param adventurer to remove
     * @throws Exception if reserves are empty
     */
    public void removeReservedAdventurer(Adventurer adventurer) throws Exception {
        if (!guildhall.getReservedAdventurers().isEmpty()) {
            guildhall.getReservedAdventurers().remove(adventurer);
        } else {
            throw new Exception("Reserves are empty.");
        }
    }

    /**
     * Retires an adventurer from the guild entirely.
     * @param adventurer to retire
     * @throws IllegalStateException if they are the last adventurer
     */
    public void retire(Adventurer adventurer) throws LastAdventurerException {
        if (getTotalAdventurers() <= 1) {
            throw new LastAdventurerException();
        }
        guildhall.getMainPartyAdventurers().remove(adventurer);
        guildhall.getReservedAdventurers().remove(adventurer);
    }

    /**
     * Moves an adventurer from reserves to the main party.
     * @param adventurer to move
     * @throws Exception if adventurer is not in reserves or main party is full
     */
    public void moveReserveToMainParty(Adventurer adventurer) throws Exception {
        if (!guildhall.getReservedAdventurers().contains(adventurer)) {
            throw new IllegalArgumentException("Adventurer is not in reserves.");
        }
        if (guildhall.getMainPartyAdventurers().size() >= Guildhall.MAIN_PARTY_CAPACITY) {
            throw new GuildFullException("Main party is full (max " + Guildhall.MAIN_PARTY_CAPACITY + ").");
        }
        removeReservedAdventurer(adventurer);
        makeMainPartyAdventurer(adventurer);
    }

    /**
     * Moves an adventurer from the main party to reserves.
     * @param adventurer to move
     * @throws GuildFullException if reserves are full or main party would be empty
     */
    public void moveMainPartyToReserve(Adventurer adventurer) throws GuildFullException {
        if (!guildhall.getMainPartyAdventurers().contains(adventurer)) {
            throw new IllegalArgumentException("Adventurer is not in main party.");
        }
        if (guildhall.getMainPartyAdventurers().size() <= 1) {
            throw new IllegalStateException("Main party must have at least one adventurer.");
        }
        if (guildhall.getReservedAdventurers().size() >= Guildhall.RESERVES_CAPACITY) {
            throw new GuildFullException("Reserves are full (max " + Guildhall.RESERVES_CAPACITY + ").");
        }
        guildhall.getMainPartyAdventurers().remove(adventurer);
        guildhall.getReservedAdventurers().add(adventurer);
        adventurer.resetExpeditionsInARow();
    }

    /**
     * Swaps a main party adventurer with a reserve adventurer.
     * @param partyAdventurer the adventurer in the main party
     * @param reserveAdventurer the adventurer in reserves
     * @throws Exception if either adventurer is not in their respective list
     */
    public void swapAdventurers(Adventurer partyAdventurer, Adventurer reserveAdventurer) throws Exception {
        int partyIndex = guildhall.getMainPartyAdventurers().indexOf(partyAdventurer);
        int reserveIndex = guildhall.getReservedAdventurers().indexOf(reserveAdventurer);
        guildhall.getMainPartyAdventurers().set(partyIndex, reserveAdventurer);
        guildhall.getReservedAdventurers().set(reserveIndex, partyAdventurer);
        partyAdventurer.resetExpeditionsInARow();
    }

    /**
     * Uses a consumable item on an adventurer and removes it from inventory.
     * @param adventurer to apply the item to
     * @param item to use
     */
    public void useItem(Adventurer adventurer, Item item) {
        item.applyTo(adventurer);
        guildhall.getInventoryItems().remove(item);
    }

    /**
     * Equips an item to an adventurer, returning any previously equipped item to inventory.
     * @param adventurer to equip the item to
     * @param item to equip
     */
    public void equipItem(Adventurer adventurer, Item item) {
        Item current = adventurer.getEquippedItem();
        if (current != null) {
            guildhall.getInventoryItems().add(current);
        }
        adventurer.setEquippedItem(item);
        guildhall.getInventoryItems().remove(item);
    }

    /**
     * Returns the total number of adventurers across main party and reserves.
     * @return total adventurer count
     */
    public int getTotalAdventurers() {
        return guildhall.getMainPartyAdventurers().size() + guildhall.getReservedAdventurers().size();
    }
}