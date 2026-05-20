package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import seng201.team0.GameEnvironment;
import seng201.team0.models.Adventurer;
import seng201.team0.models.Expedition;

import java.util.List;

/**
 * Controller for the expedition selection screen.
 * Displays three expedition options and the current party,
 * then navigates to the in-progress screen when the player departs.
 */
public class ExpeditionSelectScreenController extends ScreenController {

    @FXML private Label expeditionCountLabel;
    @FXML private Label goldLabel;

    @FXML private Button expeditionBtn1;
    @FXML private Button expeditionBtn2;
    @FXML private Button expeditionBtn3;

    @FXML private Button partybtn1;
    @FXML private Button partybtn2;
    @FXML private Button partybtn3;
    @FXML private Button partybtn4;
    @FXML private Button partybtn5;

    @FXML private Button backBtn;
    @FXML private Button departBtn;

    private List<Expedition> expeditions;
    private Expedition selectedExpedition;

    private Button[] expeditionButtons;
    private Button[] partyButtons;

    /**
     * Constructs the controller.
     * @param gameEnvironment the game environment
     */
    protected ExpeditionSelectScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    @Override
    protected String getFxmlFile() { return "/fxml/ExpeditionSelection.fxml"; }

    @Override
    protected String getTitle() { return "Choose Expedition"; }

    @FXML
    public void initialize() {
        expeditionButtons = new Button[]{expeditionBtn1, expeditionBtn2, expeditionBtn3};
        partyButtons = new Button[]{partybtn1, partybtn2, partybtn3, partybtn4, partybtn5};

        getGameEnvironment().getExpeditionService().refreshExpeditions();
        expeditions = getGameEnvironment().getExpeditionService().getAvailableExpeditions();

        populateTopBar();
        populateExpeditionButtons();
        populatePartyButtons();

        departBtn.setDisable(true);
        backBtn.setOnAction(e -> getGameEnvironment().navigateToMenu());
        departBtn.setOnAction(e -> depart());
    }

    /**
     * Populates the expedition count and gold labels.
     */
    private void populateTopBar() {
        expeditionCountLabel.setText(
                "Expedition " + getGameEnvironment().getCurrentExpedition() + " of " + getGameEnvironment().getNumExpeditions()
        );
        goldLabel.setText("Gold: " + getGameEnvironment().getGold() + "g");
    }

    /**
     * Populates each expedition button with the expedition's name, description,
     * and pay modifier. Clicking a button selects that expedition.
     */
    private void populateExpeditionButtons() {
        for (int i = 0; i < expeditionButtons.length; i++) {
            Expedition expedition = expeditions.get(i);
            Button button = expeditionButtons[i];

            String payText = expedition.getPayModifier() > 1.0
                    ? "\n[" + (int)(expedition.getPayModifier() * 100) + "% pay]"
                    : "";

            button.setText(expedition.getName() + "\n" + expedition.getDescription() + payText);
            button.setWrapText(true);

            int index = i;
            button.setOnAction(e -> selectExpedition(index));
        }
    }

    /**
     * Populates party buttons with each adventurer's name and stats.
     * Hides slots with no adventurer.
     */
    private void populatePartyButtons() {
        List<Adventurer> party = getGameEnvironment().getGuildhall().getMainPartyAdventurers();
        for (int i = 0; i < partyButtons.length; i++) {
            Button button = partyButtons[i];
            if (i < party.size()) {
                Adventurer adventurer = party.get(i);
                button.setText(adventurer.getName() + "\nSTM " + adventurer.getStamina()
                        + "  PER " + adventurer.getPerception());
                button.setWrapText(true);
                button.setVisible(true);
                button.setManaged(true);
            } else {
                button.setVisible(false);
                button.setManaged(false);
            }
        }
    }

    /**
     * Handles selecting an expedition option.
     * Highlights the selected button and enables the depart button.
     * @param index the index of the chosen expedition
     */
    private void selectExpedition(int index) {
        selectedExpedition = expeditions.get(index);
        for (Button button : expeditionButtons) {
            button.setStyle("");
        }
        expeditionButtons[index].setStyle("-fx-border-color: #185FA5; -fx-border-width: 2;");
        departBtn.setDisable(false);
    }

    /**
     * Starts the selected expedition and navigates to the in-progress screen.
     */
    private void depart() {
        getGameEnvironment().getExpeditionService().startExpedition(selectedExpedition);
//        getGameEnvironment().navigateToExpedition();
    }
}