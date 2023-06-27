package org.example.Model;

import javafx.beans.property.SimpleStringProperty;

public class Result {
    SimpleStringProperty fileName;
    SimpleStringProperty blockNumber, blockSize, associativity;
    SimpleStringProperty replacement, writeHit, writeMiss;

    SimpleStringProperty cacheReadHitRate, cacheWriteHitRate;
    SimpleStringProperty cacheEvictions;

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
    SimpleStringProperty GetFileName() { return fileName; }
    SimpleStringProperty GetBlockNumber() { return blockNumber; }
    SimpleStringProperty GetBlockSize() { return blockSize; }
    SimpleStringProperty GetAssociativity() { return associativity; }
    SimpleStringProperty GetReplacement() { return replacement; }
    SimpleStringProperty GetWriteHit() { return writeHit; }
    SimpleStringProperty GetWriteMiss() { return writeMiss; }
    SimpleStringProperty GetCacheReadHitRate() { return cacheReadHitRate; }
    SimpleStringProperty GetCacheWriteHitRate() { return cacheWriteHitRate; }
    SimpleStringProperty GetcacheEvictions() { return cacheEvictions; }
}
