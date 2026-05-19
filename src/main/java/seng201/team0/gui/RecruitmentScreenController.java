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


public class RecruitmentScreenController extends ScreenController {


    @FXML private Label goldLabel;
    @FXML private Button backBtn;
    @FXML private ListView<Adventurer> recruitsList;
    @FXML private Button hireBtn;
    @FXML private Label detailsLabel;

    public void initialize() {
        backBtn.setOnAction(actionEvent -> getGameEnvironment().navigateToMenu());
        hireBtn.setOnAction(actionEvent -> onHireBtnClicked());
        recruitsList.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldValue, newValue) -> updateDetails(newValue));
        refresh();
    }


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

    private void refresh() {
        GameEnvironment environment = getGameEnvironment();
        recruitsList.setItems(FXCollections.observableArrayList(
                environment.getRecruitmentService().getAvailableRecruits()));
        goldLabel.setText("Gold: " + environment.getGold() + "g");
        detailsLabel.setText("Pick an adventurer to view details.");
    }

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

    private void showAlert(String header, String body) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Recruitment");
        alert.setHeaderText(header);
        alert.setContentText(body);
        alert.showAndWait();
    }

    /**
     * Constructs a RecruitmentScreenController for the given environment.
     * @param gameEnvironment the game environment
     */
    protected RecruitmentScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/recruitment.fxml";
    }

    @Override
    protected String getTitle() {
        return "Recruitment";
    }
}
