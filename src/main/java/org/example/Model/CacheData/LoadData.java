package org.example.Model.CacheData;

import org.example.Model.ConvertNumber;
import org.example.Model.CacheData.CacheData;

import java.util.Random;

public class LoadData extends DataHandler {
    @Override
    public Cache  CacheDataOperation(long loopCounter, String tag, String index, int associativity,
                                   String Replacement, String writeHit, String writeMiss, Cache cache) {

        int iIndex = ConvertNumber.binaryToDecimal(index);
        int iTag = ConvertNumber.binaryToDecimal(tag);

        // Check if requested Data is already saved
        for (int i = 0; i < associativity; i++) {
            if (cache.GetCacheBlock((iIndex * associativity) + i).GetTag() == iTag &&
                    cache.GetCacheBlock((iIndex * associativity) + i).GetValidBit() == 1) {
                cache.AddCacheReadHit(true);
                return cache;
            }
        }

        // Check if there is free space to store the values
        for (int i = 0; i < associativity; i++) {
            if (cache.GetCacheBlock((iIndex * associativity) + i).GetTag() == 0) {
                cache.GetCacheBlock((iIndex * associativity) + i).SetTag(iTag);
                cache.GetCacheBlock((iIndex * associativity) + i).SetValidBit(1);
                cache.AddCacheReadHit(false);
                return cache;
            }
        }

        if (associativity == 1) {
            cache.GetCacheBlock(iIndex * associativity).SetTag(iTag);
            cache.GetCacheBlock(iIndex * associativity).SetValidBit(1);
            cache.AddCacheReadHit(false);
            return cache;
        }

        // Now we need to replace the data with an new one
        if (Replacement.equals("Random")) {
            Random randomGenerator = new Random();
            int randomReplacement = randomGenerator.nextInt(associativity - 1);
            cache.GetCacheBlock((iIndex * associativity) + randomReplacement).SetTag(iTag);
            cache.GetCacheBlock((iIndex * associativity) + randomReplacement).SetValidBit(1);
            cache.AddCacheReadHit(false);
            return cache;
        }
        else if (Replacement.equals("FIFO")) {

        }
        return cache;
    }
}
