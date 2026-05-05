package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import seng201.team0.models.Adventurer;

import java.util.ArrayList;
import java.util.List;

public class SetupScreenController extends ScreenController {

    @FXML
    private TextField guildnameField;
    @FXML
    private Slider expeditionCountSlider;
    @FXML
    private Label expeditionCountLabel;
    //    difficulty Buttons
    @FXML
    private ToggleButton easyBtn;
    @FXML
    private ToggleButton normalBtn;
    @FXML
    private ToggleButton hardBtn;
//      all starting adventurer buttons
    @FXML
    private Button adventurer1Btn;
    @FXML
    private Button adventurer2Btn;
    @FXML
    private Button adventurer3Btn;
    @FXML
    private Button adventurer4Btn;
    @FXML
    private Button adventurer5Btn;
//    stat labels
    @FXML
    private Label staminaLabel;
    @FXML
    private Label perceptionLabel;
    @FXML
    private Label payLabel;
//    buttons to contain selected adventurers
    @FXML
    private Button selectedAdventurer1;
    @FXML
    private Button selectedAdventurer2;
    @FXML
    private Button selectedAdventurer3;

    @FXML
    private Button startGameBtn;

    private int selectedAdventurerIndex= -1;
    private final Adventurer[] selectedAdventurers= new Adventurer[3];

    public void initialise() {
        List<Button> availableAdventurerbtns = List.of(adventurer1Btn,adventurer2Btn,adventurer3Btn,adventurer4Btn,adventurer5Btn);
        List<Button> selectedAdventurerbtns = List.of(selectedAdventurer1,selectedAdventurer2,selectedAdventurer3);
        for (int i; i < availableAdventurers.size(); i++) {
            int buttonIndex= i;
            availableAdventurerbtns.get(i).setOnAction(actionEvent -> onAvailableAdventurerBtnClicked(availableAdventurerbtns, buttonIndex);
        }
        }
    public void onAvailableAdventurerBtnClicked(List<Button> selectedAdventurerbtns, int buttonIndex) {
        updateStats(getGameEnvironment());

    }

    public void updateStats (Adventurer adventurer) {
        staminaLabel.setText(String.valueOf(adventurer.getStamina()));
        perceptionLabel.setText(String.valueOf(adventurer.getPerception()));
        payLabel.setText(String.valueOf(adventurer.getPay()));

    }










    @Override
    protected String getFxmlFile() {
        return "/fxml/setup_screen.fxml";
    }

    @Override
    protected String getTitle() {
        return "Game Setup";
    }
}
