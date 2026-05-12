package seng201.team0.unittests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.Adventurer;
import seng201.team0.models.Archer;
import seng201.team0.models.EquipableItem;
import seng201.team0.models.Skill;
import seng201.team0.models.Swordsman;
import seng201.team0.models.UpgradeItem;
import seng201.team0.models.Wizard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for Adventurer and its concrete subclasses.
 */
public class AdventurerTests {

    private Adventurer archer;

    @BeforeEach
    void setUp() {
        archer = new Archer();
    }

    @Test
    void archerTypeIsArcher() {
        assertEquals("Archer", archer.getType());
    }

    @Test
    void swordsmanAndWizardHaveCorrectTypes() {
        assertEquals("Swordsman", new Swordsman().getType());
        assertEquals("Wizard", new Wizard().getType());
    }

    @Test
    void decreaseStaminaClampsAtZero() {
        archer.decreaseStamina(10000);
        assertEquals(0, archer.getStamina());
    }

    @Test
    void increaseStaminaClampsAtMax() {
        int max = archer.getMaxStamina();
        archer.decreaseStamina(20);
        archer.increaseStamina(10000);
        assertEquals(max, archer.getStamina());
    }

    @Test
    void fullyRestRestoresStamina() {
        int max = archer.getMaxStamina();
        archer.decreaseStamina(40);
        archer.fullyRest();
        assertEquals(max, archer.getStamina());
    }

    @Test
    void increaseMaxStaminaIncreasesBoth() {
        int oldMax = archer.getMaxStamina();
        int oldStam = archer.getStamina();
        archer.increaseMaxStamina(15);
        assertEquals(oldMax + 15, archer.getMaxStamina());
        assertEquals(oldStam + 15, archer.getStamina());
    }

    @Test
    void perceptionMovesUpAndDown() {
        int p = archer.getPerception();
        archer.increasePerception(7);
        assertEquals(p + 7, archer.getPerception());
        archer.decreasePerception(3);
        assertEquals(p + 4, archer.getPerception());
    }

    @Test
    void decreasePerceptionClampsAtZero() {
        archer.decreasePerception(99999);
        assertEquals(0, archer.getPerception());
    }

    @Test
    void learnSkillAppliesBonuses() {
        int oldMax = archer.getMaxStamina();
        int oldPerc = archer.getPerception();
        archer.learnSkill(new Skill("Grit", "tough", 10, 5));
        assertEquals(oldMax + 10, archer.getMaxStamina());
        assertEquals(oldPerc + 5, archer.getPerception());
        assertTrue(archer.getSkills().stream().anyMatch(s -> s.getName().equals("Grit")));
    }

    @Test
    void expeditionsInARowIncrementsAndResets() {
        assertEquals(0, archer.getExpeditionsInARow());
        archer.incrementExpeditionsInARow();
        archer.incrementExpeditionsInARow();
        assertEquals(2, archer.getExpeditionsInARow());
        archer.resetExpeditionsInARow();
        assertEquals(0, archer.getExpeditionsInARow());
    }

    @Test
    void equipAndUnequipUpdatesEquippedItemAndStats() {
        int oldPerception = archer.getPerception();
        EquipableItem lens = new EquipableItem("Lens", 10, "x", "perception", 5);
        archer.equip(lens);
        assertSame(lens, archer.getEquippedItem());
        assertEquals(oldPerception + 5, archer.getPerception());
        archer.equip(null);
        assertNull(archer.getEquippedItem());
        assertEquals(oldPerception, archer.getPerception());
    }

    @Test
    void equipReplacesPreviousItem() {
        EquipableItem boots = new EquipableItem("Boots", 10, "x", "stamina", 5);
        EquipableItem armor = new EquipableItem("Armor", 20, "x", "stamina", 15);
        archer.equip(boots);
        archer.equip(armor);
        assertSame(armor, archer.getEquippedItem());
    }

    @Test
    void upgradeItemIsConsumed() {
        UpgradeItem tonic = new UpgradeItem("Tonic", 10, "x", "stamina", 10);
        assertTrue(tonic.isConsumedOnUse());
        int oldMax = archer.getMaxStamina();
        tonic.applyTo(archer);
        assertEquals(oldMax + 10, archer.getMaxStamina());
    }

    @Test
    void descriptionInheritedFromAdventurerIncludesType() {
        // Archer no longer overrides getDescription with a noop, so the
        // parent description should mention the type.
        assertNotNull(archer.getDescription());
        assertTrue(archer.getDescription().contains("Archer"));
    }
}
