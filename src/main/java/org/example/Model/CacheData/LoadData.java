package org.example.Model.CacheData;

import org.example.Model.ConvertNumber;

public class LoadData extends DataHandler {
    @Override
    public void CacheDataOperation(long loopCounter, String tag, String index, int associativity,
                                   String Replacement, String writeHit, String writeMiss) {
        if (Replacement.equals("Random")) {
            long iIndex = ConvertNumber.binaryToDecimal(index);
            long iTag = ConvertNumber.binaryToDecimal(tag);


        }
    }
}
