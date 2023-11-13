package com.example.newyorktimesspellingbee.core.managers;

import com.example.newyorktimesspellingbee.core.constants.Messages;
import com.example.newyorktimesspellingbee.core.data.DataFilter;
import com.example.newyorktimesspellingbee.core.data.DataReader;
import com.example.newyorktimesspellingbee.core.data.FilteredData;
import com.example.newyorktimesspellingbee.core.data.GameData;
import com.example.newyorktimesspellingbee.core.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * GameCreatorManager is a class that implements the GameCreatorService interface.
 * It manages the creation of game data for the New York Times Spelling Bee game,
 * including word filtering and validation.
 */
public class GameCreatorManager implements GameCreatorService{

    private final DataReader dataReader;
    private final DataFilter dataFilter;
    private final List<String> selectedWords;
    private final Random r;
    private final String ENGLISH_LETTERS = "abcdefghijklmnopqrstuvwxyz";

    /**
     * Constructs a GameCreatorManager with specified data filter and data reader.
     *
     * @param dataFilter An instance of DataFilter for filtering words.
     * @param dataReader An instance of DataReader for reading word data.
     */
    public GameCreatorManager(DataFilter dataFilter, DataReader dataReader) {
        this.dataFilter = dataFilter;
        this.dataReader = dataReader;

        selectedWords = new ArrayList<>();
        r = new Random();
    }

    /**
     * Creates game data based on the provided letters.
     * Throws various exceptions if the data does not meet specific criteria.
     *
     * @param letters The letters to be used in the game.
     * @return GameData object containing the filtered game data.
     * @throws PangramNotFoundException If no pangram words are found.
     * @throws IllegalWordCountException If the word count is not within the acceptable range.
     * @throws IllegalPointRangeException If the total points are not within the acceptable range.
     * @throws IllegalLettersLengthException If the provided letters are not of the required length.
     * @throws NotUniqueLettersException If the letters are not unique.
     * @throws IllegalLetterException If the letters are not valid Turkish letters.
     */

    public GameData create(String letters) throws PangramNotFoundException, IllegalWordCountException, IllegalPointRangeException, IllegalLettersLengthException, NotUniqueLettersException, IllegalLetterException {

        lettersCheck(letters);

        FilteredData filteredData = filterWords(letters);

        firstStatusChecks(filteredData);

        List<String> filteredWords = filteredData.getWords();
        List<String> pangramWords = filteredData.getPangramWords();


        secondStatusChecks(filteredWords, pangramWords);

        return new GameData(filteredWords, pangramWords, letters);

    }
    /**
     * Creates game data without provided letters. Letters are chosen internally.
     * Throws exceptions if the data does not meet specific criteria.
     *
     * @return GameData object containing the filtered game data.
     * @throws PangramNotFoundException If no pangram words are found.
     * @throws IllegalWordCountException If the word count is not within the acceptable range.
     * @throws IllegalPointRangeException If the total points are not within the acceptable range.
     */

    public GameData create() throws PangramNotFoundException, IllegalWordCountException, IllegalPointRangeException {

        FilteredData filteredData = filterWords();

        firstStatusChecks(filteredData);
        String letters = filteredData.getLetters();
        List<String> filteredWords = filteredData.getWords();
        List<String> pangramWords = filteredData.getPangramWords();

        secondStatusChecks(filteredWords, pangramWords);

        return new GameData(filteredWords, pangramWords, letters);
    }


    /**
     * Checks if the total points of the filtered words are within an acceptable range.
     *
     * @param totalPoint The total points of the words.
     * @return true if the total points are between 100 and 400, inclusive.
     */
    private boolean totalPointAcceptable(int totalPoint) {
        return totalPoint >= 100 && totalPoint <= 400;
    }


    /**
     * Checks if the word count is within an acceptable range.
     *
     * @param wordCount The number of words.
     * @return true if the word count is between 20 and 80, inclusive.
     */
    private boolean wordCountAcceptable(int wordCount) {
        return wordCount >= 20 && wordCount <= 80;
    }

    /**
     * Determines if a word is a pangram based on the list of known pangram words.
     *
     * @param pangramWords A list of known pangram words.
     * @param word The word to check.
     * @return true if the word is a pangram, false otherwise.
     */
    private boolean isPangram(List<String> pangramWords, String word) {
        return pangramWords.contains(word);
    }

