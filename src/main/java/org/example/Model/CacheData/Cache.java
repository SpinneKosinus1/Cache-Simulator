package org.example.Model.CacheData;

import java.util.ArrayList;
import java.util.List;

public class Cache {
    // Variables
    CacheData[] cacheData;

    // The List
    private static List<Boolean> cacheReadHit, cacheWriteHit, cacheEvictions;

    public Cache(int blockNumber) {
        cacheData = new CacheData[blockNumber];
        cacheReadHit = new ArrayList<Boolean>();

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


    public void AddCacheReadHit(Boolean cacheHit) { cacheReadHit.add(cacheHit); }
    public double GetCacheReadHitPercentage() {
        int cacheHitNumber = 0;
        for (int i = 0; i < cacheReadHit.size(); i++) {
            if (cacheReadHit.get(i)) {
                ++cacheHitNumber;
            }
        }
        return ((double) cacheHitNumber / cacheReadHit.size()) * 100;
    }
}
