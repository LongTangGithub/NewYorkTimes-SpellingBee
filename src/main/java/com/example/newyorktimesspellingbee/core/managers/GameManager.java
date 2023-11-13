package com.example.newyorktimesspellingbee.core.managers;

import com.example.newyorktimesspellingbee.core.constants.Messages;
import com.example.newyorktimesspellingbee.core.data.GameData;
import com.example.newyorktimesspellingbee.core.exceptions.*;
import com.example.newyorktimesspellingbee.core.results.PointResult;

import java.util.ArrayList;
import java.util.List;

/**
 * The GameManager class implements the GameService interface and manages the game logic
 * for the New York Times Spelling Bee game. It handles word validation, point calculation,
 * and tracking of found words and current points.
 */
public class GameManager implements GameService {
    private final int MIN_WORD_LENGTH = 4;
    private final GameData data;
    private int currentPoint = 0;
    private final List<String> foundWords;

    private final int maximumPoint;

    /**
     * Constructs a GameManager with the specified game data.
     *
     * @param data The GameData object containing the words and letters for the game.
     */
    public GameManager(GameData data) {
        this.data = data;
        foundWords = new ArrayList<>();
        maximumPoint = calculateMaximumPoint();
    }

    /**
     * Checks the validity of an input word against game rules and calculates points for it.
     * Throws various exceptions if the input word violates game rules.
     *
     * @param inputWord The word input by the player to be checked.
     * @return PointResult object representing the points earned by the input word and its validity status.
     * @throws DictionaryDoesNotContainWordException If the input word is not found in the game's dictionary.
     * @throws IllegalWordLengthException If the input word does not meet the minimum length requirement.
     * @throws WordContainsIllegalLetterException If the input word contains letters not present in the game.
     * @throws WordDoesNotContainCenterLetterException If the input word does not contain the required center letter.
     * @throws WordAlreadyFoundException If the input word has already been found in the current game session.
     */
    @Override
    public PointResult check(String inputWord) throws DictionaryDoesNotContainWordException, IllegalWordLengthException, WordContainsIllegalLetterException, WordDoesNotContainCenterLetterException, WordAlreadyFoundException {

        checkLength(inputWord);
        checkCenterLetter(inputWord);
        checkIllegalLetter(inputWord);
        checkDictionary(inputWord);
        checkFound(inputWord);
        int point = calculatePoint(inputWord);

        currentPoint += point;
        foundWords.add(inputWord);

        return new PointResult(inputWord, point, currentPoint);
    }

    /**
     * Checks if the input word has already been found in the current game session.
     * Throws an exception if the word has already been found.
     *
     * @param inputWord The word to be checked.
     * @throws WordAlreadyFoundException If the input word has already been found.
     */
    private void checkFound(String inputWord) throws WordAlreadyFoundException {
        if (foundWords.contains(inputWord)) {
            throw new WordAlreadyFoundException(Messages.WORD_ALREADY_FOUND);
        }
    }

    /**
     * Gets the current total points earned in the game.
     *
     * @return The current total points.
     */
    public int getCurrentPoint() {
        return currentPoint;
    }

    /**
     * Calculates the point value for a given input word.
     * Points are calculated differently for pangram words.
     *
     * @param inputWord The word for which to calculate the points.
     * @return The calculated point value for the input word.
     */
    private int calculatePoint(String inputWord) {
        if (data.getPangramWords().contains(inputWord))
            return inputWord.length() + MIN_WORD_LENGTH;

        return inputWord.length() - MIN_WORD_LENGTH + 1;
    }

    /**
     * Checks if the input word contains any illegal letters that are not part of the game data.
     * Throws an exception if an illegal letter is found.
     *
     * @param inputWord The word to be checked for illegal letters.
     * @throws WordContainsIllegalLetterException If the input word contains letters not in the game data.
     */
    private void checkIllegalLetter(String inputWord) throws WordContainsIllegalLetterException {
        String letters = data.getLetters();

        for (int i = 0; i < inputWord.length(); i++) {
            char ch = inputWord.charAt(i);
            if (!letters.contains(Character.toString(ch)))
                throw new WordContainsIllegalLetterException(Messages.WORD_CONTAINS_ILLEGAL_LETTER);
        }
    }

    /**
     * Checks if the input word contains the center letter required in the game.
     * Throws an exception if the center letter is not found in the word.
     *
     * @param inputWord The word to be checked for the center letter.
     * @throws WordDoesNotContainCenterLetterException If the input word does not contain the center letter.
     */
    private void checkCenterLetter(String inputWord) throws WordDoesNotContainCenterLetterException {
        char centerLetter = data.getLetters().charAt(data.getLetters().length() / 2);


        if (!inputWord.contains(Character.toString(centerLetter)))
            throw new WordDoesNotContainCenterLetterException(Messages.WORD_DOES_NOT_CONTAIN_CENTER_LETTER);

    }

    /**
     * Checks if the length of the input word is at least the minimum word length required in the game.
     * Throws an exception if the word is too short.
     *
     * @param inputWord The word to be checked for length.
     * @throws IllegalWordLengthException If the input word is shorter than the minimum required length.
     */
    private void checkLength(String inputWord) throws IllegalWordLengthException {
        if (inputWord.length() < MIN_WORD_LENGTH)
            throw new IllegalWordLengthException(Messages.ILLEGAL_WORD_LENGTH);
    }


    /**
     * Checks if the input word is contained within the game's dictionary.
     * Throws an exception if the word is not found in the dictionary.
     *
     * @param inputWord The word to be checked against the dictionary.
     * @throws DictionaryDoesNotContainWordException If the input word is not in the game's dictionary.
     */
    private void checkDictionary(String inputWord) throws DictionaryDoesNotContainWordException {
        if (!data.getWords().contains(inputWord))
            throw new DictionaryDoesNotContainWordException(Messages.DICTIONARY_DOES_NOT_CONTAIN_WORD);
    }

    /**
     * Calculates the maximum possible point score for the current game configuration.
     * This is based on the point values of all words available in the game data.
     *
     * @return An integer representing the maximum points that can be earned in the game.
     */
    private int calculateMaximumPoint() {
        int point = 0;

        for (String word : data.getWords()) {
            point += calculatePoint(word);
        }

        return point;
    }

    /**
     * Calculates the maximum possible point score for the current game configuration.
     *
     * @return An integer representing the maximum points that can be earned in the game.
     */
    @Override
    public int getMaximumPoint() {
        return maximumPoint;
    }
}
