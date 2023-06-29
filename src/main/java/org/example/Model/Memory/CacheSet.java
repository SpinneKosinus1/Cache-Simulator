package org.example.Model.Memory;

import java.util.ArrayList;
import java.util.List;

public class CacheSet {
    private final List<CacheData> cacheSet;

    public CacheSet(int associativity, int blockSize) {
        cacheSet = new ArrayList<>();

        for (int i = 0; i < associativity; i++) {
            CacheData cacheData = new CacheData(blockSize);
            cacheSet.add(cacheData);
        }
    }

    public CacheData GetCacheBlock(int index) { return cacheSet.get(index); }
}
