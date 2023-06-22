package org.example.Model;

import org.example.Model.CacheData.CacheData;
import org.example.Model.CacheData.DataHandler;
import org.example.Model.CacheData.LoadData;
import org.example.Model.CacheData.SaveData;

import java.util.List;

public class Model {
    // Classes
    CacheData[] cacheData;
    DataHandler dataHandler;

    // Variables
    private int blockNumber, blockSize, associativity;
    private int indexLength, offsetLength, tagLength;
    private String tag, index, offset;

    private String replacment, writeHit, writeMiss;

    private List<Boolean> cacheReadHit, cacheWriteHit, cacheEvictions;

    // Getter
    public CacheData GetCacheData(int position) { return cacheData[position]; }

    // Setter
    public void SetCacheData(int position, CacheData value) { cacheData[position] = value; }

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
            if (i == 1) {
            } else if (1 < i && i < 10) {
                tempAddress = tempAddress + address.charAt(i);
            }
        }

        tempAddress = ConvertNumber.hexToBinary(tempAddress);

        // Subdivide the length in index, offset and tag
        indexLength = (int) (Math.log(blockNumber) / Math.log(2) / associativity);
        offsetLength = (int) (Math.log(blockSize) / Math.log(2));
        tagLength = tempAddress.length() - indexLength - offsetLength;
    }

    public void StartSimulation(String FilePath) {
        List<String> TraceFile = FileLoader.GetFileContent(FilePath);
        CalcLength(TraceFile.get(0));
        cacheData = new CacheData[blockNumber];

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
                dataHandler.CacheDataOperation(loopCounter, tag, index, associativity, replacment, writeHit, writeMiss);
            }
            if (operation == 1) {
                dataHandler = new SaveData();
                dataHandler.CacheDataOperation(loopCounter, tag, index, associativity, replacment, writeHit, writeMiss);
            }

            ++loopCounter;
        }
    }
}
