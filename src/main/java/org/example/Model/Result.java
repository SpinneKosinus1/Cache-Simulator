package org.example.Model;

import javafx.beans.property.SimpleStringProperty;

public class Result {
    private SimpleStringProperty fileName;
    private SimpleStringProperty blockNumber;
    private SimpleStringProperty blockSize;
    private SimpleStringProperty associativity;
    private SimpleStringProperty replacement;
    private SimpleStringProperty writeHit;
    private SimpleStringProperty writeMiss;

    private SimpleStringProperty cacheReadHitRate;
    private SimpleStringProperty cacheWriteHitRate;
    private SimpleStringProperty cacheEvictions;

    public Result(String fileName, String blockNumber, String blockSize, String associativity, String replacment,
                  String writeHit, String writeMiss, String cacheReadHitRate, String cacheWriteHitRate, String cacheEvictions) {
        this.fileName = new SimpleStringProperty(fileName);
        this.blockNumber = new SimpleStringProperty(blockNumber);
        this.blockSize = new SimpleStringProperty(blockSize);
        this.associativity = new SimpleStringProperty(associativity);
        this.replacement = new SimpleStringProperty(replacment);
        this.writeHit = new SimpleStringProperty(writeHit);
        this.writeMiss = new SimpleStringProperty(writeMiss);
        this.cacheReadHitRate = new SimpleStringProperty(cacheReadHitRate);
        this.cacheWriteHitRate = new SimpleStringProperty(cacheWriteHitRate);
        this.cacheEvictions = new SimpleStringProperty(cacheEvictions);
    }

    // Getter
    public String GetFileName() { return fileName.get(); }
    public String GetBlockNumber() { return blockNumber.get(); }
    public String GetBlockSize() { return blockSize.get(); }
    public String GetAssociativity() { return associativity.get(); }
    public String GetReplacement() { return replacement.get(); }
    public String GetWriteHit() { return writeHit.get(); }
    public String GetWriteMiss() { return writeMiss.get(); }
    public String GetCacheReadHitRate() { return cacheReadHitRate.get(); }
    public String GetCacheWriteHitRate() { return cacheWriteHitRate.get(); }
    public String GetCacheEvictions() { return cacheEvictions.get(); }

    // Setter
    public void SetFileName(String fileName) { this.fileName = new SimpleStringProperty(fileName); }
}
