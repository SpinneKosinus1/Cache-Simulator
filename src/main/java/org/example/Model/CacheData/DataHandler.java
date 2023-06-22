package org.example.Model.CacheData;

public abstract class DataHandler {
    public abstract void CacheDataOperation(long loopCounter, String tag, String index, int associativity,
                                            String Replacement, String writeHit, String writeMiss);


}
