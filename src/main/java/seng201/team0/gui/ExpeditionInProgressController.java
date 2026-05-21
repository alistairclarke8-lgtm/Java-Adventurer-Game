package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import seng201.team0.GameEnvironment;
import seng201.team0.exception.ExpeditionOverException;
import seng201.team0.exception.InsufficientGoldException;
import seng201.team0.models.Adventurer;
import seng201.team0.models.Event;
import seng201.team0.models.EventOption;
import seng201.team0.models.Expedition;
import seng201.team0.services.ExpeditionService;

import java.util.List;

/**
 * Controller for the in-progress expedition screen. Renders the party's
 * current stamina, the current event prompt and option buttons, and a
 * running log of outcomes. Routes the player to the post-expedition
 * summary once the expedition ends.
 */
public class ExpeditionInProgressController extends ScreenController{
    @FXML private Label expeditionNameLabel;
    @FXML private Label eventNumLabel;
    @FXML private Label goldGainedLabel;

    @FXML private Label adventurerNameLabel1;
    @FXML private Label adventurerNameLabel2;
    @FXML private Label adventurerNameLabel3;
    @FXML private Label adventurerNameLabel4;
    @FXML private Label adventurerNameLabel5;

    @FXML private ProgressBar stamProgressBar1;
    @FXML private ProgressBar stamProgressBar2;
    @FXML private ProgressBar stamProgressBar3;
    @FXML private ProgressBar stamProgressBar4;
    @FXML private ProgressBar stamProgressBar5;

    @FXML private Label adventurerStamLabel1;
    @FXML private Label adventurerStamLabel2;
    @FXML private Label adventurerStamLabel3;
    @FXML private Label adventurerStamLabel4;
    @FXML private Label adventurerStamLabel5;

    @FXML private TextArea eventLogField;
    @FXML private Label eventNameLabel;
    @FXML private Label eventDescriptionLabel;
    @FXML private Button optionABtn;
    @FXML private Button optionBBtn;
    @FXML private Button retreatBtn;

    private Label[] nameLabels;
    private Label[] stamLabels;
    private ProgressBar[] stamBars;
    private ExpeditionService expeditionService;
    private Expedition expedition;
    private int lastLogIndex;

    @FXML
    public void initialize() {

        nameLabels= new Label[]{adventurerNameLabel1,adventurerNameLabel2,adventurerNameLabel3,adventurerNameLabel4,adventurerNameLabel5};
        stamBars = new ProgressBar[] {stamProgressBar1,stamProgressBar2,stamProgressBar3,stamProgressBar4,stamProgressBar5};
        stamLabels = new Label[] {adventurerStamLabel1,adventurerStamLabel2,adventurerStamLabel3,adventurerStamLabel4,adventurerStamLabel5};

        optionABtn.setOnAction(actionEvent -> onChoiceBtnClicked(0));
        optionBBtn.setOnAction(actionEvent -> onChoiceBtnClicked(1));
        retreatBtn.setOnAction(actionEvent -> onRetreatBtnClicked());

        expeditionNameLabel.setText(expedition.getName());

        refreshPartyDisplay();
        refreshEventDisplay();
        refreshGoldDisplay();
        lastLogIndex = 0;
        appendRecentLogs();
    }

    private void onRetreatBtnClicked() {
        getGameEnvironment().navigateToMenu();
    }

    private void onChoiceBtnClicked(int choiceIndex) {
        try {
            expeditionService.completeChoice(choiceIndex);
            refreshPartyDisplay();
            refreshGoldDisplay();
            appendRecentLogs();

            if (expeditionService.getCurrentEvent() == null) {
                getGameEnvironment().incrementExpedition();
                getGameEnvironment().navigateToPostExpedition();
            } else {
                refreshEventDisplay();
            }
        } catch (ExpeditionOverException e) {
            getGameEnvironment().incrementExpedition();
            getGameEnvironment().navigateToPostExpedition();
        }
    }
    private void refreshPartyDisplay() {
        List<Adventurer> party = getGameEnvironment().getGuildhall().getMainPartyAdventurers();
        for (int i = 0; i < nameLabels.length; i++) {
            if (i < party.size()) {
                Adventurer adventurer = party.get(i);
                int maxStam = adventurer.getMaxStamina();
                int curStam = adventurer.getStamina();
                double progress = maxStam > 0 ? (double) curStam / maxStam : 0.0;
                nameLabels[i].setText(adventurer.getName());
                stamBars[i].setProgress(progress);
                stamLabels[i].setText(curStam + " / " + maxStam);
            } else {
                nameLabels[i].setText("-");
                stamBars[i].setProgress(0.0);
                stamLabels[i].setText("");
            }
        }
    }
    private void refreshEventDisplay() {
        Event event = expeditionService.getCurrentEvent();
        if (event == null) return;
        int eventNum = expeditionService.getEventsCompleted() + 1;
        int totalEvents = expedition.getEvents().size();
        eventNumLabel.setText("Event: " + eventNum + "/" + totalEvents);
        eventNameLabel.setText("Event " + eventNum);
        eventDescriptionLabel.setText(event.prompt());

        List<EventOption> options = event.options();
        optionABtn.setText(event.options().get(0).label());
        optionBBtn.setText(event.options().get(1).label());
    }

    private void refreshGoldDisplay() {
        goldGainedLabel.setText("Gold gained: " + expeditionService.getGoldEarned());
    }

    private void appendRecentLogs() {
        List<String> log = expeditionService.getLog();
        for (int i = lastLogIndex; i < log.size(); i++) {
            appendLog(log.get(i));
        }
        lastLogIndex = log.size();
    }

    private void appendLog(String message) {
        eventLogField.appendText(message + "\n");
    }

    private void showValidationError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Cannot Start Game");
        alert.setHeaderText("Please fix the following:");
        alert.setContentText(message);
        alert.showAndWait();
    }


    protected ExpeditionInProgressController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
        this.expeditionService = gameEnvironment.getExpeditionService();
        this.expedition = gameEnvironment.getSelectedExpedition();
    }
    @Override
    protected String getFxmlFile() {
        return "/fxml/mainExpedition.fxml";
    }

    @Override
    protected String getTitle() {
        return "Screen for an expedition in progress.";
    }
}
