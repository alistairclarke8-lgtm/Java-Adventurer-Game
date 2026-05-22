package seng201.team0.gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import seng201.team0.GameEnvironment;
import seng201.team0.exception.InsufficientGoldException;
import seng201.team0.models.Item;

/**
 * Controller for the market screen
 * Two list views: Items for sale, and the player's current inventory
 * Items in the current inventory can be sold back for half the price of purchase
 */
public class MarketScreenController extends ScreenController {

    @FXML private Label goldLabel;
    @FXML private Button backBtn;
    @FXML private ListView<Item> shopList;
    @FXML private Button buyBtn;
    @FXML private ListView<Item> inventoryList;
    @FXML private Button sellBtn;
    @FXML private Label detailsLabel;

    /**
     * Creates an instance of a ScreenController with the given GameEnvironment
     * @param gameEnvironment The game environment used by this ScreenController
     */
    protected MarketScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Registers button and list-selection handlers, then performs the initial
     * refresh of both list views. Selecting an item in one list clears the
     * selection in the other.
     */
    public void initialize() {
        backBtn.setOnAction(actionEvent -> getGameEnvironment().navigateToMenu());
        buyBtn.setOnAction(actionEvent -> onBuyBtnClicked());
        sellBtn.setOnAction(actionEvent -> onSellBtnClicked());
        shopList.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldValue, newValue) -> {
                    if (newValue != null) {
                        inventoryList.getSelectionModel().clearSelection();
                        updateDetails((Item) newValue, true);
                    }
                });
        inventoryList.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldValue, newValue) -> {
                    if (newValue != null) {
                        shopList.getSelectionModel().clearSelection();
                        updateDetails((Item) newValue, false);
                    }
                });
        refresh();
    }

    /**
     * Reloads the shop and inventory lists and updates the gold label.
     */
    private void refresh() {
        GameEnvironment environment = getGameEnvironment();
        shopList.setItems(FXCollections.observableArrayList(environment.getMarket().getAvailableItems()));
        inventoryList.setItems(FXCollections.observableArrayList(environment.getGuildhall().getInventoryItems()));
        goldLabel.setText("Gold: " + environment.getGold() + "g");
        detailsLabel.setText("Pick an item to view details.");
    }

    /**
     * Updates the details label with the selected item's name, type,
     * description, and buy or sell price.
     * @param item the item to display
     * @param fromShop  true to show the buy price, false for sell price
     */
    private void updateDetails(Item item, boolean fromShop) {
        if (item == null) return;
        int price = fromShop
                ? getGameEnvironment().getMarketService().getBuyPrice(item)
                : getGameEnvironment().getMarketService().getSellPrice(item);
        detailsLabel.setText(item.getName() + " (" + item.getType() + ")\n\n"
                + item.getDescription() + "\n\n"
                + (fromShop ? "Buy price: " : "Sell price: ") + price + "g");
    }

    /**
     * Purchases the selected shop item. Shows an alert if no item is selected
     * or the player has insufficient gold.
     */
    private void onBuyBtnClicked() {
        Item item = (Item) shopList.getSelectionModel().getSelectedItem();
        if (item == null) {
            showAlert("No selection", "Pick an item from the shop list first.");
            return;
        }
        try {
            getGameEnvironment().getMarketService().buy(item);
            refresh();
        } catch (InsufficientGoldException e) {
            showAlert("Cannot buy", e.getMessage());
        }
    }

    /**
     * Sells the selected inventory item. Shows an alert if no item is selected.
     */
    private void onSellBtnClicked() {
        Item item = (Item) inventoryList.getSelectionModel().getSelectedItem();
        if (item == null) {
            showAlert("No selection", "Pick an item from the inventory first.");
            return;
        }
        getGameEnvironment().getMarketService().sell(item);
        refresh();
    }

    /**
     * Displays a modal information alert with the given header and body.
     * @param header short summary shown in the dialog header
     * @param body detailed message shown in the dialog body
     */
    private void showAlert(String header, String body) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Market");
        alert.setHeaderText(header);
        alert.setContentText(body);
        alert.showAndWait();
    }

    /** @return the location and filename of the fxml file being retrieved */
    @Override
    protected String getFxmlFile() {
        return "/fxml/market.fxml";
    }

    /** @return the screen title string */
    @Override
    protected String getTitle() {
        return "Market";
    }
}
