package org.example.Model.CacheData;

public class CacheData {
    private long tag;
    private int validBit;
    private int dirtyBit;

    // Getter
    public long GetTag() { return tag; }
    public int GetValidBit() { return validBit; }
    public int GetDirtyBit() { return dirtyBit; }

    // Setter
    public void SetTag(long tag) { this.tag = tag; }
    public void SetValidBit(int validBit) { this.validBit = validBit; }
    public void SetDirtyBit(int dirtyBit) { this.dirtyBit = dirtyBit; }
}
