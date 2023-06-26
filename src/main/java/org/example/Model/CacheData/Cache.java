package org.example.Model.CacheData;

import java.util.ArrayList;
import java.util.List;

public class Cache {
    // Variables
    CacheData[] cacheData;

    // The List
    private static List<Boolean> cacheReadHit, cacheWriteHit;
    private int cacheEvictions;

    public Cache(int blockNumber) {
        cacheData = new CacheData[blockNumber];
        cacheReadHit = new ArrayList<>();
        cacheWriteHit = new ArrayList<>();
        cacheEvictions = 0;

        for (int i = 0; i < blockNumber; i++) {
            CacheData CacheData = new CacheData();
            cacheData[i] = CacheData;
        }
    }

    // Getter
    public CacheData GetCacheBlock(int index) { return cacheData[index]; }
    public int GetCacheSize() { return cacheData.length; }

    // Setter
    public void SetCacheBlock(int index, CacheData cacheData) { this.cacheData[index] = cacheData; }

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
