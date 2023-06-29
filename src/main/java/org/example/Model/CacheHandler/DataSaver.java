package org.example.Model.CacheHandler;

import org.example.Model.Memory.Memory;
import org.example.Model.Utilities.ConvertNumber;

import java.util.List;
import java.util.Random;

public class DataSaver extends DataHandler {
    @Override
    public Memory CacheDataOperation(long loopCounter, String tag, String index, int associativity, String offset,
                                    String Replacement, String writeHit, String writeMiss, Memory memory) {
        int iIndex = ConvertNumber.binaryToDecimal(index);
        int iTag = ConvertNumber.binaryToDecimal(tag);
        int iOffset = ConvertNumber.binaryToDecimal(offset);

        Random randomGenerator = new Random();
        int SaveValue = randomGenerator.nextInt(999);

        if (writeMiss.equals("No Write Allocate")) {
            if (writeHit.equals("Write Through")) {
                for (int i = 0; i < associativity; i++) {
                    if (memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).GetTag() == iTag &&
                            memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).GetValidBit() == 1) {

                        memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).SetLastTimeUsed(loopCounter);

                        memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).SetSingleCacheData(iOffset, SaveValue);
                        memory.SetRamValue(SaveValue, iOffset, tag + index);

                        memory.getCache().AddCacheWriteHit(true);
                        return memory;
                    }
                }

                memory.getCache().AddCacheWriteHit(false);
                return memory;
            }
            else {
                for (int i = 0; i < associativity; i++) {
                    if (memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).GetTag() == iTag &&
                            memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).GetValidBit() == 1) {

                        memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).SetLastTimeUsed(loopCounter);
                        memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).SetDirtyBit(1);

                        memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).SetSingleCacheData(iOffset, SaveValue);

                        memory.getCache().AddCacheWriteHit(true);
                        return memory;
                    }
                }

                memory.getCache().AddCacheWriteHit(false);
                return memory;
            }
        }
        else {
            if (writeHit.equals("Write Through")) {
                for (int i = 0; i < associativity; i++) {
                    if (memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).GetTag() == iTag &&
                            memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).GetValidBit() == 1) {

                        memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).SetLastTimeUsed(loopCounter);

                        memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).SetSingleCacheData(iOffset, SaveValue);
                        memory.SetRamValue(SaveValue, iOffset, tag + index);

                        memory.getCache().AddCacheWriteHit(true);
                        return memory;
                    }
                }

                for (int i = 0; i < associativity; i++) {
                    if (memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).GetTag() == 0) {
                        memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).SetTag(iTag);
                        memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).SetValidBit(1);
                        memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).SetCreatedCount(loopCounter);
                        memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).SetLastTimeUsed(loopCounter);

                        List<Integer> ramData =  memory.GetRamBlock(tag + index);
                        memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).setCacheData(ramData);

                        memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).SetSingleCacheData(iOffset, SaveValue);
                        memory.SetRamValue(SaveValue, iOffset, tag + index);

                        memory.getCache().AddCacheWriteHit(false);
                        return memory;
                    }
                }

                if (associativity == 1) {
                    return GetMemory(loopCounter, memory, iIndex, iTag, index, tag, iOffset, 0, writeHit, SaveValue);
                }

                // Now we need to replace the data with a new one
                switch (Replacement) {
                    case "Random" -> {
                        int position = randomGenerator.nextInt(associativity - 1);
                        return GetMemory(loopCounter, memory, iIndex, iTag, index, tag, iOffset, position, writeHit, SaveValue);
                    }
                    case "FIFO" -> {
                        int position = 0;
                        for (int i = 1; i < associativity; i++) {
                            if (memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).GetCreatedCount() <
                                    memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).GetCreatedCount()) {
                                position = i;
                            }
                        }
                        return GetMemory(loopCounter, memory, iIndex, iTag, index, tag, iOffset, position, writeHit, SaveValue);
                    }
                    case "LRU" -> {
                        int position = 0;
                        for (int i = 1; i < associativity; i++) {
                            if (memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).GetLastTimeUsed() <
                                    memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).GetLastTimeUsed()) {
                                position = i;
                            }
                        }
                        return GetMemory(loopCounter, memory, iIndex, iTag, index, tag, iOffset, position, writeHit, SaveValue);
                    }
                }
                return memory;
            }
            else {
                for (int i = 0; i < associativity; i++) {
                    if (memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).GetTag() == iTag &&
                            memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).GetValidBit() == 1) {

                        memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).SetLastTimeUsed(loopCounter);
                        memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).SetDirtyBit(1);

                        List<Integer> ramData =  memory.GetRamBlock(tag + index);
                        memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).setCacheData(ramData);

                        memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).SetSingleCacheData(iOffset, SaveValue);

                        memory.getCache().AddCacheWriteHit(true);
                        return memory;
                    }
                }

                for (int i = 0; i < associativity; i++) {
                    if (memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).GetTag() == 0) {
                        memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).SetTag(iTag);
                        memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).SetValidBit(1);
                        memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).SetDirtyBit(1);
                        memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).SetCreatedCount(loopCounter);
                        memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).SetLastTimeUsed(loopCounter);

                        List<Integer> ramData =  memory.GetRamBlock(tag + index);
                        memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).setCacheData(ramData);

                        memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).SetSingleCacheData(iOffset, SaveValue);

                        memory.getCache().AddCacheWriteHit(false);
                        return memory;
                    }
                }

                if (associativity == 1) {
                    return GetMemory(loopCounter, memory, iIndex, iTag, index, tag, iOffset, 0, writeHit, SaveValue);
                }

                // Now we need to replace the data with a new one
                switch (Replacement) {
                    case "Random" -> {
                        int position = randomGenerator.nextInt(associativity - 1);
                        return GetMemory(loopCounter, memory, iIndex, iTag, index, tag, iOffset, position, writeHit, SaveValue);
                    }
                    case "FIFO" -> {
                        int position = 0;
                        for (int i = 1; i < associativity; i++) {
                            if (memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).GetCreatedCount() <
                                    memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).GetCreatedCount()) {
                                position = i;
                            }
                        }
                        return GetMemory(loopCounter, memory, iIndex, iTag, index, tag, iOffset, position, writeHit, SaveValue);
                    }
                    case "LRU" -> {
                        int position = 0;
                        for (int i = 1; i < associativity; i++) {
                            if (memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).GetLastTimeUsed() <
                                    memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).GetLastTimeUsed()) {
                                position = i;
                            }
                        }
                        return GetMemory(loopCounter, memory, iIndex, iTag, index, tag, iOffset, position, writeHit, SaveValue);
                    }
                }
                return memory;
            }
        }
    }

    private Memory GetMemory(long loopCounter, Memory memory, int iIndex, int iTag, String index, String tag, int offset, int position, String writeHit, int value) {
        if (writeHit.equals("Write Through")) {
            memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetTag(iTag);
            memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetValidBit(1);
            memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetCreatedCount(loopCounter);
            memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetLastTimeUsed(loopCounter);

            List<Integer> ramData =  memory.GetRamBlock(tag + index);
            memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).setCacheData(ramData);

            memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetSingleCacheData(offset, value);
            memory.SetRamValue(value, offset, tag + index);

            memory.getCache().AddCacheWriteHit(false);
            memory.getCache().AddCacheEvictions();
            return memory;
        }
        else {
            if (memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).GetDirtyBit() == 0) {
                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetTag(iTag);
                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetDirtyBit(1);
                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetValidBit(1);
                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetCreatedCount(loopCounter);
                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetLastTimeUsed(loopCounter);

                List<Integer> ramData =  memory.GetRamBlock(tag + index);
                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).setCacheData(ramData);

                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetSingleCacheData(offset, value);
                memory.SetRamValue(value, offset, tag + index);

                memory.getCache().AddCacheWriteHit(false);
                memory.getCache().AddCacheEvictions();
                return memory;
            }
            else {
                List<Integer> cacheData = memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).getCacheData();
                memory.SetRamBlock(cacheData, tag + index);

                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetTag(iTag);
                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetValidBit(1);
                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetDirtyBit(1);
                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetCreatedCount(loopCounter);
                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetLastTimeUsed(loopCounter);

                List<Integer> ramData =  memory.GetRamBlock(tag + index);
                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).setCacheData(ramData);

                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetSingleCacheData(offset, value);
                memory.SetRamValue(value, offset, tag + index);

                memory.getCache().AddCacheWriteHit(false);
                memory.getCache().AddCacheEvictions();
                return memory;
            }
        }
    }
}
