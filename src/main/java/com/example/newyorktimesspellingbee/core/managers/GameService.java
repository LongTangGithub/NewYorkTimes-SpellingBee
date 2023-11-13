package com.example.newyorktimesspellingbee.core.managers;

import com.example.newyorktimesspellingbee.core.exceptions.*;
import com.example.newyorktimesspellingbee.core.results.PointResult;

/**
 * The GameService interface defines methods for interacting with the game logic of the New York Times Spelling Bee game.
 * It outlines the contract for checking words and determining points within the game.
 */
public interface GameService {

    /**
     * Checks the validity of an input word against game rules and criteria.
     * Throws various exceptions if the input word does not comply with the game rules.
     *
     * @param inputWord The word input by the player to be checked.
     * @return PointResult object representing the points earned by the input word and its validity status.
     * @throws DictionaryDoesNotContainWordException If the input word is not found in the game's dictionary.
     * @throws IllegalWordLengthException If the input word does not meet the minimum length requirement.
     * @throws WordContainsIllegalLetterException If the input word contains letters not present in the game.
     * @throws WordDoesNotContainCenterLetterException If the input word does not contain the required center letter.
     * @throws WordAlreadyFoundException If the input word has already been found in the current game session.
     */
    PointResult check(String inputWord) throws DictionaryDoesNotContainWordException, IllegalWordLengthException,
                                               WordContainsIllegalLetterException, WordDoesNotContainCenterLetterException,
                                               WordAlreadyFoundException;
    /**
     * Retrieves the maximum possible point score for the current game configuration.
     *
     * @return An integer representing the maximum points that can be earned in the game.
     */
    int getMaximumPoint();
}
