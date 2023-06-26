package org.example.Model;

import org.example.Model.CacheData.*;

import java.util.List;

public class Model {
    // Classes
    DataHandler dataHandler;
    Cache cache;

    // Variables
    private final int blockNumber, blockSize, associativity;
    private int indexLength, offsetLength, tagLength;
    private String tag, index, offset;

    private final String replacment, writeHit, writeMiss;

    // Constructor
    public Model(int blockNumber, int blockSize, int associativity, String replacment, String writeHit, String writeMiss) {
        this.blockNumber = blockNumber;
        this.blockSize = blockSize;
        this.associativity = associativity;
        this.writeMiss = writeMiss;
        this.writeHit = writeHit;
        this.replacment = replacment;
    }

    // Functions
    private void CalcLength(String address) {
        String tempAddress = "";

        // Remove all whitespaces
        address = address.replaceAll("\\s", "");

        // Loop through every char in the line and separate the operation and the address
        for (int i = 0; i < address.length(); i++) {
            if (1 < i && i < 10) {
                tempAddress = tempAddress + address.charAt(i);
            }
        }

        tempAddress = ConvertNumber.hexToBinary(tempAddress);

        // Subdivide the length in index, offset and tag
        int newBlockNumber = blockNumber / associativity;
        indexLength = (int) (Math.log(newBlockNumber) / Math.log(2));
        offsetLength = (int) (Math.log(blockSize) / Math.log(2));
        tagLength = tempAddress.length() - indexLength - offsetLength;
    }

    public Cache StartSimulation(String FilePath) {
        List<String> TraceFile = FileLoader.GetFileContent(FilePath);
        CalcLength(TraceFile.get(0));
        cache = new Cache(blockNumber);
        long loopCounter = 0;

        for (String TraceLine : TraceFile) {
            int operation = 99;
            String address = "";

            // Remove all whitespaces
            TraceLine = TraceLine.replaceAll("\\s", "");

            // Loop through every char in the line and separate the operation and the address
            for (int i = 0; i < TraceLine.length(); i++) {
                if (i == 1) {
                    operation = Integer.parseInt(String.valueOf(TraceLine.charAt(i)));
                } else if (1 < i && i < 10) {
                    address = address + TraceLine.charAt(i);
                }
            }

            // Transform the address from a hex number to a binary number
            address = ConvertNumber.hexToBinary(address);

            // Subdivide the address in index, offset and tag
            tag = "";
            index = "";
            offset = "";
            for (int i = 0; i < address.length(); i++) {
                if (i < tagLength) {
                    tag = tag + address.charAt(i);
                } else if (i < tagLength + indexLength) {
                    index = index + address.charAt(i);
                } else {
                    offset = offset + address.charAt(i);
                }
            }

            if (operation == 0) { // Execute, when loading data
                dataHandler = new LoadData();
                cache = dataHandler.CacheDataOperation(loopCounter, tag, index, associativity, replacment,
                        writeHit, writeMiss, cache);
            }
            if (operation == 1) {
                dataHandler = new SaveData();
                cache = dataHandler.CacheDataOperation(loopCounter, tag, index, associativity, replacment,
                        writeHit, writeMiss, cache);
            }

            ++loopCounter;
        }
        return cache;
    }
}
