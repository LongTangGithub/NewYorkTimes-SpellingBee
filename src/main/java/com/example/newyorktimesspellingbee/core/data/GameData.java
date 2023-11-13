package com.example.newyorktimesspellingbee.core.data;

/**
 * This class serves as a container for game data. It stores a list of words (words), a list of pangram words (pangramWords),
 * and a string of letters (letters).
 * The class provides getter methods to access these data elements.
 * It is used to hold and provide access to the current game's words, special pangram words, and the set of letters used in the game.
 */

import java.util.List;
public class GameData {
    private final List<String> words;
    private final List<String> pangramWords;
    private final String letters;

    public GameData(List<String> words, List<String> pangramWords, String letters) {
        this.words = words;
        this.pangramWords = pangramWords;
        this.letters = letters;
    }

    public List<String> getWords() {
        return words;
    }

    public List<String> getPangramWords() {
        return pangramWords;
    }

    public String getLetters() {
        return letters;
    }
}
