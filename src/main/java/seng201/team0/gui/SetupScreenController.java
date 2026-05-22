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
 * Controller for the game setup screen. Allows the player to enter a guild
 * name, choose a difficulty, set the expedition count, and select three
 * starting adventurers before starting the game.
 */
public class SetupScreenController extends ScreenController {

    @FXML private TextField guildNameField;
    @FXML private Slider expeditionCountSlider;
    @FXML private Label expeditionCountLabel;

    @FXML private Button easyBtn;
    @FXML private Button normalBtn;
    @FXML private Button hardBtn;

    @FXML private Button adventurer1Btn;
    @FXML private Button adventurer2Btn;
    @FXML private Button adventurer3Btn;
    @FXML private Button adventurer4Btn;
    @FXML private Button adventurer5Btn;

    @FXML private Label nameLabel;
    @FXML private Label staminaLabel;
    @FXML private Label perceptionLabel;
    @FXML private Label payLabel;

    @FXML private Button selectedAdventurer1Btn;
    @FXML private Button selectedAdventurer2Btn;
    @FXML private Button selectedAdventurer3Btn;

    @FXML private Button startGameBtn;

    private int selectedAdventurerIndex= -1;
    private final Adventurer[] selectedAdventurers= new Adventurer[3];
    private SetupModel setupModel;
    private SetupService setupService;

    /**
     * Constructs the controller.
     * @param gameEnvironment the shared game environment
     */
    protected SetupScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Initializes the setup model and service, wires up all button and
     * listener handlers, and populates the available adventurer buttons.
     */
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
        guildNameField.textProperty().addListener((observable, oldValue, newValue) ->
                setupModel.setGuildName(newValue)
        );

        startGameBtn.setOnAction(e -> onStartGameBtnClicked());
    }

    /**
     * Validates the setup and starts the game. Shows a validation error
     * dialog if the setup is incomplete or invalid.
     */
    private void onStartGameBtnClicked() {
        try {
            setupService.completeSetup(selectedAdventurers);
            getGameEnvironment().onSetupComplete(setupModel);
        } catch (SetupValidationException e) {
            showValidationError(e.getMessage());
        }
    }

    /**
     * Highlights the selected difficulty button and updates the difficulty
     * on the setup model.
     * @param difficultyBtns the list of difficulty buttons
     * @param buttonIndex zero-based index of the clicked difficulty button
     */
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

    /**
     * Marks the clicked adventurer as the current selection and updates the
     * stats panel. Highlights the selected button.
     * @param availableAdventurerBtns the list of available adventurer buttons
     * @param index zero-based index of the clicked adventurer
     */
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

    /**
     * Places the currently selected adventurer into the given party slot and
     * updates the slot button label. Does nothing if no adventurer is selected.
     * @param selectedAdventurerBtns the list of selected-party slot buttons
     * @param slot zero-based index of the target party slot
     */
    private void onSelectedButtonClicked(List<Button> selectedAdventurerBtns, int slot) {
        if (selectedAdventurerIndex == -1) return;

        boolean placed = setupService.selectAdventurer(selectedAdventurers, selectedAdventurerIndex, slot);

        if (placed) {
            selectedAdventurerBtns.get(slot).setText(selectedAdventurers[slot].getType());
        }
    }

    /**
     * Updates the stats panel to display the given adventurer's attributes.
     * @param adventurer the adventurer whose stats should be displayed
     */
    private void updateStats(Adventurer adventurer) {
        nameLabel.setText("Name: " + adventurer.getName());
        staminaLabel.setText("Stamina: " + adventurer.getStamina());
        perceptionLabel.setText("Perception: " + adventurer.getPerception());
        payLabel.setText("Pay: " + adventurer.getPay());
    }

    /**
     * Displays a modal error dialog describing a setup validation failure.
     *
     * @param message the validation error message to display
     */
    private void showValidationError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Cannot Start Game");
        alert.setHeaderText("Please fix the following:");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /** @return the location and filename of the fxml file being retrieved */
    @Override
    protected String getFxmlFile() {
        return "/fxml/gameSetup.fxml";
    }

    /** @return the screen title string */
    @Override
    protected String getTitle() {
        return "Game Setup";
    }
}