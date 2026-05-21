package seng201.team0.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import seng201.team0.GameEnvironment;
import seng201.team0.models.Expedition;

import java.io.IOException;

/**
 * Class that handles navigation between various {@link ScreenController}s. This navigator
 * uses a {@link BorderPane} layout for the root pane. A launched screen is placed in the
 * center area of the border pane, replacing the previous screen if any.
 *
 * @author seng201 teaching team
 */
public class ScreenNavigator {

    private final Stage stage;

    private final BorderPane rootPane;

    /**
     * Creates a ScreenNavigator with the given stage.
     *
     * @param stage The JavaFX stage
     */
    public ScreenNavigator(Stage stage) {
        this.stage = stage;

        // Use a border pane as the root component to allow children to be resizable.
        rootPane = new BorderPane();
        rootPane.setPrefHeight(400);
        rootPane.setPrefWidth(600);
        stage.setScene(new Scene(rootPane));
        stage.show();
    }

    /**
     * Launches the setup screen.
     *
     * @param gameEnvironment The environment for the game used by the setup screen controller
     */
    public void launchSetupScreen(GameEnvironment gameEnvironment) {
        ScreenController controller = new SetupScreenController(gameEnvironment);
        launchScreen(controller);
    }

    /**
     * Launches the main screen.
     *
     * @param gameEnvironment The game environment used by the main screen controller
     */
    public void launchMenuScreenController(GameEnvironment gameEnvironment) {
        ScreenController controller = new MenuScreenController(gameEnvironment);
        launchScreen(controller);
    }

    public void launchGuildHallScreenController(GameEnvironment gameEnvironment) {
        ScreenController controller = new GuildHallScreenController(gameEnvironment);
        launchScreen(controller);
    }
    public void launchMarketScreenController(GameEnvironment gameEnvironment) {
        ScreenController controller = new MarketScreenController(gameEnvironment);
        launchScreen(controller);
    }

    public void launchRecruitmentScreenController(GameEnvironment gameEnvironment) {
        ScreenController controller = new RecruitmentScreenController(gameEnvironment);
        launchScreen(controller);
    }
    public void launchExpeditionSelectScreenController(GameEnvironment gameEnvironment) {
        ScreenController controller = new ExpeditionSelectScreenController(gameEnvironment);
        launchScreen(controller);
    }
    public void launchExpeditionInProgressScreen(GameEnvironment gameEnvironment) {
        ScreenController controller = new ExpeditionInProgressController(gameEnvironment);
        launchScreen(controller);
    }



    /**
     * Replaces the root border pane's center component with the screen defined by the given
     * {@link ScreenController}.
     *
     * @param controller The JavaFX screen controller for the screen to be launched
     */
    private void launchScreen(ScreenController controller) {
        System.out.println(getClass().getResource(controller.getFxmlFile()));
        try {
            FXMLLoader setupLoader = new FXMLLoader(getClass().getResource(controller.getFxmlFile()));
            // Set a controller factory that returns the given ScreenController.
            // This allows us to have screen controllers that take argument(s) in their constructor.
            setupLoader.setControllerFactory(param -> controller);
            Parent setupParent  = setupLoader.load();
            rootPane.setCenter(setupParent);
            stage.setTitle(controller.getTitle());
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

}
