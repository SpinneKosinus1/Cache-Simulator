package org.example.Model;

import org.example.Model.Memory.*;
import org.example.Model.CacheHandler.DataHandler;
import org.example.Model.CacheHandler.DataLoader;
import org.example.Model.CacheHandler.DataSaver;
import org.example.Model.Utilities.ConvertNumber;
import org.example.Model.Utilities.FileLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Model {
    // Classes
    DataHandler dataHandler;
    Memory memory;

    // Variables
    private final int blockNumber, blockSize, associativity;
    private int indexLength, offsetLength, tagLength;

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
        memory = new Memory((blockNumber / associativity), associativity, blockSize);
        String tag, index, offset;
        long loopCounter = 0;

//        List<Integer> ramData = new ArrayList<>();
//        ramData.add(5);
//        ramData.add(4);
//        ramData.add(99);
//        ramData.add(76);
//        memory.SetRamBlock(ramData, "Testadresse");

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

            // Adding a ramblock for future calculations
            if (memory.GetRamBlock(tag + index) == null) {
                List<Integer> ramData = new ArrayList<>();
                Random randomGenerator = new Random();

                for (int i = 0; i < blockSize; i++) {
                    int value = randomGenerator.nextInt(999);
                    ramData.add(value);
                }

                memory.SetRamBlock(ramData, tag + index);
            }

            if (operation == 0) { // Execute, when loading data
                dataHandler = new DataLoader();
                memory = dataHandler.CacheDataOperation(loopCounter, tag, index, associativity, offset, replacment,
                        writeHit, writeMiss, memory);
            }
            if (operation == 1) {
                dataHandler = new DataSaver();
                memory = dataHandler.CacheDataOperation(loopCounter, tag, index, associativity, offset, replacment,
                        writeHit, writeMiss, memory);
            }

            ++loopCounter;
        }
        return memory.getCache();
    }
}
