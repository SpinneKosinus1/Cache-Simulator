package org.example.Model.CacheHandler;

import org.example.Model.Memory.Cache;
import org.example.Model.Memory.Memory;
import org.example.Model.Utilities.ConvertNumber;
import java.util.Random;

public class LoadData extends DataHandler {
    @Override
    public Memory CacheDataOperation(long loopCounter, String tag, String index, int associativity,
                                     String Replacement, String writeHit, String writeMiss, Memory memory) {

        int iIndex = ConvertNumber.binaryToDecimal(index);
        int iTag = ConvertNumber.binaryToDecimal(tag);

        /*************************************************************************/
        /*              Check if requested Data is already saved                 */
        /*************************************************************************/

        for (int i = 0; i < associativity; i++) {
            if (memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).GetTag() == iTag &&
                    memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).GetValidBit() == 1) {

                memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).SetLastTimeUsed(loopCounter);
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
                memory.getCache().AddCacheReadHit(false);
                return memory;
            }
        }

        /*************************************************************************/
        /*         If there is no free space to store, replace an old one        */
        /*************************************************************************/

        if (associativity == 1) {
            return GetMemory(loopCounter, memory, iIndex, iTag, 0);
        }

        // Now we need to replace the data with a new one
        switch (Replacement) {
            case "Random" -> {
                Random randomGenerator = new Random();
                int position = randomGenerator.nextInt(associativity - 1);
                return GetMemory(loopCounter, memory, iIndex, iTag, position);
            }
            case "FIFO" -> {
                int position = 0;
                for (int i = 1; i < associativity; i++) {
                    if (memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).GetCreatedCount() <
                            memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).GetCreatedCount()) {
                        position = i;
                    }
                }
                return GetMemory(loopCounter, memory, iIndex, iTag, position);
            }
            case "LRU" -> {
                int position = 0;
                for (int i = 1; i < associativity; i++) {
                    if (memory.getCache().GetCacheSet(iIndex).GetCacheBlock(i).GetLastTimeUsed() <
                            memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).GetLastTimeUsed()) {
                        position = i;
                    }
                }
                return GetMemory(loopCounter, memory, iIndex, iTag, position);
            }
        }
        return memory;
    }

    private Memory GetMemory(long loopCounter, Memory memory, int iIndex, int iTag, int position) {
        memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetTag(iTag);
        memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetValidBit(1);
        memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetCreatedCount(loopCounter);
        memory.getCache().GetCacheSet(iIndex).GetCacheBlock(position).SetLastTimeUsed(loopCounter);
        memory.getCache().AddCacheReadHit(false);
        memory.getCache().AddCacheEvictions();
        return memory;
    }
}
