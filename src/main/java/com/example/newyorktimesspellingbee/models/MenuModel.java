package com.example.newyorktimesspellingbee.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The MenuModel class represents the model for the menu interface of the Spelling Bee game.
 * It holds properties related to the letters input and error messages.
 */
public class MenuModel {
    private final StringProperty lettersProperty = new SimpleStringProperty();
    private final StringProperty errorProperty = new SimpleStringProperty();

    public String getLettersPropertyValue() {
        return lettersProperty.getValue();
    }

    public StringProperty getLettersProperty() {
        return lettersProperty;
    }

    public StringProperty getErrorProperty() {
        return errorProperty;
    }

    public void setErrorPropertyValue(String val) {
        errorProperty.setValue(val);
    }
}
