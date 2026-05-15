package seng201.team0.unittests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.Adventurer;
import seng201.team0.models.EquipableItem;
import seng201.team0.models.Skill;
import seng201.team0.models.UpgradeItem;

import static org.junit.jupiter.api.Assertions.*;

public class AdventurerTests {

    private Adventurer adventurer;

    // helper so every test starts with known stats
    private Adventurer makeAdventurer() {
        return new Adventurer("Test", 60, 50, 20, 10, "Warrior");
    }

    @BeforeEach
    void setUp() {
        adventurer = makeAdventurer();
    }

    @Test
    void typeIsSetCorrectly() {
        assertEquals("Warrior", adventurer.getType());
    }

    @Test
    void differentTypesSetCorrectly() {
        assertEquals("Mage", new Adventurer("x", 40, 90, 20, 10, "Mage").getType());
        assertEquals("Ranger", new Adventurer("x", 50, 70, 20, 10, "Ranger").getType());
    }

    @Test
    void decreaseStaminaClampsAtZero() {
        adventurer.decreaseStamina(10000);
        assertEquals(0, adventurer.getStamina());
    }

    @Test
    void increaseStaminaClampsAtMax() {
        int max = adventurer.getMaxStamina();
        adventurer.decreaseStamina(20);
        adventurer.increaseStamina(10000);
        assertEquals(max, adventurer.getStamina());
    }

    @Test
    void fullyRestRestoresStamina() {
        int max = adventurer.getMaxStamina();
        adventurer.decreaseStamina(40);
        adventurer.fullyRest();
        assertEquals(max, adventurer.getStamina());
    }

    @Test
    void increaseMaxStaminaIncreasesBoth() {
        int oldMax = adventurer.getMaxStamina();
        int oldStam = adventurer.getStamina();
        adventurer.increaseMaxStamina(15);
        assertEquals(oldMax + 15, adventurer.getMaxStamina());
        assertEquals(oldStam + 15, adventurer.getStamina());
    }

    @Test
    void perceptionMovesUpAndDown() {
        int p = adventurer.getPerception();
        adventurer.increasePerception(7);
        assertEquals(p + 7, adventurer.getPerception());
        adventurer.decreasePerception(3);
        assertEquals(p + 4, adventurer.getPerception());
    }

    @Test
    void decreasePerceptionClampsAtZero() {
        adventurer.decreasePerception(99999);
        assertEquals(0, adventurer.getPerception());
    }

    @Test
    void learnSkillAppliesBonuses() {
        int oldMax = adventurer.getMaxStamina();
        int oldPerc = adventurer.getPerception();
        adventurer.learnSkill(new Skill("Grit", "tough", 10, 5));
        assertEquals(oldMax + 10, adventurer.getMaxStamina());
        assertEquals(oldPerc + 5, adventurer.getPerception());
        assertTrue(adventurer.getSkills().stream().anyMatch(s -> s.getName().equals("Grit")));
    }

    @Test
    void expeditionsInARowIncrementsAndResets() {
        assertEquals(0, adventurer.getExpeditionsInARow());
        adventurer.incrementExpeditionsInARow();
        adventurer.incrementExpeditionsInARow();
        assertEquals(2, adventurer.getExpeditionsInARow());
        adventurer.resetExpeditionsInARow();
        assertEquals(0, adventurer.getExpeditionsInARow());
    }

    @Test
    void equipAndUnequipUpdatesEquippedItemAndStats() {
        int oldPerception = adventurer.getPerception();
        EquipableItem lens = new EquipableItem("Lens", 10, "x", "perception", 5);
        adventurer.equip(lens);
        assertSame(lens, adventurer.getEquippedItem());
        assertEquals(oldPerception + 5, adventurer.getPerception());
        adventurer.equip(null);
        assertNull(adventurer.getEquippedItem());
        assertEquals(oldPerception, adventurer.getPerception());
    }

    @Test
    void equipReplacesPreviousItem() {
        EquipableItem boots = new EquipableItem("Boots", 10, "x", "stamina", 5);
        EquipableItem armor = new EquipableItem("Armor", 20, "x", "stamina", 15);
        adventurer.equip(boots);
        adventurer.equip(armor);
        assertSame(armor, adventurer.getEquippedItem());
    }

    @Test
    void upgradeItemIsConsumed() {
        UpgradeItem tonic = new UpgradeItem("Tonic", 10, "x", "stamina", 10);
        assertTrue(tonic.isConsumedOnUse());
        int oldMax = adventurer.getMaxStamina();
        tonic.applyTo(adventurer);
        assertEquals(oldMax + 10, adventurer.getMaxStamina());
    }

    @Test
    void descriptionIncludesType() {
        assertNotNull(adventurer.getDescription());
        assertTrue(adventurer.getDescription().contains("Warrior"));
    }
}