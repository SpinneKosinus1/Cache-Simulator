package org.example.Model.CacheData;

public class SaveData extends DataHandler {
    @Override
    public Cache CacheDataOperation(long loopCounter, String tag, String index, int associativity,
                                   String Replacement, String writeHit, String writeMiss, Cache cache) {
        return cache;
    }
}
