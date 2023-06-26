package org.example.Model.CacheData;

import org.example.Model.ConvertNumber;

import java.util.Random;

public class SaveData extends DataHandler {
    @Override
    public Cache CacheDataOperation(long loopCounter, String tag, String index, int associativity,
                                   String Replacement, String writeHit, String writeMiss, Cache cache) {
        int iIndex = ConvertNumber.binaryToDecimal(index);
        int iTag = ConvertNumber.binaryToDecimal(tag);

        if (writeMiss.equals("No Write Allocate")) {
            // Check if requested Data is already saved
            for (int i = 0; i < associativity; i++) {
                if (cache.GetCacheBlock((iIndex * associativity) + i).GetTag() == iTag &&
                        cache.GetCacheBlock((iIndex * associativity) + i).GetValidBit() == 1) {
                    cache.GetCacheBlock((iIndex * associativity) + i).SetLastTimeUsed(loopCounter);
                    cache.AddCacheWriteHit(true);
                    return cache;
                }
            }

            cache.AddCacheWriteHit(false);
            return cache;
        }
        else if (writeMiss.equals("Write Allocate")) {
            // Check if requested Data is already saved
            for (int i = 0; i < associativity; i++) {
                if (cache.GetCacheBlock((iIndex * associativity) + i).GetTag() == iTag &&
                        cache.GetCacheBlock((iIndex * associativity) + i).GetValidBit() == 1) {
                    cache.GetCacheBlock((iIndex * associativity) + i).SetLastTimeUsed(loopCounter);
                    cache.AddCacheWriteHit(true);
                    return cache;
                }
            }

            // Check if there is free space to store the values
            for (int i = 0; i < associativity; i++) {
                if (cache.GetCacheBlock((iIndex * associativity) + i).GetTag() == 0) {
                    cache.GetCacheBlock((iIndex * associativity) + i).SetTag(iTag);
                    cache.GetCacheBlock((iIndex * associativity) + i).SetValidBit(1);
                    cache.GetCacheBlock((iIndex * associativity) + i).SetCreatedCount(loopCounter);
                    cache.GetCacheBlock((iIndex * associativity) + i).SetLastTimeUsed(loopCounter);
                    cache.AddCacheReadHit(false);
                    return cache;
                }
            }

            if (associativity == 1) {
                return getCache(loopCounter, associativity, cache, writeHit, iIndex, iTag, 0);
            }

            // Now we need to replace the data with a new one
            switch (Replacement) {
                case "Random" -> {
                    Random randomGenerator = new Random();
                    int position = randomGenerator.nextInt(associativity - 1);
                    return getCache(loopCounter, associativity, cache, writeHit, iIndex, iTag, position);
                }
                case "FIFO" -> {
                    int position = 0;
                    for (int i = 1; i < associativity; i++) {
                        if (cache.GetCacheBlock((iIndex * associativity) + i).GetCreatedCount() <
                                cache.GetCacheBlock((iIndex * associativity) + position).GetCreatedCount()) {
                            position = i;
                        }
                    }
                    return getCache(loopCounter, associativity, cache, writeHit, iIndex, iTag, position);
                }
                case "LRU" -> {
                    int position = 0;
                    for (int i = 1; i < associativity; i++) {
                        if (cache.GetCacheBlock((iIndex * associativity) + i).GetLastTimeUsed() <
                                cache.GetCacheBlock((iIndex * associativity) + position).GetLastTimeUsed()) {
                            position = i;
                        }
                    }
                    return getCache(loopCounter, associativity, cache, writeHit, iIndex, iTag, position);
                }
            }
            return cache;
        }
        return cache;
    }

    private Cache getCache(long loopCounter, int associativity, Cache cache, String writeHit, int iIndex, int iTag, int position) {
        cache.GetCacheBlock((iIndex * associativity) + position).SetTag(iTag);
        cache.GetCacheBlock((iIndex * associativity) + position).SetValidBit(1);
        if (writeHit.equals("Write Back")) { cache.GetCacheBlock((iIndex * associativity) + position).SetDirtyBit(1);}
        cache.GetCacheBlock((iIndex * associativity) + position).SetCreatedCount(loopCounter);
        cache.GetCacheBlock((iIndex * associativity) + position).SetLastTimeUsed(loopCounter);
        cache.AddCacheWriteHit(false);
        cache.AddCacheEvictions();
        return cache;
    }
}
