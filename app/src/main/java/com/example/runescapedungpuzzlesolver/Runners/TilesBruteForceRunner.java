package com.example.runescapedungpuzzlesolver.Runners;

import android.util.Log;

import com.example.runescapedungpuzzlesolver.Models.Tile;
import com.example.runescapedungpuzzlesolver.Models.Solution;

import java.util.BitSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.content.ContentValues.TAG;

public class TilesBruteForceRunner {
    private static final int TOTAL_SIZE=25;
    private HashSet<BitSet> checkedSolutions = new HashSet<>();
    private Queue<Solution> queue = new LinkedList<>();
    private final Tile[][] startingTiles;

    public TilesBruteForceRunner(Tile[][] startingTiles) {
        this.startingTiles = startingTiles;
    }

    public BitSet getSolution(){
        queue.add(new Solution(new BitSet()));
        ExecutorService executor = Executors.newFixedThreadPool(6);
        Log.d(TAG, "MYDEBUG getSolution: START");
        do{
            Solution solution = queue.poll();
            if(solution.isValid(startingTiles)){Log.d(TAG, "MYDEBUG getSolution: END"); return solution.getBitSet();}
            populateQueue(solution.getBitSet());
        } while (!queue.isEmpty());
        return null;

    }

    private void populateQueue(BitSet bitSet){
        //Log.d(TAG, "MYDEBUG populateQueue: START");
        for (int i = 0; i < TOTAL_SIZE; i++) {
            //Log.d(TAG, "MYDEBUG populateQueue: LOOP");
            if(!bitSet.get(i)) {
                //Log.d(TAG, "MYDEBUG populateQueue: MOVE");
                BitSet newBitSet = (BitSet) bitSet.clone();
                newBitSet.set(i,true);
                if(!checkedSolutions.contains(newBitSet)){
                    //Log.d(TAG, "MYDEBUG populateQueue: ADD");
                    queue.add(new Solution(newBitSet));
                    checkedSolutions.add(newBitSet);
                }
            }
        }
    }

}
