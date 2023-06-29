package org.example.Model.CacheHandler;

import org.example.Model.Memory.Cache;
import org.example.Model.Memory.Memory;

public abstract class DataHandler {
    public abstract Memory CacheDataOperation(long loopCounter, String tag, String index, int associativity, String offset,
                                             String Replacement, String writeHit, String writeMiss, Memory memory);


}
