package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.GameEnvironment;
import seng201.team0.exception.GuildFullException;
import seng201.team0.exception.InsufficientGoldException;
import seng201.team0.services.MarketService;
import seng201.team0.services.RecruitmentService;
import seng201.team0.unittests.gui.TestNavigator;
import seng201.team0.models.Adventurer;
import seng201.team0.models.Difficulty;
import seng201.team0.models.Guildhall;
import seng201.team0.models.Item;
import seng201.team0.models.SetupModel;
import seng201.team0.models.UpgradeItem;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link MarketService} and {@link RecruitmentService}.
 */
class MarketAndRecruitmentServiceTest {

    private GameEnvironment environment;
    private MarketService market;
    private RecruitmentService recruits;

    @BeforeEach
    void setUp() {
        TestNavigator navigator = new TestNavigator();
        environment = new GameEnvironment(navigator);

        SetupModel setup = new SetupModel();
        setup.setGuildName("Testers");
        setup.setDifficulty(Difficulty.NORMAL);
        setup.setExpeditionCount(5);
        for (int i = 0; i < 3; i++) {
            setup.setSelectedAdventurer(i, setup.getDefaultAdventurer(i));
        }
        environment.onSetupComplete(setup);
        // Replace the services with seeded ones for deterministic tests
        market = new MarketService(environment);
        recruits = new RecruitmentService(environment, new Random(2));
    }

    @Test
    void refreshStock_populatesMarketInRange() {
        market.refreshStock();
        int size = environment.getMarket().getAvailableItems().size();
        assertTrue(size >= MarketService.MIN_STOCK && size <= MarketService.MAX_STOCK,
                "stock size out of range: " + size);
    }

    @Test
    void buy_deductsGoldAndAddsToInventory() throws InsufficientGoldException {
        market.refreshStock();
        Item item = environment.getMarket().getAvailableItems().getFirst();
        int goldBefore = environment.getGold();
        int price = market.getBuyPrice(item);
        market.buy(item);
        assertEquals(goldBefore - price, environment.getGold());
        assertTrue(environment.getGuildhall().getInventoryItems().contains(item));
        assertFalse(environment.getMarket().getAvailableItems().contains(item));
    }

    @Test
    void buy_throwsIfNotInMarket() {
        UpgradeItem foreign = new UpgradeItem("X", 10, "x", "stamina", 1);
        assertThrows(IllegalArgumentException.class, () -> market.buy(foreign));
    }

    @Test
    void sell_addsGoldAndRemovesFromInventory() throws InsufficientGoldException {
        market.refreshStock();
        Item item = environment.getMarket().getAvailableItems().getFirst();
        market.buy(item);
        int goldAfterBuy = environment.getGold();
        int sellPrice = market.getSellPrice(item);
        market.sell(item);
        assertEquals(goldAfterBuy + sellPrice, environment.getGold());
        assertFalse(environment.getGuildhall().getInventoryItems().contains(item));
    }

    @Test
    void sell_throwsIfNotInInventory() {
        UpgradeItem foreign = new UpgradeItem("X", 10, "x", "stamina", 1);
        assertThrows(IllegalArgumentException.class, () -> market.sell(foreign));
    }

    @Test
    void getBuyPrice_scalesByDifficulty() {
        UpgradeItem item = new UpgradeItem("X", 100, "x", "stamina", 1);
        environment.getMarket().addItem(item);
        int price = market.getBuyPrice(item);
        // NORMAL costMultiplier is 1.0
        assertEquals(100, price);
    }

    @Test
    void refreshPool_populatesRecruits() {
        recruits.refreshPool();
        int size = recruits.getAvailableRecruits().size();
        assertTrue(size >= RecruitmentService.minPool && size <= RecruitmentService.maxPool,
                "pool size out of range: " + size);
    }

    @Test
    void getCheapestHireCost_returnsLowestPrice() {
        recruits.refreshPool();
        int cheapest = recruits.getCheapestHireCost();
        for (Adventurer adventurer : recruits.getAvailableRecruits()) {
            assertTrue(recruits.getHirePrice(adventurer) >= cheapest);
        }
    }

    @Test
    void getCheapestHireCost_negativeWhenPoolEmpty() {
        // refreshPool not called; pool empty by construction in this seeded service
        assertEquals(-1, recruits.getCheapestHireCost());
    }

    @Test
    void hire_addsToReservesAndDeductsGold() throws Exception {
        recruits.refreshPool();
        Adventurer chosen = recruits.getAvailableRecruits().getFirst();
        int goldBefore = environment.getGold();
        int price = recruits.getHirePrice(chosen);
        recruits.hire(chosen);
        assertEquals(goldBefore - price, environment.getGold());
        assertTrue(environment.getGuildhall().getReservedAdventurers().contains(chosen));
        assertFalse(recruits.getAvailableRecruits().contains(chosen));
    }

    @Test
    void hire_throwsIfReservesFull() throws Exception {
        recruits.refreshPool();
        // Fill reserves with 5 dummy adventurers
        for (int i = 0; i < Guildhall.reservesCapacity; i++) {
            environment.getGuildhall().getReservedAdventurers().add(
                    new Adventurer("D" + i, 50, 10, 10, 5, "Dummy"));
        }
        Adventurer chosen = recruits.getAvailableRecruits().getFirst();
        assertThrows(GuildFullException.class, () -> recruits.hire(chosen));
    }
}
