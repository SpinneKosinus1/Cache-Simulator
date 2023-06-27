package org.example.Model.CacheData;

import java.util.ArrayList;
import java.util.List;

public class CacheSet {
    private final List<CacheData> cacheSet;

    public CacheSet(int associativity) {
        cacheSet = new ArrayList<>();

        for (int i = 0; i < associativity; i++) {
            CacheData cacheData = new CacheData();
            cacheSet.add(cacheData);
        }
    }

    public CacheData GetCacheBlock(int index) { return cacheSet.get(index); }
}
