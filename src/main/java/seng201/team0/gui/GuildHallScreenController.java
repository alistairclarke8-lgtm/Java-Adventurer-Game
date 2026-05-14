package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import seng201.team0.GameEnvironment;
import seng201.team0.models.Adventurer;

import java.util.List;

public class GuildHallScreenController extends ScreenController{

    @FXML private Button backBtn;

    @FXML private Button mainPartyBtn1;
    @FXML private Button mainPartyBtn2;
    @FXML private Button mainPartyBtn3;
    @FXML private Button mainPartyBtn4;
    @FXML private Button mainPartyBtn5;

    @FXML private Button reserveParty1;
    @FXML private Button reserveParty2;
    @FXML private Button reserveParty3;
    @FXML private Button reserveParty4;
    @FXML private Button reserveParty5;

    @FXML private Button swapBtn;
    @FXML private Button useEquipBtn;
    @FXML private Button retireBtn;

    @FXML private Button InventoryBtn1;
    @FXML private Button InventoryBtn2;
    @FXML private Button InventoryBtn3;
    @FXML private Button InventoryBtn4;
    @FXML private Button InventoryBtn5;

    @FXML private Label adventurerNameLabel;
    @FXML private Label adventurerStaminaLabel;
    @FXML private Label adventurerPerceptionLabel;
    @FXML private Label adventurerPayLabel;
    @FXML private Label equippedItemLabel;

    @FXML private Label ItemNameLabel;
    @FXML private Label ItemTypeLabel;
    @FXML private Label ItemBuffLabel;

    private int selectedMainPartyIndex = -1;
    private int selectedReservePartyIndex = -1;

    public void initialize() {
        setupMainPartyButtons();
        setupReserveButtons();
        backBtn.setOnAction(actionEvent -> onBackBtnClicked());
        swapBtn.setOnAction(actionEvent -> onSwapBtnClicked());
        retireBtn.setOnAction(actionEvent -> onRetireBtnClicked());
    }

    private void setupMainPartyButtons() {
        List<Button> mainPartyBtns = List.of(mainPartyBtn1, mainPartyBtn2, mainPartyBtn3, mainPartyBtn4, mainPartyBtn5);
        for (int i = 0; i < mainPartyBtns.size(); i++) {
            int index = i;
            if (i < getGameEnvironment().getGuildhall().getMainPartyAdventurers().size()) {
                mainPartyBtns.get(i).setText(getGameEnvironment().getGuildhall().getMainPartyAdventurers().get(i).getName());
            } else {
                mainPartyBtns.get(i).setText("Empty");
            }
            mainPartyBtns.get(i).setOnAction(actionEvent -> onMainPartyBtnClicked(mainPartyBtns, index));
        }
    }

    private void setupReserveButtons() {
        List<Button> reservePartyBtns = List.of(reserveParty1, reserveParty2, reserveParty3, reserveParty4, reserveParty5);
        for (int i = 0; i < reservePartyBtns.size(); i++) {
            int index = i;
            if (i < getGameEnvironment().getGuildhall().getReservedAdventurers().size()) {
                reservePartyBtns.get(i).setText(getGameEnvironment().getGuildhall().getReservedAdventurers().get(i).getName());
            } else {
                reservePartyBtns.get(i).setText("Empty");
            }
            reservePartyBtns.get(i).setOnAction(actionEvent -> onReservePartBtnClicked(reservePartyBtns, index));
        }
    }

    private void onSwapBtnClicked() {
        if (selectedMainPartyIndex == -1 || selectedReservePartyIndex == -1) return;
        try {
            getGameEnvironment().getGuildHallService().swapAdventurers(
                    getGameEnvironment().getGuildhall().getMainPartyAdventurers().get(selectedMainPartyIndex),
                    getGameEnvironment().getGuildhall().getReservedAdventurers().get(selectedReservePartyIndex));
            setupMainPartyButtons();
            setupReserveButtons();
        } catch (Exception e) {}
    }

    private void onRetireBtnClicked() {
        if (selectedMainPartyIndex == -1) return;
        try {
            getGameEnvironment().getGuildHallService().retire(
                    getGameEnvironment().getGuildhall().getMainPartyAdventurers().get(selectedMainPartyIndex));
            setupMainPartyButtons();
            setupReserveButtons();
        } catch (Exception e) {}
    }

    private void onReservePartBtnClicked(List<Button> reservePartyBtns, int index) {
        if (index >= getGameEnvironment().getGuildhall().getReservedAdventurers().size()) return;
        selectedReservePartyIndex = index;
        updateAdventurerStats(getGameEnvironment().getGuildhall().getReservedAdventurers().get(index));
    }

    private void onMainPartyBtnClicked(List<Button> mainPartyBtns, int index) {
        if (index >= getGameEnvironment().getGuildhall().getMainPartyAdventurers().size()) return;
        selectedMainPartyIndex = index;
        updateAdventurerStats(getGameEnvironment().getGuildhall().getMainPartyAdventurers().get(index));
    }

    private void updateAdventurerStats(Adventurer adventurer) {
        adventurerNameLabel.setText("Name: " + adventurer.getName());
        adventurerStaminaLabel.setText("Stamina: " + adventurer.getStamina());
        adventurerPerceptionLabel.setText("Perception: " + adventurer.getPerception());
        adventurerPayLabel.setText("Pay: " + adventurer.getPay());
        if (adventurer.getEquippedItem() != null) {
            equippedItemLabel.setText("Equipped Item: " + adventurer.getEquippedItem().getName());
        } else {
            equippedItemLabel.setText("Equipped Item: None");
        }
    }

    private void onBackBtnClicked() {
        getGameEnvironment().navigateToMenu();
    }

    /**
     * Creates an instance of a ScreenController with the given {@link GameEnvironment}
     *
     * @param gameEnvironment The game environment used by this ScreenController
     */
    protected GuildHallScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/GuildHall.fxml";
    }

    @Override
    protected String getTitle() {
        return "Guild Hall";
    }
}