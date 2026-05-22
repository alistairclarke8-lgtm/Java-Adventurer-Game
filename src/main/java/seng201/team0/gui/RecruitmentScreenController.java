package seng201.team0.gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import seng201.team0.GameEnvironment;
import seng201.team0.exception.GuildFullException;
import seng201.team0.exception.InsufficientGoldException;
import seng201.team0.models.Adventurer;

/**
 * Controller for the recruitment screen. Displays available recruits in a
 * list view and allows the player to hire them, subject to gold and guild
 * capacity constraints.
 */
public class RecruitmentScreenController extends ScreenController {

    @FXML private Label goldLabel;
    @FXML private Button backBtn;
    @FXML private ListView<Adventurer> recruitsList;
    @FXML private Button hireBtn;
    @FXML private Label detailsLabel;

    /**
     * Constructs a RecruitmentScreenController for the given environment.
     * @param gameEnvironment the game environment
     */
    protected RecruitmentScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Registers button and list-selection handlers, then performs the initial
     * refresh of the recruits list.
     */
    public void initialize() {
        backBtn.setOnAction(actionEvent -> getGameEnvironment().navigateToMenu());
        hireBtn.setOnAction(actionEvent -> onHireBtnClicked());
        recruitsList.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldValue, newValue) -> updateDetails(newValue));
        refresh();
    }

    /**
     * Updates the details label with the selected adventurer's stats and hire cost.
     * Displays a prompt if no adventurer is selected.
     * @param adventurer the selected adventurer, or {@code null} if none
     */
    private void updateDetails(Adventurer adventurer) {
        if (adventurer == null) {
            detailsLabel.setText("Pick an adventurer to view details.");
            return;
        }
        int price = getGameEnvironment().getRecruitmentService().getHirePrice(adventurer);
        detailsLabel.setText(adventurer.getName()
                + " (" + adventurer.getType() + ")\n"
                + "Stamina: " + adventurer.getStamina() + " / " + adventurer.getMaxStamina() + "\n"
                + "Perception: " + adventurer.getPerception() + "\n"
                + "Pay per expedition: " + adventurer.getPay() + "g\n"
                + "Hire cost: " + price + "g");
    }

    /**
     * Reloads the recruits list and updates the gold label.
     */
    private void refresh() {
        GameEnvironment environment = getGameEnvironment();
        recruitsList.setItems(FXCollections.observableArrayList(
                environment.getRecruitmentService().getAvailableRecruits()));
        goldLabel.setText("Gold: " + environment.getGold() + "g");
        detailsLabel.setText("Pick an adventurer to view details.");
    }

    /**
     * Hires the selected recruit. Shows an alert if no recruit is selected,
     * the player has insufficient gold, or the guild reserves are full.
     */
    private void onHireBtnClicked() {
        Adventurer chosen = recruitsList.getSelectionModel().getSelectedItem();
        if (chosen == null) {
            showAlert("No selection", "Pick a recruit first.");
            return;
        }
        try {
            getGameEnvironment().getRecruitmentService().hire(chosen);
            refresh();
        } catch (InsufficientGoldException e) {
            showAlert("Cannot hire", e.getMessage());
        } catch (GuildFullException e) {
            showAlert("Reserves full", e.getMessage());
        }
    }

    /**
     * Displays a modal information alert with the given header and body.
     * @param header short summary shown in the dialog header
     * @param body   detailed message shown in the dialog body
     */
    private void showAlert(String header, String body) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Recruitment");
        alert.setHeaderText(header);
        alert.setContentText(body);
        alert.showAndWait();
    }

    /** @return the location and filename of the fxml file being retrieved */
    @Override
    protected String getFxmlFile() {
        return "/fxml/recruit.fxml";
    }

    /** @return the screen title string */
    @Override
    protected String getTitle() {
        return "Recruitment";
    }
}
