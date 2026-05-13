package seng201.team0.gui;

import javafx.event.EventType;
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

    private int mainPartyIndex =-1;
    private int reservePartyIndex=-1;

    public void initialize() {
        List<Button> mainPartyBtns = List.of(mainPartyBtn1, mainPartyBtn2, mainPartyBtn3, mainPartyBtn4, mainPartyBtn5);
        List<Button> reservePartyBtns = List.of(reserveParty1, reserveParty2, reserveParty3, reserveParty4, reserveParty5);
        for (int i = 0; i < mainPartyBtns.size(); i++) {
            mainPartyIndex = i;
            if (i < getGameEnvironment().getGuildhall().getMainPartyAdventurers().size()) {
                mainPartyBtns.get(i).setText(getGameEnvironment().getGuildhall().getMainPartyAdventurers().get(i).getName());
            } else {
                mainPartyBtns.get(i).setText("Empty");
            }
            mainPartyBtns.get(mainPartyIndex).setOnAction(actionEvent -> onMainPartyBtnClicked(mainPartyBtns, mainPartyIndex));
        }

        for (int i = 0; i < reservePartyBtns.size(); i++) {
            reservePartyIndex = i;
            if (i < getGameEnvironment().getGuildhall().getReservedAdventurers().size()) {
                reservePartyBtns.get(i).setText(getGameEnvironment().getGuildhall().getReservedAdventurers().get(i).getName());
            } else {
                reservePartyBtns.get(i).setText("Empty");
            }
            reservePartyBtns.get(reservePartyIndex).setOnAction(actionEvent -> onReservePartBtnClicked(reservePartyBtns, reservePartyIndex));
        }
        backBtn.setOnAction(actionEvent -> onBackBtnClicked());
        swapBtn.setOnAction(actionEvent -> onSwapBtnClicked(mainPartyIndex, reservePartyIndex));

    }

    private void onSwapBtnClicked(int mainPartyIndex, int reservePartyIndex) throws Exception {
        if (mainPartyIndex == -1 || reservePartyIndex == -1) return;

        getGameEnvironment().getGuildHallService().swapAdventurers(getGameEnvironment().getGuildhall().getMainPartyAdventurers().get(mainPartyIndex),getGameEnvironment().getGuildhall().getReservedAdventurers().get(reservePartyIndex));
    }

    private void onReservePartBtnClicked(List<Button> reservePartyBtns, int reservePartyIndex) {
        updateAdventurerStats(getGameEnvironment().getGuildhall().getReservedAdventurers().get(reservePartyIndex));
    }


    private void onMainPartyBtnClicked(List<Button> mainPartyBtns, int mainPartyIndex) {
        updateAdventurerStats(getGameEnvironment().getGuildhall().getMainPartyAdventurers().get(mainPartyIndex));
    }

    private void updateAdventurerStats(Adventurer adventurer) {
        adventurerNameLabel.setText("Name: " + adventurer.getName());
        adventurerStaminaLabel.setText("Stamina: " + adventurer.getStamina());
        adventurerPerceptionLabel.setText("Perception: " + adventurer.getPerception());
        adventurerPayLabel.setText("Pay: " + adventurer.getPay());
        equippedItemLabel.setText("Equipped Item: " + adventurer.getEquippedItem().getName());



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