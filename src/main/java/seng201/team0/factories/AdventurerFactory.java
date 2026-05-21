package seng201.team0.factories;

import seng201.team0.models.Adventurer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Factory for generating the available adventurers in each game
 */
public class AdventurerFactory {
    private static final List<Adventurer> POOL = List.of(
            new Adventurer("Simon", 70, 40, 18, 3,  "Archer"),
            new Adventurer("Bob", 40, 70, 18, 3,  "Brute"),
            new Adventurer("Matt", 80, 25, 18, 4,  "Soldier"),
            new Adventurer("Sarah", 35, 80, 19, 4,  "Guard"),
            new Adventurer("Scarlett", 75, 30, 20, 3,  "Ranger"),

            new Adventurer("Ben", 50, 70, 24, 5, "Mage"),
            new Adventurer("Will", 65, 55, 24, 5, "Assassin"),
            new Adventurer("Tim", 45, 75, 25, 6, "Knight"),
            new Adventurer("Lucy", 70, 50, 25, 6, "Gladiator"),
            new Adventurer("Sam", 55, 65, 26, 7, "Samurai"),
            new Adventurer("Rigby", 80, 35, 26, 7, "Spartan"),
            new Adventurer("Olivia", 40, 85, 27, 7, "Wizard"),

            new Adventurer("Aiden", 60, 75, 30, 8, "King"),
            new Adventurer("Paul", 85, 50, 32, 8, "General"),
            new Adventurer("Michelle", 50, 90, 33, 8, "Queen"),
            new Adventurer("Brooke", 75, 65, 33, 9, "Berserker"),
            new Adventurer("Georgia", 65, 75, 34, 9, "Pyromaniac"),
            new Adventurer("Dylan", 70, 75, 35, 9, "Paladin"),
            new Adventurer("Annabelle", 90, 55, 36, 9, "Legionary"),
            new Adventurer("Charlie", 90, 95, 38, 10, "Frog")
    );

    /**
     * Adventurer pool is shuffled every method call.
     * This is in order ot receive random adventurers each call.
     * @param count the number of adventurers need to return
     * @return a shuffled list of adventurers of length (count)
     */
    public static List<Adventurer> getRandom(int count) {
        List<Adventurer> shuffled = new ArrayList<>(POOL);
        Collections.shuffle(shuffled);
        return shuffled.subList(0, count);
    }
}