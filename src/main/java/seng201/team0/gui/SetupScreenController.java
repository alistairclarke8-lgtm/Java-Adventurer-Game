package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import seng201.team0.GameEnvironment;
import seng201.team0.models.Adventurer;
import seng201.team0.models.Difficulty;

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
        List<Button> difficultyBtns = List.of(easyBtn,normalBtn,hardBtn);
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
        for (int i=0;i<difficultyBtns.size();i++) {
            int buttonIndex = i;
            difficultyBtns.get(i).setOnAction(actionEvent -> onDifficultyBtnClicked(difficultyBtns,buttonIndex));
        }
        expeditionCountSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                onExpeditionCountUpdated(newValue.intValue()));
        guildnameField.textProperty().addListener((observable, oldValue, newValue) ->
                getGameEnvironment().setGuildName(newValue));
    }

    private void onDifficultyBtnClicked(List<Button> difficultyBtns, int buttonIndex) {
        difficultyBtns.forEach(button -> {
            if (button == difficultyBtns.get(buttonIndex)) {
                button.setStyle("-fx-background-color: #b3b3b3; -fx-background-radius: 5;");
            } else {
                button.setStyle("");
            }
        });
        if (difficultyBtns.get(buttonIndex) == easyBtn) {
            getGameEnvironment().setDifficulty(Difficulty.EASY);
        } else if (difficultyBtns.get(buttonIndex) == normalBtn) {
            getGameEnvironment().setDifficulty(Difficulty.NORMAL);
        } else if (difficultyBtns.get(buttonIndex) == hardBtn) {
            getGameEnvironment().setDifficulty(Difficulty.HARD);
        }
    }

    private void onExpeditionCountUpdated(int i) {
        getGameEnvironment().setNumExpeditions(i);
        expeditionCountLabel.setText(" "+getGameEnvironment().getNumExpeditions());
    }

    private void onSelectedButtonClicked(List<Button> selectedAdventurerBtns, int index) {
        GameEnvironment gameEnvironment = getGameEnvironment();
        if (selectedAdventurerIndex != -1) {
            Adventurer selectedAdventurer = gameEnvironment.getDefaultAdventurers().get(selectedAdventurerIndex);
            if (Arrays.stream(selectedAdventurers).noneMatch(adventurer -> adventurer == selectedAdventurer)) {
                selectedAdventurerBtns.get(index).setText(selectedAdventurer.getName());
                selectedAdventurers[index] = selectedAdventurer;
                gameEnvironment.setSelectedAdventurers(selectedAdventurer);
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

    private void onStartGameBtnClicked() {
        System.out.println(getGameEnvironment().getGuildName());
        System.out.println(getGameEnvironment().getNumExpeditions());
        System.out.println(getGameEnvironment().getSelectedAdventurers());
        System.out.println(getGameEnvironment().getDifficulty());
        getGameEnvironment().onSetupComplete();
    }

    private void updateStats (Adventurer adventurer) {
        nameLabel.setText("Name: " + adventurer.getName());
        staminaLabel.setText("Stamina: "+ adventurer.getStamina());
        perceptionLabel.setText("Perception: " + adventurer.getPerception());
        payLabel.setText("Pay: " + adventurer.getPay());
    }

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