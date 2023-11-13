package com.example.newyorktimesspellingbee.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The GameModel class represents the model for the New York Times Spelling Bee game.
 * It holds the game state, including the letters used in the game, points, and status information.
 */
public class GameModel {
    public final String letters;
    private final IntegerProperty pointProperty = new SimpleIntegerProperty();
    private final StringProperty wordProperty = new SimpleStringProperty();
    private final StringProperty statusProperty = new SimpleStringProperty();
    private final StringProperty resultWordProperty = new SimpleStringProperty();
    private final IntegerProperty currentPointProperty = new SimpleIntegerProperty();
    private final IntegerProperty maximumPointProperty = new SimpleIntegerProperty();

    public GameModel(String letters) {
        this.letters = letters;
    }

    public void setPointPropertyValue(int val) {
        pointProperty.setValue(val);
    }

    public int getPointPropertyValue() {
        return pointProperty.getValue();
    }

    public void setWordPropertyValue(String val) {
        wordProperty.setValue(val);
    }

    public String getWordPropertyValue() {
        return wordProperty.getValue();
    }

    public void setStatusPropertyValue(String val) {
        statusProperty.setValue(val);
    }

    public StringProperty getStatusProperty() {
        return statusProperty;
    }

    public void setResultWordPropertyValue(String val) {
        resultWordProperty.setValue(val);
    }

    public String getResultWordPropertyValue() {
        return resultWordProperty.getValue();
    }

    public StringProperty getResultWordProperty() {
        return resultWordProperty;
    }

    public void setCurrentPointPropertyValue(int val) {
        currentPointProperty.setValue(val);
    }

    public int getCurrentPointPropertyValue() {
        return currentPointProperty.getValue();
    }

    public IntegerProperty getCurrentPointProperty() {
        return currentPointProperty;
    }

    public void setMaximumPointPropertyValue(int val) {
        maximumPointProperty.setValue(val);
    }

    public int getMaximumPointPropertyValue() {
        return maximumPointProperty.getValue();
    }

}
