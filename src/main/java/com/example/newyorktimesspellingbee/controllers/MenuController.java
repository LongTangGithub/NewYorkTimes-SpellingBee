package com.example.newyorktimesspellingbee.controllers;

import javafx.scene.Scene;
import java.util.List;

import com.example.newyorktimesspellingbee.App;
import com.example.newyorktimesspellingbee.core.exceptions.*;
import com.example.newyorktimesspellingbee.core.managers.GameManager;
import com.example.newyorktimesspellingbee.core.managers.GameService;
import com.example.newyorktimesspellingbee.core.managers.GameCreatorManager;
import com.example.newyorktimesspellingbee.core.managers.GameCreatorService;
import com.example.newyorktimesspellingbee.core.data.GameData;

import com.example.newyorktimesspellingbee.models.MenuModel;
import com.example.newyorktimesspellingbee.models.GameModel;

import com.example.newyorktimesspellingbee.views.GameView;

/**
 * MenuController is responsible for managing the menu interactions in the New York Times Spelling Bee application.
 * It handles starting the game either with default letters or with a user-specified set of letters.
 */
public class MenuController {
    private final MenuModel model;

    /**
     * Constructs a MenuController with a specified menu model.
     *
     * @param model The MenuModel associated with this controller.
     */
    public MenuController(MenuModel model) {
        this.model = model;
    }

    /**
     * Starts the game with a default set of letters.
     * This method delegates to a private method that handles the game start process.
     */
    public void startWithLetters() {
        handleStartWithLetters();
    }

    /**
     * Starts the game with a random set of letters.
     * This method delegates to a private method that handles the game start process.
     */
    public void start() {
        handleStart();
    }


    /**
     * Handles the process of starting the game with a random set of letters.
     * It creates a new game using the GameCreatorService, and sets up the game view and controller.
     */
    private void handleStart() {
        GameCreatorService creatorService = new GameCreatorManager(App.getInstance().getDataFilter(), App.getInstance().getDataReader());
        try {
            GameData data = creatorService.create();
            debug(data.getWords(), data.getPangramWords(), data.getLetters());
            model.setErrorPropertyValue("");
            GameService gameService = new GameManager(data);
            GameModel gameModel = new GameModel(data.getLetters());
            GameController gameController = new GameController(gameModel, gameService);
            GameView gameView = new GameView(gameModel, gameController);
            Scene scene = new Scene(gameView.getAsParent());
            scene.getStylesheets().addAll("com/example/newyorktimesspellingbee/style.css");
            App.getInstance().setScene(scene);
        } catch (PangramNotFoundException | IllegalPointRangeException | IllegalWordCountException e) {
            handleStart();
        }

    }

    /**
     * Handles the process of starting the game with a specific set of letters provided by the user.
     * It creates a new game using the GameCreatorService with the specified letters, and sets up the game view and controller.
     */
    private void handleStartWithLetters() {
        GameCreatorService creatorService = new GameCreatorManager(App.getInstance().getDataFilter(), App.getInstance().getDataReader());
        try {
            GameData data = creatorService.create(model.getLettersPropertyValue().toLowerCase());
            debug(data.getWords(), data.getPangramWords(), data.getLetters());
            model.setErrorPropertyValue("");
            GameService gameService = new GameManager(data);
            GameModel gameModel = new GameModel(data.getLetters());
            GameController gameController = new GameController(gameModel, gameService);
            GameView gameView = new GameView(gameModel, gameController);
            Scene scene = new Scene(gameView.getAsParent());
            scene.getStylesheets().addAll("com/example/newyorktimesspellingbee/style.css");
            App.getInstance().setScene(scene);
        } catch (PangramNotFoundException | IllegalPointRangeException | IllegalWordCountException |
                 NotUniqueLettersException | IllegalLettersLengthException | IllegalLetterException e) {
            model.setErrorPropertyValue(e.getMessage());
        }
    }

    /**
     * Debugging method to print game data such as words, pangrams, and letters.
     *
     * @param words A list of words used in the game.
     * @param pangrams A list of pangram words used in the game.
     * @param letters The letters used in the game.
     */
    private void debug(List<String> words, List<String> pangrams, String letters) {
        System.out.println("Letters: " + letters);
        System.out.printf("=== Pangrams ( %d ) ===%n", pangrams.size());
        pangrams.forEach(System.out::println);
        System.out.println("================");
        System.out.printf("=== Words ( %d ) ===%n", words.size());
        words.forEach(System.out::println);
        System.out.println("=============");
    }
}
