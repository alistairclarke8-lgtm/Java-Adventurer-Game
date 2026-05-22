package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import seng201.team0.GameEnvironment;
import seng201.team0.exception.*;
import seng201.team0.models.Adventurer;
import seng201.team0.models.Item;

import java.util.List;

/**
 * Controller for the Guild Hall screen. Displays the main party, reserve
 * party, and inventory, and allows the player to swap, retire, move, equip,
 * and use items on adventurers.
 */
public class GuildHallScreenController extends ScreenController {

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
    @FXML private Button makeReserveBtn;
    @FXML private Button moveToMainBtn;

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
    @FXML private TextArea ItemDescriptionTextArea;

    private int selectedMainPartyIndex = -1;
    private int selectedReservePartyIndex = -1;
    private Adventurer selectedAdventurer = null;
    private Item selectedItem = null;

    /**
     * Constructs the controller.
     * @param gameEnvironment the shared game environment
     */
    protected GuildHallScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Wires up all button arrays and handlers, then performs the initial
     * population of the main party, reserve, and inventory displays.
     */
    public void initialize() {
        setupMainPartyButtons();
        setupReserveButtons();
        setupInventoryButtons();
        backBtn.setOnAction(actionEvent -> onBackBtnClicked());
        swapBtn.setOnAction(actionEvent -> onSwapBtnClicked());
        retireBtn.setOnAction(actionEvent -> onRetireBtnClicked());
        makeReserveBtn.setOnAction(actionEvent -> onMakeReserveBtnClicked());
        moveToMainBtn.setOnAction(actionEvent -> onMoveToMainBtnClicked());
        useEquipBtn.setOnAction(actionEvent -> onUseEquipBtnClicked());
    }

    /**
     * Populates the main party buttons with adventurer types and registers
     * selection handlers. Hides slots with no adventurer.
     */
    private void setupMainPartyButtons() {
        List<Button> mainPartyBtns = List.of(mainPartyBtn1, mainPartyBtn2, mainPartyBtn3, mainPartyBtn4, mainPartyBtn5);
        for (int i = 0; i < mainPartyBtns.size(); i++) {
            int index = i;
            Button btn = mainPartyBtns.get(i);
            if (i < getGameEnvironment().getGuildhall().getMainPartyAdventurers().size()) {
                btn.setText(getGameEnvironment().getGuildhall().getMainPartyAdventurers().get(i).getType());
                btn.setVisible(true);
            } else {
                btn.setVisible(false);
            }
            btn.setOnAction(actionEvent -> onMainPartyBtnClicked(mainPartyBtns, index));
        }
    }

    /**
     * Populates the reserve party buttons with adventurer types and registers
     * selection handlers. Hides slots with no adventurer.
     */
    private void setupReserveButtons() {
        List<Button> reservePartyBtns = List.of(reserveParty1, reserveParty2, reserveParty3, reserveParty4, reserveParty5);
        for (int i = 0; i < reservePartyBtns.size(); i++) {
            int index = i;
            Button btn = reservePartyBtns.get(i);
            if (i < getGameEnvironment().getGuildhall().getReservedAdventurers().size()) {
                btn.setText(getGameEnvironment().getGuildhall().getReservedAdventurers().get(i).getType());
                btn.setVisible(true);
            } else {
                btn.setVisible(false);
            }
            btn.setOnAction(actionEvent -> onReservePartBtnClicked(reservePartyBtns, index));
        }
    }

    /**
     * Populates the inventory buttons with item names and registers selection
     * handlers. Hides empty slots.
     */
    private void setupInventoryButtons() {
        List<Button> inventoryBtns = List.of(InventoryBtn1, InventoryBtn2, InventoryBtn3, InventoryBtn4, InventoryBtn5);
        for (int i = 0; i < inventoryBtns.size(); i++) {
            int index = i;
            Button btn = inventoryBtns.get(i);
            if (i < getGameEnvironment().getGuildhall().getInventoryItems().size()) {
                btn.setText(getGameEnvironment().getGuildhall().getInventoryItems().get(i).getName());
                btn.setVisible(true);
                btn.setOnAction(actionEvent -> onInventoryBtnClicked(index));
            } else {
                btn.setVisible(false);
            }
        }
    }

    /**
     * Swaps the selected main party and reserve adventurers. Shows an error
     * if either selection is absent or the operation is invalid.
     */
    private void onSwapBtnClicked() {
        if (selectedMainPartyIndex == -1 || selectedReservePartyIndex == -1) {
            showError("Please select an adventurer from both the main party and reserves.");
            return;
        }
        try {
            getGameEnvironment().getGuildHallService().swapAdventurers(
                    getGameEnvironment().getGuildhall().getMainPartyAdventurers().get(selectedMainPartyIndex),
                    getGameEnvironment().getGuildhall().getReservedAdventurers().get(selectedReservePartyIndex));
            selectedMainPartyIndex = -1;
            selectedReservePartyIndex = -1;
            selectedAdventurer = null;
            setupMainPartyButtons();
            setupReserveButtons();
        } catch (EmptyPartyException | EmptyReservesException e) {
            showError(e.getMessage());
        }
    }

