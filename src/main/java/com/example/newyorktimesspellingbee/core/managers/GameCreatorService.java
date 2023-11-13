package com.example.newyorktimesspellingbee.core.managers;

import com.example.newyorktimesspellingbee.core.data.GameData;
import com.example.newyorktimesspellingbee.core.exceptions.*;
public interface GameCreatorService {
    GameData create(String letters) throws PangramNotFoundException, IllegalWordCountException, IllegalPointRangeException,
                                           IllegalLettersLengthException, NotUniqueLettersException, IllegalLetterException;

    GameData create() throws PangramNotFoundException, IllegalWordCountException, IllegalPointRangeException;

}
