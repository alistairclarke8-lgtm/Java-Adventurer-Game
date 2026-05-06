package seng201.team0.unittests.services;

import org.junit.jupiter.api.Test;
import seng201.team0.models.Skill;
import seng201.team0.models.Swordsman;

import static org.junit.jupiter.api.Assertions.*;

public class AdventurerSkillsTest {

    @Test
    void learnSkillRecordsTheSkill() {
        Swordsman s = new Swordsman();
        s.learnSkill(new Skill("Eagle-eye", "x", 0, 20));
        assertEquals(1, s.getSkills().size());
        assertEquals("Eagle-eye", s.getSkills().getFirst().getName());
    }

    @Test
    void learnSkillChangesPerception() {
        Swordsman s = new Swordsman();
        int before = s.getPerception();
        s.learnSkill(new Skill("Eagle-eye", "x", 0, 20));
        int after = s.getPerception();
        assertEquals(before + 20, after);
    }

    @Test
    void multipleSkillsLearned() {
        Swordsman s = new Swordsman();
        s.learnSkill(new Skill("Eagle-eye", "x", 0, 20));
        s.learnSkill(new Skill("Twinkle-Toes", "y", 15, 5));
        assertEquals();
    }
}
