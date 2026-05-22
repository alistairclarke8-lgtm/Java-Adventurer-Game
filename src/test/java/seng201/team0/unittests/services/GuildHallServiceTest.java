package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.exception.EmptyPartyException;
import seng201.team0.exception.EmptyReservesException;
import seng201.team0.exception.GuildFullException;
import seng201.team0.exception.LastAdventurerException;
import seng201.team0.models.Adventurer;
import seng201.team0.models.EquipableItem;
import seng201.team0.models.Guildhall;
import seng201.team0.models.UpgradeItem;
import seng201.team0.services.GuildHallService;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link GuildHallService}.
 */
class GuildHallServiceTest {

    private Guildhall guildhall;
    private GuildHallService service;
    private Adventurer alice;
    private Adventurer bob;
    private Adventurer carol;

    @BeforeEach
    void setUp() {
        guildhall = new Guildhall();
        service = new GuildHallService(guildhall);
        alice = new Adventurer("Alice", 50, 10, 10, 5, "Archer");
        bob = new Adventurer("Bob", 60, 12, 10, 5, "Brute");
        carol = new Adventurer("Carol", 70, 15, 10, 5, "Mage");
    }

    @Test
    void addStartingParty_skipsNulls() {
        Adventurer[] starters = new Adventurer[]{alice, null, bob};
        service.addStartingParty(starters);
        assertEquals(2, guildhall.getMainPartyAdventurers().size());
    }

    @Test
    void makeMainPartyAdventurer_addsToMainParty() throws GuildFullException {
        service.makeMainPartyAdventurer(alice);
        assertTrue(guildhall.getMainPartyAdventurers().contains(alice));
    }

    @Test
    void makeMainPartyAdventurer_throwsWhenFull() throws GuildFullException {
        for (int i = 0; i < Guildhall.mainPartyCapacity; i++) {
            service.makeMainPartyAdventurer(new Adventurer("A" + i, 50, 10, 10, 5, "T"));
        }
        assertThrows(GuildFullException.class, () -> service.makeMainPartyAdventurer(alice));
    }

    @Test
    void makeReservedAdventurer_throwsWhenFull() throws GuildFullException {
        for (int i = 0; i < Guildhall.reservesCapacity; i++) {
            service.makeReservedAdventurer(new Adventurer("A" + i, 50, 10, 10, 5, "T"));
        }
        assertThrows(GuildFullException.class, () -> service.makeReservedAdventurer(alice));
    }

    @Test
    void retire_throwsWhenLastAdventurer() throws GuildFullException {
        service.makeMainPartyAdventurer(alice);
        assertThrows(LastAdventurerException.class, () -> service.retire(alice));
    }

    @Test
    void retire_removesFromMainParty() throws Exception {
        service.makeMainPartyAdventurer(alice);
        service.makeMainPartyAdventurer(bob);
        service.retire(alice);
        assertFalse(guildhall.getMainPartyAdventurers().contains(alice));
        assertEquals(1, guildhall.getMainPartyAdventurers().size());
    }

    @Test
    void retire_removesFromReserves() throws Exception {
        service.makeMainPartyAdventurer(alice);
        service.makeReservedAdventurer(bob);
        service.retire(bob);
        assertFalse(guildhall.getReservedAdventurers().contains(bob));
    }

    @Test
    void moveReserveToMainParty_movesAdventurer() throws Exception {
        service.makeReservedAdventurer(alice);
        service.moveReserveToMainParty(alice);
        assertTrue(guildhall.getMainPartyAdventurers().contains(alice));
        assertFalse(guildhall.getReservedAdventurers().contains(alice));
    }

    @Test
    void moveReserveToMainParty_rejectsNonReservist() {
        assertThrows(IllegalArgumentException.class,
                () -> service.moveReserveToMainParty(alice));
    }

    @Test
    void moveMainPartyToReserve_movesAndResetsCounter() throws Exception {
        service.makeMainPartyAdventurer(alice);
        service.makeMainPartyAdventurer(bob);
        alice.incrementExpeditionsInARow();
        alice.incrementExpeditionsInARow();
        service.moveMainPartyToReserve(alice);
        assertTrue(guildhall.getReservedAdventurers().contains(alice));
        assertEquals(0, alice.getExpeditionsInARow());
    }


    @Test
    void swapAdventurers_swapsAndResetsCounter() throws Exception {
        service.makeMainPartyAdventurer(alice);
        service.makeReservedAdventurer(bob);
        alice.incrementExpeditionsInARow();
        service.swapAdventurers(alice, bob);
        assertTrue(guildhall.getMainPartyAdventurers().contains(bob));
        assertTrue(guildhall.getReservedAdventurers().contains(alice));
        assertEquals(0, alice.getExpeditionsInARow());
    }

    @Test
    void useItem_consumesAndApplies() {
        UpgradeItem tonic = new UpgradeItem("Tonic", 25, "x", "stamina", 10);
        guildhall.getInventoryItems().add(tonic);
        service.useItem(alice, tonic);
        assertEquals(60, alice.getMaxStamina());
        assertFalse(guildhall.getInventoryItems().contains(tonic));
    }

    @Test
    void getTotalAdventurers_countsBothLists() throws GuildFullException {
        service.makeMainPartyAdventurer(alice);
        service.makeReservedAdventurer(bob);
        service.makeReservedAdventurer(carol);
        assertEquals(3, service.getTotalAdventurers());
    }

    @Test
    void removeMainPartyAdventurer_throwsWhenEmpty() {
        assertThrows(EmptyPartyException.class,
                () -> service.removeMainPartyAdventurer(alice));
    }

    @Test
    void removeReservedAdventurer_throwsWhenEmpty() {
        assertThrows(EmptyReservesException.class,
                () -> service.removeReservedAdventurer(alice));
    }
}
