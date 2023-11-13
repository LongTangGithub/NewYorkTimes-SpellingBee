package com.example.newyorktimesspellingbee.core.data;

import java.util.List;
public interface DataFilter {

    //  takes a list of words and a string of letters as input
    FilteredData filter(List<String> words, String letters);

    // only takes a list of words as input
    FilteredData filter(List<String> words);
}
