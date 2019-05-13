package com.example.runescapedungpuzzlesolver.Models;

import com.example.runescapedungpuzzlesolver.Models.Tile;
import com.example.runescapedungpuzzlesolver.Models.TilesState;

import java.util.BitSet;

public class Solution {
    private final BitSet bitSet;
    private Boolean isValid = null;

    public Solution(BitSet bitSet){
        this.bitSet = bitSet;
    }

    public BitSet getBitSet() {
        return bitSet;
    }

    public Boolean isValid(Tile[][] tiles) {
        if(isValid==null){
            isValid = new TilesState(tiles,bitSet).checkBoard();
        }
        return isValid;
    }
}
