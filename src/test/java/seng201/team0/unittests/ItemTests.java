package seng201.team0.unittests;

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
}