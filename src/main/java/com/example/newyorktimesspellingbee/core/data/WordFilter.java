package com.example.newyorktimesspellingbee.core.data;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * WordFilter is a class that implements the DataFilter interface.
 * It is used for filtering and processing a list of words according to specific criteria
 * relevant to the New York Times Spelling Bee game.
 */
public class WordFilter implements DataFilter{

    /**
     * Filters words by randomly selecting a pangram and then filtering the list based on the unique letters
     * in the pangram. Returns a FilteredData object containing words that match the filtering criteria.
     *
     * @param words The list of words to be filtered.
     * @return FilteredData containing the filtered list of words.
     */
    @Override
    public FilteredData filter(List<String> words) {
        Random random = new Random();
        List<String> pangramWords = findPangrams(words);
        String randomPangram = pangramWords.get(random.nextInt(pangramWords.size()));
        List<Character> uniqueLetters = getUniqueLetters(randomPangram);

        StringBuilder letters = new StringBuilder();
        mixLetters(uniqueLetters);
        uniqueLetters.forEach(letters::append);

        return filter(words, letters.toString());
    }

    /**
     * Filters words based on provided letters. It applies multiple filters like excluding words
     * that contain unused letters, not containing the center letter, or appearing more than once.
     *
     * @param words The list of words to be filtered.
     * @param letters The string of letters used for filtering.
     * @return FilteredData containing the filtered list of words and pangrams.
     */
    @Override
    public FilteredData filter(List<String> words, String letters) {
        char centerLetter = letters.charAt(letters.length() / 2);

        List<String> firstFilter = filterWordsContainUnusedLetters(words, letters);
        List<String> secondFilter = filterWordsNotContainCenterLetter(firstFilter, centerLetter);
        List<String> thirdFilter = filterWordsAppearMoreThanOnce(secondFilter);
        List<String> pangramWords = findPangrams(thirdFilter, letters);

        return new FilteredData(thirdFilter, pangramWords, letters);
    }

    /**
     * Finds pangram words from a list of words. A pangram is a word that uses all seven unique letters.
     *
     * @param words The list of words to search for pangrams.
     * @return A list of pangram words.
     */
    private List<String> findPangrams(List<String> words) {
        List<String> pangramWords = new LinkedList<>();

        for (String word : words) {
            int uniqueLetterCount = getUniqueLetterCount(word);
            if (uniqueLetterCount == 7) {
                pangramWords.add(word);
            }
        }

        return pangramWords;
    }

    /**
     * Retrieves a list of unique characters from a given word.
     *
     * @param word The word from which to extract unique characters.
     * @return A list of unique characters.
     */
    private List<Character> getUniqueLetters(String word) {
        List<Character> uniqueCharacters = new LinkedList<>();

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (!uniqueCharacters.contains(ch)) {
                uniqueCharacters.add(ch);
            }
        }

        return uniqueCharacters;
    }

    /**
     * Counts the number of unique letters in a given word.
     *
     * @param word The word to count unique letters in.
     * @return The count of unique letters.
     */
    private int getUniqueLetterCount(String word) {
        List<Character> characters = new LinkedList<>();
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (!characters.contains(ch)) {
                characters.add(ch);
            }
        }

        return characters.size();
    }

    /**
     * Finds pangram words from a list of words based on a given set of letters.
     *
     * @param words The list of words to search for pangrams.
     * @param letters The set of letters to use for identifying pangrams.
     * @return A list of pangram words.
     */
    private List<String> findPangrams(List<String> words, String letters) {
        List<String> pangramWords = new LinkedList<>();
        for (String word : words) {
            if (isWordPangram(word, letters)) {
                pangramWords.add(word);
            }
        }

        return pangramWords;
    }

    /**
     * Determines if a given word is a pangram based on a set of letters.
     *
     * @param word The word to check.
     * @param letters The set of letters to use for checking.
     * @return true if the word is a pangram, false otherwise.
     */
    private boolean isWordPangram(String word, String letters) {
        int letterCount = 0;
        for (int i = 0; i < letters.length(); i++) {
            for (int j = 0; j < word.length(); j++) {
                char ch = word.charAt(j);
                if (ch == letters.charAt(i)) {
                    letterCount++;
                    break;
                }
            }
        }

        return letterCount == letters.length();
    }

    /**
     * Filters out words that contain letters not present in the given string of letters.
     *
     * @param words The list of words to be filtered.
     * @param letters The string of letters used for filtering.
     * @return A list of words that do not contain unused letters.
     */
    private List<String> filterWordsContainUnusedLetters(List<String> words, String letters) {
        List<String> filteredWords = new LinkedList<>();
        for (String word : words) {
            boolean containsUnusedLetter = false;
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                if (!lettersContainCharacter(letters, ch)) {
                    containsUnusedLetter = true;
                    break;
                }
            }

            if (!containsUnusedLetter) {
                filteredWords.add(word);
            }
        }

        return filteredWords;
    }

    /**
     * Filters out words that do not contain the specified center letter.
     *
     * @param words The list of words to be filtered.
     * @param centerLetter The center letter that must be present in the words.
     * @return A list of words that contain the center letter.
     */
    private List<String> filterWordsNotContainCenterLetter(List<String> words, char centerLetter) {
        List<String> filteredWords = new LinkedList<>();
        for (String word : words) {
            boolean containsCenterLetter = false;

            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                if (ch == centerLetter) {
                    containsCenterLetter = true;
                    break;
                }
            }

            if (containsCenterLetter) {
                filteredWords.add(word);
            }
        }

        return filteredWords;
    }

    /**
     * Filters out duplicate words, ensuring each word appears only once in the list.
     *
     * @param words The list of words to be filtered.
     * @return A list of unique words.
     */
    private List<String> filterWordsAppearMoreThanOnce(List<String> words) {
        List<String> filteredWords = new LinkedList<>();

        for (String word : words) {
            if (!filteredWords.contains(word)) {
                filteredWords.add(word);
            }
        }

        return filteredWords;
    }

    /**
     * Randomly mixes the letters in a given list.
     *
     * @param letters The list of characters to be mixed.
     */
    private void mixLetters(List<Character> letters) {
        Random random = new Random();

        for (int i = 0; i < letters.size(); i++) {
            int randomIndex = random.nextInt(letters.size());
            char temp = letters.get(randomIndex);
            letters.set(randomIndex, letters.get(i));
            letters.set(i, temp);
        }

    }

    /**
     * Checks if a given string of letters contains a specific character.
     *
     * @param letters The string of letters to check.
     * @param character The character to look for in the string.
     * @return true if the character is found in the string, false otherwise.
     */
    private boolean lettersContainCharacter(String letters, char character) {
        for (int i = 0; i < letters.length(); i++) {
            char ch = letters.charAt(i);
            if (ch == character)
                return true;
        }

        return false;
    }
}