    /**
     * Calculates the point value of a word based on whether it is a pangram.
     *
     * @param pangramWords A list of known pangram words.
     * @param word The word for which to calculate the points.
     * @return The point value of the word.
     */
    private int getWordPoint(List<String> pangramWords, String word) {
        int sum = 0;
        if (!isPangram(pangramWords, word)) {
            sum += word.length() - 3;
        } else {
            sum += word.length() + 4;
        }
        return sum;
    }

    /**
     * Calculates the total points of a list of words.
     *
     * @param pangramWords A list of known pangram words.
     * @param words The list of words for which to calculate total points.
     * @return The total points of the list of words.
     */
    private int filteredWordsTotal(List<String> pangramWords, List<String> words) {
        int totalPoint = 0;
        for (String word : words) {
            totalPoint += getWordPoint(pangramWords, word);
        }
        return totalPoint;
    }

    /**
     * Checks the provided letters for compliance with game rules.
     *
     * @param letters The letters to be checked.
     * @throws IllegalLettersLengthException If the number of letters is not 7.
     * @throws NotUniqueLettersException If the letters are not unique.
     * @throws IllegalLetterException If the letters contain non-Turkish characters.
     */
    private void lettersCheck(String letters) throws IllegalLettersLengthException, NotUniqueLettersException, IllegalLetterException {
        if (letters.length() != 7) {
            throw new IllegalLettersLengthException(Messages.ILLEGAL_LETTERS_LENGTH);
        }
        checkIllegalLetter(letters);
        checkUniqueLetters(letters);
    }

    /**
     * Ensures that all letters in the given string are unique.
     *
     * @param letters The string of letters to be checked.
     * @throws NotUniqueLettersException If any letter is not unique within the string.
     */
    private void checkUniqueLetters(String letters) throws NotUniqueLettersException {
        for (int i = 0; i < letters.length(); i++) {
            for (int j = i + 1; j < letters.length(); j++) {
                if (letters.charAt(i) == letters.charAt(j)) {
                    throw new NotUniqueLettersException(Messages.NOT_UNIQUE_LETTERS);
                }
            }
        }
    }

    /**
     * Checks if the given letters are valid Turkish letters.
     *
     * @param letters The string of letters to be checked.
     * @throws IllegalLetterException If any letter is not a valid Turkish letter.
     */
    private void checkIllegalLetter(String letters) throws IllegalLetterException {
        boolean contains;
        String alphabet = ENGLISH_LETTERS;
        for (int i = 0; i < letters.length(); i++) {
            contains = false;
            for (int j = 0; j < alphabet.length(); j++) {
                if (letters.charAt(i) == alphabet.charAt(j)) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                throw new IllegalLetterException(Messages.ILLEGAL_LETTER);
            }
        }
    }

    /**
     * Performs initial status checks on the filtered data.
     *
     * @param filteredData The filtered data to be checked.
     * @throws PangramNotFoundException If no pangram words are found.
     * @throws IllegalWordCountException If the word count is not within the acceptable range.
     */
    private void firstStatusChecks(FilteredData filteredData) throws PangramNotFoundException, IllegalWordCountException {
        if (filteredData.getPangramWords().size() == 0) {
            throw new PangramNotFoundException(Messages.PANGRAM_NOT_FOUND);
        }
        if (!wordCountAcceptable(filteredData.getWords().size())) {
            throw new IllegalWordCountException(Messages.ILLEGAL_WORD_COUNT);
        }
    }

    /**
     * Performs secondary status checks on the filtered words and pangram words.
     *
     * @param filteredWords The list of filtered words.
     * @param pangramWords The list of pangram words.
     * @throws IllegalPointRangeException If the total points are not within the acceptable range.
     */
    private void secondStatusChecks(List<String> filteredWords, List<String> pangramWords) throws IllegalPointRangeException {
        if (!totalPointAcceptable(filteredWordsTotal(pangramWords, filteredWords))) {
            throw new IllegalPointRangeException(Messages.ILLEGAL_POINT_RANGE);
        }
    }

    /**
     * Filters words based on the given letters.
     *
     * @param letters The letters used for filtering.
     * @return FilteredData containing the filtered list of words.
     */
    private FilteredData filterWords(String letters) {
        return dataFilter.filter(readWords(), letters);
    }

    /**
     * Filters words without any specific letters.
     *
     * @return FilteredData containing the filtered list of words.
     */
    private FilteredData filterWords() {
        return dataFilter.filter(readWords());
    }

    /**
     * Reads words from a data source.
     *
     * @return A list of words read from the data source.
     */
    private List<String> readWords() {
        return dataReader.read();
    }
}
