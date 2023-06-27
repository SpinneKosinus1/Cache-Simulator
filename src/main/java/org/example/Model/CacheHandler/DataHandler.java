package org.example.Model.CacheHandler;

import org.example.Model.CacheData.Cache;

public abstract class DataHandler {
    public abstract Cache CacheDataOperation(long loopCounter, String tag, String index, int associativity,
                                             String Replacement, String writeHit, String writeMiss, Cache cache);


}
