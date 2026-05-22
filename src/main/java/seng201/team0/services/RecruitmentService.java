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

/**
 * Handles adventurer recruitment logic. Each round a pool of randomly
 * generated candidates is made available. The player can hire adventurers
 * into the guildhall reserves subject to gold and capacity constraints.
 */
public class RecruitmentService {


    /** The minimum number of recruits shown per round. */
    public static final int minPool = 3;

    /** The maximum number of recruits shown per round. */
    public static final int maxPool = 5;

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

    /**
     * Constructs the service with an explicit Random parameter for deterministic
     * testing.
     * @param gameEnvironment the active game environment
     * @param random the random source used for pool generation
     */
    public RecruitmentService(GameEnvironment gameEnvironment, Random random) {
        this.gameEnvironment = gameEnvironment;
        this.random = random;
    }

    /**
     * Clears the current recruit pool and populates it with a fresh set of
     * randomly generated Adventurer candidates for the new round.
     */
    public void refreshPool() {
        availableRecruits.clear();
        availableRecruits.addAll(AdventurerFactory.getRandom(5));
    }

    /**
     * Hires the given adventurer into the guildhall reserves, deducting the
     * difficulty-adjusted hire price from the treasury and removing them from
     * the available pool.
     * @param adventurer the adventurer to hire
     * @throws InsufficientGoldException if the player cannot afford the hire price
     * @throws GuildFullException if the guildhall reserves are at capacity
     */
    public void hire(Adventurer adventurer) throws InsufficientGoldException, GuildFullException {
        if (!availableRecruits.contains(adventurer)) {
            throw new IllegalArgumentException("Adventurer is not in the recruitment pool.");
        }
        int price = getHirePrice(adventurer);
        if (gameEnvironment.getGuildhall().getReservedAdventurers().size()
                >= Guildhall.reservesCapacity) {
            throw new GuildFullException("Reserves are full (max "
                    + Guildhall.reservesCapacity + ").");
        }
        gameEnvironment.spendGold(price);
        gameEnvironment.getGuildHallService().makeReservedAdventurer(adventurer);
        availableRecruits.remove(adventurer);
    }

    /**
     * Returns the gold cost to hire the given adventurer under the current difficulty.
     * @param adventurer the adventurer in question
     * @return the cost to hire an adventurer weighted to the
     *         difficulty of the game environment
     */
    public int getHirePrice(Adventurer adventurer) {
        double multiplier = gameEnvironment.getDifficulty().getCostMultiplier();
        return (int) (adventurer.getHiringCost() * multiplier);
    }

    /**
     * Returns the lowest hire price among all adventurers currently in the pool.
     * @return the cheapest hire cost in the current pool of Adventurers
     */
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
