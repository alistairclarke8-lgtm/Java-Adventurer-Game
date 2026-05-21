package seng201.team0.services;

import seng201.team0.GameEnvironment;
import seng201.team0.exception.GuildFullException;
import seng201.team0.exception.InsufficientGoldException;
import seng201.team0.factories.AdventurerFactory;
import seng201.team0.models.Adventurer;
import seng201.team0.models.Guildhall;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecruitmentService {


    /** The minimum number of recruits shown per round. */
    public static final int MIN_POOL = 3;

    /** The maximum number of recruits shown per round. */
    public static final int MAX_POOL = 5;

    private final GameEnvironment gameEnvironment;
    private final Random random;
    private final List<Adventurer> availableRecruits = new ArrayList<>();

    /**
     * Constructs a RecruitmentService with a default.
     * @param gameEnvironment the game environment to operate on
     */
    public RecruitmentService(GameEnvironment gameEnvironment) {
        this(gameEnvironment, new Random());
    }

    public RecruitmentService(GameEnvironment gameEnvironment, Random random) {
        this.gameEnvironment = gameEnvironment;
        this.random = random;
    }

    /**
     * refreshes the available adventurers in the recruitment screen
     */
    public void refreshPool() {
        availableRecruits.clear();
        int size = MIN_POOL + random.nextInt(MAX_POOL - MIN_POOL + 1);
        availableRecruits.addAll(AdventurerFactory.getRandom(size));
    }

    /**
     *
     * @param adventurer the adventurer in question being purchased
     * @throws InsufficientGoldException if the player does not have enough gold to
     *                                   purchase the adventurer,
     * @throws GuildFullException if the player's guild is full of adventurers
     */
    public void hire(Adventurer adventurer)
            throws InsufficientGoldException, GuildFullException {
        if (!availableRecruits.contains(adventurer)) {
            throw new IllegalArgumentException("Adventurer is not in the recruitment pool.");
        }
        int price = getHirePrice(adventurer);
        if (gameEnvironment.getGuildhall().getReservedAdventurers().size()
                >= Guildhall.RESERVES_CAPACITY) {
            throw new GuildFullException("Reserves are full (max "
                    + Guildhall.RESERVES_CAPACITY + ").");
        }
        gameEnvironment.spendGold(price);
        gameEnvironment.getGuildHallService().makeReservedAdventurer(adventurer);
        availableRecruits.remove(adventurer);
    }

    /**
     *
     * @param adventurer the adventurer in question
     * @return the cost to hire an adventurer weighted to the
     *         difficulty of the game environment
     */
    public int getHirePrice(Adventurer adventurer) {
        double multiplier = gameEnvironment.getDifficulty().getCostMultiplier();
        return (int) (adventurer.getHiringCost() * multiplier);
    }

    public int getCheapestHireCost() {
        int cheapest = -1;
        for (Adventurer adventurer : availableRecruits) {
            int price = getHirePrice(adventurer);
            if (cheapest < 0 || price < cheapest) cheapest = price;
        }
        return cheapest;
    }

    /** @return the current list of hireable adventurers */
    public List<Adventurer> getAvailableRecruits() { return availableRecruits; }

}
