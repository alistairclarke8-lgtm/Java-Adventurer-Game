package seng201.team0.gui;

import seng201.team0.GameEnvironment;

public class GuildHallScreenController extends ScreenController{







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
