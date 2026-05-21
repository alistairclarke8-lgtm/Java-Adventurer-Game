package seng201.team0.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import seng201.team0.GameEnvironment;

/**
 * Controller for the end of the game summary screen
 * Shows the guild name, expeditions planned and completed,
 * their final score and gold count
 */
public class EndGameScreenController extends ScreenController {

    @FXML private Label headerLabel;
    @FXML private Label guildNameLabel;
    @FXML private Label outcomeLabel;
    @FXML private Label expeditionsPlannedLabel;
    @FXML private Label expeditionsCompletedLabel;
    @FXML private Label goldLabel;
    @FXML private Label scoreLabel;
    @FXML private Button quitBtn;

    /**
     * Constructs the end-game screen controller.
     * @param gameEnvironment the game environment
     */
    protected EndGameScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Populates the labels from the game environment and wires the quit
     * button.
     */
    public void initialize() {
        GameEnvironment environment = getGameEnvironment();
        boolean won = environment.isWon();
        headerLabel.setText(won ? "Victory!" : "Game Over");
        guildNameLabel.setText("Guild: " + environment.getGuildName());
        if (won) {
            outcomeLabel.setText("Your guild completed all of its expeditions. Well done!");
        } else {
            outcomeLabel.setText("Your guild ran out of adventurers and gold. Better luck next time.");
        }
        expeditionsPlannedLabel.setText("Expeditions planned: " + environment.getNumExpeditions());
        expeditionsCompletedLabel.setText("Expeditions completed: " + environment.getExpeditionsCompleted());
        goldLabel.setText("Final gold: " + environment.getGold() + "g");
        scoreLabel.setText("Final score: " + environment.getScore());
        quitBtn.setOnAction(actionEvent -> Platform.exit());
    }




    @Override
    protected String getFxmlFile() {
        return "";
    }

    @Override
    protected String getTitle() {
        return "";
    }


}
