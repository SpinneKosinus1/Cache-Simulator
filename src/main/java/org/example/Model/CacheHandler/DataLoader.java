package org.example.Model.CacheHandler;

import org.example.Model.Memory.Memory;
import org.example.Model.Utilities.ConvertNumber;

import java.util.List;
import java.util.Random;

public class DataLoader extends DataHandler {
    @Override
    public Memory CacheDataOperation(long loopCounter, String tag, String index, int associativity, String offset,
                                     String Replacement, String writeHit, String writeMiss, Memory memory) {

        int iIndex = ConvertNumber.binaryToDecimal(index);
        int iTag = ConvertNumber.binaryToDecimal(tag);
        int iOffset = ConvertNumber.binaryToDecimal(offset);
        int loadedValue = -1;

        /*************************************************************************/
        /*              Check if requested Data is already saved                 */
        /*************************************************************************/

        for (int i = 0; i < associativity; i++) {
            if (memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).GetTag() == iTag &&
                    memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).GetValidBit() == 1) {

                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).SetLastTimeUsed(loopCounter);
                loadedValue = memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).GetSingleCacheData(iOffset);
                memory.getCache().AddCacheReadHit(true);
                return memory;
            }
        }


        /*************************************************************************/
        /*            Check if there is free space to store the values           */
        /*************************************************************************/

        for (int i = 0; i < associativity; i++) {
            if (memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).GetTag() == 0) {
                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).SetTag(iTag);
                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).SetValidBit(1);
                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).SetCreatedCount(loopCounter);
                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).SetLastTimeUsed(loopCounter);

                List<Integer> ramData =  memory.GetRamBlock(tag + index);
                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).setCacheData(ramData);
                loadedValue = memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).GetSingleCacheData(iOffset);

                memory.getCache().AddCacheReadHit(false);
                return memory;
            }
        }

        /*************************************************************************/
        /*         If there is no free space to store, replace an old one        */
        /*********************************************************+***************/

        if (associativity == 1) {
            return GetMemory(loopCounter, memory, iIndex, iTag, index, tag, iOffset, 0, writeHit);
        }

        // Now we need to replace the data with a new one
        switch (Replacement) {
            case "Random" -> {
                Random randomGenerator = new Random();
                int position = randomGenerator.nextInt(associativity - 1);
                return GetMemory(loopCounter, memory, iIndex, iTag, index, tag, iOffset, position, writeHit);
            }
            case "FIFO" -> {
                int position = 0;
                for (int i = 1; i < associativity; i++) {
                    if (memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).GetCreatedCount() <
                            memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).GetCreatedCount()) {
                        position = i;
                    }
                }
                return GetMemory(loopCounter, memory, iIndex, iTag, index, tag, iOffset, position, writeHit);
            }
            case "LRU" -> {
                int position = 0;
                for (int i = 1; i < associativity; i++) {
                    if (memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).GetLastTimeUsed() <
                            memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).GetLastTimeUsed()) {
                        position = i;
                    }
                }
                return GetMemory(loopCounter, memory, iIndex, iTag, index, tag, iOffset, position, writeHit);
            }
        }
        return memory;
    }

    private Memory GetMemory(long loopCounter, Memory memory, int iIndex, int iTag, String index, String tag, int offset, int position, String writeHit) {
        if (writeHit.equals("Write Through")) {
            memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetTag(iTag);
            memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetValidBit(1);
            memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetCreatedCount(loopCounter);
            memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetLastTimeUsed(loopCounter);

            List<Integer> ramData = memory.GetRamBlock(tag + index);
            memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).setCacheData(ramData);
            int loadedValue = memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).GetSingleCacheData(position);

            memory.getCache().AddCacheReadHit(false);
            memory.getCache().AddCacheEvictions();
            return memory;
        }
        else {
            if (memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).GetDirtyBit() == 0) {
                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetTag(iTag);
                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetValidBit(1);
                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetCreatedCount(loopCounter);
                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetLastTimeUsed(loopCounter);

                List<Integer> ramData = memory.GetRamBlock(tag + index);
                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).setCacheData(ramData);
                int loadedValue = memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).GetSingleCacheData(position);

                memory.getCache().AddCacheReadHit(false);
                memory.getCache().AddCacheEvictions();
                return memory;
            }
            else {
                List<Integer> cacheData = memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).getCacheData();
                memory.SetRamBlock(cacheData, tag + index);

                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetTag(iTag);
                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetValidBit(1);
                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetCreatedCount(loopCounter);
                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetLastTimeUsed(loopCounter);

                List<Integer> ramData = memory.GetRamBlock(tag + index);
                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).setCacheData(ramData);
                int loadedValue = memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).GetSingleCacheData(position);

                memory.getCache().AddCacheReadHit(false);
                memory.getCache().AddCacheEvictions();
                return memory;
            }
        }
    }
}
