package seng201.team0.services;

import seng201.team0.models.Adventurer;
import seng201.team0.models.Difficulty;

import java.util.ArrayList;

public class GameSetup {
    private final int min_length_guild_name = 3;
    private final int max_length_guild_name = 15;
    private final int min_length_expedition_count = 5;
    private final int max_length_expedition_count = 15;

    private String guildName;
    private int numOfExpeditions;
    private Difficulty difficulty;
    private ArrayList<Adventurer> startingAdventueres = new ArrayList<>();

    public GameSetup() {}

    public String createGuildName(String name) {
        if ( name.length() > min_length_guild_name | name.length() < max_length_guild_name ) {
            this.guildName = name;
        }

    }
}
