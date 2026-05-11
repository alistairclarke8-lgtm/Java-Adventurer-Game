package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import seng201.team0.GameEnvironment;


public class MenuScreenController extends ScreenController {

    @FXML
    private Label currentExpeditionLabel;
    @FXML
    private Label remainingExpeditionsLabel;
    @FXML
    private Label goldLabel;

    @FXML
    private Button guildHallBtn;
    @FXML
    private Button marketBtn;
    @FXML
    private Button recruitBtn;
    @FXML
    private Button startExpeditionBtn;

    public void initialize() {
        guildHallBtn.setOnAction(actionEvent -> onGuildHallBtnClicked());
        marketBtn.setOnAction(actionEvent -> onMarketBtnBtnClicked());
        recruitBtn.setOnAction(actionEvent -> onRecruitBtnBtnClicked());
        startExpeditionBtn.setOnAction(actionEvent -> onStartExpeditionBtnClicked());

        currentExpeditionLabel.setText("Current Expedition: " + getGameEnvironment().getCurrentExpedition());
        goldLabel.setText("Gold: " + getGameEnvironment().getGold());
        remainingExpeditionsLabel.setText("Expeditions Remaining: " + getGameEnvironment().getRemainingExpeditions());
    }

    private void onStartExpeditionBtnClicked() {
        getGameEnvironment().navigateToExpedition();
    }
    private void onRecruitBtnBtnClicked() {
        getGameEnvironment().navigateToRecruitment();
    }
    private void onMarketBtnBtnClicked() {
        getGameEnvironment().navigateToMarket();
    }
    private void onGuildHallBtnClicked() {
        getGameEnvironment().navigateToGuildHall();
    }

    /**
     * Creates an instance of a ScreenController with the given {@link GameEnvironment}
     *
     * @param gameEnvironment The game environment used by this ScreenController
     */
    protected MenuScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }


    @Override
    protected String getFxmlFile() {
        return "/fxml/menu.fxml";
    }

    @Override
    protected String getTitle() {
        return "Menu Screen";
    }
}
