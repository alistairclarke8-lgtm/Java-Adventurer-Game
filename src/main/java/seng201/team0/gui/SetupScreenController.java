package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import seng201.team0.GameEnvironment;
import seng201.team0.models.Adventurer;

import java.util.Arrays;
import java.util.List;

/**
 * Controller for the setup screen. allows pl
 */
public class SetupScreenController extends ScreenController {

    @FXML
    private TextField guildnameField;
    @FXML
    private Slider expeditionCountSlider;
    @FXML
    private Label expeditionCountLabel;
    //    difficulty Buttons
    @FXML
    private Button easyBtn;
    @FXML
    private Button normalBtn;
    @FXML
    private Button hardBtn;
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
    private Label nameLabel;
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


    public void initialize() {
        List<Button> availableAdventurerBtns = List.of(adventurer1Btn,adventurer2Btn,adventurer3Btn,adventurer4Btn,adventurer5Btn);
        List<Button> selectedAdventurerBtns = List.of(selectedAdventurer1,selectedAdventurer2,selectedAdventurer3);
        for (int i=0; i < availableAdventurerBtns.size(); i++) {
            int buttonIndex= i;
            availableAdventurerBtns.get(i).setOnAction(actionEvent -> onAvailableAdventurerBtnClicked(availableAdventurerBtns, buttonIndex));
            availableAdventurerBtns.get(i).setText(getGameEnvironment().getDefaultAdventurers().get(i).getName());
        }
        startGameBtn.setOnAction(actionEvent -> onStartGameBtnClicked());

        for (int i=0; i< selectedAdventurerBtns.size(); i++) {
            int index=i;
            selectedAdventurerBtns.get(i).setOnAction(actionEvent -> onSelectedButtonClicked(selectedAdventurerBtns, index));
        }
        expeditionCountSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                onExpeditionCountUpdated(newValue.intValue()));
    }

    private void onExpeditionCountUpdated(int i) {
        getGameEnvironment().setNumExpeditions(i);
        expeditionCountLabel.setText(" "+getGameEnvironment().getNumExpeditions());
    }

    public void onSelectedButtonClicked(List<Button> selectedAdventurerBtns, int index) {
        GameEnvironment gameEnvironment = getGameEnvironment();
        if (selectedAdventurerIndex != -1) {
            Adventurer selectedAdventurer = gameEnvironment.getDefaultAdventurers().get(selectedAdventurerIndex);
            // Only add the selected rocket to the array of selected rockets if it isn't already in
            // the array.
            if (Arrays.stream(selectedAdventurers).noneMatch(adventurer -> adventurer == selectedAdventurer)) {
                selectedAdventurerBtns.get(index).setText(selectedAdventurer.getName());
                selectedAdventurers[index] = selectedAdventurer;
            }
        }
    }

    private void onAvailableAdventurerBtnClicked(List<Button> availableAdventurerbtns, int adventurerIndex) {
        selectedAdventurerIndex = adventurerIndex;
        updateStats(getGameEnvironment().getDefaultAdventurers().get(adventurerIndex));
        availableAdventurerbtns.forEach(button -> {
            if (button == availableAdventurerbtns.get(adventurerIndex)) {
                button.setStyle("-fx-background-color: #b3b3b3; -fx-background-radius: 5;");
            } else {
                button.setStyle("");
            }
        });

    }
    public void onStartGameBtnClicked() {
        getGameEnvironment().setGuildName(guildnameField.getText());
        System.out.println(getGameEnvironment().getGuildName());
    }

    public void updateStats (Adventurer adventurer) {
        nameLabel.setText("Name: " + adventurer.getName());
        staminaLabel.setText("Stamina: "+ adventurer.getStamina());
        perceptionLabel.setText("Perception: " + adventurer.getPerception());
        payLabel.setText("Pay: " + adventurer.getPay());

    }







    /**
     * Creates an instance of a ScreenController with the given {@link GameEnvironment}
     *
     * @param gameEnvironment
     */
    protected SetupScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/gameSetup.fxml";
    }

    @Override
    protected String getTitle() {
        return "Game Setup";
    }
}