    /**
     * Retires the selected adventurer from the guild. Shows an error if no
     * adventurer is selected or they are the last remaining member.
     */
    private void onRetireBtnClicked() {
        if (selectedAdventurer == null) {
            showError("Please select an adventurer to retire.");
            return;
        }
        try {
            getGameEnvironment().getGuildHallService().retire(selectedAdventurer);
            selectedAdventurer = null;
            selectedMainPartyIndex = -1;
            selectedReservePartyIndex = -1;
            setupMainPartyButtons();
            setupReserveButtons();
        } catch (LastAdventurerException e) {
            showError(e.getMessage());
        }
    }

    /**
     * Moves the selected main party adventurer to the reserves. Shows an
     * error if the operation would leave the party empty or the reserves full.
     */
    private void onMakeReserveBtnClicked() {
        if (selectedMainPartyIndex == -1) {
            showError("Please select a main party adventurer to move to reserves.");
            return;
        }
        try {
            getGameEnvironment().getGuildHallService().moveMainPartyToReserve(
                    getGameEnvironment().getGuildhall().getMainPartyAdventurers().get(selectedMainPartyIndex));
            selectedMainPartyIndex = -1;
            selectedAdventurer = null;
            setupMainPartyButtons();
            setupReserveButtons();
        } catch (GuildFullException | EmptyPartyException | LastInPartyException e) {
            showError(e.getMessage());
        }
    }

    /**
     * Moves the selected reserve adventurer to the main party. Shows an error
     * if the party is full or reserves are empty.
     */
    private void onMoveToMainBtnClicked() {
        if (selectedReservePartyIndex == -1) {
            showError("Please select a reserve adventurer to move to the main party.");
            return;
        }
        try {
            getGameEnvironment().getGuildHallService().moveReserveToMainParty(
                    getGameEnvironment().getGuildhall().getReservedAdventurers().get(selectedReservePartyIndex));
            selectedReservePartyIndex = -1;
            selectedAdventurer = null;
            setupMainPartyButtons();
            setupReserveButtons();
        } catch (GuildFullException | EmptyReservesException e) {
            showError(e.getMessage());
        }

    }

    /**
     * Uses or equips the selected item on the selected adventurer, depending
     * on whether the item is consumed on use. Shows an error if either
     * selection is absent.
     */
    private void onUseEquipBtnClicked() {
        if (selectedAdventurer == null || selectedItem == null) {
            showError("Please select an adventurer and an item.");
            return;
        }
        if (selectedItem.isConsumedOnUse()) {
            getGameEnvironment().getGuildHallService().useItem(selectedAdventurer, selectedItem);
        } else {
            getGameEnvironment().getGuildHallService().equipItem(selectedAdventurer, selectedItem);
        }
        selectedItem = null;
        setupInventoryButtons();
        updateAdventurerStats(selectedAdventurer);
    }

    /**
     * Selects the inventory item at the given index and updates the item
     * detail labels.
     * @param index zero-based index of the clicked inventory slot
     */
    private void onInventoryBtnClicked(int index) {
        if (index >= getGameEnvironment().getGuildhall().getInventoryItems().size()) return;
        selectedItem = getGameEnvironment().getGuildhall().getInventoryItems().get(index);
        ItemNameLabel.setText("Name: " + selectedItem.getName());
        ItemTypeLabel.setText("Type: " + selectedItem.getType());
        ItemDescriptionTextArea.setText(selectedItem.getDescription());
    }

    /**
     * Selects the reserve adventurer at the given index and updates the
     * adventurer stats panel.
     * @param reservePartyBtns the list of reserve party buttons
     * @param index zero-based index of the clicked reserve slot
     */
    private void onReservePartBtnClicked(List<Button> reservePartyBtns, int index) {
        if (index >= getGameEnvironment().getGuildhall().getReservedAdventurers().size()) return;
        selectedReservePartyIndex = index;
        selectedAdventurer = getGameEnvironment().getGuildhall().getReservedAdventurers().get(index);
        updateAdventurerStats(selectedAdventurer);
    }

    /**
     * Selects the main party adventurer at the given index and updates the
     * adventurer stats panel.
     * @param mainPartyBtns the list of main party buttons
     * @param index zero-based index of the clicked main party slot
     */
    private void onMainPartyBtnClicked(List<Button> mainPartyBtns, int index) {
        if (index >= getGameEnvironment().getGuildhall().getMainPartyAdventurers().size()) return;
        selectedMainPartyIndex = index;
        selectedAdventurer = getGameEnvironment().getGuildhall().getMainPartyAdventurers().get(index);
        updateAdventurerStats(selectedAdventurer);
    }

    /**
     * Updates the adventurer stats panel to reflect the given adventurer's
     * current name, stamina, perception, pay, and equipped item.
     * @param adventurer the adventurer whose stats should be displayed
     */
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

    /**
     * Displays a modal error dialog with the given message.
     * @param message the error message to display
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /** Navigates back to the main menu. */
    private void onBackBtnClicked() {
        getGameEnvironment().navigateToMenu();
    }

    /** @return the location and filename of the fxml file being retrieved */
    @Override
    protected String getFxmlFile() {
        return "/fxml/GuildHall.fxml";
    }

    /** @return the screen title string */
    @Override
    protected String getTitle() {
        return "Guild Hall";
    }
}