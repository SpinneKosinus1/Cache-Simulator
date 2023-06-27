package org.example.Model.CacheHandler;

import org.example.Model.CacheData.Cache;
import org.example.Model.Utilities.ConvertNumber;
import java.util.Random;

public class LoadData extends DataHandler {
    @Override
    public Cache CacheDataOperation(long loopCounter, String tag, String index, int associativity,
                                    String Replacement, String writeHit, String writeMiss, Cache cache) {

        int iIndex = ConvertNumber.binaryToDecimal(index);
        int iTag = ConvertNumber.binaryToDecimal(tag);

        /*************************************************************************/
        /*              Check if requested Data is already saved                 */
        /*************************************************************************/

        for (int i = 0; i < associativity; i++) {
            if (cache.GetCacheSet(iIndex).GetCacheBlock(i).GetTag() == iTag &&
                    cache.GetCacheSet(iIndex).GetCacheBlock(i).GetValidBit() == 1) {

                cache.GetCacheSet(iIndex).GetCacheBlock(i).SetLastTimeUsed(loopCounter);
                cache.AddCacheReadHit(true);
                return cache;
            }
        }


        /*************************************************************************/
        /*            Check if there is free space to store the values           */
        /*************************************************************************/

        for (int i = 0; i < associativity; i++) {
            if (cache.GetCacheSet(iIndex).GetCacheBlock(i).GetTag() == 0) {
                cache.GetCacheSet(iIndex).GetCacheBlock(i).SetTag(iTag);
                cache.GetCacheSet(iIndex).GetCacheBlock(i).SetValidBit(1);
                cache.GetCacheSet(iIndex).GetCacheBlock(i).SetCreatedCount(loopCounter);
                cache.GetCacheSet(iIndex).GetCacheBlock(i).SetLastTimeUsed(loopCounter);
                cache.AddCacheReadHit(false);
                return cache;
            }
        }

        /*************************************************************************/
        /*         If there is no free space to store, replace an old one        */
        /*************************************************************************/

        if (associativity == 1) {
            return getCache(loopCounter, cache, iIndex, iTag, 0);
        }

        // Now we need to replace the data with a new one
        switch (Replacement) {
            case "Random" -> {
                Random randomGenerator = new Random();
                int position = randomGenerator.nextInt(associativity - 1);
                return getCache(loopCounter, cache, iIndex, iTag, position);
            }
            case "FIFO" -> {
                int position = 0;
                for (int i = 1; i < associativity; i++) {
                    if (cache.GetCacheSet(iIndex).GetCacheBlock(i).GetCreatedCount() <
                            cache.GetCacheSet(iIndex).GetCacheBlock(position).GetCreatedCount()) {
                        position = i;
                    }
                }
                return getCache(loopCounter, cache, iIndex, iTag, position);
            }
            case "LRU" -> {
                int position = 0;
                for (int i = 1; i < associativity; i++) {
                    if (cache.GetCacheSet(iIndex).GetCacheBlock(i).GetLastTimeUsed() <
                            cache.GetCacheSet(iIndex).GetCacheBlock(position).GetLastTimeUsed()) {
                        position = i;
                    }
                }
                return getCache(loopCounter, cache, iIndex, iTag, position);
            }
        }
        return cache;
    }

    private Cache getCache(long loopCounter, Cache cache, int iIndex, int iTag, int position) {
        cache.GetCacheSet(iIndex).GetCacheBlock(position).SetTag(iTag);
        cache.GetCacheSet(iIndex).GetCacheBlock(position).SetValidBit(1);
        cache.GetCacheSet(iIndex).GetCacheBlock(position).SetCreatedCount(loopCounter);
        cache.GetCacheSet(iIndex).GetCacheBlock(position).SetLastTimeUsed(loopCounter);
        cache.AddCacheReadHit(false);
        cache.AddCacheEvictions();
        return cache;
    }
}
