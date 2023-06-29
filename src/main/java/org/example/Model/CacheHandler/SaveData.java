package org.example.Model.CacheHandler;

import org.example.Model.Memory.Cache;
import org.example.Model.Memory.Memory;
import org.example.Model.Utilities.ConvertNumber;

import java.util.Random;

public class SaveData extends DataHandler {
    @Override
    public Memory CacheDataOperation(long loopCounter, String tag, String index, int associativity,
                                    String Replacement, String writeHit, String writeMiss, Memory memory) {
        int iIndex = ConvertNumber.binaryToDecimal(index);
        int iTag = ConvertNumber.binaryToDecimal(tag);

        return memory;
    }
}
