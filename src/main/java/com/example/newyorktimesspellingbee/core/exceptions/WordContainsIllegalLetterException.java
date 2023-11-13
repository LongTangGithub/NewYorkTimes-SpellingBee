package com.example.newyorktimesspellingbee.core.exceptions;

public class WordContainsIllegalLetterException extends Exception{
    public WordContainsIllegalLetterException(String message) {
        super(message);
    }
}
