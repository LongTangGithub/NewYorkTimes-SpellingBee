package com.example.newyorktimesspellingbee.core.managers;

import com.example.newyorktimesspellingbee.core.data.GameData;
import com.example.newyorktimesspellingbee.core.exceptions.*;

/**
 * The GameCreatorService interface defines methods for creating game data for the New York Times Spelling Bee game.
 * It outlines the contract for generating game data based on specific rules and criteria.
 */
public interface GameCreatorService {

    /**
     * Creates game data based on the provided set of letters. This method is responsible for
     * generating game data that meets certain criteria and throws exceptions if these criteria are not met.
     *
     * @param letters A string of letters used to create the game data.
     * @return GameData object representing the data necessary for a game instance.
     * @throws PangramNotFoundException If no pangram words are found in the data.
     * @throws IllegalWordCountException If the number of words does not meet the game's requirements.
     * @throws IllegalPointRangeException If the total points of the words are not within the acceptable range.
     * @throws IllegalLettersLengthException If the provided letters do not meet the required length.
     * @throws NotUniqueLettersException If the provided letters are not unique.
     * @throws IllegalLetterException If the provided letters contain illegal characters.
     */
    GameData create(String letters) throws PangramNotFoundException, IllegalWordCountException, IllegalPointRangeException,
                                           IllegalLettersLengthException, NotUniqueLettersException, IllegalLetterException;

    /**
     * Creates game data without a predefined set of letters. This method allows for the generation of game data
     * where the letters are determined internally rather than being provided.
     *
     * @return GameData object representing the data necessary for a game instance.
     * @throws PangramNotFoundException If no pangram words are found in the data.
     * @throws IllegalWordCountException If the number of words does not meet the game's requirements.
     * @throws IllegalPointRangeException If the total points of the words are not within the acceptable range.
     */
    GameData create() throws PangramNotFoundException, IllegalWordCountException, IllegalPointRangeException;

}
