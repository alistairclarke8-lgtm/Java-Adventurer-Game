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
 * Service class that handles adventurer recruitment logic.
 *
 * Each round, a pool of randomly generated Adventurer candidates is
 * made available via refreshPool(). The player can inspect the pool,
 * check hire prices adjusted for difficulty, and hire adventurers into the
 * guildhall reserves, provided they have enough gold and reserve space.
 *
 * All gold and guildhall mutations are delegated to GameEnvironment
 * so that game state remains consistent across services.
 */
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

    /**
     * Constructs a RecruitmentService with an explicit Random instance.
     *
     * Use this constructor in tests to pass a seeded Random and get
     * deterministic recruit generation.
     *
     * @param gameEnvironment the active GameEnvironment; must not be null
     * @param random the random instance to use for pool generation; must not be null
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
     * Hires an adventurer from the current pool into the guildhall reserves.
     * The hire price is calculated via getHirePrice(Adventurer), which
     * applies the current difficulty cost multiplier. On success, the gold is
     * deducted, the adventurer is moved to the guildhall reserves via
     * makeReservedAdventurer(Adventurer) in GuildHallService,
     * and removed from the available pool.
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
