package org.example.Model.Memory;

import java.util.List;

public class Memory {
    private Cache cache;
    private Ram ram;

    public Memory(int index, int associativity, int blockSize) {
        cache = new Cache(index, associativity, blockSize);
        ram = new Ram();
    }

    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public void SetRamBlock(List<Integer> ramData, String address) {
        ram.SetRamData(ramData, address);
    }

    public void SetRamValue(int value, int offset, String address) {
        List<Integer> ramData = ram.GetRamData(address);
        ramData.set(offset, value);
        ram.SetRamData(ramData, address);
    }

    public List<Integer> GetRamBlock(String address) {
        return ram.GetRamData(address);
    }

    public int GetRamValue(String address, int offset) {
        List<Integer> ramData = ram.GetRamData(address);
        return ramData.get(offset);
    }
}
