package seng201.team0.models;

public enum Difficulty {

    EASY("Easy", 120, 1.0, 1.0, 0.10),

    NORMAL("Normal", 80, 1.0, 1.25, 0.15),

    HARD("Hard", 50, 1.25, 1.75, 0.25);

    private final String displayName;
    private final int startingGold;
    private final double rewardMultiplier;
    private final double scoreMultiplier;
    private final double randomEventChance;

    Difficulty(String displayName,
               int startingGold,
               double rewardMultiplier,
               double scoreMultiplier,
               double randomEventChance) {
        this.displayName = displayName;
        this.startingGold = startingGold;
        this.rewardMultiplier = rewardMultiplier;
        this.scoreMultiplier = scoreMultiplier;
        this.randomEventChance = randomEventChance;
    }

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

}
