package org.example.Model.CacheData;

import java.util.ArrayList;
import java.util.List;

public class Cache {
    // Variables
    List<CacheSet> cache;

    // The List
    private static List<Boolean> cacheReadHit, cacheWriteHit;
    private int cacheEvictions;

    public Cache(int index, int associativity) {
        cache = new ArrayList<>();
        cacheReadHit = new ArrayList<>();
        cacheWriteHit = new ArrayList<>();
        cacheEvictions = 0;

        for (int i = 0; i < index; i++) {
            CacheSet cacheSet = new CacheSet(associativity);
            cache.add(cacheSet);
        }
    }

    // Getter
    public CacheSet GetCacheSet(int index) { return cache.get(index); }

    //
    public void AddCacheReadHit(Boolean cacheHit) { cacheReadHit.add(cacheHit); }
    public double GetCacheReadHitPercentage() {
        int cacheHitNumber = 0;
        for (Boolean aBoolean : cacheReadHit) {
            if (aBoolean) {
                ++cacheHitNumber;
            }
        }
        return ((double) cacheHitNumber / cacheReadHit.size()) * 100;
    }

    public void AddCacheWriteHit(Boolean cacheHit) { cacheWriteHit.add(cacheHit); }
    public double GetCacheWriteHitPercentage() {
        int cacheHitNumber = 0;
        for (Boolean aBoolean : cacheWriteHit) {
            if (aBoolean) {
                ++cacheHitNumber;
            }
        }
        return ((double) cacheHitNumber / cacheWriteHit.size()) * 100;
    }

    public void AddCacheEvictions() { ++cacheEvictions; }
    public int GetCacheEvictions() { return cacheEvictions; }
}
