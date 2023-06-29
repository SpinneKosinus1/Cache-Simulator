package org.example.Model.Memory;

import java.util.ArrayList;
import java.util.List;

public class CacheData {
    private List<Integer> cacheData;
    private long tag;

    private int validBit;
    private int dirtyBit;
    private long createdCount;
    private long  lastTimeUsed;

    // Constructor
    public CacheData(int blockSize) {
        tag = 0;
        validBit = 0;
        dirtyBit = 0;
        createdCount = 0;
        lastTimeUsed = 0;
        cacheData = new ArrayList<>();

        for (int i = 0; i < blockSize; i++) {
            cacheData.add(0);
        }
    }

    // Getter
    public long GetTag() { return tag; }
    public int GetValidBit() { return validBit; }
    public int GetDirtyBit() { return dirtyBit; }
    public long GetCreatedCount() { return createdCount; };
    public long GetLastTimeUsed() { return lastTimeUsed; }

    // Setter
    public void SetTag(int tag) { this.tag = tag; }
    public void SetValidBit(int validBit) { this.validBit = validBit; }
    public void SetDirtyBit(int dirtyBit) { this.dirtyBit = dirtyBit; }
    public void SetCreatedCount(long createdCount) { this.createdCount = createdCount; };
    public void SetLastTimeUsed(long lastTimeUsed) { this.lastTimeUsed = lastTimeUsed; };

    public List<Integer> getCacheData() { return cacheData; }

    public void setCacheData(List<Integer> cacheData) { this.cacheData = cacheData; }

    public int GetSingleCacheData(int index) { return cacheData.get(index); }

    public void SetSingleCacheData(int index, int data) { cacheData.set(index, data); }
}
