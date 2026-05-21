package seng201.team0.unittests.factories;

import org.junit.jupiter.api.Test;
import seng201.team0.factories.AdventurerFactory;
import seng201.team0.factories.ExpeditionPool;
import seng201.team0.factories.ItemFactory;
import seng201.team0.models.Adventurer;
import seng201.team0.models.Expedition;
import seng201.team0.models.Item;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the factory classes.
 */
class FactoriesTest {

    @Test
    void adventurerFactory_returnsRequestedCount() {
        List<Adventurer> sample = AdventurerFactory.getRandom(5);
        assertEquals(5, sample.size());
    }

    @Test
    void adventurerFactory_returnsDifferentAdventurers() {
        List<Adventurer> sample = AdventurerFactory.getRandom(5);
        // Make sure all 5 are distinct objects
        long distinct = sample.stream().map(Adventurer::getName).distinct().count();
        assertEquals(5, distinct);
    }

    @Test
    void adventurerFactory_returnsFreshShuffleEachCall() {
        // Best we can do without a seed: check that the call doesn't fail
        // and that we don't get the same list reference back.
        List<Adventurer> a = AdventurerFactory.getRandom(5);
        List<Adventurer> b = AdventurerFactory.getRandom(5);
        assertNotSame(a, b);
    }

    @Test
    void itemFactory_includesEachType() {
        List<Item> items = ItemFactory.buildMasterItemPool();
        long upgrades = items.stream().filter(i -> i.getType().equals("Upgrade")).count();
        long equipables = items.stream().filter(i -> i.getType().equals("Equipable")).count();
        long tomes = items.stream().filter(i -> i.getType().equals("Skill Tome")).count();
        assertTrue(upgrades > 0);
        assertTrue(equipables > 0);
        assertTrue(tomes > 0);
    }

    @Test
    void expeditionPool_hasAtLeastThreeExpeditions() {
        List<Expedition> expeditions = ExpeditionPool.getAll();
        assertTrue(expeditions.size() >= 3);
    }

    @Test
    void expeditionPool_allHaveAtLeastFiveEvents() {
        for (Expedition expedition : ExpeditionPool.getAll()) {
            assertTrue(expedition.getEvents().size() >= 5,
                    "Expedition " + expedition.getName() + " has fewer than 5 events");
        }
    }

    @Test
    void expeditionPool_allHaveAName() {
        for (Expedition expedition : ExpeditionPool.getAll()) {
            assertNotNull(expedition.getName());
            assertFalse(expedition.getName().isBlank());
        }
    }
}
