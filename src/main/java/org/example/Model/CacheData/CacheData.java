package org.example.Model.CacheData;

public class CacheData {
    private int tag;
    private int validBit;
    private int dirtyBit;

    // Constructor
    public CacheData() {
        tag = 0;
        validBit = 0;
        dirtyBit = 0;
    }

    // Getter
    public long GetTag() { return tag; }
    public int GetValidBit() { return validBit; }
    public int GetDirtyBit() { return dirtyBit; }

    // Setter
    public void SetTag(int tag) { this.tag = tag; }
    public void SetValidBit(int validBit) { this.validBit = validBit; }
    public void SetDirtyBit(int dirtyBit) { this.dirtyBit = dirtyBit; }
}
