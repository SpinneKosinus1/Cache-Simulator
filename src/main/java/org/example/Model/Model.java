package org.example.Model;

import java.util.List;

public class Model {
    // Variables
    private int blocknumber, blocksize, associativity;
    private int tag, index, offset;

    private int[][] cachedata;

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

    public void StartSimulation() {

    }
}
