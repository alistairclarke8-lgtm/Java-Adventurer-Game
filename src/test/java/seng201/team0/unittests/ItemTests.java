package seng201.team0.unittests;

import org.junit.jupiter.api.Test;
import seng201.team0.models.Adventurer;
import seng201.team0.models.Archer;
import seng201.team0.models.EquipableItem;
import seng201.team0.models.Skill;
import seng201.team0.models.SkillTome;
import seng201.team0.models.UpgradeItem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link UpgradeItem}, {@link EquipableItem}, and {@link SkillTome}.
 */
public class ItemTests {

    @Test
    void upgradeItemIsConsumedAndChangesMaxStat() {
        UpgradeItem tonic = new UpgradeItem("Tonic", 10, "x", "stamina", 12);
        assertTrue(tonic.isConsumedOnUse());
        Adventurer archer = new Archer();
        int oldMax = archer.getMaxStamina();
        tonic.applyTo(archer);
        assertEquals(oldMax + 12, archer.getMaxStamina());
    }

    @Test
    void upgradeItemPerceptionVariant() {
        UpgradeItem tonic = new UpgradeItem("Eye", 10, "x", "perception", 9);
        Adventurer archer = new Archer();
        int oldPerc = archer.getPerception();
        tonic.applyTo(archer);
        assertEquals(oldPerc + 9, archer.getPerception());
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
        Adventurer archer = new Archer();
        int oldPerc = archer.getPerception();
        lens.applyTo(archer);
        assertEquals(oldPerc + 5, archer.getPerception());
        lens.removeFrom(archer);
        assertEquals(oldPerc, archer.getPerception());
    }

    @Test
    void skillTomeTeachesSkillAndIsConsumed() {
        SkillTome tome = new SkillTome("Tome", 20, "x",
                new Skill("Grit", "tough", 10, 5));
        assertTrue(tome.isConsumedOnUse());
        Adventurer archer = new Archer();
        int oldMax = archer.getMaxStamina();
        int oldPerception = archer.getPerception();
        tome.applyTo(archer);
        assertEquals(oldMax + 10, archer.getMaxStamina());
        assertEquals(oldPerception + 5, archer.getPerception());
        assertTrue(archer.getSkills().stream()
                .anyMatch(s -> s.getName().equals("Grit")));
    }

    @Test
    void sellPriceIsLessThanBuyPrice() {
        UpgradeItem tonic = new UpgradeItem("Tonic", 100, "x", "stamina", 1);
        // Sell at default multiplier should be less than buy price.
        assertTrue(tonic.getSellPrice(1.0) < tonic.getBuyPrice());
    }

    @Test
    void buyPriceMatchesCost() {
        UpgradeItem tonic = new UpgradeItem("Tonic", 50, "x", "stamina", 1);
        assertEquals(50, tonic.getBuyPrice());
    }
}
