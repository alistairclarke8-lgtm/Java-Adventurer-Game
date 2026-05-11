package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import seng201.team0.GameEnvironment;


public class MenuScreenController extends ScreenController {

    @FXML
    private Label currentExpeditionLabel;
    @FXML
    private Label expeditionsRemainingLabel;
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
