package org.example.Model;

import java.util.List;
import org.example.Model.CacheData;

public class Model {
    CacheData Cache;
    // Variables
    private int blocknumber, blocksize, associativity;
    private String tag, index, offset;

    private String replacment, writehit, writemiss;

    private List<Boolean> CacheReadHit, CacheWriteHit, CacheEvictions;

    // Constructor
    public Model(int blocknumber, int blocksize, int associativity, String replacment, String writehit, String writemiss) {
        this.blocknumber = blocknumber;
        this.blocksize = blocksize;
        this.associativity = associativity;
        this.writemiss = writemiss;
        this.writehit = writehit;
        this.replacment = replacment;
    }

    public void StartSimulation(String FilePath) {
        List<String> TraceFile = FileLoader.GetFileContent(FilePath);
        Cache = new CacheData();

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

            // Subdivide the length in index, offset and tag
            double indexLength = Math.log(blocknumber) / Math.log(2) / associativity;
            double offsetLength = Math.log(blocksize) / Math.log(2);
            double tagLength = address.length() - indexLength - offsetLength;

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

            if (operation == 0) { // Load Data
                Cache.LoadData(tag, index, associativity, replacment);
            }
            else if (operation == 1) { // Save Data
            }
        }
    }
}
