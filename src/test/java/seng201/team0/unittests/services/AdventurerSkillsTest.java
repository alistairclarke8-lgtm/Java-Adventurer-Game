package seng201.team0.unittests.services;

import org.junit.jupiter.api.Test;
import seng201.team0.factories.AdventurerFactory;
import seng201.team0.models.Adventurer;
import seng201.team0.models.Skill;

import static org.junit.jupiter.api.Assertions.*;

public class AdventurerSkillsTest {

    @Test
    void learnSkillRecordsTheSkill() {
        Adventurer a = AdventurerFactory.getRandom(1).getFirst();
        a.learnSkill(new Skill("Eagle-eye", "x", 0, 20));
        assertEquals(1, a.getSkills().size());
        assertEquals("Eagle-eye", a.getSkills().getFirst().getName());
    }

    @Test
    void learnSkillChangesPerception() {
        Adventurer a = AdventurerFactory.getRandom(1).getFirst();
        int before = a.getPerception();
        a.learnSkill(new Skill("Eagle-eye", "x", 0, 20));
        assertEquals(before + 20, a.getPerception());
    }
}