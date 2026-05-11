package seng201.team0.models;

/**
 * Enum for providing the parameters on how certain things
 * change with respect to the different difficulties available
 */

public enum Difficulty {

    EASY("Easy", 120, 1.0, 1.0, 0.2, 0.8),

    NORMAL("Normal", 80, 1.0, 1.25, 0.3, 1.0),

    HARD("Hard", 50, 1.25, 1.75, 0.4, 1.1);

    /**
     * A series of setters, initializing the variables that apply to difficulty
     */
    private final String displayName;
    private final int startingGold;
    private final double rewardMultiplier;
    private final double scoreMultiplier;
    private final double randomEventChance;
    private final double costMultiplier;

    Difficulty(String displayName,
               int startingGold,
               double rewardMultiplier,
               double scoreMultiplier,
               double randomEventChance,
               double costMultiplier) {
        this.displayName = displayName;
        this.startingGold = startingGold;
        this.rewardMultiplier = rewardMultiplier;
        this.scoreMultiplier = scoreMultiplier;
        this.randomEventChance = randomEventChance;
        this.costMultiplier = costMultiplier;
    }

    /**
     * A series of getters which provide external classes with information
     * on the difficulty chosen by the user
     * @return (one of) displayName (Name of difficulty),
     *                  startingGold (how much gold the player starts with)
     *                  rewardMultiplier (how much the given reward changes)
     *                  scoreMultiplier (how much the given score changes)
     *                  randomEventChance (chance of a pop-up when in the guildhall)
     */

    public String getDisplayName() {
        return displayName;
    }
    public int getStartingGold() {
        return startingGold;
    }
    public double getRewardMultiplier() {
        return rewardMultiplier;
    }
    public double getScoreMultiplier() {
        return scoreMultiplier;
    }
    public double getRandomEventChance() {
        return randomEventChance;
    }
    public double getCostMultiplier() {
        return costMultiplier;
    }


}
