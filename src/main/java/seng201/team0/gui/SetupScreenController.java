package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import seng201.team0.GameEnvironment;
import seng201.team0.exception.SetupValidationException;
import seng201.team0.models.Adventurer;
import seng201.team0.models.Difficulty;
import seng201.team0.models.SetupModel;
import seng201.team0.services.SetupService;

import java.util.List;

/**
 * Controller for the Game Setup Page
 */
public class SetupScreenController extends ScreenController {

    @FXML
    private TextField guildnameField;
    @FXML
    private Slider expeditionCountSlider;
    @FXML
    private Label expeditionCountLabel;

    @FXML
    private Button easyBtn;
    @FXML
    private Button normalBtn;
    @FXML
    private Button hardBtn;

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

    @FXML
    private Label nameLabel;
    @FXML
    private Label staminaLabel;
    @FXML
    private Label perceptionLabel;
    @FXML
    private Label payLabel;

    @FXML
    private Button selectedAdventurer1Btn;
    @FXML
    private Button selectedAdventurer2Btn;
    @FXML
    private Button selectedAdventurer3Btn;

    @FXML
    private Button startGameBtn;

    private int selectedAdventurerIndex= -1;
    private final Adventurer[] selectedAdventurers= new Adventurer[3];
    private SetupModel setupModel;
    private SetupService setupService;

    public void initialize() {
        setupModel = new SetupModel();
        setupService = new SetupService(setupModel);
        List<Button> availableAdventurerBtns = List.of(adventurer1Btn,adventurer2Btn,adventurer3Btn,adventurer4Btn,adventurer5Btn);
        List<Button> selectedAdventurerBtns = List.of(selectedAdventurer1Btn,selectedAdventurer2Btn,selectedAdventurer3Btn);
        List<Button> difficultyBtns = List.of(easyBtn,normalBtn,hardBtn);
        for (int i=0; i < availableAdventurerBtns.size(); i++) {
            int index = i;
            availableAdventurerBtns.get(i).setText(setupService.getDefaultAdventurer(i).getType());
            availableAdventurerBtns.get(i).setOnAction(e -> onAvailableAdventurerBtnClicked(availableAdventurerBtns, index));
        }

        for (int i=0; i< selectedAdventurerBtns.size(); i++) {
            int index=i;
            selectedAdventurerBtns.get(i).setOnAction(actionEvent -> onSelectedButtonClicked(selectedAdventurerBtns, index));
        }
        for (int i=0;i<difficultyBtns.size();i++) {
            int buttonIndex = i;
            difficultyBtns.get(i).setOnAction(actionEvent -> onDifficultyBtnClicked(difficultyBtns,buttonIndex));
        }
        expeditionCountSlider.valueProperty().addListener((observable, oldValue, newValue) ->{
            int count = newValue.intValue();
            setupModel.setExpeditionCount(count);
            expeditionCountLabel.setText(" " + count);
        });
        guildnameField.textProperty().addListener((observable, oldValue, newValue) ->
                setupModel.setGuildName(newValue)
        );

        startGameBtn.setOnAction(e -> onStartGameBtnClicked());
    }

    private void onStartGameBtnClicked() {
        try {
            setupService.completeSetup(selectedAdventurers);
            getGameEnvironment().onSetupComplete(setupModel);
        } catch (SetupValidationException e) {
            showValidationError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            setupModel.setDifficulty(Difficulty.EASY);
        } else if (difficultyBtns.get(buttonIndex) == normalBtn) {
            setupModel.setDifficulty(Difficulty.NORMAL);
        } else if (difficultyBtns.get(buttonIndex) == hardBtn) {
            setupModel.setDifficulty(Difficulty.HARD);
        }
    }

    private void onAvailableAdventurerBtnClicked(List<Button> availableAdventurerBtns, int index) {
        selectedAdventurerIndex = index;
        updateStats(setupService.getDefaultAdventurer(index));

        availableAdventurerBtns.forEach(btn -> {
            if (btn == availableAdventurerBtns.get(index)) {
                btn.setStyle("-fx-background-color: #b3b3b3; -fx-background-radius: 5;");
            } else {
                btn.setStyle("");
            }
        });
    }

    private void onSelectedButtonClicked(List<Button> selectedAdventurerBtns, int slot) {
        if (selectedAdventurerIndex == -1) return;

        boolean placed = setupService.selectAdventurer(selectedAdventurers, selectedAdventurerIndex, slot);

        if (placed) {
            selectedAdventurerBtns.get(slot).setText(selectedAdventurers[slot].getType());
        }
    }

    private void updateStats(Adventurer adventurer) {
        nameLabel.setText("Name: " + adventurer.getName());
        staminaLabel.setText("Stamina: " + adventurer.getStamina());
        perceptionLabel.setText("Perception: " + adventurer.getPerception());
        payLabel.setText("Pay: " + adventurer.getPay());
    }

    private void showValidationError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Cannot Start Game");
        alert.setHeaderText("Please fix the following:");
        alert.setContentText(message);
        alert.showAndWait();
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