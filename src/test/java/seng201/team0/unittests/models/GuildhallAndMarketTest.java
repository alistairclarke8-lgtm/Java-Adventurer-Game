package seng201.team0.unittests.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Guildhall} and {@link Market}.
 */
class GuildhallAndMarketTest {

    private Guildhall guildhall;
    private Market market;

    @BeforeEach
    void setUp() {
        guildhall = new Guildhall();
        market = new Market();
    }

    @Test
    void newGuildhall_isEmpty() {
        assertTrue(guildhall.getMainPartyAdventurers().isEmpty());
        assertTrue(guildhall.getReservedAdventurers().isEmpty());
        assertTrue(guildhall.getInventoryItems().isEmpty());
        assertTrue(guildhall.getSelectedItems().isEmpty());
    }

    @Test
    void capacityConstants_areFive() {
        assertEquals(5, Guildhall.mainPartyCapacity);
        assertEquals(5, Guildhall.reservesCapacity);
    }

    @Test
    void guildhall_canAddAdventurers() {
        Adventurer adventurer = new Adventurer("A", 50, 10, 10, 5, "Test");
        guildhall.getMainPartyAdventurers().add(adventurer);
        assertEquals(1, guildhall.getMainPartyAdventurers().size());
    }

    @Test
    void market_startsEmpty() {
        assertTrue(market.getAvailableItems().isEmpty());
    }

    @Test
    void market_canAddAndRemove() {
        UpgradeItem item = new UpgradeItem("Tonic", 25, "test", "stamina", 10);
        market.addItem(item);
        assertEquals(1, market.getAvailableItems().size());
        assertTrue(market.removeItem(item));
        assertEquals(0, market.getAvailableItems().size());
    }

    @Test
    void market_clearEmptiesEverything() {
        market.addItem(new UpgradeItem("A", 1, "", "stamina", 1));
        market.addItem(new UpgradeItem("B", 1, "", "stamina", 1));
        market.clear();
        assertTrue(market.getAvailableItems().isEmpty());
    }

    @Test
    void market_removeNonExistentReturnsFalse() {
        UpgradeItem item = new UpgradeItem("Tonic", 25, "test", "stamina", 10);
        assertFalse(market.removeItem(item));
    }
}
