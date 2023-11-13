package com.example.newyorktimesspellingbee.controllers;


import javafx.scene.Scene;

import com.example.newyorktimesspellingbee.App;
import com.example.newyorktimesspellingbee.core.exceptions.*;
import com.example.newyorktimesspellingbee.core.managers.GameService;
import com.example.newyorktimesspellingbee.core.results.PointResult;
import com.example.newyorktimesspellingbee.models.MenuModel;
import com.example.newyorktimesspellingbee.models.GameModel;
import com.example.newyorktimesspellingbee.views.MenuView;


/**
 * GameController is responsible for controlling the game flow in the New York Times Spelling Bee application.
 * It manages interactions between the GameModel, GameService, and the game's view components.
 */
public class GameController {
    private final GameModel model;
    private final GameService gameService;

    /**
     * Constructs a GameController with a specified game model and game service.
     *
     * @param model The GameModel associated with this controller.
     * @param gameService The GameService that provides game logic and rules.
     */
    public GameController(GameModel model, GameService gameService) {
        this.model = model;
        this.gameService = gameService;
        model.setMaximumPointPropertyValue(this.gameService.getMaximumPoint());
    }

    /**
     * Checks the current word in the GameModel against the game's rules and updates the model with the result.
     * This includes updating points, word status, and handling any exceptions related to game rules.
     */
    public void check() {
        try {
            PointResult pointResult = gameService.check(model.getWordPropertyValue());
            model.setPointPropertyValue(pointResult.getPoint());
            model.setResultWordPropertyValue(pointResult.getWord());
            model.setCurrentPointPropertyValue(pointResult.getCurrentPoint());
            model.setStatusPropertyValue(String.format("\"%s\" Word found!", pointResult.getWord()));
        } catch (DictionaryDoesNotContainWordException | IllegalWordLengthException |
                 WordContainsIllegalLetterException | WordDoesNotContainCenterLetterException |
                 WordAlreadyFoundException e) {
            model.setStatusPropertyValue(e.getMessage());
        }
    }

    /**
     * Transitions from the game view back to the main menu view.
     * It creates a new MenuModel, MenuController, and MenuView, and sets the application's scene to the menu view.
     */
    public void returnMenu() {
        MenuModel model = new MenuModel();
        MenuController controller = new MenuController(model);
        MenuView view = new MenuView(model, controller);
        Scene scene = new Scene(view.getAsParent());
        scene.getStylesheets().add("com/example/newyorktimesspellingbee/style.css");
        App.getInstance().setScene(scene);
    }
}
