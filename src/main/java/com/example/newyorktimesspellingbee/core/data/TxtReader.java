package com.example.newyorktimesspellingbee.core.data;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * TxtReader is a class implementing the DataReader interface, used for reading text data from a file.
 * It reads words from a specified text file, processes them, and returns them as a list of strings.
 */
public class TxtReader implements DataReader {
    private final String FILE_PATH = "data.txt";
    private final File file;   // File object representing the data file.
    private List<String> data; // Cached data read from the file.

    /**
     * Constructor for TxtReader.
     * Initializes the file object based on the specified FILE_PATH.
     */

    public TxtReader() {
        file = new File(getClass().getClassLoader().getResource(FILE_PATH).getFile());
    }

    /**
     * Reads words from the file and returns them as a list.
     * Words are filtered to exclude those with less than 4 characters.
     * Also performs normalization of specific accented characters to their basic Latin equivalents.
     * This is particularly useful for processing English text with borrowed words that may contain accents.
     *
     * @return List of strings representing the words read from the file.
     */
    @Override
    public List<String> read() {
        List<String> words = new ArrayList<>();
        try {
            if (data != null) return data; // Return cached data if already read

            Scanner scanner = new Scanner(file, StandardCharsets.UTF_8);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.length() > 3) { // Filter out words with less than 4 characters
                    line = replaceCharacters(line); // Normalize characters in the line
                    words.add(line);
                }
            }
            scanner.close();

        } catch (Exception exception) {
            // Exception handling (e.g., logging the error, returning an empty list, or rethrowing)
        }

        return data = words; // Cache and return the list of words
    }
    /**
     * Normalizes specific accented characters in the given string to their basic Latin equivalents.
     * This method is particularly useful for English texts where accented characters
     * might appear but need to be standardized to non-accented versions.
     *
     * @param str The string in which the characters are to be normalized.
     * @return A new string with the normalized characters.
     */

    private String replaceCharacters(String str) {
        str = str.toLowerCase();

        str = str.replaceAll("á", "a");
        str = str.replaceAll("é", "e");
        str = str.replaceAll("í", "i");
        str = str.replaceAll("ó", "o");
        str = str.replaceAll("ú", "u");
        str = str.replaceAll("ñ", "n");

        return str;
    }
}
