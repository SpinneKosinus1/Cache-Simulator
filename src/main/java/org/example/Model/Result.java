package org.example.Model;

public class Result {
    private final String fileName;
    private final String blockNumber;
    private final String blockSize;
    private final String associativity;
    private final String replacement;
    private final String writeHit;
    private final String writeMiss;

    private final String cacheReadMissRate;
    private final String cacheWriteMissRate;
    private final String cacheEvictions;

    public Result(String fileName, String blockNumber, String blockSize, String associativity, String replacment,
                  String writeHit, String writeMiss, String cacheReadMissRate, String cacheWriteMissRate, String cacheEvictions) {
        this.fileName = fileName;
        this.blockNumber = blockNumber;
        this.blockSize = blockSize;
        this.associativity = associativity;
        this.replacement = replacment;
        this.writeHit = writeHit;
        this.writeMiss = writeMiss;
        this.cacheReadMissRate = cacheReadMissRate;
        this.cacheWriteMissRate = cacheWriteMissRate;
        this.cacheEvictions = cacheEvictions;
    }

    public String getFileName() {
        return fileName;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public String getBlockSize() {
        return blockSize;
    }

    public String getAssociativity() {
        return associativity;
    }

    public String getReplacement() {
        return replacement;
    }

    public String getWriteHit() {
        return writeHit;
    }

    public String getWriteMiss() {
        return writeMiss;
    }

    public String getCacheReadMissRate() {
        return cacheReadMissRate;
    }

    public String getCacheWriteMissRate() {
        return cacheWriteMissRate;
    }

    public String getCacheEvictions() {
        return cacheEvictions;
    }


}
