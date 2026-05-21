package seng201.team0.unittests.gui;

import seng201.team0.GameEnvironment;
import seng201.team0.gui.ScreenNavigator;

/**
 * Test double for {@link ScreenNavigator} that records which launches
 * were called rather than rendering anything. Bypasses the JavaFX
 * Stage setup so it can be used in plain JUnit tests.
 */
public class TestNavigator extends ScreenNavigator {

    /** name of the last screen launched, for assertions */
    public String lastLaunched = null;

    /** Constructs a test navigator. */
    public TestNavigator() {
        super();
    }

    @Override
    public void launchSetupScreen(GameEnvironment gameEnvironment) { lastLaunched = "setup"; }

    @Override
    public void launchMenuScreenController(GameEnvironment gameEnvironment) { lastLaunched = "menu"; }

    @Override
    public void launchGuildHallScreenController(GameEnvironment gameEnvironment) { lastLaunched = "guildhall"; }

    @Override
    public void launchMarketScreenController(GameEnvironment gameEnvironment) { lastLaunched = "market"; }

    @Override
    public void launchRecruitmentScreenController(GameEnvironment gameEnvironment) { lastLaunched = "recruitment"; }

    @Override
    public void launchExpeditionSelectScreenController(GameEnvironment gameEnvironment) { lastLaunched = "expSelect"; }

    @Override
    public void launchExpeditionInProgressScreen(GameEnvironment gameEnvironment) { lastLaunched = "expInProgress"; }

    @Override
    public void launchPostExpeditionScreen(GameEnvironment gameEnvironment) { lastLaunched = "postExp"; }

    //@Override
    public void launchEndGameScreen(GameEnvironment gameEnvironment) { lastLaunched = "endGame"; }
}
