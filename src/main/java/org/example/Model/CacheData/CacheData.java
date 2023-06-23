package org.example.Model.CacheData;

public class CacheData {
    private long tag;
    private int validBit;
    private int dirtyBit;
    private long createdCount;
    private long  lastTimeUsed;

    // Constructor
    public CacheData() {
        tag = 0;
        validBit = 0;
        dirtyBit = 0;
        createdCount = 0;
        lastTimeUsed = 0;
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
}
