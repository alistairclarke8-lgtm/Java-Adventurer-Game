package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import seng201.team0.GameEnvironment;

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
    @FXML private Label equipedItemLabel;

    @FXML private Label ItemNameLabel;
    @FXML private Label ItemTypeLabel;
    @FXML private Label ItemBuffLabel;












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
