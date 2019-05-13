package com.example.runescapedungpuzzlesolver.Runners;

import android.util.Log;

import com.example.runescapedungpuzzlesolver.Models.TilesState;

import java.util.BitSet;

import static android.content.ContentValues.TAG;

public class TilesRunner {
    private static final int SIZE=5;
    private static final int TOTAL_SIZE=SIZE*SIZE;
    private final TilesState tilesState;

    public TilesRunner(TilesState tilesState) {
        this.tilesState = new TilesState(tilesState.getTiles());
    }
    public BitSet getSolution(){
        BitSet bitSet1 = getSolution(false);
        BitSet bitSet2 = getSolution(true);
        if(bitSet1==null) return bitSet2;
        if(bitSet2==null) return bitSet1;
        return bitSet1.cardinality()<bitSet2.cardinality() ? bitSet1 : bitSet2;

    }
    private BitSet getSolution(boolean checkLight){
        int lightCount = 0;
        int darkCount = 0;
        BitSet moves = new BitSet(TOTAL_SIZE);
        TilesState tilesState = new TilesState(this.tilesState.getTiles());
        for (int i = 0; i < TOTAL_SIZE; i++) {
            if(tilesState.get(i).isLight()) lightCount++; else darkCount++;
        }
        while (true) {
            int pointer = 0;
            while (pointer + SIZE < TOTAL_SIZE) {
                if (tilesState.get(pointer).isLight() == checkLight) {
                    tilesState.doTurn(pointer + SIZE);
                    moves.flip(pointer + SIZE);
                }
                pointer++;
            }
            String lastRow = "";
            for (int i = TOTAL_SIZE - SIZE; i < TOTAL_SIZE; i++) {
                lastRow += tilesState.get(i).isLight() != checkLight ? "0" : "1";
            }
            Log.d(TAG, "getSolution: LAST ROW: " + lastRow);
            switch (lastRow) {
                case "00000":
                    return moves;
                case "10001":
                    tilesState.doTurn(0);
                    moves.flip(0);
                    tilesState.doTurn(1);
                    moves.flip(1);
                    break;
                case "01010":
                    tilesState.doTurn(0);
                    moves.flip(0);
                    tilesState.doTurn(3);
                    moves.flip(3);
                    break;
                case "11100":
                    tilesState.doTurn(1);
                    moves.flip(1);
                    break;
                case "00111":
                    tilesState.doTurn(3);
                    moves.flip(3);
                    break;
                case "10110":
                    tilesState.doTurn(4);
                    moves.flip(4);
                    break;
                case "01101":
                    tilesState.doTurn(0);
                    moves.flip(0);
                    break;
                case "11011":
                    tilesState.doTurn(2);
                    moves.flip(2);
                    break;
                default: return null;
            }
        }

    }
}
