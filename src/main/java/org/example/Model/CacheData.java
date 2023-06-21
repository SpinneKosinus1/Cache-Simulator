package org.example.Model;

import java.util.HashMap;

public class CacheData {
    private HashMap<String, String> hashMap;

    public CacheData() {
        hashMap = new HashMap<>();
    }

    public void LoadData(String tag, String index, int associativity, String replacment) {
        if (associativity == 1) {
            if (hashMap.containsKey(index)) {
                if (hashMap.get(index).equals(tag)) {
                    System.out.println("Cache Hit!!!");
                }
                else {
                    System.out.println("Cache Miss :(");
                    hashMap.put(index, tag);
                }
            }
            else {
                hashMap.put(index, tag);
                System.out.println("Cache Miss :(");
            }
            System.out.println();
        }
    }
}
