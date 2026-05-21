package seng201.team0.unittests.models;

import org.junit.jupiter.api.Test;
import seng201.team0.models.Adventurer;
import seng201.team0.models.EquipableItem;
import seng201.team0.models.Skill;
import seng201.team0.models.SkillTome;
import seng201.team0.models.UpgradeItem;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTests {

    private Adventurer makeAdventurer() {
        return new Adventurer("Test", 60, 50, 20, 10, "Warrior");
    }

    @Test
    void upgradeItemIsConsumedAndChangesMaxStat() {
        UpgradeItem tonic = new UpgradeItem("Tonic", 10, "x", "stamina", 12);
        assertTrue(tonic.isConsumedOnUse());
        Adventurer a = makeAdventurer();
        int oldMax = a.getMaxStamina();
        tonic.applyTo(a);
        assertEquals(oldMax + 12, a.getMaxStamina());
    }

    @Test
    void upgradeItemPerceptionVariant() {
        UpgradeItem tonic = new UpgradeItem("Eye", 10, "x", "perception", 9);
        Adventurer a = makeAdventurer();
        int oldPerc = a.getPerception();
        tonic.applyTo(a);
        assertEquals(oldPerc + 9, a.getPerception());
    }

    @Test
    void equipableItemIsNotConsumed() {
        EquipableItem boots = new EquipableItem("Boots", 10, "x", "stamina", 5);
        assertFalse(boots.isConsumedOnUse());
        assertEquals("Equipable", boots.getType());
    }

    @Test
    void equipableItemRemovesItsBonus() {
        EquipableItem lens = new EquipableItem("Lens", 10, "x", "perception", 5);
        Adventurer a = makeAdventurer();
        int oldPerc = a.getPerception();
        lens.applyTo(a);
        assertEquals(oldPerc + 5, a.getPerception());
        lens.removeFrom(a);
        assertEquals(oldPerc, a.getPerception());
    }

    @Test
    void skillTomeTeachesSkillAndIsConsumed() {
        SkillTome tome = new SkillTome("Tome", 20, "x",
                new Skill("Grit", "tough", 10, 5));
        assertTrue(tome.isConsumedOnUse());
        Adventurer a = makeAdventurer();
        int oldMax = a.getMaxStamina();
        int oldPerception = a.getPerception();
        tome.applyTo(a);
        assertEquals(oldMax + 10, a.getMaxStamina());
        assertEquals(oldPerception + 5, a.getPerception());
        assertTrue(a.getSkills().stream()
                .anyMatch(s -> s.getName().equals("Grit")));
    }

    @Test
    void sellPriceIsLessThanBuyPrice() {
        UpgradeItem tonic = new UpgradeItem("Tonic", 100, "x", "stamina", 1);
        assertTrue(tonic.getSellPrice(1.0) < tonic.getBuyPrice());
    }

    @Test
    void buyPriceMatchesCost() {
        UpgradeItem tonic = new UpgradeItem("Tonic", 50, "x", "stamina", 1);
        assertEquals(50, tonic.getBuyPrice());
    }

    @Test
    void upgradeItem_isConsumed() {
        UpgradeItem item = new UpgradeItem("Tonic", 25, "test", "stamina", 10);
        assertTrue(item.isConsumedOnUse());
        assertEquals("Upgrade", item.getType());
    }

    @Test
    void upgradeItem_appliesPermanentStamina() {
        Adventurer adventurer = new Adventurer("A", 50, 10, 10, 5, "Test");
        UpgradeItem item = new UpgradeItem("Tonic", 25, "test", "stamina", 10);
        item.applyTo(adventurer);
        assertEquals(60, adventurer.getMaxStamina());
    }

    @Test
    void upgradeItem_appliesPermanentPerception() {
        Adventurer adventurer = new Adventurer("A", 50, 10, 10, 5, "Test");
        UpgradeItem item = new UpgradeItem("Lens", 25, "test", "perception", 7);
        item.applyTo(adventurer);
        assertEquals(17, adventurer.getPerception());
    }

    @Test
    void upgradeItem_removeIsNoOp() {
        Adventurer adventurer = new Adventurer("A", 50, 10, 10, 5, "Test");
        UpgradeItem item = new UpgradeItem("Tonic", 25, "test", "stamina", 10);
        item.applyTo(adventurer);
        item.removeFrom(adventurer);
        // Should still have the bonus — upgrades are permanent
        assertEquals(60, adventurer.getMaxStamina());
    }

    @Test
    void equipableItem_appliesAndRemovesPerceptionCorrectly() {
        Adventurer adventurer = new Adventurer("A", 50, 10, 10, 5, "Test");
        EquipableItem item = new EquipableItem("Glass", 20, "test", "perception", 8);
        item.applyTo(adventurer);
        assertEquals(18, adventurer.getPerception());
        item.removeFrom(adventurer);
        assertEquals(10, adventurer.getPerception());
    }

    @Test
    void equipableItem_isNotConsumed() {
        EquipableItem item = new EquipableItem("Boots", 20, "test", "stamina", 5);
        assertFalse(item.isConsumedOnUse());
        assertEquals("Equipable", item.getType());
    }

    @Test
    void skillTome_teachesSkill() {
        Adventurer adventurer = new Adventurer("A", 50, 10, 10, 5, "Test");
        Skill skill = new Skill("Test", "desc", 5, 0);
        SkillTome tome = new SkillTome("Tome", 30, "test", skill);
        tome.applyTo(adventurer);
        assertEquals(1, adventurer.getSkills().size());
        assertEquals(55, adventurer.getMaxStamina());
    }

    @Test
    void skillTome_isConsumed() {
        Skill skill = new Skill("Test", "desc", 5, 0);
        SkillTome tome = new SkillTome("Tome", 30, "test", skill);
        assertTrue(tome.isConsumedOnUse());
        assertEquals("Skill Tome", tome.getType());
    }

    @Test
    void getSellPrice_isHalfBuyPriceScaledByDifficulty() {
        UpgradeItem item = new UpgradeItem("Tonic", 40, "test", "stamina", 10);
        // base: 40 * 0.5 = 20; multiplier 1.0 -> 20, multiplier 0.8 -> 16
        assertEquals(20.0, item.getSellPrice(1.0));
        assertEquals(16.0, item.getSellPrice(0.8));
    }

    @Test
    void itemFactory_returnsFreshLists() {
        var first = seng201.team0.factories.ItemFactory.buildMasterItemPool();
        var second = seng201.team0.factories.ItemFactory.buildMasterItemPool();
        assertNotSame(first, second);
        assertEquals(first.size(), second.size());
        assertFalse(first.isEmpty());
    }
}