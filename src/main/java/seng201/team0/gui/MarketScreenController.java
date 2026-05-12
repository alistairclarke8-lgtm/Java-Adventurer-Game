package seng201.team0.gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import seng201.team0.GameEnvironment;

/**
 * Controller for the market screen
 * Two list views: Items for sale, and the player's current inventory
 * Items in the current inventory can be sold back for half the price of purchase
 */
public class MarketScreenController extends ScreenController {
    @FXML private Label goldLabel;
    @FXML private Button backBtn;
    @FXML private ListView shopList;
    @FXML private Button buyBtn;
    @FXML private ListView inventoryList;
    @FXML private Button sellBtn;
    @FXML private Label detailsLabel;

    public void initialize() {
        refresh();
    }

    private void refresh() {
        GameEnvironment env = getGameEnvironment();
        shopList.setItems(FXCollections.observableArrayList(env.getMarket().getAvailableItems()));
        inventoryList.setItems(FXCollections.observableArrayList(env.getGuildhall().getInventoryItems()));
        goldLabel.setText("Gold: " + env.getGold() + "g");
        detailsLabel.setText("Pick an item to view details.");
    }


    /**
     * Creates an instance of a ScreenController with the given GameEnvironment
     * @param gameEnvironment The game environment used by this ScreenController
     */
    protected MarketScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    @Override
    protected String getFxmlFile() {
        return "market.fxml";
    }

    @Override
    protected String getTitle() {
        return "Market";
    }
}
