package org.example.Model.Memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Ram {
    private HashMap<String, List<Integer>> ram;

    public Ram() {
        ram = new HashMap<>();
    }

    public void SetRamData(List<Integer> ramData, String address) {
        ram.put(address, ramData);
    }

    public List<Integer> GetRamData(String address) {
        return ram.get(address);
    }
}
