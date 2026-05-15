package seng201.team0.factories;

import seng201.team0.models.Adventurer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdventurerFactory {
    private static final List<Adventurer> POOL = List.of(
            new Adventurer("Simon", 70, 40, 18, 8,  "Archer"),
            new Adventurer("Bob", 40, 70, 18, 8,  "Brute"),
            new Adventurer("Matt", 80, 25, 18, 8,  "Soldier"),
            new Adventurer("Sarah", 35, 80, 19, 9,  "Guard"),
            new Adventurer("Scarlett", 75, 30, 20, 8,  "Ranger"),

            new Adventurer("Ben", 50, 70, 24, 10, "Mage"),
            new Adventurer("Will", 65, 55, 24, 10, "Assassin"),
            new Adventurer("Tim", 45, 75, 25, 11, "Knight"),
            new Adventurer("Lucy", 70, 50, 25, 10, "Gladiator"),
            new Adventurer("Sam", 55, 65, 26, 11, "Samurai"),
            new Adventurer("Rigby", 80, 35, 26, 10, "Spartan"),
            new Adventurer("Olivia", 40, 85, 27, 11, "Wizard"),

            new Adventurer("Aiden", 60, 75, 30, 12, "King"),
            new Adventurer("Paul", 85, 50, 32, 13, "General"),
            new Adventurer("Michelle", 50, 90, 33, 13, "Queen"),
            new Adventurer("Brooke", 75, 65, 33, 13, "Berserker"),
            new Adventurer("Georgia", 65, 75, 34, 14, "Pyromaniac"),
            new Adventurer("Dylan", 70, 75, 35, 14, "Paladin"),
            new Adventurer("Annabelle", 90, 55, 36, 14, "Legionary"),
            new Adventurer("Charlie", 90, 95, 38, 15, "Frog")
    );

    public static List<Adventurer> getRandom(int count) {
        List<Adventurer> shuffled = new ArrayList<>(POOL);
        Collections.shuffle(shuffled);
        return shuffled.subList(0, count);
    }
}