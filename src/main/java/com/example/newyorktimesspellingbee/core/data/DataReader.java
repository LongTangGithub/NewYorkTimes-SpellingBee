package com.example.newyorktimesspellingbee.core.data;

import java.util.List;

/**
 * This interface defines a contract for reading data, specifically with a method read() which returns a list of strings.
 * It is used for reading data from a source aka file and returning it in a list format.
 * The specific type of data read (e.g., words for the game) is not specified but can be inferred to be related to the game's content.
 */
public interface DataReader {
    List<String> read();
}
