package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import seng201.team0.GameEnvironment;
import seng201.team0.models.Adventurer;
import seng201.team0.models.Item;
import seng201.team0.services.ExpeditionService;

import java.util.List;

public class PostExpeditionScreenController extends ScreenController {

    @FXML private Label expeditionNameLabel;
    @FXML private Label numEventsLabel;
    @FXML private Label goldEarnedLabel;
    @FXML private Label payDeductedLabel;
    @FXML private Label netGainLabel;
    @FXML private Label itemsFoundLabel;

    @FXML private Label adventurerNameLabel1;
    @FXML private Label adventurerNameLabel2;
    @FXML private Label adventurerNameLabel3;
    @FXML private Label adventurerNameLabel4;
    @FXML private Label adventurerNameLabel5;

    @FXML private Label staminaLabel1;
    @FXML private Label staminaLabel2;
    @FXML private Label staminaLabel3;
    @FXML private Label staminaLabel4;
    @FXML private Label staminaLabel5;

    @FXML private Label itemNameLabel1;
    @FXML private Label itemNameLabel2;
    @FXML private Label itemNameLabel3;

    @FXML private Label buffLabel1;
    @FXML private Label buffLabel2;
    @FXML private Label buffLabel3;

    @FXML private TextArea randomEventsArea;

    @FXML private Button continueBtn;

    private Label[] nameLabels;
    private Label[] stamLabels;
    private Label[] itemNameLabels;
    private Label[] buffLabels;

    protected PostExpeditionScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    @FXML
    public void initialize() {
        nameLabels = new Label[]{adventurerNameLabel1,adventurerNameLabel2,adventurerNameLabel3, adventurerNameLabel4, adventurerNameLabel5
        };
        stamLabels = new Label[]{staminaLabel1, staminaLabel2, staminaLabel3,staminaLabel4,staminaLabel5
        };
        itemNameLabels = new Label[]{itemNameLabel1, itemNameLabel2, itemNameLabel3};
        buffLabels = new Label[]{buffLabel1, buffLabel2, buffLabel3};

        ExpeditionService expeditionService = getGameEnvironment().getExpeditionService();

        expeditionNameLabel.setText(getGameEnvironment().getSelectedExpedition().getName());
        numEventsLabel.setText("Events completed: " + expeditionService.getEventsCompleted());
        goldEarnedLabel.setText(expeditionService.getGoldEarned() + "g");
        payDeductedLabel.setText(expeditionService.getPayDeducted() + "g");
        netGainLabel.setText(expeditionService.getNetGain() + "g");
        itemsFoundLabel.setText(String.valueOf(expeditionService.getItemsFound().size()));

        populateAdventurerSummary();
        populateItemsFound(expeditionService.getItemsFound());
        populateRandomEvents(expeditionService.getRandomEventMessages());

        continueBtn.setOnAction(e -> getGameEnvironment().navigateToMenu());
    }

    /**
     * Populates the random events text area with the messages produced by
     * the RandomEventService for this round.
     * @param messages random-event messages to display
     */
    private void populateRandomEvents(List<String> messages) {
        if (messages == null || messages.isEmpty()) {
            randomEventsArea.setText("Nothing unusual happened this round.");
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (String message : messages) {
            builder.append("- ").append(message).append("\n");
        }
        randomEventsArea.setText(builder.toString().trim());
    }

    private void populateAdventurerSummary() {
        List<Adventurer> party = getGameEnvironment().getGuildhall().getMainPartyAdventurers();
        for (int i = 0; i < nameLabels.length; i++) {
            if (i < party.size()) {
                Adventurer adventurer = party.get(i);
                nameLabels[i].setText(adventurer.getName());
                stamLabels[i].setText("Stamina: " + adventurer.getStamina() + " / " + adventurer.getMaxStamina());
            } else {
                nameLabels[i].setText("-");
                stamLabels[i].setText("");
            }
        }
    }

    private void populateItemsFound(List<Item> items) {
        for (int i = 0; i < itemNameLabels.length; i++) {
            if (i < items.size()) {
                itemNameLabels[i].setText(items.get(i).getName());
                buffLabels[i].setText(items.get(i).getDescription());
            } else {
                itemNameLabels[i].setText("-");
                buffLabels[i].setText("");
            }
        }
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/postExpedition.fxml";
    }

    @Override
    protected String getTitle() {
        return "Expedition Complete";
    }
}